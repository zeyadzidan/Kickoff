import 'dart:async';

import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/application/screens/Account.dart';
import 'package:kickoff_frontend/application/screens/ProfileappearToPlayer.dart';
import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/application/screens/SearchScreen.dart';
import 'package:kickoff_frontend/application/screens/dataloading.dart';
import 'package:kickoff_frontend/application/screens/player/player-reservations.dart';

import 'package:kickoff_frontend/application/screens/player/showPartyPlayers.dart';


import 'package:kickoff_frontend/application/screens/rating.dart';

import 'package:kickoff_frontend/application/screens/playerprofile.dart';


import 'package:kickoff_frontend/components/announcements/plusannouncementbutton.dart';
import 'package:kickoff_frontend/components/announcements/view.dart';
import 'package:kickoff_frontend/components/application/applicationbar.dart';
import 'package:kickoff_frontend/components/courts/pluscourtbutton.dart';
import 'package:kickoff_frontend/components/tickets/plusreservationbutton.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/themes.dart';
import 'screens/BuildComponentsCourtOwner.dart';
import 'screens/BuildComponentsPlayer.dart';
import 'screens/profile.dart';
import 'screens/reservations.dart';

class KickoffApplication extends StatefulWidget {
  KickoffApplication({super.key, required this.profileData}) {
    data = profileData;
  }

  final Map<String, dynamic> profileData;
  static int _selectedPage = 0;
  static bool player = true;
  static String userIP = '';
  static String ownerId = '';
  static String playerId = '';
  static late Map<String, dynamic> data;
  static late Map<String, dynamic> dataPlayer;
  static final KickoffApplicationState _currentState =
      KickoffApplicationState();

  @override
  State<KickoffApplication> createState() => _currentState;

  static update() => _currentState.setState(() {});

  static setStartState() => _selectedPage=0;


  static onTapSelect(index) async {
    if(!KickoffApplication.player){
      await AnnouncementsHome.buildAnnouncements();
      await ReservationsHome.buildTickets();
    }
    _selectedPage = index;
    KickoffApplication.update();
  }

  checkData(context) =>
      (loginData == "") ? _currentState.updateCounter(context) : null;
}

class KickoffApplicationState extends State<KickoffApplication> {
  int counter = 0;
  late Timer _timer;
  final GlobalKey<ScaffoldState> _key = GlobalKey();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: AppThemes.lightTheme,
      title: "Kickoff",
      debugShowCheckedModeBanner: false,
      initialRoute:firstTime?'/loginPlayer':'/kickoff',
      routes: {
        '/loginPlayer': (context)=> const LoginScreen(),
        '/login': (context) => const LoginScreenCourtOwner(),
        '/profilePlayer':(context)=>  ProfileBaseScreenPlayer(),
        '/account' : (context) => Account(),
        '/Party' : (context) => showPartyPlayers(),
        '/writepost':(context)=> Writing(),
        '/kickoff': (context) => Builder(
              builder: (context) => Scaffold(
                key: _key,
                appBar: KickoffAppBar().build(context,_key),
                drawer: playerProfile(),
                body: Center(
                // Player Application
                  child: (KickoffApplication.player) ?
                    (KickoffApplication._selectedPage == 0) ?
                      SearchScreen() :
                    (KickoffApplication._selectedPage == 1) ?
                      AnnouncementsHome(full: true,) :
                    (KickoffApplication._selectedPage == 2) ?
                      PlayerReservationsHome() : Container()
                // Court Owner Application
                  : (KickoffApplication._selectedPage == 0) ?
                      ProfileBaseScreen() :
                    (KickoffApplication._selectedPage == 1) ?
                    // PlusAnnouncementButton3() : ReservationsHome()
                      AnnouncementsHome(full: false,) : ReservationsHome()

                ),
                // Court Owner Floating Buttons
                floatingActionButton: (!KickoffApplication.player) ?
                  (KickoffApplication._selectedPage == 0) ?
                    const PlusCourtButton() :
                  (KickoffApplication._selectedPage == 1) ?
                    const PlusAnnouncementButton() : const PlusReservationButton()
                : null,
                bottomNavigationBar:KickoffApplication.player ?
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
        } else if (loginData=="0"){
          _timer.cancel();
        }
      });
    });
  }

  _buildNavBar() => Container(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
      decoration: BoxDecoration(
          boxShadow: <BoxShadow>[
            BoxShadow(
              color: mainSwatch,
              blurRadius: 3,
            ),
          ],
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: mainSwatch.shade100),
      margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 10.0),
      child: GNav(
          gap: 5,
          activeColor: Colors.white,
          color: mainSwatch,
          tabBackgroundColor: Colors.black.withAlpha(25),
          duration: const Duration(milliseconds: 300),
          tabs: <GButton>[
            GButton(
              backgroundColor: mainSwatch,
              text: "الملف الشخصي",
              icon: Icons.person,
            ),
            GButton(
              backgroundColor: mainSwatch,
              text: "الإعلانات",
              icon: Icons.announcement,
            ),
            GButton(
              backgroundColor: mainSwatch,
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
          boxShadow: <BoxShadow>[
            BoxShadow(
              color: mainSwatch,
              blurRadius: 3,
            ),
          ],
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: mainSwatch.shade100),
      margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 10.0),
      child: GNav(
          gap: 5,
          activeColor: Colors.white,
          color: mainSwatch,
          tabBackgroundColor: Colors.black.withAlpha(25),
          duration: const Duration(milliseconds: 300),
          tabs: <GButton>[
            GButton(
              backgroundColor: mainSwatch,
              text: "Search",
              icon: Icons.search,
            ),
            GButton(
              backgroundColor: mainSwatch,
              text: "News Feed",
              icon: Icons.new_releases_sharp,
            ),
            GButton(
              backgroundColor: mainSwatch,
              text: "Reservations",
              icon: Icons.stadium,
            ),
          ],
          selectedIndex: KickoffApplication._selectedPage,
          onTabChange: KickoffApplication.onTapSelect)
  );

}
