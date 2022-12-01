import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/input_container.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedPasswordSignup extends StatelessWidget {
  const RoundedPasswordSignup({
    Key? key,
    required this.hint
  }) : super(key: key);

  final String hint;
  static TextEditingController Password =TextEditingController();
  @override
  Widget build(BuildContext context) {
    return InputContainer(
        child: TextField(
          cursorColor: kPrimaryColor,
          controller: Password,
          obscureText: true,
          decoration: InputDecoration(
              icon: Icon(Icons.lock, color: kPrimaryColor),
              hintText: hint,
              border: InputBorder.none
          ),
        ));
  }
}