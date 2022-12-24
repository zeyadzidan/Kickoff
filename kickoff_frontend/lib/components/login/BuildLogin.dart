import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';

import '../../httpshandlers/loginrequests.dart';
import 'EmailLogin.dart';
import 'PasswordLogin.dart';

class LoginForm extends StatelessWidget {
  const LoginForm({
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
    return AnimatedOpacity(
      opacity: isLogin ? 1.0 : 0.0,
      duration: animationDuration * 4,
      child: Align(
        alignment: Alignment.center,
        child: SizedBox(
          width: size.width,
          height: defaultLoginSize,
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Text(
                  'Court Owner',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
                ),
                const SizedBox(height: 40),
                const SizedBox(
                    height: 175,
                    width: 175,
                    child: Image(
                        image: AssetImage('assets/images/football court.png'))),
                const SizedBox(height: 40),
                const RoundedInputLogin(
                    icon: Icons.mail,
                    materialColor: courtOwnerColor,
                    hint: 'البريد الالكتروني'),
                PasswordInput(),
                const SizedBox(height: 10),
                LoginButtonCourtOwner(),
                const SizedBox(height: 10),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
