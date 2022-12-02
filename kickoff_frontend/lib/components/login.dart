import 'dart:convert';
import 'dart:core';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/rounded_input.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/rounded_inpu_Username.dart';
import '../../../components/rounded_password_Signup.dart';
import 'package:kickoff_frontend/components/rounded_input_login.dart';
import 'package:kickoff_frontend/components/rounded_password_input.dart';
import 'package:kickoff_frontend/components/rounded_phone_number.dart';
import 'package:kickoff_frontend/components/Sign_up_location.dart';
import 'package:http/http.dart' as http;
class RoundedLogin extends StatelessWidget {
  RoundedLogin({
    Key? key,
    required this.title,
  }) : super(key: key);
  final String title;
  String url = "http://localhost:8080/signup/courtOwner";
  Future save() async{
    print(RoundedInput.EmailSignUp.text);
    var res= await http.post(Uri.parse(url),headers: {"Access-Control-Allow-Origin": "*","Access-Control-Allow-Credentials":"true", "Access-Control-Allow-Headers": "Origin,Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,locale",  "Access-Control-Allow-Methods": "POST, OPTIONS" },body: json.encode(
        {
          "email": RoundedInput.EmailSignUp.text,
          "password": RoundedPasswordSignup.Password.text,
          "username": RoundedInputUsername.username.text,
          "phoneNumber": RoundedPhoneNumber.PhoneNumber.text,
          "location": FindLocation.Locationaddress,
          "xAxis": FindLocation.X_axis,
          "yAxis": FindLocation.Y_axis,
        }));
    print(res.body);
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: () {
          var Email = RoundedInputLogin.EmailLogin.text;
          var Password=RoundedPasswordInput.Password.text;
          print(Email);
          print(Password);
          RoundedInputLogin.EmailLogin.clear();
        Navigator.of(context).push(
            MaterialPageRoute(
                builder: (context) => const KickoffApplication()
            )
        );
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
          title,
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
    child: Text("OK"),
    onPressed: () {
      Navigator.of(context).pop();
    },
  );
  // Create AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text("Warning"),
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
