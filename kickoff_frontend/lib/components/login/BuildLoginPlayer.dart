import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/PasswordPlayer.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/httpshandlers/loginrequestsplayer.dart';

import 'EmailLogin.dart';

class LoginFormPlayer extends StatelessWidget {
  const LoginFormPlayer({
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
                  'Player',
                  style: TextStyle(fontWeight: FontWeight.bold, fontSize: 24),
                ),
                const SizedBox(height: 40),
                const SizedBox(
                    height: 175,
                    width: 175,
                    child:
                        Image(image: AssetImage('assets/images/player.png'))),
                const SizedBox(height: 40),
                const RoundedInputLogin(
                    icon: Icons.mail,
                    materialColor: playerColor,
                    hint: 'Email Address'),
                PasswordInputPlayer(),
                const SizedBox(height: 10),
                LoginButtonPlayer(),
                const SizedBox(height: 10),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
