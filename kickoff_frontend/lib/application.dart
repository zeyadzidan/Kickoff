import 'dart:io';

import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/fixtures/widgets/reservations.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:kickoff_frontend/httpshandlers/newticketrequests.dart';
import 'package:kickoff_frontend/themes.dart';
import 'package:path_provider/path_provider.dart';

import 'fixtures/widgets/login.dart';
import 'fixtures/widgets/profile.dart';

class KickoffApplication extends StatefulWidget {
  static late Map<String, dynamic> data;
  const KickoffApplication({super.key});

  static late String OWNER_ID;
  static bool loggedIn = false;

  // Application Pages
  static final List pages = [
    const ProfileBaseScreen(),
    const Center(child: Text("ANNOUNCEMENTS FEATURE IS NOT YET IMPLEMENTED")),
    ReservationsHome(),
  ];

  @override
  State<KickoffApplication> createState() => KickoffApplicationState();
}

class KickoffApplicationState extends State<KickoffApplication> {

  late TimeOfDay _initSelectedTime = TimeOfDay.now().replacing(minute: 00);
  late TimeOfDay _finSelectedTime = TimeOfDay.now().replacing(hour: _initSelectedTime.hour + 1, minute: 00);
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
              child: KickoffApplication.pages[_selectedPage],
            ),
          ]),
          floatingActionButton:
              (_selectedPage == 2) ? _buildAddFixtureButton(context) : null,
          bottomNavigationBar: _buildNavBar(),
        ),
      )
    );
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
        tooltip: 'Show Snackbar',
        onPressed: () async {
          localFile.clearLoginData();
          var appDir = (await getTemporaryDirectory()).path;
          Directory(appDir).delete(recursive: true);
          Navigator.of(context).push(
              MaterialPageRoute(builder: (context) => LoginScreen()));
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
                  margin: const EdgeInsets.symmetric(vertical: 25.0, horizontal: 25.0),
                  child: Column(
                    children: [
                      _buildTextField(key, ticketInfo, false),
                      _buildTextField(key, ticketInfo, true),
                      _buildTimePicker(true, context),
                      const Divider(
                        height: 1,
                        color: kPrimaryColor,
                        thickness: 2,
                      ),
                      _buildTimePicker(false, context),
                      const Divider(
                        height: 1,
                        color: kPrimaryColor,
                        thickness: 2,
                      ),
                      _buildSubmitButton(context, key, ticketInfo),
                    ],
                  ),
                ),
              )
            )
          ),
        ),
        elevation: 4,
        foregroundColor:
            Theme.of(context).floatingActionButtonTheme.foregroundColor,
        backgroundColor:
            Theme.of(context).floatingActionButtonTheme.backgroundColor,
        hoverColor: Colors.green.shade800,
        child: const Icon(Icons.add, size: 35)
      );
    }
  );


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
    keyboardType: (!moneyPayment) ? TextInputType.name : TextInputType.number,
    validator: (input) {
      if (input!.isEmpty) {
        return "This field can't be blank.";
      }
      if (moneyPayment) {key.currentState!.save();}
      return null;
    },
    onSaved: (value) => ticketInfo.add(value!),
  );

  _buildTimePicker(initTime, context) => MaterialButton(
    padding: const EdgeInsets.only(top: 10, bottom: 10),
    onPressed: _pickTime(initTime),
    child: Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        const Icon(Icons.timer, color: kPrimaryColor,),
        Text(
          (initTime)
              ? '   From - ${
                  DateFormat("hh:mm a").format(
                    DateFormat.jm().parse(
                        _initSelectedTime.format(context)
                    )
                  )
                }'
              : '   To  - ${
                  DateFormat("hh:mm a").format(
                      DateFormat.jm().parse(
                          _finSelectedTime.format(context)
                      )
                  )
                }',
          style: const TextStyle(color: kPrimaryColor),
        )
      ],
    ),
  );

  _pickTime(initTime) => () async {
    var time =  await showTimePicker(
      helpText: 'Please make sure to select only hour.',
      initialEntryMode: TimePickerEntryMode.inputOnly,
      initialTime: (initTime) ? _initSelectedTime : _finSelectedTime,
      context: context,
    );
    if (initTime) {
      if (time!.minute > 0) {
        showDialog(
            context: context,
            builder: (BuildContext context) =>
            const AlertDialog(
              title: Text('Please select hour only.\nMinutes are not considered.'),
            )
        );
      } else {
        setState(() => _initSelectedTime = time);
      }
    } else {
      if (time!.hour > _initSelectedTime.hour) {
        (time.minute == 0)
            ? setState(() => _finSelectedTime = time)
            : showDialog(
            context: context,
            builder: (BuildContext context) =>
            const AlertDialog(
              title: Text('Please select hour only.\nMinutes are not considered.'),
            )
        );
      } else {
        showDialog(
            context: context,
            builder: (BuildContext context) =>
            const AlertDialog(
              title: Text('Minimum number of hours to reserve is 1.\nPlease try again.'),
            )
        );
      }
    }
  };

  _buildSubmitButton(context, key, ticketInfo) =>  Container(
    alignment: Alignment.bottomCenter,
    margin: const EdgeInsets.only(top: 15),
    child: ElevatedButton.icon(
      label: const Text('SUBMIT'),
      icon: const Icon(Icons.schedule_send),
      style: ElevatedButton.styleFrom(
        backgroundColor: kPrimaryColor,
        padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 15)
      ),
      onPressed: () {
        // Validate name and money constraints
            if (!key.currentState!.validate()) {
          return;
        }

        String initTime = DateFormat("HH:mm").format(DateFormat.jm().parse(_initSelectedTime.format(context)));
        String finTime = DateFormat("HH:mm").format(DateFormat.jm().parse(_finSelectedTime.format(context)));

        // Validate time constraints
        if (initTime.compareTo(finTime) > 0) {
          return;
        }

        // Data Preparation
        // TODO: Modify the court identification to CID
        String court = 'Court: ${KickoffApplication.pages[_selectedPage].selectedCourt}';
        DateTime date = KickoffApplication.pages[_selectedPage].selectedDate;
        String formattedDate = DateFormat.yMd().format(date);

        // Player Name + Amount of Money
        ticketInfo.add(court);
        ticketInfo.add(formattedDate);
        ticketInfo.add(initTime);
        ticketInfo.add(finTime);

        print(ticketInfo);
        // TODO: Test the creation request in the back-end
        NewTicket.sendTicket(ticketInfo);

        ticketInfo = [];
        Navigator.pop(context);
      },
    ),
  );

  _buildNavBar() => Container(
    padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
    decoration: BoxDecoration(
      boxShadow: const <BoxShadow>[
        BoxShadow(
          color: Colors.black,
          blurRadius: 10,
        ),
      ],
      shape: BoxShape.rectangle,
      borderRadius: BorderRadius.circular(100),
      color: Colors.green.shade100
    ),
    margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 20.0),
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
      onTabChange: _onTapSelect
    )
  );
}
