import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInputLogin extends StatelessWidget {
  const RoundedInputLogin({Key? key, required this.icon,required this.color, required this.hint})
      : super(key: key);

  final IconData icon;
  final Color color;
  final String hint;
  static TextEditingController EmailLogin = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: color,
      child: TextField(
        cursorColor: color,
        controller: EmailLogin,
        decoration: InputDecoration(
            icon: Icon(icon, color: color),
            hintText: hint,
            border: InputBorder.none),
      ),
    );
  }
}
