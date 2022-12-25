import 'package:flutter/material.dart';
import '../../httpshandlers/loginrequestsCourtOwner.dart';
import 'Email.dart';
import 'PasswordCourtOwner.dart';

class BuildLoginFormCourtOwner extends StatelessWidget {
  const BuildLoginFormCourtOwner({
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
        child: Container(
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
                SizedBox(height: 40),
                Container(
                    height: 175,
                    width: 175,
                    child: const Image(
                        image: AssetImage('assets/images/football court.png'))),
                SizedBox(height: 40),
                BuildLoginEmail(
                    icon: Icons.mail,
                    color: Colors.cyan,
                    hint: 'البريد الالكتروني'),
                PasswordCourtOwner(),
                SizedBox(height: 10),
                LoginButtonCourtOwner(),
                SizedBox(height: 10),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
