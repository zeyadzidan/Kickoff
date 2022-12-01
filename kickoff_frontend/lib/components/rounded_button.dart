import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/rounded_input.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/rounded_inpu_Username.dart';
import '../../../components/rounded_Confirmpassword_Signup.dart';
import '../../../components/rounded_password_Signup.dart';
import 'package:kickoff_frontend/components/rounded_input_login.dart';
import 'package:kickoff_frontend/components/rounded_password_input.dart';
import 'package:kickoff_frontend/components/rounded_phone_number.dart';
import 'package:kickoff_frontend/components/Sign_up_location.dart';
class RoundedButton extends StatelessWidget {
  const RoundedButton({
    Key? key,
    required this.title,
  }) : super(key: key);

  final String title;

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return InkWell(
      onTap: () {
        showAlertDialog(context);
        if(title=='SIGN UP')
          {
            var Email = RoundedInput.EmailSignUp.text;
            var username = RoundedInputUsername.username.text;
            var Password = RoundedPasswordSignup.Password.text;
            var confirmPassword = ConfirmPasswordSignup.ConfirmPassword.text;
            var phoneNumber =RoundedPhoneNumber.PhoneNumber.text;
            var Locationaddress = FindLocation.Locationaddress;
            var x_axis = FindLocation.X_axis.toString();
            var y_axis = FindLocation.Y_axis.toString();
            print(Email);
            print(username);
            print(Password);
            print(confirmPassword);
            print(phoneNumber);
            print(Locationaddress);
            print(x_axis);
            print(y_axis);

          }
        else
          {
            var Email = RoundedInputLogin.EmailLogin.text;
            var Password=RoundedPasswordInput.Password.text;
            print(Email);
            print(Password);
            RoundedInputLogin.EmailLogin.clear();
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
showAlertDialog(BuildContext context) {
  // Create button
  Widget okButton = TextButton(
    child: Text("OK"),
    onPressed: () {
      Navigator.of(context).pop();
    },
  );

  // Create AlertDialog
  AlertDialog alert = AlertDialog(
    title: Text("Simple Alert"),
    content: Text("Enter Valid Input."),
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
