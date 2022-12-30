import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/components/SignUp/Email.dart';
import 'package:kickoff_frontend/components/SignUp/PhoneNumber.dart';
import 'package:kickoff_frontend/components/SignUp/Location.dart';
import 'package:kickoff_frontend/components/SignUp/UserName.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../components/SignUp/PasswordCourtOwner.dart';
import '../application/screens/profile.dart';

class SignUpButton extends StatefulWidget {
  @override
  RoundedButton createState() => RoundedButton();
}

class RoundedButton extends State<SignUpButton> {
  String url = "http://${ip}:8080/signup/courtOwner";
  var resp = "";
  late Map<String, dynamic> profileData;

  Future save() async {
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "email": BuildEmailSignUp.EmailSignUp.text.toLowerCase(),
          "password": BuildPassword_CourtOwnerSignUp.Password.text,
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
        var Password = BuildPassword_CourtOwnerSignUp.Password.text;
        var phoneNumber = PhoneNumberSignUp.PhoneNumber.text;
        var Locationaddress = BuildLocation.Locationaddress;
        if (Email.isEmpty) {
          showAlertDialog(context, 'تأكد من بيانات حسابك');
          BuildEmailSignUp.EmailSignUp.clear();
        } else if (username.isEmpty) {
          showAlertDialog(context, 'لا يمكنك ترك هذا الحقل فارغاً');
          UsernameSignUp.username.clear();
        } else if (phoneNumber.isEmpty || phoneNumber.length < 11) {
          showAlertDialog(context, 'تأكد من إدخال رقم هاتف صحيح');
          PhoneNumberSignUp.PhoneNumber.clear();
        } else if (Locationaddress.toString() == 'null') {
          showAlertDialog(context, 'تأكد من اختيارك لموقع ملعبك');
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context, 'تأكد من إدخال كلمة مرور صالحة');
          BuildPassword_CourtOwnerSignUp.Password.clear();
        } else if (username.length < 3) {
          showAlertDialog(context, 'لا يمكن أن يقل الاسم عن 3 أحرف');
          UsernameSignUp.username.clear();
        } else {
          var res = await save();
          if (resp == "invalid") {
            showAlertDialog(context, 'تأكد من بيانات حسابك');
            BuildEmailSignUp.EmailSignUp.clear();
          } else if (resp == "Email exist") {
            showAlertDialog(context, 'الحساب موجود بالفعل');
            BuildEmailSignUp.EmailSignUp.clear();
          } else {
            KickoffApplication.data = profileData;
            KickoffApplication.ownerId="${profileData['id']}";
            ProfileBaseScreen.courts.clear();
            localFile.writeLoginData(BuildEmailSignUp.EmailSignUp.text,
                BuildPassword_CourtOwnerSignUp.Password.text,"0");
            UsernameSignUp.username.clear();
            BuildEmailSignUp.EmailSignUp.clear();
            BuildPassword_CourtOwnerSignUp.Password.clear();
            PhoneNumberSignUp.PhoneNumber.clear();
            KickoffApplication.player=false;
            ProfileBaseScreen.courts.clear();
            Navigator.popAndPushNamed(context, '/kickoff');
          }
        }
      },
      borderRadius: BorderRadius.circular(30),
      child: Container(
        width: size.width * 0.8,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(30),
          color: mainSwatch,
        ),
        padding: EdgeInsets.symmetric(vertical: 20),
        alignment: Alignment.center,
        child: Text(
          'تسجيل حساب جديد',
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
            title: Text("تحذير!"),
            content: Text(text3),
            actions: [
              TextButton(
                child: Text("حسناً"),
                onPressed: () {
                  Navigator.of(ctx).pop();
                },
              ),
            ],
          ));
}
