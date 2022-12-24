import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordInputPlayer extends StatefulWidget {
  @override
  RoundedPasswordInputPlayer createState() => RoundedPasswordInputPlayer();
}

class RoundedPasswordInputPlayer extends State<PasswordInputPlayer> {

  static TextEditingController password = TextEditingController();
  var obsecureText = true;

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: playerColor,
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
                child: Icon(obsecureText ? Icons.visibility : Icons.visibility_off),
              ),
              hintText: 'Password',
              border: InputBorder.none),
          obscureText: obsecureText,
        ));
  }
}
