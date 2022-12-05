import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/httpshandlers/courtsrequests.dart';
import 'package:kickoff_frontend/httpshandlers/newticketrequests.dart';

import '../../components/classes/fixtureticket.dart';
import '../../constants.dart';

class ReservationsHome extends StatefulWidget {
  ReservationsHome({super.key}) {
    _getCourts();
  }

  _getCourts() async {
    // courts = await CourtsHTTPsHandler.getCourts(KickoffApplication.OWNER_ID);
  }

  static get selectedCourt => _ReservationsHomeState._selectedCourt;
  static get selectedDate => _ReservationsHomeState._selectedDate;

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
    List<dynamic> tickets = CourtsHTTPsHandler.getCourtFixtures(
        ReservationsHome.courts[_selectedCourt].cid,
        KickoffApplication.OWNER_ID,
        date) as List<dynamic>;
    return Expanded(
      child: SingleChildScrollView(
        child: Column(
            children: List<GestureDetector>.generate(tickets.length, (index) {
            List<dynamic> body = _buildBody(tickets[index]);
            return GestureDetector(
              onDoubleTap: _setBooked(index, tickets[index]),
              child: Container(
                decoration: BoxDecoration(
                    shape: BoxShape.rectangle,
                    borderRadius: BorderRadius.circular(25),
                    color: (_buildBody(tickets[index])[0] == 'Pending')
                        ? Colors.yellow.withOpacity(0.3)
                        : (_buildBody(tickets[index])[0] == 'Active')
                        ? kPrimaryColor.withOpacity(0.3)
                        : Colors.red.withOpacity(0.3) // Expired
                ),
                padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 0),
                margin: const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
                alignment: Alignment.center,
                child: Column(
                  children: List<Text>.generate(body.length - 1, (index) =>
                      Text(
                          body[index + 1].toString()
                      )
                  ),
                ),
              ),
            );
        })),
      ),
    );
  }

  _buildBody(fixtureTicket) => [
    fixtureTicket.isPending,
    'Player Name: ${fixtureTicket.pname}',
    'Start Date: ${fixtureTicket.startDate}',
    'End Date: ${fixtureTicket.endDate}',
    'State: ${fixtureTicket.state}',
    'Money Paid: ${fixtureTicket.paidAmount} EGP',
    'Total Cost: ${fixtureTicket.totalCost} EGP',
  ];

  _setBooked(index, FixtureTicket ticket) {
    GlobalKey<FormState> key = GlobalKey();
    showDialog(
        context: context, builder: (context) =>
        Dialog(
          child: Column(
            children: [
              Form(
                child: TextFormField(
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(
                    prefixIcon: Icon(Icons.monetization_on, color: kPrimaryColor),
                    labelText: "Paid amount",
                    labelStyle: TextStyle(color: kPrimaryColor),
                    focusColor: kPrimaryColor,
                    border: UnderlineInputBorder(),
                    suffixText: 'EGP'
                  ),
                  validator: (input) {
                    if(input! == 0) {
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
                      padding:
                      const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
                  onPressed: () {
                    // Validate name and money constraints
                    if (!key.currentState!.validate()) {
                      return;
                    }
                    key.currentState!.save();
                    ticket.state = 'Active';
                    Tickets.bookTicket(ticket);
                    Navigator.pop(context);
                  },
                ),
              )
            ],
          )
        )
    );

    setState(() {

    });
  }
}
