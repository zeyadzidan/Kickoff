import 'package:flutter/material.dart';

import 'EmailSignUP.dart';
import 'PasswordSignUp.dart';
import 'PhoneNumberSignUp.dart';
import 'SignUpLocation.dart';
import 'SignUpRequest.dart';
import 'SignUpUserName.dart';

class RegisterForm extends StatelessWidget {
  const RegisterForm({
    Key? key,
    required this.isLogin,
    required this.animationDuration,
    required this.size,
    required this.defaultLoginSize,
  }) : super(key: key);
  final bool isLogin;
  final Duration animationDuration;
  final Size size;
  final double defaultLoginSize;

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return AnimatedOpacity(
      opacity: isLogin ? 0.0 : 1.0,
      duration: animationDuration * 5,
      child: Visibility(
        visible: !isLogin,
        child: Align(
          alignment: Alignment.bottomCenter,
          child: Container(
            width: size.width,
            height: defaultLoginSize,
            child: SingleChildScrollView(
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.center,
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  SizedBox(height: 10),
                  Text(
                    'Kickoff',
                    style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
                  ),
                  SizedBox(height: 40),
                  Container(
                      height: 175,
                      width: 175,
                      child:
                          Image(image: AssetImage('assets/images/pic4.PNG'))),
                  SizedBox(height: 40),
                  RoundedInput(icon: Icons.mail, hint: 'Email'),
                  RoundedInputUsername(icon: Icons.face_rounded, hint: 'Name'),
                  PasswordInputSignup(),
                  RoundedPhoneNumber(icon: Icons.phone, hint: 'Phone Number'),
                  Container(
                    height: 450,
                    width: size.width * 0.8,
                    child: FindLocation(title: 'Location'),
                  ),
                  SizedBox(height: 30),
                  SignUpButton(),
                  SizedBox(height: 10),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
