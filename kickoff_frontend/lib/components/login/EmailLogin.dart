import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';

class RoundedInputLogin extends StatelessWidget {
  const RoundedInputLogin({Key? key, required this.icon, required this.hint, required this.materialColor})
      : super(key: key);

  final IconData icon;
  final String hint;
  final MaterialColor materialColor;
  static TextEditingController emailLogin = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: materialColor,
      child: TextField(
        cursorColor: materialColor,
        controller: emailLogin,
        decoration: InputDecoration(
            icon: Icon(icon, color: materialColor),
            hintText: hint,
            border: InputBorder.none),
      ),
    );
  }
}
