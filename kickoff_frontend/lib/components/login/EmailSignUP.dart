import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInput extends StatelessWidget {
  const RoundedInput({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
  static TextEditingController emailSignUp = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: playerColor,
      child: TextField(
        cursorColor: playerColor,
        controller: emailSignUp,
        decoration: InputDecoration(
            icon: Icon(icon, color: playerColor),
            hintText: hint,
            border: InputBorder.none),
      ),
    );
  }
}
