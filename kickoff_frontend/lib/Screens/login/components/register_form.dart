import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/Sign_up_location.dart';
import 'package:kickoff_frontend/components/rounded_button.dart';
import 'package:kickoff_frontend/components/rounded_input.dart';
import 'package:kickoff_frontend/components/rounded_password_input.dart';
import 'package:kickoff_frontend/components/rounded_phone_number.dart';
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
                    style: TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 24
                    ),
                  ),

                  SizedBox(height: 40),

                  Container(height:175 ,width:175,child: Image(image: AssetImage('assets/images/pic4.PNG'))),

                  SizedBox(height: 40),

                  RoundedInput(icon: Icons.mail, hint: 'Email'),

                  RoundedInput(icon: Icons.face_rounded, hint: 'Username'),

                  RoundedPasswordInput(hint: 'Password'),
                  RoundedPhoneNumber(icon: Icons.phone, hint: 'Phone Number'),
                 // FindLocation(title: 'Location',),

                  Container(

                      height:450 ,
                    width:size.width * 0.8,
                    child:
                      FindLocation(title: 'Location'),

                  ),
                  SizedBox(height: 30),
                  RoundedButton(title: 'SIGN UP'),

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