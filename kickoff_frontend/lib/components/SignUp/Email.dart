import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';


class BuildEmailSignUp extends StatelessWidget {
  const BuildEmailSignUp({Key? key, required this.icon,required this.color, required this.hint})
      : super(key: key);

  final IconData icon;
  final Color color;
  final String hint;
  static TextEditingController EmailSignUp = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return BuildContainer(
      color: color,
      child: TextField(
        cursorColor: color,
        controller: EmailSignUp,
        decoration: InputDecoration(
            icon: Icon(icon, color: color),
            hintText: hint,
            border: InputBorder.none),
      ),
    );
  }
}
