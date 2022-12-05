import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/login/EmailLogin.dart';
import 'package:kickoff_frontend/components/login/PasswordLogin.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

class LoginButton extends StatefulWidget {
  const LoginButton({super.key});

  @override
  RoundedLogin createState() => RoundedLogin();
}

class RoundedLogin extends State<LoginButton> {
  static String url = "http://$ip:8080/login/courtOwner";
  var resp = 52;
  late Map<String, dynamic> profileData;

  Future save(email, pass) async {
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": email.toLowerCase(),
          "password": pass,
        }));

    setState(() => profileData = json.decode(res.body));
  }

  static Future save2(email, pass) async {
    var res = await http.post(Uri.parse(url),
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
        var Password = RoundedPasswordInput.Password.text;
        if (Email.isEmpty) {
          showAlertDialog(context, 'Enter valid Email');
          RoundedInputLogin.EmailLogin.clear();
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context, 'Enter Valid Password');
          RoundedPasswordInput.Password.clear();
        } else {
          await save(RoundedInputLogin.EmailLogin.text,
              RoundedPasswordInput.Password.text);

          if (profileData.isEmpty) {
            showAlertDialog(context, 'Enter valid Email');
            RoundedInputLogin.EmailLogin.clear();
          } else if (profileData.length == 4) {
            showAlertDialog(context, 'Enter valid Password');
            RoundedPasswordInput.Password.clear();
          } else {
            print(profileData);
            KickoffApplication.data = profileData;
            KickoffApplication.OWNER_ID = profileData["id"].toString();
            localFile.writeLoginData(RoundedInputLogin.EmailLogin.text,
                RoundedPasswordInput.Password.text);
            Navigator.of(context).push(MaterialPageRoute(
                builder: (context) =>
                    KickoffApplication(profileData: profileData)));
          }
        }
      },
      borderRadius: BorderRadius.circular(30),
      child: Container(
        width: size.width * 0.8,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(30),
          color: kPrimaryColor,
        ),
        padding: const EdgeInsets.symmetric(vertical: 20),
        alignment: Alignment.center,
        child: const Text(
          'LOGIN',
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
            title: Text("Warning"),
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
