import 'package:flutter/material.dart';

import 'fixtures.dart';

// TODO: ADD A WEEK-DAY NAVIGATOR

class FixturesHome extends StatelessWidget {
  const FixturesHome({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "KICKOFF",
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          title: const Text("KICKOFF",
            style: TextStyle(
              fontFamily: ""  // Yet to be resolved
            ),
          ),
          centerTitle: true,
          backgroundColor: Colors.green,
        ),
        body: const SingleChildScrollView(
            child: Fixtures([
              ["Ahmed", "Court #1", "10:30 - DD/MM/YYYY"],
              ["Mahmoud", "Court #2", "12:30 - DD/MM/YYYY"],
              ["Youssef", "Court #3", "14:30 - DD/MM/YYYY"],
              ["Zeyad", "Court #4", "16:30 - DD/MM/YYYY"],
              ["Gad", "Court #5", "18:30 - DD/MM/YYYY"],
              ["Khairat", "Court #6", "20:30 - DD/MM/YYYY"],
              ["Ahmed", "Court #1", "10:30 - DD/MM/YYYY"],
              ["Mahmoud", "Court #2", "12:30 - DD/MM/YYYY"],
              ["Youssef", "Court #3", "14:30 - DD/MM/YYYY"],
              ["Zeyad", "Court #4", "16:30 - DD/MM/YYYY"],
              ["Gad", "Court #5", "18:30 - DD/MM/YYYY"],
              ["Khairat", "Court #6", "20:30 - DD/MM/YYYY"],
              ["Ahmed", "Court #1", "10:30 - DD/MM/YYYY"],
              ["Mahmoud", "Court #2", "12:30 - DD/MM/YYYY"],
              ["Youssef", "Court #3", "14:30 - DD/MM/YYYY"],
              ["Zeyad", "Court #4", "16:30 - DD/MM/YYYY"],
              ["Gad", "Court #5", "18:30 - DD/MM/YYYY"],
              ["Khairat", "Court #6", "20:30 - DD/MM/YYYY"],
            ])
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            /*
            * TODO: SEND A CREATE-NEW-FIXTURE-TICKET REQUEST
            * */
          },
          backgroundColor: Colors.green,
          child: const Text(
            '+',
            style: TextStyle(
                fontSize: 50,
                fontWeight: FontWeight.bold
            ),
          ),
        ),
      )
    );
  }

}