import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/login/EmailSignUP.dart';
import 'package:kickoff_frontend/components/login/PasswordSignUpPlayer.dart';
import 'package:kickoff_frontend/components/login/PhoneNumberSignUp.dart';
import 'package:kickoff_frontend/components/login/SignUpLocation.dart';
import 'package:kickoff_frontend/components/login/SignUpUserName.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../../../components/login/PasswordSignUpPlayer.dart';

class SignUpButtonPlayer extends StatefulWidget {
  @override
  RoundedButton createState() => RoundedButton();
}

class RoundedButton extends State<SignUpButtonPlayer> {
  String url = "http://${ip}:8080/signup/player";
  var resp = "";
  late Map<String, dynamic> profileData;

  Future save() async {
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": RoundedInput.EmailSignUp.text.toLowerCase(),
          "password": RoundedPasswordSignupPlayer.Password.text,
          "name": RoundedInputUsername.username.text,
          "phoneNumber": RoundedPhoneNumber.PhoneNumber.text,
          "location": FindLocation.Locationaddress,
          "xAxis": FindLocation.X_axis,
          "yAxis": FindLocation.Y_axis,
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
        var Email = RoundedInput.EmailSignUp.text;
        var username = RoundedInputUsername.username.text;
        var Password = RoundedPasswordSignupPlayer.Password.text;
        var phoneNumber = RoundedPhoneNumber.PhoneNumber.text;
        var Locationaddress = FindLocation.Locationaddress;
        if (Email.isEmpty) {
          showAlertDialog(context, 'Check your Email');
          RoundedInput.EmailSignUp.clear();
        } else if (username.isEmpty) {
          showAlertDialog(context, 'Name can not be emptyً');
          RoundedInputUsername.username.clear();
        } else if (phoneNumber.isEmpty || phoneNumber.length < 11) {
          showAlertDialog(context, 'Check your phone number');
          RoundedPhoneNumber.PhoneNumber.clear();
        } else if (Locationaddress.toString() == 'null') {
          showAlertDialog(context, 'Check your Location');
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context, 'Check your password');
          RoundedPasswordSignupPlayer.Password.clear();
        } else if (username.length < 3) {
          showAlertDialog(context, 'UserName cannot be less than 3');
          RoundedInputUsername.username.clear();
        } else {
          var res = await save();
          if (resp == "invalid") {
            showAlertDialog(context, 'Check your Email');
            RoundedInput.EmailSignUp.clear();
          } else if (resp == "Email exist") {
            showAlertDialog(context, 'Email Already Exist');
            RoundedInput.EmailSignUp.clear();
          } else {
            // KickoffApplication.data = profileData;
            // localFile.writeLoginData(RoundedInput.EmailSignUp.text,
            //     RoundedPasswordSignupPlayer.Password.text);
            // Navigator.of(context).push(MaterialPageRoute(
            //     builder: (context) =>
            //         KickoffApplication(profileData: profileData)));
            KickoffApplication.Player=true;
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
        child: Text(
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
        title: Text("Alert"),
        content: Text(text3),
        actions: [
          TextButton(
            child: Text("OK"),
            onPressed: () {
              Navigator.of(ctx).pop();
            },
          ),
        ],
      ));
}
