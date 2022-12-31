import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordSignupPlayer extends StatefulWidget {
  @override
  BuildPasswordSignupPlayer createState() => BuildPasswordSignupPlayer();
}

class BuildPasswordSignupPlayer extends State<PasswordSignupPlayer> {
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
