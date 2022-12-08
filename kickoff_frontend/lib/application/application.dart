import 'dart:io';

import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/httpshandlers/courtsrequests.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:kickoff_frontend/httpshandlers/ticketsrequests.dart';
import 'package:kickoff_frontend/themes.dart';
import 'package:path_provider/path_provider.dart';

import 'screens/login.dart';
import 'screens/profile.dart';
import 'screens/reservations.dart';
import '../components/classes/court.dart';

class KickoffApplication extends StatefulWidget {
  static late Map<String, dynamic> data;
  final Map<String, dynamic> profileData;
  KickoffApplication({super.key, required this.profileData}) {
    data = profileData;
    OWNER_ID = profileData["id"].toString();
  }

  static List<Court> courts = [];

  static String OWNER_ID = "";
  static bool loggedIn = false;

  @override
  State<KickoffApplication> createState() => KickoffApplicationState();
}

class KickoffApplicationState extends State<KickoffApplication> {
  late TimeOfDay _initSelectedTime = TimeOfDay.now().replacing(minute: 00);
  late TimeOfDay _finSelectedTime = TimeOfDay.now()
      .replacing(hour: (_initSelectedTime.hour + 1) % 24, minute: 00);
  late TimeOfDay _sWorkingHours = TimeOfDay.now().replacing(minute: 00);
  late TimeOfDay _fWorkingHours = TimeOfDay.now()
      .replacing(hour: (_sWorkingHours.hour + 1) % 24, minute: 00);
  int _selectedPage = 0;

