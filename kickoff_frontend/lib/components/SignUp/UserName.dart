import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';


class UsernameSignUp extends StatelessWidget {
  UsernameSignUp({Key? key, required this.icon,required this.color, required this.hint})
      : super(key: key);

  final IconData icon;
  final Color color;
  final String hint;
  static TextEditingController username = TextEditingController();

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return BuildContainer(
      color: color,
        child: TextField(
      controller: username,
      cursorColor: color,
      decoration: InputDecoration(
          icon: Icon(icon, color: color),
          hintText: hint,
          border: InputBorder.none),
    ));
  }
}
