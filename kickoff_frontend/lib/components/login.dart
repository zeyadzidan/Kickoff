import 'dart:convert';
import 'dart:core';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/rounded_button.dart';
import 'package:kickoff_frontend/components/rounded_input.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/rounded_input_login.dart';
import 'package:kickoff_frontend/components/rounded_password_input.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/localFile.dart';

class LogiButton extends StatefulWidget {
  @override
  RoundedLogin createState() => RoundedLogin();
}

class RoundedLogin extends State<LogiButton> {
  String url = "http://192.168.1.7:8080/login/courtOwner";
  static String url2 = "http://192.168.1.7:8080/login/courtOwner";
  var resp = 52;
  late Map<String, dynamic> Profile_data;
  Future save(email, pass) async {
    print(RoundedInput.EmailSignUp.text);
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": email,
          "password": pass,
        }));
    setState(() {
      Profile_data = json.decode(res.body);
    });
    print(res.body);
  }

  static Future save2(email, pass) async {
    var res = await http.post(Uri.parse(url2),
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Credentials": "true",
          "Access-Control-Allow-Headers":
              "Origin,Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,locale",
          "Access-Control-Allow-Methods": "POST, OPTIONS"
        },
        body: json.encode({
          "email": email,
          "password": pass,
        }));
    print(res.body);
    return json.decode(res.body);
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: () async {
        var Email = RoundedInputLogin.EmailLogin.text;
        var Password = RoundedPasswordInput.Password.text;
        print(Email);
        print(Password);
        if (Email.isEmpty) {
          showAlertDialog(context, 'Enter valid Email');
          RoundedInputLogin.EmailLogin.clear();
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context, 'Enter Valid Password');
          RoundedPasswordInput.Password.clear();
        } else {
          var res = await save(RoundedInputLogin.EmailLogin.text,
              RoundedPasswordInput.Password.text);
          print(Profile_data.length);
          if (Profile_data.length == 0) {
            showAlertDialog(context, 'Enter valid Email');
            RoundedInputLogin.EmailLogin.clear();
          } else if (Profile_data.length == 4) {
            showAlertDialog(context, 'Enter valid Password');
            RoundedPasswordInput.Password.clear();
          } else {
            print(Profile_data);
            // print("\t"+Profile_data);
            KickoffApplication.profileData = Profile_data;
            localFile.writeLoginData(RoundedInputLogin.EmailLogin.text,
                RoundedPasswordInput.Password.text);
            Navigator.of(context).push(MaterialPageRoute(
                builder: (context) => KickoffApplication(Data: Profile_data)));
          }
          /*

              if(resp==0)
              {
                showAlertDialog(context,'Enter valid Data');
                RoundedInputLogin.EmailLogin.clear();
                RoundedPasswordInput.Password.clear();
              }
              else
              {


              }
               */
        }
      },
      borderRadius: BorderRadius.circular(30),
      child: Container(
        width: size.width * 0.8,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(30),
          color: kPrimaryColor,
        ),
        padding: EdgeInsets.symmetric(vertical: 20),
        alignment: Alignment.center,
        child: Text(
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
