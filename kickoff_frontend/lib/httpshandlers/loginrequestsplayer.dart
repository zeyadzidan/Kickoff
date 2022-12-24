import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/login.dart';
import 'package:kickoff_frontend/components/login/EmailLogin.dart';
import 'package:kickoff_frontend/components/login/PasswordPlayer.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

class LoginButtonPlayer extends StatefulWidget {
  const LoginButtonPlayer({super.key});

  @override
  RoundedLogin createState() => RoundedLogin();
}

class RoundedLogin extends State<LoginButtonPlayer> {
  static const String _url = "http://$ip:8080/login/player";
  static String url2 = "http://$ip:8080/search/courtOwner/distance";

  static Future getCourtsInSearch(xAxis, yAxis) async {
    var res = await http.post(Uri.parse(url2),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "keyword": "",
          "xAxis": xAxis,
          "yAxis": yAxis, //TODO: make this dynamic
        }));
    print(res.body);
    LoginScreen.courtsSearch = jsonDecode(res.body) as List<dynamic>;
    print("lol");
  }

  var resp = 52;
  late Map<String, dynamic> profileData;

  // static List<dynamic> courtsSearch=[];
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
        var email = RoundedInputLogin.emailLogin.text;
        var password = RoundedPasswordInputPlayer.password.text;
        if (email.isEmpty) {
          showAlertDialog(context, 'Empty Email Address');
          RoundedInputLogin.emailLogin.clear();
        } else if (password.length < 6 ||
            password.length > 15 ||
            password.isEmpty) {
          showAlertDialog(context, 'Wrong Password');
          RoundedPasswordInputPlayer.password.clear();
        } else {
          print(RoundedPasswordInputPlayer.password.text);
          await save(RoundedInputLogin.emailLogin.text,
              RoundedPasswordInputPlayer.password.text);
          if (profileData.isEmpty) {
            showAlertDialog(context, 'Check your Information');
            RoundedInputLogin.emailLogin.clear();
          } else if (profileData.length == 4) {
            showAlertDialog(context,
                'Wrong password password less than 4 character not accepted ');
            RoundedPasswordInputPlayer.password.clear();
          } else {
            print(profileData);
            KickoffApplication.data = profileData;
            localFile.writeLoginData(RoundedInputLogin.emailLogin.text,
                RoundedPasswordInputPlayer.password.text, "1");
            KickoffApplication.player = true;
            RoundedInputLogin.emailLogin.clear();
            RoundedPasswordInputPlayer.password.clear();
            await getCourtsInSearch(KickoffApplication.data["xAxis"],
                KickoffApplication.data["yAxis"]);
            Navigator.popAndPushNamed(context, '/kickoff');
          }
        }
      },
      borderRadius: BorderRadius.circular(30),
      child: Container(
        width: size.width * 0.8,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(30),
          color: playerColor,
          boxShadow: const <BoxShadow>[
            BoxShadow(
              color: Colors.black,
              blurRadius: 2,
            ),
          ],
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
