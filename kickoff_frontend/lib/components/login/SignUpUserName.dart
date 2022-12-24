import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInputUsername extends StatelessWidget {
  const RoundedInputUsername({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
  static TextEditingController username = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
        color: courtOwnerColor,
        child: TextField(
          controller: username,
          cursorColor: courtOwnerColor,
          decoration: InputDecoration(
              icon: Icon(icon, color: courtOwnerColor),
              hintText: hint,
              border: InputBorder.none),
        ));
  }
}
