import 'package:flutter/material.dart';
import 'package:kickoff_frontend/fixtures/builders/fplbuilder.dart';
import 'package:kickoff_frontend/fixtures/classes/addfixturebutton.dart';
import 'package:kickoff_frontend/themes.dart';

// TODO: ADD A WEEK-DAY NAVIGATOR

// Dummy values.
List info = [
  3,
  ["Hello World", "Fixture # 2", "Hello Fixtures"],
  [
    "This is a description of panel 1",
    "This is a description of panel 2",
    "This is a description of panel 3"
  ],
  [false, false, false]
];

class FixturesHome extends StatelessWidget {
  const FixturesHome({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: AppThemes.darkTheme,
      title: "FIXTURES",
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          title: const Text(
            "FIXTURES",
            style: TextStyle(
              color: Colors.white,
              fontFamily: ""  // Yet to be resolved
            ),
          ),
          centerTitle: true,
          backgroundColor: Theme.of(context).backgroundColor,
        ),
        body: SingleChildScrollView(
            child: FPListBuilder(info)
        ),
        floatingActionButton: const AddFixtureButton()
      )
    );
  }

}