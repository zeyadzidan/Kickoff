import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/components/login/EmailLogin.dart';
import 'package:kickoff_frontend/components/login/PasswordPlayer.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../application/screens/reservations.dart';
import 'courtsrequests.dart';

class LoginButtonPlayer extends StatefulWidget {
  const LoginButtonPlayer({super.key});

  @override
  RoundedLogin createState() => RoundedLogin();
}

class RoundedLogin extends State<LoginButtonPlayer> {
  static final String _url =
      "http://${KickoffApplication.userIP}:8080/login/player";
  var resp = 52;
  late Map<String, dynamic> profileData;

  Future save(email, pass) async {
    var res = await http.post(Uri.parse(_url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": email.toLowerCase(),
          "password": pass,
        }));
     // print(res.body);
    setState(() => profileData = json.decode(res.body));
  }

  static Future save2(email, pass) async {
    var res = await http.post(Uri.parse(_url),
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Credentials": "true",
          "Access-Control-Allow-Headers":
          "Origin,Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,locale",
          "Access-Control-Allow-Methods": "POST, OPTIONS"
        },
        body: json.encode({
          "email": email.toLowerCase(),
          "password": pass,
        }));
    return json.decode(res.body);
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: () async {
        var Email = RoundedInputLogin.EmailLogin.text;
        var Password = RoundedPasswordInputPlayer.Password.text;
        if (Email.isEmpty) {
          showAlertDialog(context, 'Empty Email Address');
          RoundedInputLogin.EmailLogin.clear();
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context,
              'Wrong Password');
          RoundedPasswordInputPlayer.Password.clear();
        } else {
          await save(RoundedInputLogin.EmailLogin.text,
              RoundedPasswordInputPlayer.Password.text);
          if (profileData.isEmpty) {
            showAlertDialog(context, 'Check your Information');
            RoundedInputLogin.EmailLogin.clear();
          } else if (profileData.length == 4) {
            showAlertDialog(
                context, 'Wrong password password less than 4 character not accepted ');
            RoundedPasswordInputPlayer.Password.clear();
          } else {
            print(profileData);
            KickoffApplication.data = profileData;
            KickoffApplication.ownerId = profileData["id"].toString();
            // ProfileBaseScreen.courts =
            // await CourtsHTTPsHandler.getCourts(KickoffApplication.ownerId);
            // await ReservationsHome.buildTickets();
            // localFile.writeLoginData(RoundedInputLogin.EmailLogin.text,
            //     RoundedPasswordInputPlayer.Password.text);
            KickoffApplication.Player=true;
            Navigator.popAndPushNamed(context, '/kickoff');
            // Navigator.pushNamed(context, '/playersearch');
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
        padding: const EdgeInsets.symmetric(vertical: 20),
        alignment: Alignment.center,
        child: const Text(
          'Login',
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
        title: const Text("Alert!"),
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
