import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/Screens/Profile.dart';
import 'package:kickoff_frontend/Screens/login/login.dart';
import 'package:kickoff_frontend/fixtures/widgets/reservations.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:kickoff_frontend/themes.dart';
import 'package:kickoff_frontend/constants.dart';

class KickoffApplication extends StatefulWidget {
  final Map<String, dynamic> Data;
  static late Map<String, dynamic> profileData;
  KickoffApplication({super.key, required this.Data}) {
    profileData = Data;
  }

  static bool loggedIn = false;

  static final List pages = [
    const ProfileBaseScreen(),
    const Center(child: Text("ANNOUNCEMENTS FEATURE IS NOT YET IMPLEMENTED")),
    ReservationsHome(MyInfo.info),
    LoginScreen()
  ];

  @override
  State<KickoffApplication> createState() => KickoffApplicationState();
}

class KickoffApplicationState extends State<KickoffApplication> {
  late TimeOfDay _initSelectedTime = TimeOfDay.now();
  late TimeOfDay _finSelectedTime = TimeOfDay.now();
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
            tooltip: 'Show Snackbar',
            onPressed: () {
              localFile.clearLoginData();
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
                      height: 200,
                      child: SingleChildScrollView(
                        child: Form(
                          key: key,
                          child: Column(
                            children: [
                              // TODO: HANDLE EMPTY PLAYER NAME CASE
                              TextFormField(
                                decoration: const InputDecoration(
                                    prefixIcon: Icon(Icons.person),
                                    hintText: "Player Name"),
                                keyboardType: TextInputType.name,
                                validator: (input) {
                                  if (input == null)
                                    return "Name can not be blank";
                                  key.currentState?.save();
                                  return null;
                                },
                                onSaved: (value) => ticketInfo.add(value!),
                              ),
                              // TODO: ACCEPT HOUR ONLY
                              MaterialButton(
                                padding: const EdgeInsets.symmetric(
                                    vertical: 20, horizontal: 100),
                                onPressed: () async {
                                  var time = await showTimePicker(
                                      initialEntryMode:
                                          TimePickerEntryMode.inputOnly,
                                      initialTime: TimeOfDay.now(),
                                      builder: (BuildContext context,
                                          Widget? child) {
                                        return MediaQuery(
                                          data: MediaQuery.of(context).copyWith(
                                              alwaysUse24HourFormat: true),
                                          child: child!,
                                        );
                                      },
                                      context: context);
                                  setState(() => _initSelectedTime = time!);
                                },
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Icon(Icons.timer),
                                    Text(
                                        '  From: ${_initSelectedTime.format(context)}')
                                  ],
                                ),
                              ),
                              MaterialButton(
                                padding: const EdgeInsets.symmetric(
                                    vertical: 20, horizontal: 100),
                                onPressed: () async {
                                  var time = await showTimePicker(
                                      initialTime: TimeOfDay.now(),
                                      context: context);
                                  setState(() => _finSelectedTime = time!);
                                },
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: [
                                    Icon(Icons.timer),
                                    Text(
                                        '  To: ${_finSelectedTime.format(context)}')
                                  ],
                                ),
                              ),
                              Container(
                                alignment: Alignment.bottomCenter,
                                child: ElevatedButton(
                                  onPressed: () {
                                    key.currentState?.save();

                                    String court =
                                        'Court: ${KickoffApplication.pages[_selectedPage].selectedCourt}';
                                    String date = KickoffApplication
                                        .pages[_selectedPage].selectedDate;

                                    ticketInfo.add(court);
                                    ticketInfo.add(date);
                                    ticketInfo
                                        .add(_initSelectedTime.format(context));
                                    ticketInfo
                                        .add(_finSelectedTime.format(context));
                                  },
                                  child: const Text("SUBMIT"),
                                ),
                              )
                            ],
                          ),
                        ),
                      ),
                    )),
            elevation: 4,
            foregroundColor:
                Theme.of(context).floatingActionButtonTheme.foregroundColor,
            backgroundColor:
                Theme.of(context).floatingActionButtonTheme.backgroundColor,
            hoverColor: Colors.green.shade800,
            child: const Icon(Icons.add, size: 35));
      });

  _buildNavBar() => Container(
        decoration: const BoxDecoration(
          boxShadow: <BoxShadow>[
            BoxShadow(
              color: Colors.black,
              blurRadius: 10,
            ),
          ],
        ),
        child: GNav(
            gap: 7,
            backgroundColor: Colors.green,
            activeColor: Colors.black,
            color: Colors.white,
            tabBackgroundColor: Colors.black.withAlpha(25),
            tabs: const <GButton>[
              GButton(
                padding: EdgeInsets.symmetric(vertical: 20.0, horizontal: 30.0),
                text: "Profile",
                icon: Icons.person,
              ),
              GButton(
                padding: EdgeInsets.symmetric(vertical: 20.0, horizontal: 30.0),
                text: "Announcements",
                icon: Icons.add,
              ),
              GButton(
                padding: EdgeInsets.symmetric(vertical: 20.0, horizontal: 30.0),
                text: "Reservations",
                icon: Icons.stadium,
              ),
            ],
            selectedIndex: _selectedPage,
            onTabChange: _onTapSelect),
      );
}
