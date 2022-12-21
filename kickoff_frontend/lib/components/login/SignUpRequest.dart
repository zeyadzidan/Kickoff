import 'dart:convert';
import 'dart:core';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/login/EmailSignUP.dart';
import 'package:kickoff_frontend/components/login/PhoneNumberSignUp.dart';
import 'package:kickoff_frontend/components/login/SignUpLocation.dart';
import 'package:kickoff_frontend/components/login/SignUpUserName.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../../../components/login/PasswordSignUp.dart';

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
          "email": RoundedInput.EmailSignUp.text.toLowerCase(),
          "password": RoundedPasswordSignup.Password.text,
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
        var Password = RoundedPasswordSignup.Password.text;
        var phoneNumber = RoundedPhoneNumber.PhoneNumber.text;
        var Locationaddress = FindLocation.Locationaddress;
        if (Email.isEmpty) {
          showAlertDialog(context, 'تأكد من بيانات حسابك');
          RoundedInput.EmailSignUp.clear();
        } else if (username.isEmpty) {
          showAlertDialog(context, 'لا يمكنك ترك هذا الحقل فارغاً');
          RoundedInputUsername.username.clear();
        } else if (phoneNumber.isEmpty || phoneNumber.length < 11) {
          showAlertDialog(context, 'تأكد من إدخال رقم هاتف صحيح');
          RoundedPhoneNumber.PhoneNumber.clear();
        } else if (Locationaddress.toString() == 'null') {
          showAlertDialog(context, 'تأكد من اختيارك لموقع ملعبك');
        } else if (Password.length < 6 ||
            Password.length > 15 ||
            Password.isEmpty) {
          showAlertDialog(context, 'تأكد من إدخال كلمة مرور صالحة');
          RoundedPasswordSignup.Password.clear();
        } else if (username.length < 3) {
          showAlertDialog(context, 'لا يمكن أن يقل الاسم عن 3 أحرف');
          RoundedInputUsername.username.clear();
        } else {
          var res = await save();
          if (resp == "invalid") {
            showAlertDialog(context, 'تأكد من بيانات حسابك');
            RoundedInput.EmailSignUp.clear();
          } else if (resp == "Email exist") {
            showAlertDialog(context, 'الحساب موجود بالفعل');
            RoundedInput.EmailSignUp.clear();
          } else {
            KickoffApplication.data = profileData;
            KickoffApplication.ownerId=profileData['id'];
            localFile.writeLoginData(RoundedInput.EmailSignUp.text,
                RoundedPasswordSignup.Password.text,"0");
            RoundedInputUsername.username.clear();
            RoundedInput.EmailSignUp.clear();
            RoundedPasswordSignup.Password.clear();
            RoundedPhoneNumber.PhoneNumber.clear();
            KickoffApplication.Player=false;
            Navigator.popAndPushNamed(context, '/kickoff');
          }
        }
      },
      borderRadius: BorderRadius.circular(30),
      child: Container(
        width: size.width * 0.8,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(30),
          color: courtOwnerColor,
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
