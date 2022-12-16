import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInputLogin extends StatelessWidget {
  const RoundedInputLogin({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
  static TextEditingController EmailLogin = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      child: TextField(
        cursorColor: primaryColor,
        controller: EmailLogin,
        decoration: InputDecoration(
            icon: Icon(icon, color: primaryColor),
            hintText: hint,
            border: InputBorder.none),
      ),
    );
  }
}
