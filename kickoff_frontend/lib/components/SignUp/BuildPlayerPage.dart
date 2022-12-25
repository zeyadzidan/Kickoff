import 'package:flutter/material.dart';
import 'package:kickoff_frontend/httpshandlers/SignUpRequestPlayer.dart';

import 'Email.dart';
import 'PasswordPlayer.dart';
import 'PhoneNumber.dart';
import 'Location.dart';
import 'UserName.dart';

class BuildRegisterFormPlayer extends StatelessWidget {
  const BuildRegisterFormPlayer({
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
                  BuildEmailSignUp(icon: Icons.mail,color: Colors.green, hint: 'Email Address'),
                  UsernameSignUp(icon: Icons.face_rounded,color: Colors.green, hint: 'Name'),
                  PasswordSignupPlayer(),
                  PhoneNumberSignUp(icon: Icons.phone,color: Colors.green, hint: 'Phone Number'),
                  Container(
                    height: 450,
                    width: size.width * 0.8,
                    child: BuildLocation(title: 'Choose Location',color: Colors.green),
                  ),
                  SizedBox(height: 30),
                  SignUpButtonPlayer(),
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
