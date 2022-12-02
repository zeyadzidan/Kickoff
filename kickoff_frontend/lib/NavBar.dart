import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';

class NavBar {
  static navBar() {
    return GNav(
      gap: 8,
      activeColor: const Color(0XFF4CAF50),
      //to be changed
      color: Colors.black,
      //to be changed
      tabBackgroundColor: const Color(0XFF4CAF50).withAlpha(50),
      tabs: const [
        GButton(
          text: "Profile",
          icon: Icons.person,
        ),
        GButton(
          text: "Announcements",
          icon: Icons.add_circle_outline,
        ),
        GButton(
          text: "Reservations",
          icon: Icons.timer,
        ),
      ],
      onTabChange: (index) {
        print(index); //to be changed
      },
    );
  }
}
