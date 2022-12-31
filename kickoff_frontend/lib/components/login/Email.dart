import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
class BuildLoginEmail extends StatelessWidget {
  const BuildLoginEmail({Key? key, required this.icon,required this.color, required this.hint})
      : super(key: key);

  final IconData icon;
  final Color color;
  final String hint;
  static TextEditingController EmailLogin = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return BuildContainer(
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
