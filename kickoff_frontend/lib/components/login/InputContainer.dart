import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';

class InputContainer extends StatelessWidget {
  const InputContainer({
    Key? key,
    required this.child,
    required this.color,
  }) : super(key: key);

  final Widget child;
  final Color color;
  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Container(
        margin: EdgeInsets.symmetric(vertical: 10),
        padding: EdgeInsets.symmetric(horizontal: 20, vertical: 5),
        width: size.width * 0.8,
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(30),
            color: color.withAlpha(50)),
        child: child);
  }
}
