import 'dart:convert';
import 'dart:core';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/rounded_input.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/rounded_input_login.dart';
import 'package:kickoff_frontend/components/rounded_password_input.dart';
import 'package:http/http.dart' as http;
class LogiButton extends StatefulWidget
{
  @override
  RoundedLogin createState() => RoundedLogin();
}
class RoundedLogin extends State<LogiButton> {
  String url = "http://localhost:8080/login/courtOwner";
  var resp=52;
  late Map<String, dynamic> Profile_data;
  Future save() async{
    print(RoundedInput.EmailSignUp.text);
    var res= await http.post(Uri.parse(url),headers: {"Access-Control-Allow-Origin": "*","Access-Control-Allow-Credentials":"true", "Access-Control-Allow-Headers": "Origin,Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token,locale",  "Access-Control-Allow-Methods": "POST, OPTIONS" },body: json.encode(
        {
          "email": RoundedInputLogin.EmailLogin.text,
          "password": RoundedPasswordInput.Password.text,
        })
    );
    setState(() {
       Profile_data=json.decode(res.body);
    });
    print(res.body);
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: () async {
          var Email = RoundedInputLogin.EmailLogin.text;
          var Password=RoundedPasswordInput.Password.text;
          print(Email);
          print(Password);
          if(Email.isEmpty )
          {
            showAlertDialog(context,'Enter valid Email');
            RoundedInputLogin.EmailLogin.clear();
          }
          else if( Password.length<6 || Password.length>15 || Password.isEmpty)
          {
            showAlertDialog(context,'Enter Valid Password');
            RoundedPasswordInput.Password.clear();
          }
          else
            {

              var res= await save();
              print(Profile_data.length);
              if(Profile_data.length==0)
                {
                  showAlertDialog(context,'Enter valid Email');
                  RoundedInputLogin.EmailLogin.clear();

                }
              else if(Profile_data.length==4)
                {
                  showAlertDialog(context,'Enter valid Password');
                  RoundedPasswordInput.Password.clear();
                }
              else
                {
                  print(Profile_data);
                  KickoffApplication.profileData=Profile_data;
                  Navigator.of(context).push(
                      MaterialPageRoute(
                          builder: (context) => KickoffApplication()

                      )
                  );
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
