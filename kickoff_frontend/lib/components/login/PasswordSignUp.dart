import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordInputSignup extends StatefulWidget {
  @override
  RoundedPasswordSignup createState() => RoundedPasswordSignup();
}

class RoundedPasswordSignup extends State<PasswordInputSignup> {
  static TextEditingController Password = TextEditingController();
  var obsecuretext = true;

  @override
  Widget build(BuildContext context) {
    return InputContainer(
        child: TextField(
      cursorColor: primaryColor,
      controller: Password,
      decoration: InputDecoration(
          icon: Icon(Icons.lock, color: primaryColor),
          suffix: GestureDetector(
            onTap: () {
              setState(() {
                obsecuretext = !obsecuretext;
              });
            },
            child: Icon(obsecuretext ? Icons.visibility : Icons.visibility_off),
          ),
          hintText: 'كلمة المرور',
          border: InputBorder.none),
      obscureText: obsecuretext,
    ));
  }
}
