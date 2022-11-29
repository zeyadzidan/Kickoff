import 'package:flutter/material.dart';

// CAUTION: NOT DONE YET.

class FixtureTicket extends StatelessWidget {
  const FixtureTicket({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialButton (
      onPressed: () {
        /*
        * TODO: VIEW FIXTURE DETAILS
        * */

      },
      child: Container(
        color: Colors.green,
        padding: const EdgeInsets.symmetric(horizontal: 180, vertical: 20.0),
        margin: const EdgeInsets.symmetric(vertical: 10),
        child: const Text(
          "FIXTURE 01",
          style: TextStyle(
            color: Colors.white,
            fontWeight: FontWeight.bold
          )
        )
      )
    );
  }

}