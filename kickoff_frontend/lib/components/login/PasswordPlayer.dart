import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordInputPlayer extends StatefulWidget {
  @override
  RoundedPasswordInput createState() => RoundedPasswordInput();
}

class RoundedPasswordInput extends State<PasswordInputPlayer> {

  static TextEditingController Password = TextEditingController();
  var obsecuretext = true;

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: Colors.green,
        child: TextField(
          cursorColor: PlayerColor,
          controller: Password,
          decoration: InputDecoration(
              icon: Icon(Icons.lock, color: PlayerColor),
              suffix: GestureDetector(
                onTap: () {
                  setState(() {
                    obsecuretext = !obsecuretext;
                  });
                },
                child: Icon(obsecuretext ? Icons.visibility : Icons.visibility_off),
              ),
              hintText: 'Password',
              border: InputBorder.none),
          obscureText: obsecuretext,
        ));
  }
}
