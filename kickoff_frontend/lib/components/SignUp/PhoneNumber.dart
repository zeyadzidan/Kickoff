import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';

class PhoneNumberSignUp extends StatelessWidget {
  const PhoneNumberSignUp({Key? key, required this.icon,required this.color, required this.hint})
      : super(key: key);
  final Color color;
  final IconData icon;
  final String hint;
  static TextEditingController PhoneNumber = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return BuildContainer(
      color: color,
      child: TextField(
        cursorColor: color,
        controller: PhoneNumber,
        decoration: InputDecoration(
            icon: Icon(icon, color: color),
            hintText: hint,
            border: InputBorder.none),
        keyboardType: TextInputType.number,
      ),
    );
  }
}
