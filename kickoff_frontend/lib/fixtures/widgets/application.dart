import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/fixtures/widgets/addfixturebutton.dart';
import 'package:kickoff_frontend/fixtures/widgets/reservations.dart';
import 'package:kickoff_frontend/themes.dart';

class KickoffApplication extends StatefulWidget {
  const KickoffApplication({super.key});

  static final List pages = [
    const AddFixtureButton(),
    const Center(child: Text("ANNOUNCEMENTS FEATURE IS NOT YET IMPLEMENTED")),
    ReservationsHome(MyInfo.info)
  ];

  @override
  State<KickoffApplication> createState() => KickoffApplicationState();
}

class KickoffApplicationState extends State<KickoffApplication> {

  static int selectedPage = 0;

  _onTapSelect(index) =>
      setState(
              () => selectedPage = index
      );

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: AppThemes.lightTheme,
        title: "Kickoff",
        debugShowCheckedModeBanner: false,
        home: Scaffold(
          appBar: _buildAppBar(),
          body: Center(
            child: KickoffApplication.pages[selectedPage],
          ),
          floatingActionButton: (selectedPage == 2) ? _buildAddFixtureButton() : null,
          bottomNavigationBar: _buildNavBar()
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
    centerTitle: true,
    backgroundColor: Colors.green,
  );

  late String _selectedTime = TimeOfDay.now().format(context);

  _pickTime() async {
    final String? timeOfDay = (await showTimePicker(
      context: context,
      initialTime: TimeOfDay.now(),
    ))?.format(context);

    if (timeOfDay != _selectedTime) {
      setState(() => _selectedTime = timeOfDay!);
    }
  }

  _buildAddFixtureButton() => FloatingActionButton(
      onPressed: () {
        GlobalKey<FormState> formKey = GlobalKey();
        List ticketInformation = <String>[];
        showModalBottomSheet(
          context: context,
          builder: (BuildContext context) => SingleChildScrollView(
            child: SizedBox(
              height: 300,
              child: Form(
                key: formKey,
                child: Padding(
                    padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 30),
                    child: Column(
                      children: [
                        TextFormField(
                          keyboardType: TextInputType.name,
                          validator: (input) {
                            if (input == null) {
                              return 'Name cannot be blank';
                            } else {
                              formKey.currentState?.save();
                              return null;
                            }
                          },
                          decoration: const InputDecoration(
                            hintText: "Player Name",
                            prefixIcon: Icon(Icons.person),
                          ),
                          onSaved: (value) => ticketInformation.add(value),
                        ),
                        MaterialButton(
                          padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 30),
                          onPressed: _pickTime,
                          child: Row(
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: [
                              const Icon(Icons.timer),
                              Text('  Picked time: $_selectedTime'),
                            ],
                          ),
                        ),
                        Container(
                          alignment: Alignment.bottomCenter,
                          child: ElevatedButton(
                              onPressed: () {
                                String court = KickoffApplication.pages[KickoffApplicationState.selectedPage].selectedCourt;
                                String date = KickoffApplication.pages[KickoffApplicationState.selectedPage].selectedDate;
                                ticketInformation.add(court);
                                ticketInformation.add(date);

                                formKey.currentState?.save();

                                print(ticketInformation);

                                // TODO: SEND THE TICKET INFO TO BACK END
                                print(ticketInformation);
                              },
                              child: const Text("SUBMIT")
                          ),
                        )
                      ],
                    )
                ),
              ),
            ),
          ),
          elevation: 4,
        );
      },
      foregroundColor: Theme.of(context).floatingActionButtonTheme.foregroundColor,
      backgroundColor: Theme.of(context).floatingActionButtonTheme.backgroundColor,
      hoverColor: Colors.green.shade800,
      hoverElevation: 4,
      child: const Icon(Icons.add, size: 35)
  );

  _buildNavBar() => GNav(
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
          icon: Icons.notifications,
        ),
        GButton(
          padding: EdgeInsets.symmetric(vertical: 20.0, horizontal: 30.0),
          text: "Reservations",
          icon: Icons.stadium,
        ),
      ],
      selectedIndex: selectedPage,
      onTabChange: _onTapSelect
  );
}