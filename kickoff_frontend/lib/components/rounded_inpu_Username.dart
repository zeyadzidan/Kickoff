import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/input_container.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInputUsername extends StatelessWidget {
  const RoundedInputUsername({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InputContainer(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            SizedBox(
              width: size.width-size.width * 0.5,
              child: TextField(
                cursorColor: kPrimaryColor,
                decoration: InputDecoration(
                    icon: Icon(icon, color: kPrimaryColor),
                    hintText: hint,
                    border: InputBorder.none),
              ),
            ),
            Text("@Kickoff.com")
          ],
        ));
  }
}
