import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/fixtures/widgets/addfixturebutton.dart';
import 'package:kickoff_frontend/fixtures/widgets/themes.dart';

// TODO: ADD A WEEK-DAY NAVIGATOR

class KickoffApplication extends StatefulWidget {
  const KickoffApplication(this._pages, {super.key});
  final List _pages;

  @override
  State<KickoffApplication> createState() => KickoffApplicationState(_pages);
}

class KickoffApplicationState extends State<KickoffApplication> {

  KickoffApplicationState(this._pages);
  final List _pages;
  late int _selectedIndex = 0;
  _onTapSelect(index) =>
      setState(
              () => _selectedIndex = index
      );

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        theme: AppThemes.lightTheme,
        title: "Kickoff",
        debugShowCheckedModeBanner: false,
        home: Scaffold(
          appBar: AppBar(
            leading: const Icon(Icons.sports_soccer),
            elevation: 4,
            title: const Text(
              "Kickoff",
              style: TextStyle(
                  color: Colors.white,
                  fontFamily: ""  // Yet to be resolved
              ),
            ),
            centerTitle: true,
            backgroundColor: Theme.of(context).backgroundColor,
          ),
          body: Center(
            child: _pages[_selectedIndex]
          ),
          floatingActionButton: const AddFixtureButton(),
          bottomNavigationBar: GNav(
              gap: 8,
              activeColor: const Color(0XFF4CAF50),
              color: Colors.black,
              tabBackgroundColor: const Color(0XFF4CAF50).withAlpha(50),
              tabs: const <GButton>[
                GButton(
                  text: "Profile",
                  icon: Icons.person,
                ),
                GButton(
                  text: "Announcements",
                  icon: Icons.notifications,
                ),
                GButton(
                  text: "Reservations",
                  icon: Icons.stadium,
                ),
              ],
              selectedIndex: _selectedIndex,
              onTabChange: _onTapSelect
          )
        )
    );
  }

}