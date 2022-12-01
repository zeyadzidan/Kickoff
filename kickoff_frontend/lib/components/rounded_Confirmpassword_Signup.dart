import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/input_container.dart';
import 'package:kickoff_frontend/constants.dart';

class ConfirmPasswordInput extends StatefulWidget
{
  @override
  ConfirmPasswordSignup createState() => ConfirmPasswordSignup();
}

class ConfirmPasswordSignup extends State<ConfirmPasswordInput> {

  static TextEditingController ConfirmPassword =TextEditingController();
  var obsecuretext = true;
  @override
  Widget build(BuildContext context) {
    return InputContainer(
        child: TextField(
          cursorColor: kPrimaryColor,
          controller: ConfirmPassword,
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
              hintText: 'Confirm Password',
              border: InputBorder.none
          ),
          obscureText: obsecuretext,
        ));
  }
}