import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/httpshandlers/ticketsrequests.dart';

import '../../components/classes/fixtureticket.dart';
import '../../constants.dart';
import '../components/builders/fpbuilder.dart';

class ReservationsHome extends StatefulWidget {
  ReservationsHome({super.key}) {
    // buildTickets(); //TODO:hanknsl hina
    for (FixtureTicket ticket in ReservationsHome.reservations)
      print(ticket.asView());
    isExpanded = List<bool>.generate(reservations.length, (index) => false);
  }

  static List<FixtureTicket> reservations = [];
  static List<bool> isExpanded = [];
  static int _selectedCourt = 0;
  static DateTime _selectedDate = DateTime.now();
  static get selectedCourt => ReservationsHome._selectedCourt;
  static get selectedDate => ReservationsHome._selectedDate;
  static buildTickets(x) async {
    reservations = await Tickets.getCourtFixtures(
        KickoffApplication.courts[ReservationsHome._selectedCourt].cid,
        KickoffApplication.OWNER_ID,
        DateFormat.yMd().format(ReservationsHome.selectedDate));
    print(x);
  }

  @override
  State<ReservationsHome> createState() => _ReservationsHomeState();
}

class _ReservationsHomeState extends State<ReservationsHome> {
  _onTabSelect(index) async {
    ReservationsHome._selectedCourt = index;
    await ReservationsHome.buildTickets("ontap");
    setState(() {});
  }

  _pickDate() async {
    final DateTime? dateTime = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(DateTime.now().year - 5),
      lastDate: DateTime(DateTime.now().year + 5),
    );

    // if (dateTime != null) {
    //   setState(() => ReservationsHome._selectedDate = dateTime);
    // }
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      scrollDirection: Axis.vertical,
      child: Column(
        children: [_buildCourts(), _buildDatePicker(), _buildFixtures()],
      ),
    );
  }

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
            selectedIndex: ReservationsHome._selectedCourt,
            onTabChange: _onTabSelect,
            duration: const Duration(milliseconds: 300),
            activeColor: Colors.white,
            color: kPrimaryColor,
            tabBackgroundColor: Colors.black.withAlpha(25),
            tabs: List<GButton>.generate(
                KickoffApplication.courts.length,
                (index) => GButton(
                      backgroundColor: kPrimaryColor,
                      icon: Icons.stadium,
                      text: "   ${KickoffApplication.courts[index].cname}",
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
            Text(
                '  ${DateFormat.yMMMMEEEEd().format(ReservationsHome._selectedDate)}'),
          ],
        ),
      );

  _buildFixtures() {
    return SingleChildScrollView(
        child: ExpansionPanelList(
      animationDuration: const Duration(milliseconds: 300),
      expandedHeaderPadding: EdgeInsets.zero,
      dividerColor: Theme.of(context).dividerColor,
      elevation: 4,
      children: List<ExpansionPanel>.generate(
          ReservationsHome.reservations.length,
          (index) => FixturePanelBuilder().build(
              ReservationsHome.reservations[index],
              ReservationsHome.isExpanded[index])),
      expansionCallback: (i, isExpanded) =>
          setState(() => ReservationsHome.isExpanded[i] = !isExpanded),
    ));
  }

  _setBooked(FixtureTicket ticket) {
    GlobalKey<FormState> key = GlobalKey();
    showDialog(
        context: context,
        builder: (context) => AlertDialog(actions: <Widget>[
              Column(
                children: [
                  Form(
                    child: TextFormField(
                      keyboardType: TextInputType.number,
                      decoration: const InputDecoration(
                          prefixIcon:
                              Icon(Icons.monetization_on, color: kPrimaryColor),
                          labelText: "Paid amount",
                          labelStyle: TextStyle(color: kPrimaryColor),
                          focusColor: kPrimaryColor,
                          border: UnderlineInputBorder(),
                          suffixText: 'EGP'),
                      validator: (input) {
                        if (input! == 0) {
                          return "This field can't be blank.";
                        }
                      },
                      onSaved: (input) => ticket.paidAmount = input!,
                    ),
                  ),
                  Container(
                    alignment: Alignment.bottomCenter,
                    margin: const EdgeInsets.only(top: 15),
                    child: ElevatedButton.icon(
                      label: const Text('SUBMIT'),
                      icon: const Icon(Icons.schedule_send),
                      style: ElevatedButton.styleFrom(
                          backgroundColor: kPrimaryColor,
                          padding: const EdgeInsets.symmetric(
                              vertical: 20, horizontal: 15)),
                      onPressed: () async {
                        // Validate name and money constraints
                        if (!key.currentState!.validate()) {
                          return;
                        }
                        key.currentState!.save();
                        ticket.state = 'Active';
                        await Tickets.bookTicket(ticket);
                        Navigator.pop(context);
                      },
                    ),
                  )
                ],
              )
            ]));

    // setState(() {});
  }
}
