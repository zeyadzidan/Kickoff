import 'dart:convert';
import 'dart:core';

import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/courts/CourtsInSearch.dart';
import 'package:kickoff_frontend/components/SignUp/Email.dart';
import 'package:kickoff_frontend/components/SignUp/PasswordPlayer.dart';
import 'package:kickoff_frontend/components/SignUp/PhoneNumber.dart';
import 'package:kickoff_frontend/components/SignUp/Location.dart';
import 'package:kickoff_frontend/components/SignUp/UserName.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../components/SignUp/PasswordPlayer.dart';
import '../application/screens/BuildComponentsPlayer.dart';

class SignUpButtonPlayer extends StatefulWidget {
  @override
  RoundedButton createState() => RoundedButton();
}

class RoundedButton extends State<SignUpButtonPlayer> {
  String url2 = "http://${ip}:8080/search/courtOwner/distance";
  String url = "http://${ip}:8080/signup/player";

  var resp = "";
  late Map<String, dynamic> profileData;
  // late List<CourtModel> courts;
  Future getCourtsinSearch() async{
    var res = await http.post(Uri.parse(url2),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "keyword":"",
          "xAxis": BuildLocation.X_axis,
          "yAxis": BuildLocation.Y_axis,
        }));
    setState(() {
        print(res.body);
        // FieldValue arrayUnion(List<dynamic> elements) =>
        //     FieldValue._(FieldValueType.arrayUnion, elements);
        // courts= jsonEncode(res.body) as List<CourtModel>  ;
        LoginScreen.courtsSearch=jsonDecode(res.body) as List<dynamic>;
        // print(courts);
    });

  }
  Future save() async {
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": BuildEmailSignUp.EmailSignUp.text.toLowerCase(),
          "password": BuildPasswordSignupPlayer.Password.text,
          "name": UsernameSignUp.username.text,
          "phoneNumber": PhoneNumberSignUp.PhoneNumber.text,
          "location": BuildLocation.Locationaddress,
          "xAxis": BuildLocation.X_axis,
          "yAxis": BuildLocation.Y_axis,
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
        var Email = BuildEmailSignUp.EmailSignUp.text;
        var username = UsernameSignUp.username.text;
        var Password = BuildPasswordSignupPlayer.Password.text;
        var phoneNumber = PhoneNumberSignUp.PhoneNumber.text;
        var Locationaddress = BuildLocation.Locationaddress;
        if (Email.isEmpty) {
          showAlertDialog(context, 'Check your Email');
          BuildEmailSignUp.EmailSignUp.clear();
        } else if (username.isEmpty) {
          showAlertDialog(context, 'Name can not be emptyً');
          UsernameSignUp.username.clear();
        } else if (phoneNumber.isEmpty || phoneNumber.length < 11) {
          showAlertDialog(context, 'Check your phone number');
          PhoneNumberSignUp.PhoneNumber.clear();
        } else if (Locationaddress.toString() == 'null') {
          showAlertDialog(context, 'Check your Location');
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context, 'Check your password');
          BuildPasswordSignupPlayer.Password.clear();
        } else if (username.length < 3) {
          showAlertDialog(context, 'UserName cannot be less than 3');
          UsernameSignUp.username.clear();
        } else {
          var res = await save();
          if (resp == "invalid") {
            showAlertDialog(context, 'Check your Email');
            BuildEmailSignUp.EmailSignUp.clear();
          } else if (resp == "Email exist") {
            showAlertDialog(context, 'Email Already Exist');
            BuildEmailSignUp.EmailSignUp.clear();
          } else {
            KickoffApplication.data = profileData;
            localFile.writeLoginData(BuildEmailSignUp.EmailSignUp.text,
                BuildPasswordSignupPlayer.Password.text,"1");
            BuildEmailSignUp.EmailSignUp.clear();
            UsernameSignUp.username.clear();
            PhoneNumberSignUp.PhoneNumber.clear();
            BuildPasswordSignupPlayer.Password.clear();
            // Navigator.of(context).push(MaterialPageRoute(
            //     builder: (context) =>
            //         KickoffApplication(profileData: profileData)));
            KickoffApplication.player=true;
            await getCourtsinSearch();
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
