import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/application/screens/ProfileappearToPlayer.dart';
import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/application/screens/SearchScreen.dart';
import 'package:kickoff_frontend/application/screens/dataloading.dart';
import 'package:kickoff_frontend/components/announcements/plusannouncementbutton.dart';
import 'package:kickoff_frontend/components/application/applicationbar.dart';
import 'package:kickoff_frontend/components/courts/pluscourtbutton.dart';
import 'package:kickoff_frontend/components/tickets/plusreservationbutton.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/themes.dart';

import '../components/classes/court.dart';
import '../components/login/BuildComponentsCourtOwner.dart';
import 'screens/login.dart';
import 'screens/profile.dart';
import 'screens/reservations.dart';

class KickoffApplication extends StatefulWidget {
  KickoffApplication({super.key, required this.profileData}) {
    data = profileData;
    ownerId = profileData["id"].toString();
  }

  final Map<String, dynamic> profileData;
  static int _selectedPage = 0;
  static bool Player=true;
  static String userIP = '';
  static String ownerId = '';
  static List<Court> courts = [];
  static late Map<String, dynamic> data;
  static late Map<String, dynamic> dataPlayer;
  static final KickoffApplicationState _currentState =
      KickoffApplicationState();

  @override
  State<KickoffApplication> createState() => _currentState;

  static update() => _currentState.setState(() {});


  static onTapSelect(index) =>
      _currentState.setState(() => _selectedPage = index);

  checkData(context) =>
      (loginData == "") ? _currentState.updateCounter(context) : null;
}

class KickoffApplicationState extends State<KickoffApplication> {
  int counter = 0;
  late Timer _timer;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: AppThemes.lightTheme,
      title: "Kickoff",
      debugShowCheckedModeBanner: false,
      initialRoute:firstTime?'/loginPlayer':'/kickoff',
      //initialRoute: firstTime?'/login':'/kickoff',
      routes: {
        '/loginPlayer': (context)=> const LoginScreen(),
        '/login': (context) => const LoginScreenCourtOwner(),
        '/profilePlayer':(context)=>  ProfileBaseScreenPlayer(),
        '/kickoff': (context) => Builder(
              builder: (context) => Scaffold(
                appBar: KickoffAppBar().build(context),
                body: Center(
                // Player Application
                  child: (KickoffApplication.Player) ?
                    (KickoffApplication._selectedPage == 0) ?
                      SearchScreen() :
                    (KickoffApplication._selectedPage == 1) ?
                     const Center(child: Text("DUMMY PAGE 1")) :
                    (KickoffApplication._selectedPage == 2) ?
                      const Center(child: Text("DUMMY PAGE 2")) :
                      const Center(child: Text("DUMMY PAGE 3"))
                // Court Owner Application
                  : (KickoffApplication._selectedPage == 0) ?
                      ProfileBaseScreen() :
                    (KickoffApplication._selectedPage == 1) ?
                      AnnouncementsHome() : ReservationsHome()
                ),
                // Court Owner Floating Buttons
                floatingActionButton: (!KickoffApplication.Player) ?
                  (KickoffApplication._selectedPage == 0) ?
                    const PlusCourtButton() :
                  (KickoffApplication._selectedPage == 1) ?
                    const PlusAnnouncementButton() : const PlusReservationButton()
                : null,
                bottomNavigationBar:KickoffApplication.Player ?
                    _buildPlayerNavBar() : _buildNavBar(),
              ),
            )
      },
    );
  }

  updateCounter(context) {
    _timer = Timer.periodic(const Duration(seconds: 1), (timer) {
      counter++;
      setState(() {
        if (loginData != "" && finish) {
          firstTime = (loginData == "0");
          loading = false;
          _timer.cancel();
        }else if (loginData=="0"){
          _timer.cancel();
        }

      });
    });
  }

  _buildNavBar() => Container(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
      decoration: BoxDecoration(
          boxShadow: const <BoxShadow>[
            BoxShadow(
              color: courtOwnerColor,
              blurRadius: 3,
            ),
          ],
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: courtOwnerColor.shade100),
      margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 10.0),
      child: GNav(
          gap: 5,
          activeColor: Colors.white,
          color: courtOwnerColor,
          tabBackgroundColor: Colors.black.withAlpha(25),
          duration: const Duration(milliseconds: 300),
          tabs: const <GButton>[
            GButton(
              backgroundColor: courtOwnerColor,
              text: "الملف الشخصي",
              icon: Icons.person,
              onPressed: null,
            ),
            GButton(
              backgroundColor: courtOwnerColor,
              text: "الإعلانات",
              icon: Icons.add,
            ),
            GButton(
              backgroundColor: courtOwnerColor,
              text: "الحجوزات",
              icon: Icons.stadium,
            ),
          ],
          selectedIndex: KickoffApplication._selectedPage,
          onTabChange: KickoffApplication.onTapSelect)
  );

  _buildPlayerNavBar() => Container(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
      decoration: BoxDecoration(
          boxShadow: const <BoxShadow>[
            BoxShadow(
              color: playerColor,
              blurRadius: 3,
            ),
          ],
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: playerColor.shade100),
      margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 10.0),
      child: GNav(
          gap: 5,
          activeColor: Colors.white,
          color: playerColor,
          tabBackgroundColor: Colors.black.withAlpha(25),
          duration: const Duration(milliseconds: 300),
          tabs: const <GButton>[
            GButton(
              backgroundColor: playerColor,
              text: "Search",
              icon: Icons.search,
            ),
            GButton(
              backgroundColor: playerColor,
              text: "News Feed",
              icon: Icons.new_releases_sharp,
            ),
            GButton(
              backgroundColor: playerColor,
              text: "Reservations",
              icon: Icons.stadium,
            ),
          ],
          selectedIndex: KickoffApplication._selectedPage,
          onTabChange: KickoffApplication.onTapSelect)
  );

}
