import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordInput extends StatefulWidget {
  const PasswordInput({super.key});

  @override
  RoundedPasswordInput createState() => RoundedPasswordInput();
}

class RoundedPasswordInput extends State<PasswordInput> {
  static TextEditingController password = TextEditingController();
  var obsecureText = true;

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: courtOwnerColor,
        child: TextField(
      cursorColor: playerColor,
      controller: password,
      decoration: InputDecoration(
          icon: const Icon(Icons.lock, color: courtOwnerColor),
          suffix: GestureDetector(
            onTap: () {
              setState(() {
                obsecureText = !obsecureText;
              });
            },
            child: Icon(obsecureText ? Icons.visibility : Icons.visibility_off),
          ),
          hintText: 'كلمة المرور',
          border: InputBorder.none),
      obscureText: obsecureText,
    ));
  }
}
