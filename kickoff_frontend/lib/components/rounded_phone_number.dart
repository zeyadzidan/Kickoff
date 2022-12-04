import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/input_container.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedPhoneNumber extends StatelessWidget {
  const RoundedPhoneNumber({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
  static TextEditingController PhoneNumber = TextEditingController();
  @override
  Widget build(BuildContext context) {
    return InputContainer(
      child: TextField(
        cursorColor: kPrimaryColor,
        controller: PhoneNumber,
        decoration: InputDecoration(
            icon: Icon(icon, color: kPrimaryColor),
            hintText: hint,
            border: InputBorder.none),
        keyboardType: TextInputType.number,
      ),
    );
  }
}
