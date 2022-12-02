import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/input_container.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInput extends StatelessWidget {
  const RoundedInput({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
  static TextEditingController EmailSignUp =TextEditingController();
  @override
  Widget build(BuildContext context) {
    return InputContainer(
      child: TextField(
        cursorColor: kPrimaryColor,
          controller: EmailSignUp,
        decoration: InputDecoration(
            icon: Icon(icon, color: kPrimaryColor),
            hintText: hint,
            border: InputBorder.none),
      ),
    );
  }
}
