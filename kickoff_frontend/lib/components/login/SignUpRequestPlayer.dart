import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/login/EmailSignUP.dart';
import 'package:kickoff_frontend/components/login/PasswordPlayer.dart';
import 'package:kickoff_frontend/components/login/PasswordSignUpPlayer.dart';
import 'package:kickoff_frontend/components/login/PhoneNumberSignUp.dart';
import 'package:kickoff_frontend/components/login/SignUpLocation.dart';
import 'package:kickoff_frontend/components/login/SignUpUserName.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../../application/screens/login.dart';

class SignUpButtonPlayer extends StatefulWidget {
  const SignUpButtonPlayer({super.key});

  @override
  RoundedButton createState() => RoundedButton();
}

class RoundedButton extends State<SignUpButtonPlayer> {
  String url2 = "http://$ip:8080/search/courtOwner/distance";
  String url = "http://$ip:8080/signup/player";

  var resp = "";
  late Map<String, dynamic> profileData;

  // late List<CourtModel> courts;
  Future getCourtsInSearch() async {
    var res = await http.post(Uri.parse(url2),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "keyword": "",
          "xAxis": FindLocation.xAxis,
          "yAxis": FindLocation.yAxis,
        }));
    setState(() {
      print(res.body);
      // FieldValue arrayUnion(List<dynamic> elements) =>
      //     FieldValue._(FieldValueType.arrayUnion, elements);
      // courts= jsonEncode(res.body) as List<CourtModel>  ;
      LoginScreen.courtsSearch = jsonDecode(res.body) as List<dynamic>;
      // print(courts);
    });
  }

  Future save() async {
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": RoundedInput.emailSignUp.text.toLowerCase(),
          "password": RoundedPasswordSignupPlayer.password.text,
          "name": RoundedInputUsername.username,
          "phoneNumber": RoundedPhoneNumber.phoneNumber.text,
          "location": FindLocation.locationAddress,
          "xAxis": FindLocation.xAxis,
          "yAxis": FindLocation.yAxis,
        }));
    setState(() {
      if (res.body == "invalid") {
        resp = "invalid";
      } else if (res.body == "Email exist") {
        resp = "Email exist";
      } else {
        resp = "";
        print(res.body);
        profileData = jsonDecode(res.body);
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: () async {
        var email = RoundedInput.emailSignUp.text;
        var username = RoundedInputUsername.username.text;
        var password = RoundedPasswordInputPlayer.password.text;
        var phoneNumber = RoundedPhoneNumber.phoneNumber.text;
        var locationAddress = FindLocation.locationAddress;
        if (email.isEmpty) {
          showAlertDialog(context, 'Check your Email');
          RoundedInput.emailSignUp.clear();
        } else if (username.isEmpty) {
          showAlertDialog(context, 'Name can not be emptyً');
          RoundedInputUsername.username.clear();
        } else if (phoneNumber.isEmpty || phoneNumber.length < 11) {
          showAlertDialog(context, 'Check your phone number');
          RoundedPhoneNumber.phoneNumber.clear();
        } else if (locationAddress.toString() == 'null') {
          showAlertDialog(context, 'Check your Location');
        } else if (password.length < 6 ||
            password.length > 15 ||
            password.isEmpty) {
          showAlertDialog(context, 'Check your password');
          RoundedPasswordSignupPlayer.password.clear();
        } else if (username.length < 3) {
          showAlertDialog(context, 'UserName cannot be less than 3');
          RoundedInputUsername.username.clear();
        } else {
          var res = await save();
          if (resp == "invalid") {
            showAlertDialog(context, 'Check your Email');
            RoundedInput.emailSignUp.clear();
          } else if (resp == "Email exist") {
            showAlertDialog(context, 'Email Already Exist');
            RoundedInput.emailSignUp.clear();
          } else {
            KickoffApplication.data = profileData;
            localFile.writeLoginData(RoundedInput.emailSignUp.text,
                RoundedPasswordSignupPlayer.password.text, "1");
            RoundedInput.emailSignUp.clear();
            RoundedInputUsername.username.clear();
            RoundedPhoneNumber.phoneNumber.clear();
            RoundedPasswordSignupPlayer.password.clear();
            // Navigator.of(context).push(MaterialPageRoute(
            //     builder: (context) =>
            //         KickoffApplication(profileData: profileData)));
            KickoffApplication.player = true;
            await getCourtsInSearch();
            Navigator.popAndPushNamed(context, '/kickoff');
            print(resp);
          }
        }
      },
      borderRadius: BorderRadius.circular(30),
      child: Container(
        width: size.width * 0.8,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(30),
          color: playerColor,
        ),
        padding: EdgeInsets.symmetric(vertical: 20),
        alignment: Alignment.center,
        child: const Text(
          'SignUp',
          style: TextStyle(color: Colors.white, fontSize: 18),
        ),
      ),
    );
  }
}

showAlertDialog(BuildContext context, text3) {
  return showDialog(
      context: context,
      builder: (ctx) => AlertDialog(
            title: const Text("Alert"),
            content: Text(text3),
            actions: [
              TextButton(
                child: const Text("OK"),
                onPressed: () {
                  Navigator.of(ctx).pop();
                },
              ),
            ],
          ));
}
