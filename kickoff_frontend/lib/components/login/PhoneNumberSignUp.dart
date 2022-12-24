import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/InputContainer.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedPhoneNumber extends StatelessWidget {
  const RoundedPhoneNumber({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
  static TextEditingController phoneNumber = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return InputContainer(
      color: courtOwnerColor,
      child: TextField(
        cursorColor: courtOwnerColor,
        controller: phoneNumber,
        decoration: InputDecoration(
            icon: Icon(icon, color: courtOwnerColor),
            hintText: hint,
            border: InputBorder.none),
        keyboardType: TextInputType.number,
      ),
    );
  }
}
