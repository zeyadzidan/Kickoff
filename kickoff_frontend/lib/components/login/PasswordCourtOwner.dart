import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordCourtOwner extends StatefulWidget {
  @override
  Build_Password_CourtOwner createState() => Build_Password_CourtOwner();
}

class Build_Password_CourtOwner extends State<PasswordCourtOwner> {

  static TextEditingController Password = TextEditingController();
  var obsecuretext = true;

  @override
  Widget build(BuildContext context) {
    return BuildContainer(
      color: Colors.cyan,
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
          hintText: 'كلمه مرور',
          border: InputBorder.none),
      obscureText: obsecuretext,
    ));
  }
}
