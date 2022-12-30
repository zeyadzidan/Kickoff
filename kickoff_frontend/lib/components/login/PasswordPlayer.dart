import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordLoginPlayer extends StatefulWidget {
  @override
  Build_Password_Player createState() => Build_Password_Player();
}

class Build_Password_Player extends State<PasswordLoginPlayer> {

  static TextEditingController Password = TextEditingController();
  var obsecuretext = true;

  @override
  Widget build(BuildContext context) {

    return BuildContainer(
      color: Colors.green,
        child: TextField(
          cursorColor: mainSwatch,
          controller: Password,
          decoration: InputDecoration(
              icon: Icon(Icons.lock, color: mainSwatch),
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
