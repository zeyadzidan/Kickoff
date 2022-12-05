import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/input_container.dart';
import 'package:kickoff_frontend/constants.dart';

class RoundedInputUsername extends StatelessWidget {
   RoundedInputUsername({Key? key, required this.icon, required this.hint})
      : super(key: key);

  final IconData icon;
  final String hint;
 static TextEditingController username =TextEditingController();
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    return InputContainer(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            SizedBox(
              width: size.width * 0.4,
              child: TextField(
                controller: username,
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
