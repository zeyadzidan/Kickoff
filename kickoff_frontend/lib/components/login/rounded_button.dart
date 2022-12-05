import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/login/rounded_input_Username.dart';
import 'package:kickoff_frontend/components/login/rounded_input.dart';
import 'package:kickoff_frontend/components/login/rounded_password_Signup.dart';
import 'package:kickoff_frontend/components/login/rounded_phone_number.dart';
import 'package:kickoff_frontend/constants.dart';

import 'Sign_up_location.dart';

class SignUpButton extends StatefulWidget {
  const SignUpButton({super.key});

  @override
  RoundedButton createState() => RoundedButton();
}

class RoundedButton extends State<SignUpButton> {
  String url = "http://192.168.1.2:8080/signup/courtOwner";
  var resp=52;
  late Map<String, dynamic> profileData;
  Future save() async {
    var res= await http.post(Uri.parse(url),headers:{"Content-Type": "application/json"},body: json.encode(
       {
         "email": RoundedInput.EmailSignUp.text,
         "password": RoundedPasswordSignup.Password.text,
         "username": RoundedInputUsername.username.text,
         "phoneNumber": RoundedPhoneNumber.PhoneNumber.text,
         "location": FindLocation.Locationaddress,
         "xAxis": FindLocation.X_axis,
         "yAxis": FindLocation.Y_axis,
       }));
   setState(() => profileData=jsonDecode(res.body));
   print(res.body);
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: () async {
        var email = RoundedInput.EmailSignUp.text;
        var username = RoundedInputUsername.username.text;
        var password = RoundedPasswordSignup.Password.text;
        var phoneNumber = RoundedPhoneNumber.PhoneNumber.text;
        var locationAddress = FindLocation.Locationaddress;
        if (email.isEmpty) {
          showAlertDialog(context, 'Enter valid Email');
          RoundedInput.EmailSignUp.clear();
        } else if (username.isEmpty) {
          showAlertDialog(context, 'Enter Valid Username');
          RoundedInputUsername.username.clear();
        } else if (phoneNumber.isEmpty) {
          showAlertDialog(context, 'Enter Valid phone Number');
          RoundedPhoneNumber.PhoneNumber.clear();
        } else if (locationAddress.toString() == 'null') {
          showAlertDialog(context, 'Select location from Map');
        } else
        if (password.length < 6 || password.length > 15 || password.isEmpty) {
          showAlertDialog(context, 'Enter Valid Password');
          RoundedPasswordSignup.Password.clear();
        } else if (username.length < 3 || username.length > 12) {
          showAlertDialog(context, 'Enter Valid Username');
          RoundedInputUsername.username.clear();
        } else {
          await save();
          if (resp == 0) {
            showAlertDialog(context, 'Enter valid Email');
            RoundedInput.EmailSignUp.clear();
          } else {
            KickoffApplication.data = profileData;
            Navigator.of(context).push(
                MaterialPageRoute(
                    builder: (context) => KickoffApplication()
                )
            );
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
          'SIGN UP',
          style: TextStyle(
              color: Colors.white,
              fontSize: 18
          ),
        ),
      ),
    );
  }
}
showAlertDialog(BuildContext context,text3) {
  // Create button
  Widget okButton = TextButton(
    child: const Text("OK"),
    onPressed: () {
      Navigator.of(context).pop();
    },
  );
  // Create AlertDialog
  AlertDialog alert = AlertDialog(
    title: const Text("Warning"),
    content: Text(text3),
    actions: [
      okButton,
    ],
  );
  showDialog(
    context: context,
    builder: (BuildContext context) {
      return alert;
    },
  );
}
