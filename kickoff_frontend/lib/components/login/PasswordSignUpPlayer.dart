import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordInputSignupPlayer extends StatefulWidget {
  @override
  RoundedPasswordSignupPlayer createState() => RoundedPasswordSignupPlayer();
}

class RoundedPasswordSignupPlayer extends State<PasswordInputSignupPlayer> {
  static TextEditingController password = TextEditingController();
  var obsecureText = true;

  @override
  Widget build(BuildContext context) {
    return InputContainer(
        color: Colors.green,
        child: TextField(
          cursorColor: playerColor,
          controller: password,
          decoration: InputDecoration(
              icon: const Icon(Icons.lock, color: playerColor),
              suffix: GestureDetector(
                onTap: () {
                  setState(() {
                    obsecureText = !obsecureText;
                  });
                },
                child: Icon(
                    obsecureText ? Icons.visibility : Icons.visibility_off),
              ),
              hintText: 'Password',
              border: InputBorder.none),
          obscureText: obsecureText,
        ));
  }
}
