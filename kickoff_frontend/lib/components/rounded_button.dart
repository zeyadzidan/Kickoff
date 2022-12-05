import 'dart:convert';
import 'dart:core';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/login/rounded_input.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/login/rounded_input_Username.dart';
import '../../../components/login/rounded_password_Signup.dart';
import 'package:kickoff_frontend/components/login/rounded_phone_number.dart';
import 'package:kickoff_frontend/components/login/Sign_up_location.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/localFile.dart';

class SignUpButton extends StatefulWidget {
  @override
  RoundedButton createState() => RoundedButton();
}

class RoundedButton extends State<SignUpButton> {
  String url = "http://${ip}:8080/signup/courtOwner";
  var resp = "";
  late Map<String, dynamic> Profile_data;
  Future save() async {
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": RoundedInput.EmailSignUp.text.toLowerCase(),
          "password": RoundedPasswordSignup.Password.text,
          "username": RoundedInputUsername.username.text,
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
        Profile_data = jsonDecode(res.body);
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
        var Password = RoundedPasswordSignup.Password.text;
        var phoneNumber = RoundedPhoneNumber.PhoneNumber.text;
        var Locationaddress = FindLocation.Locationaddress;
        var x_axis = FindLocation.X_axis;
        var y_axis = FindLocation.Y_axis;
        if (Email.isEmpty) {
          showAlertDialog(context, 'Enter valid Email');
          RoundedInput.EmailSignUp.clear();
        } else if (username.isEmpty) {
          showAlertDialog(context, 'Enter Valid Username');
          RoundedInputUsername.username.clear();
        } else if (phoneNumber.isEmpty || phoneNumber.length < 11) {
          showAlertDialog(context, 'Enter Valid phone Number');
          RoundedPhoneNumber.PhoneNumber.clear();
        } else if (Locationaddress.toString() == 'null') {
          showAlertDialog(context, 'Select location from Map');
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context, 'Enter Valid Password');
          RoundedPasswordSignup.Password.clear();
        } else if (username.length < 3) {
          showAlertDialog(context, 'Enter Valid Username');
          RoundedInputUsername.username.clear();
        } else {
          var res = await save();
          if (resp == "invalid") {
            showAlertDialog(context, 'Enter valid Email');
            RoundedInput.EmailSignUp.clear();
          } else if (resp == "Email exist") {
            showAlertDialog(context, 'Email already Exist');
            RoundedInput.EmailSignUp.clear();
          } else {
            KickoffApplication.data = Profile_data;
            localFile.writeLoginData(RoundedInput.EmailSignUp.text,
                RoundedPasswordSignup.Password.text);
            Navigator.of(context).push(
                MaterialPageRoute(builder: (context) => KickoffApplication()));
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
        padding: EdgeInsets.symmetric(vertical: 20),
        alignment: Alignment.center,
        child: Text(
          'SIGN UP',
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
