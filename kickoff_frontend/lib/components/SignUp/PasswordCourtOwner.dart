import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class Password_SignUp_CourtOwner extends StatefulWidget {
  @override
  BuildPassword_CourtOwnerSignUp createState() => BuildPassword_CourtOwnerSignUp();
}

class BuildPassword_CourtOwnerSignUp extends State<Password_SignUp_CourtOwner> {
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
          hintText: 'كلمة المرور',
          border: InputBorder.none),
      obscureText: obsecuretext,
    ));
  }
}
