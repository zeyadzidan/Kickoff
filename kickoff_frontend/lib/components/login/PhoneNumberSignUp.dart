import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedPhoneNumber extends StatelessWidget {
  const RoundedPhoneNumber({Key? key, required this.icon,required this.color, required this.hint})
      : super(key: key);
  final Color color;
  final IconData icon;
  final String hint;
  static TextEditingController PhoneNumber = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
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