  _onTapSelect(index) => setState(() => _selectedPage = index);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: AppThemes.lightTheme,
        title: "Kickoff",
        debugShowCheckedModeBanner: false,
        home: Builder(
          builder: (context) => Scaffold(
            appBar: _buildAppBar(),
            body: Stack(children: [
              Positioned(
                  top: 100,
                  right: -50,
                  child: Container(
                    width: 100,
                    height: 100,
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(50),
                        boxShadow: const <BoxShadow>[
                          BoxShadow(
                            color: Colors.black,
                            blurRadius: 5,
                          ),
                        ],
                        color: kPrimaryColor),
                  )),
              Positioned(
                  top: -50,
                  left: -50,
                  child: Container(
                    width: 190,
                    height: 190,
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(95),
                        boxShadow: const <BoxShadow>[
                          BoxShadow(
                            color: Colors.black,
                            blurRadius: 5,
                          ),
                        ],
                        color: kPrimaryColor),
                  )),
              Positioned(
                  bottom: -100,
                  left: -100,
                  child: Container(
                    width: 300,
                    height: 300,
                    decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(150),
                        boxShadow: const <BoxShadow>[
                          BoxShadow(
                            color: Colors.black,
                            blurRadius: 5,
                          ),
                        ],
                        color: kPrimaryColor),
                  )),
              Center(
                child: (_selectedPage == 0)
                    ? ProfileBaseScreen()
                    : (_selectedPage == 1)
                        ? const Center(
                            child: Text(
                                "ANNOUNCEMENTS FEATURE IS NOT YET IMPLEMENTED"))
                        : ReservationsHome(),
              ),
            ]),
            floatingActionButton: (_selectedPage == 0)
                ? _buildAddCourtButton(context)
                : (_selectedPage == 2)
                    ? _buildAddFixtureButton(context)
                    : null,
            bottomNavigationBar: _buildNavBar(),
          ),
        ));
  }

  _buildAppBar() => AppBar(
        leading: const Icon(Icons.sports_soccer),
        elevation: 4,
        title: const Text(
          "Kickoff",
          style: TextStyle(color: Colors.white),
        ),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.logout),
            tooltip: 'Show Kickoff Bar',
            onPressed: () async {
              localFile.clearLoginData();
              KickoffApplication.data.clear();
              ProfileBaseScreen.path = "";
              var appDir = (await getTemporaryDirectory()).path;
              Directory(appDir).delete(recursive: true);
              Navigator.of(context)
                  .push(MaterialPageRoute(builder: (context) => LoginScreen()));
            },
          ),
        ],
        centerTitle: true,
        backgroundColor: Colors.green,
      );

  _buildAddFixtureButton(context) => Builder(builder: (context) {
        GlobalKey<FormState> key = GlobalKey();
        List<String> ticketInfo = <String>[];
        return FloatingActionButton(
            onPressed: () => showModalBottomSheet(
                  elevation: 4,
                  context: context,
                  builder: (context) => SizedBox(
                      height: 350,
                      child: SingleChildScrollView(
                          child: Form(
                        key: key,
                        child: Container(
                          margin: const EdgeInsets.symmetric(
                              vertical: 25.0, horizontal: 25.0),
                          child: Column(
                            children: [
                              _buildTextField(key, ticketInfo, false),
                              // _buildTextField(key, ticketInfo, true), TODO: ADD THIS FEATURE IN SPRINT 2
                              _buildFixtureTimePicker(true, context),
                              const Divider(
                                height: 1,
                                color: kPrimaryColor,
                                thickness: 2,
                              ),
                              _buildFixtureTimePicker(false, context),
                              const Divider(
                                height: 1,
                                color: kPrimaryColor,
                                thickness: 2,
                              ),
                              _buildSubmitButton(context, key, ticketInfo),
                            ],
                          ),
                        ),
                      ))),
                ),
            elevation: 4,
            foregroundColor:
                Theme.of(context).floatingActionButtonTheme.foregroundColor,
            backgroundColor:
                Theme.of(context).floatingActionButtonTheme.backgroundColor,
            hoverColor: Colors.green.shade800,
            child: const Icon(Icons.add, size: 35));
      });

  _buildTextField(key, ticketInfo, moneyPayment) => TextFormField(
        maxLength: 32,
        autofocus: true,
        decoration: (!moneyPayment)
            ? const InputDecoration(
                prefixIcon: Icon(Icons.person, color: kPrimaryColor),
                labelText: "Enter player name",
                labelStyle: TextStyle(color: kPrimaryColor),
                hintText: "Example: Mohammed El-Mohammady",
                focusColor: kPrimaryColor,
                border: UnderlineInputBorder(),
              )
            : const InputDecoration(
                prefixIcon: Icon(Icons.monetization_on, color: kPrimaryColor),
                labelText: "Enter amount of money paid",
                labelStyle: TextStyle(color: kPrimaryColor),
                hintText: "Example: 200",
                suffixText: 'EGP',
                focusColor: kPrimaryColor,
                border: UnderlineInputBorder(),
              ),
        keyboardType:
            (!moneyPayment) ? TextInputType.name : TextInputType.number,
        validator: (input) {
          if (!moneyPayment && input!.isEmpty) {
            return "This field can't be blank.";
          }
          return null;
        },
        onSaved: (value) => ticketInfo.add(value!),
      );

  _buildFixtureTimePicker(initTime, context) => MaterialButton(
        padding: const EdgeInsets.only(top: 10, bottom: 10),
        onPressed: _pickTimeFixture(initTime),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(
              Icons.timer,
              color: kPrimaryColor,
            ),
            Text(
              (initTime)
                  ? '   From - ${DateFormat("hh:mm a").format(DateFormat.jm().parse(_initSelectedTime.format(context)))}'
                  : '   To  - ${DateFormat("hh:mm a").format(DateFormat.jm().parse(_finSelectedTime.format(context)))}',
              style: const TextStyle(color: kPrimaryColor),
            )
          ],
        ),
      );

  _pickTimeFixture(initTime) => () async {
        var time = await showTimePicker(
          helpText: 'Please make sure to select only hour.',
          initialEntryMode: TimePickerEntryMode.inputOnly,
          initialTime: (initTime) ? _initSelectedTime : _finSelectedTime,
          context: context,
        );
        if (initTime) {
          if (time!.minute > 0) {
            showDialog(
                context: context,
                builder: (BuildContext context) => const AlertDialog(
                      title: Text(
                          'Please select hour only.\nMinutes are not considered.'),
                    ));
          } else {
            setState(() => _initSelectedTime = time);
          }
        } else {
          if (time!.hour % 24 > _initSelectedTime.hour % 24) {
            (time.minute == 0)
                ? setState(() => _finSelectedTime = time)
                : showDialog(
                    context: context,
                    builder: (BuildContext context) => const AlertDialog(
                          title: Text(
                              'Please select hour only.\nMinutes are not considered.'),
                        ));
          } else {
            showDialog(
                context: context,
                builder: (BuildContext context) => const AlertDialog(
                      title: Text(
                          'Minimum number of hours to reserve is 1.\nPlease try again.'),
                    ));
          }
        }
      };

  _buildAddCourtButton(context) => Builder(builder: (context) {
        GlobalKey<FormState> key = GlobalKey();
        List<String> courtInfo = <String>[];
        return FloatingActionButton(
            onPressed: () => showModalBottomSheet(
                  elevation: 4,
                  context: context,
                  builder: (context) => SizedBox(
                      height: 700,
                      child: SingleChildScrollView(
                          child: Form(
                        key: key,
                        child: Container(
                          margin: const EdgeInsets.symmetric(
                              vertical: 25.0, horizontal: 25.0),
                          child: Column(
                            children: [
                              TextFormField(
                                maxLength: 32,
                                autofocus: true,
                                decoration: const InputDecoration(
                                  prefixIcon:
                                      Icon(Icons.stadium, color: kPrimaryColor),
                                  labelText: "Enter court name",
                                  labelStyle: TextStyle(color: kPrimaryColor),
                                  focusColor: kPrimaryColor,
                                  border: UnderlineInputBorder(),
                                ),
                                validator: (input) {
                                  if (input!.isEmpty) {
                                    return "This field can't be blank.";
                                  }
                                  return null;
                                },
                                onSaved: (value) => courtInfo.add(value!),
                              ),
                              TextFormField(
                                maxLength: 200,
                                autofocus: true,
                                decoration: const InputDecoration(
                                  prefixIcon: Icon(Icons.description,
                                      color: kPrimaryColor),
                                  labelText: "Enter court description",
                                  labelStyle: TextStyle(color: kPrimaryColor),
                                  focusColor: kPrimaryColor,
                                  border: UnderlineInputBorder(),
                                ),
                                validator: (input) {
                                  if (input!.isEmpty) {
                                    return "This field can't be blank.";
                                  }
                                  return null;
                                },
                                onSaved: (value) => courtInfo.add(value!),
                              ),
                              TextFormField(
                                keyboardType: TextInputType.number,
                                maxLength: 4,
                                autofocus: true,
                                decoration: const InputDecoration(
                                  prefixIcon: Icon(Icons.monetization_on,
                                      color: kPrimaryColor),
                                  labelText: "Enter morning hour cost",
                                  labelStyle: TextStyle(color: kPrimaryColor),
                                  focusColor: kPrimaryColor,
                                  border: UnderlineInputBorder(),
                                ),
                                validator: (input) {
                                  if (input!.isEmpty) {
                                    return "This field can't be blank.";
                                  } else if (double.parse(input) ==
                                      double.nan) {
                                    return 'Please specify a numeric value.';
                                  }
                                  return null;
                                },
                                onSaved: (value) => courtInfo.add(value!),
                              ),
                              TextFormField(
                                keyboardType: TextInputType.number,
                                maxLength: 4,
                                autofocus: true,
                                decoration: const InputDecoration(
                                  prefixIcon: Icon(Icons.monetization_on,
                                      color: kPrimaryColor),
                                  labelText: "Enter evening hour cost",
                                  labelStyle: TextStyle(color: kPrimaryColor),
                                  focusColor: kPrimaryColor,
                                  border: UnderlineInputBorder(),
                                ),
                                validator: (input) {
                                  if (input!.isEmpty) {
                                    return "This field can't be blank.";
                                  } else if (double.parse(input) ==
                                      double.nan) {
                                    return 'Please specify a numeric value.';
                                  }
                                  return null;
                                },
                                onSaved: (value) => courtInfo.add(value!),
                              ),
                              TextFormField(
                                keyboardType: TextInputType.number,
                                maxLength: 2,
                                autofocus: true,
                                decoration: const InputDecoration(
                                  prefixIcon:
                                      Icon(Icons.timer, color: kPrimaryColor),
                                  labelText: "Minimum booking hours",
                                  labelStyle: TextStyle(color: kPrimaryColor),
                                  focusColor: kPrimaryColor,
                                  border: UnderlineInputBorder(),
                                ),
                                validator: (input) {
                                  if (input!.isEmpty) {
                                    return "This field can't be blank.";
                                  } else if (double.parse(input) ==
                                      double.nan) {
                                    return 'Please specify a numeric value.';
                                  }
                                  return null;
                                },
                                onSaved: (value) => courtInfo.add(value!),
                              ),
                              _buildCourtTimePicker(true, context),
                              _buildCourtTimePicker(false, context),
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
                                    String sWorkingHoursString = DateFormat(
                                            "HH")
                                        .format(DateFormat.jm().parse(
                                            _sWorkingHours.format(context)));
                                    String fWorkingHoursString = DateFormat(
                                            "HH")
                                        .format(DateFormat.jm().parse(
                                            _fWorkingHours.format(context)));
                                    courtInfo.add(sWorkingHoursString);
                                    courtInfo.add(fWorkingHoursString);
                                    print(courtInfo);
                                    // TODO: Test the creation request in the back-end
                                    await CourtsHTTPsHandler.sendCourt(
                                        courtInfo);
                                    courtInfo = [];
                                    KickoffApplication.courts =
                                        await CourtsHTTPsHandler.getCourts(
                                            KickoffApplication.OWNER_ID);
                                    Navigator.pop(context);
                                    setState(() {});
                                  },
                                ),
                              ),
                            ],
                          ),
                        ),
                      ))),
                ),
            elevation: 4,
            foregroundColor:
                Theme.of(context).floatingActionButtonTheme.foregroundColor,
            backgroundColor:
                Theme.of(context).floatingActionButtonTheme.backgroundColor,
            hoverColor: Colors.green.shade800,
            child: const Icon(Icons.add, size: 35));
      });

  _pickTimeCourt(initTime, TimeOfDay? startingWorkingHours) => () async {
        var time = await showTimePicker(
          helpText: 'Please make sure to select only hour.',
          initialEntryMode: TimePickerEntryMode.inputOnly,
          initialTime: (initTime) ? _initSelectedTime : _finSelectedTime,
          context: context,
        );
        if (initTime) {
          if (time!.minute > 0) {
            showDialog(
                context: context,
                builder: (BuildContext context) => const AlertDialog(
                      title: Text(
                          'Please select hour only.\nMinutes are not considered.'),
                    ));
          } else {
            setState(() => _sWorkingHours = time);
          }
        } else {
          if (time!.hour % 24 > startingWorkingHours!.hour % 24) {
            (time.minute == 0)
                ? setState(() => _fWorkingHours = time)
                : showDialog(
                    context: context,
                    builder: (BuildContext context) => const AlertDialog(
                          title: Text(
                              'Please select hour only.\nMinutes are not considered.'),
                        ));
          } else {
            showDialog(
                context: context,
                builder: (BuildContext context) => const AlertDialog(
                      title: Text(
                          'Minimum number of hours to reserve is 1.\nPlease try again.'),
                    ));
          }
        }
      };

  _buildCourtTimePicker(initTime, context) => MaterialButton(
        padding: const EdgeInsets.only(top: 10, bottom: 10),
        onPressed: (initTime)
            ? _pickTimeCourt(initTime, null)
            : _pickTimeCourt(initTime, _sWorkingHours),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(
              Icons.timer,
              color: kPrimaryColor,
            ),
            Text(
              (initTime)
                  ? '   Starting Working Hour - ${DateFormat("hh:mm a").format(DateFormat.jm().parse(_sWorkingHours.format(context)))}'
                  : '   Finishing Working Hour  - ${DateFormat("hh:mm a").format(DateFormat.jm().parse(_fWorkingHours.format(context)))}',
              style: const TextStyle(color: kPrimaryColor),
            )
          ],
        ),
      );

  _buildSubmitButton(context, key, ticketInfo) => Container(
        alignment: Alignment.bottomCenter,
        margin: const EdgeInsets.only(top: 15),
        child: ElevatedButton.icon(
          label: const Text('SUBMIT'),
          icon: const Icon(Icons.schedule_send),
          style: ElevatedButton.styleFrom(
              backgroundColor: kPrimaryColor,
              padding:
                  const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
          onPressed: () async {
            // Validate name and money constraints
            if (!key.currentState!.validate()) {
              return;
            }
            key.currentState!.save();
            FixtureTicket ticket = FixtureTicket();

            String initTime = DateFormat("HH").format(
                DateFormat.jm().parse(_initSelectedTime.format(context)));
            String finTime = DateFormat("HH").format(
                DateFormat.jm().parse(_finSelectedTime.format(context)));

            if (initTime.compareTo(finTime) > 0) {
              return;
            }

            ticket.pname = ticketInfo[0];
            ticket.coid = KickoffApplication.OWNER_ID;
            ticket.cid =
                KickoffApplication.courts[ReservationsHome.selectedCourt].cid;
            DateTime date = ReservationsHome.selectedDate;
            String formattedDate = DateFormat.yMd().format(date);
            ticket.startDate = formattedDate;
            ticket.endDate = formattedDate;
            ticket.startTime = initTime;
            ticket.endTime = finTime;

            await Tickets.sendTicket(ticket);
            await ReservationsHome.buildTickets("apppliacation");
            setState(() {
              (Navigator.pop(context));
            });
          },
        ),
      );

  _buildNavBar() => Container(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
      decoration: BoxDecoration(
          boxShadow: const <BoxShadow>[
            BoxShadow(
              color: Colors.black,
              blurRadius: 5,
            ),
          ],
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: Colors.green.shade100),
      margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 10.0),
      child: GNav(
          gap: 5,
          activeColor: Colors.white,
          color: kPrimaryColor,
          tabBackgroundColor: Colors.black.withAlpha(25),
          duration: const Duration(milliseconds: 300),
          tabs: const <GButton>[
            GButton(
              // padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 5),
              backgroundColor: kPrimaryColor,
              text: "Profile",
              icon: Icons.person,
            ),
            GButton(
              // padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 5),
              backgroundColor: kPrimaryColor,
              text: "Announcements",
              icon: Icons.add,
            ),
            GButton(
              // padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 5),
              backgroundColor: kPrimaryColor,
              text: "Reservations",
              icon: Icons.stadium,
            ),
          ],
          selectedIndex: _selectedPage,
          onTabChange: _onTapSelect));
}
