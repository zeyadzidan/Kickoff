import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInput extends StatelessWidget {
  const RoundedInput({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
  static TextEditingController EmailSignUp = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      child: TextField(
        cursorColor: primaryColor,
        controller: EmailSignUp,
        decoration: InputDecoration(
            icon: Icon(icon, color: primaryColor),
            hintText: hint,
            border: InputBorder.none),
      ),
    );
  }
}
