import 'dart:convert';
import 'dart:core';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/rounded_input.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/rounded_inpu_Username.dart';
import '../../../components/rounded_password_Signup.dart';
import 'package:kickoff_frontend/components/rounded_phone_number.dart';
import 'package:kickoff_frontend/components/Sign_up_location.dart';
import 'package:http/http.dart' as http;
class SignUpButton extends StatefulWidget
{
  @override
  RoundedButton createState() => RoundedButton();
}
class RoundedButton extends State<SignUpButton> {
 // String url = "http://localhost:8080/signup/courtOwner";
  String url = "http://192.168.1.2:8080/signup/courtOwner";
  var resp=52;
  late Map<String, dynamic> Profile_data;
  Future save() async{
    print(RoundedInput.EmailSignUp.text);
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
   setState(() {
   //  resp=json.decode(res.body);
     Profile_data=jsonDecode(res.body);
   });
   print(res.body);
  }
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InkWell(
      onTap: () async {
            var Email = RoundedInput.EmailSignUp.text;
            var username = RoundedInputUsername.username.text;
            var Password = RoundedPasswordSignup.Password.text;
            var phoneNumber =RoundedPhoneNumber.PhoneNumber.text;
            var Locationaddress = FindLocation.Locationaddress;
            var x_axis = FindLocation.X_axis;
            var y_axis = FindLocation.Y_axis;
            print(Email);
            print(username);
            print(Password);
            print(phoneNumber);
            print(Locationaddress);
            print(x_axis);
            print(y_axis);
            if(Email.isEmpty )
              {
                showAlertDialog(context,'Enter valid Email');
                RoundedInput.EmailSignUp.clear();
              }
            else if(username.isEmpty)
              {
                showAlertDialog(context,'Enter Valid Username');
                RoundedInputUsername.username.clear();
              }
              else if(phoneNumber.isEmpty)
              {
                showAlertDialog(context,'Enter Valid phone Number');
                RoundedPhoneNumber.PhoneNumber.clear();
              }
              else if(Locationaddress.toString()=='null')
                {
                  showAlertDialog(context,'Select location from Map');
                }
              else if( Password.length<6 || Password.length>15 || Password.isEmpty)
                {
                  showAlertDialog(context,'Enter Valid Password');
                  RoundedPasswordSignup.Password.clear();
                }
              else if(username.length<3 || username.length> 12)
                {
                  showAlertDialog(context,'Enter Valid Username');
                  RoundedInputUsername.username.clear();
                }
            else
              {
                var res= await save();
                if(resp==0)
                  {
                    showAlertDialog(context,'Enter valid Email');
                    RoundedInput.EmailSignUp.clear();
                  }
                else
                  {
                    //Map<String,dynamic> lol=["mento",0] as Map<String, dynamic>;
                    KickoffApplication.profileData=Profile_data;
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
        padding: EdgeInsets.symmetric(vertical: 20),
        alignment: Alignment.center,
        child: Text(
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
