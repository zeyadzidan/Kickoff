import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/fixtures/builders/fpbuilder.dart';

import 'addfixturebutton.dart';

// TODO: ADD A WEEK-DAY NAVIGATOR

class ReservationsHome extends StatefulWidget {
  const ReservationsHome(this.info, {super.key});
  final List info;

  @override
  State<ReservationsHome> createState() => _ReservationsHomeState(info);
}


class _ReservationsHomeState extends State<ReservationsHome> {
  _ReservationsHomeState(this._courtFixtures);
  final List _courtFixtures;
  late int _selectedCourt = 0;

  _onTabSelect(index) {
    _selectedCourt = index;
    setState(() {

    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        GestureDetector(
          child: Column(
            children: [
              SingleChildScrollView(
                scrollDirection: Axis.horizontal,
                child: GNav(
                  gap: 3,
                  selectedIndex: _selectedCourt,
                  onTabChange: _onTabSelect,
                  duration: const Duration(milliseconds: 300),
                  tabs: List<GButton>.generate(9, (index) =>
                      GButton(
                        icon: Icons.stadium,
                        text: "   COURT ${index + 1}",
                      )),
                ),
              ),
              SingleChildScrollView(
                  child: ExpansionPanelList(
                    animationDuration: const Duration(milliseconds: 300),
                    expandedHeaderPadding: EdgeInsets.zero,
                    dividerColor: Theme.of(context).dividerColor,
                    elevation: 4,
                    children: List<ExpansionPanel>.generate(
                        _courtFixtures[_selectedCourt][0],
                        (index) => FixturePanelBuilder().build(
                        _courtFixtures[_selectedCourt][1][index],
                        _courtFixtures[_selectedCourt][2][index],
                        _courtFixtures[_selectedCourt][3][index]
                      )
                    ),
                    expansionCallback: (i, isExpanded) =>
                        setState(
                                () =>
                            _courtFixtures[_selectedCourt][3][i] = !isExpanded
                        ),
                  )
              )
            ],
          ),
        )
      ],
    );
  }
}

class MyInfo {
  // Dummy values.
  static final List info = [
    [
      3,
      ["FIXTURE #1", "FIXTURE #2", "FIXTURE #3"],
      [
        "This is a description of panel 1",
        "This is a description of panel 2",
        "This is a description of panel 3"
      ],
      [false, false, false]
    ],
    [
      3,
      ["FIXTURE #4", "FIXTURE #5", "FIXTURE #6"],
      [
        "This is a description of panel 1",
        "This is a description of panel 2",
        "This is a description of panel 3"
      ],
      [false, false, false]
    ],
    [
      3,
      ["FIXTURE #7", "FIXTURE #8", "FIXTURE #9"],
      [
        "This is a description of panel 1",
        "This is a description of panel 2",
        "This is a description of panel 3"
      ],
      [false, false, false]
    ]
  ];
}