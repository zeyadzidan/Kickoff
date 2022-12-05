import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/httpshandlers/courtsrequests.dart';

import '../../components/builders/ticket.dart';
import '../../components/classes/court.dart';
import '../../components/classes/fixtureticket.dart';
import '../../constants.dart';

class ReservationsHome extends StatefulWidget {
  ReservationsHome({super.key}) {
    _getCourts();
  }

  _getCourts() async {
    print("llll");
    // courts = await CourtsHTTPsHandler.getCourts(KickoffApplication.OWNER_ID);
  }

  static Map<String, dynamic> courts = {};

  @override
  State<ReservationsHome> createState() => _ReservationsHomeState();
}

class _ReservationsHomeState extends State<ReservationsHome> {
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
  Widget build(BuildContext context) => Column(
        children: [_buildCourts(), _buildDatePicker(), _buildFixtures()],
      );

  _buildCourts() => Container(
      padding: const EdgeInsets.symmetric(vertical: 0.0, horizontal: 15.0),
      decoration: BoxDecoration(
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: kPrimaryColor.withOpacity(0.3)),
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
            tabs: List<GButton>.generate(
                ReservationsHome.courts.length,
                (index) => GButton(
                      backgroundColor: kPrimaryColor,
                      icon: Icons.stadium,
                      text: "   ${ReservationsHome.courts[index].cname}",
                    )),
          ),
        ),
      ));

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
    ticket.totalCost = '200';
    ticket.state = 'Pending';

    // TODO: Generate the list of fixtures received from backend.
    String date = DateFormat.yMd().format(_selectedDate);
    List<FixtureTicket> tickets = CourtsHTTPsHandler.getCourtFixtures(
        ReservationsHome.courts[_selectedCourt].cid,
        KickoffApplication.OWNER_ID,
        date) as List<FixtureTicket>;
    return Expanded(
      child: SingleChildScrollView(
        child: Column(
            children: List<Container>.generate(tickets.length, (index) {
          // ticket.isPending = !ticket.isPending;
          return Ticket().build(tickets[index]);
        })),
      ),
    );
  }
}
