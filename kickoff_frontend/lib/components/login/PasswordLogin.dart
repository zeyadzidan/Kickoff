import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordInput extends StatefulWidget {
  @override
  RoundedPasswordInput createState() => RoundedPasswordInput();
}

class RoundedPasswordInput extends State<PasswordInput> {

  static TextEditingController Password = TextEditingController();
  var obsecuretext = true;

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: Colors.cyan,
        child: TextField(
      cursorColor: playerColor,
      controller: Password,
      decoration: InputDecoration(
          icon: Icon(Icons.lock, color: courtOwnerColor),
          suffix: GestureDetector(
            onTap: () {
              setState(() {
                obsecuretext = !obsecuretext;
              });
            },
            child: Icon(obsecuretext ? Icons.visibility : Icons.visibility_off),
          ),
          hintText: 'كلمه مرور',
          border: InputBorder.none),
      obscureText: obsecuretext,
    ));
  }
}
