import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/fixtures/builders/fplbuilder.dart';

// TODO: ADD A WEEK-DAY NAVIGATOR

class ReservationsHome extends StatefulWidget {
  const ReservationsHome(this.info, {super.key});
  final List info;

  @override
  State<ReservationsHome> createState() => ReservationsHomeState(info);
}


class ReservationsHomeState extends State<ReservationsHome> {
  ReservationsHomeState(this.courtFixtures);
  final List courtFixtures;
  late int _selectedCourt = 2;

  _onTabSelect(index) => setState(
      () =>
          _selectedCourt = index
  );

  @override
  Widget build(BuildContext context) {
    print(_selectedCourt);
    return Column(
      children: [
        SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: GNav(
            gap: 3,
            selectedIndex: _selectedCourt,
            onTabChange: _onTabSelect,
            tabs: List<GButton>.generate(9, (index) =>
                GButton(
                  icon: Icons.stadium,
                  text: "   COURT ${index + 1}",
                )),
          ),
        ),
        SingleChildScrollView(
            child: FPListBuilder(courtFixtures[_selectedCourt])
        ),
      ],
    );
  }
}