import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/input_container.dart';
import 'package:kickoff_frontend/constants.dart';

class PasswordInput extends StatefulWidget
{
  @override
  RoundedPasswordInput createState() => RoundedPasswordInput();
}

class RoundedPasswordInput extends State<PasswordInput> {

  static TextEditingController Password =TextEditingController();
  var obsecuretext = true;
  @override
  Widget build(BuildContext context) {
    return InputContainer(
        child: TextField(
          cursorColor: kPrimaryColor,
          controller: Password,

          decoration: InputDecoration(
              icon: Icon(Icons.lock, color: kPrimaryColor),
              suffix: GestureDetector(onTap: (){
                setState(()
                {
                  obsecuretext = !obsecuretext;
                }
                );
              },
                child: Icon(obsecuretext ?Icons.visibility: Icons.visibility_off ),
              ),
              hintText: 'Password',
              border: InputBorder.none
          ),
          obscureText: obsecuretext,
        ));
  }
}