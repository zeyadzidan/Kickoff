import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/components/builders/fixtureticketbuilder.dart';
import 'package:kickoff_frontend/fixtures/classes/fixtureticket.dart';

import '../../components/builders/ticket.dart';
import '../../constants.dart';


class ReservationsHome extends StatefulWidget {
  const ReservationsHome(this.info, {super.key});
  final List info;

  get selectedCourt => _ReservationsHomeState._selectedCourt;

  get selectedDate => _ReservationsHomeState._selectedDate;

  @override
  State<ReservationsHome> createState() => _ReservationsHomeState(info);
}


class _ReservationsHomeState extends State<ReservationsHome> {
  _ReservationsHomeState(this._courtFixtures);
  final List _courtFixtures;
  static int _selectedCourt = 0;
  static DateTime _selectedDate = DateTime.now();

  _onTabSelect(index) {
    _selectedCourt = index;
    setState(() {});
  }

  _pickDate() async {
    final DateTime? dateTime = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(DateTime.now().year - 5),
      lastDate: DateTime(DateTime.now().year + 5),
    );

    if (dateTime != null) {
      setState(() => _selectedDate = dateTime);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        GestureDetector(
          child: Column(
            children: [
              _buildCourts(),
              _buildDatePicker(),
              _buildFixtures()
            ],
          ),
        )
      ],
    );
  }

  _buildCourts() => Container(
    padding: const EdgeInsets.symmetric(vertical: 0.0, horizontal: 15.0),
    decoration: BoxDecoration(
        shape: BoxShape.rectangle,
        borderRadius: BorderRadius.circular(100),
        color: kPrimaryColor.withOpacity(0.3)
    ),
    margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
    child: SingleChildScrollView(
      scrollDirection: Axis.horizontal,
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: 0.0, vertical: 10.0),
        child: GNav(
          selectedIndex: _selectedCourt,
          onTabChange: _onTabSelect,
          duration: const Duration(milliseconds: 300),
          activeColor: Colors.white,
          color: kPrimaryColor,
          tabBackgroundColor: Colors.black.withAlpha(25),
          tabs: List<GButton>.generate(9, (index) =>
              GButton(
                backgroundColor: kPrimaryColor,
                icon: Icons.stadium,
                text: "   COURT ${index + 1}",
              )
            ),
          ),
      ),
      )
  );

  _buildDatePicker() => MaterialButton(
    padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 30),
    onPressed: _pickDate,
    child: Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        const Icon(Icons.calendar_month),
        Text('  ${DateFormat.yMMMMEEEEd().format(_selectedDate)}'),
      ],
    ),
  );

  _buildFixtures() {
    FixtureTicket ticket = FixtureTicket();
    ticket.pname = 'Ahmed';
    ticket.startDate = '16:00';
    ticket.endDate = '17:00';
    ticket.paidAmount = '200';
    ticket.isPending = true;
    return SingleChildScrollView(
      child: Column(
        children: List<Container>.generate(
              7,
              (index) => Ticket().build(ticket)
        )
      ),
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