import 'dart:io';

import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:path_provider/path_provider.dart';

import '../../application/application.dart';
import '../../application/screens/announcements.dart';
import '../../application/screens/profile.dart';

class KickoffAppBar {
  build(context, globalKey) => KickoffApplication.player
      ?AppBar(
    centerTitle: true,
    leading: IconButton(
      icon: const Icon(Icons.person),
      tooltip: 'تعديل البيانات',
      onPressed: () async {

        // globalKey.currentState?.openDrawer();
        //to be implemented

        Navigator.pushNamed(context, '/account');

      },
    ),
    elevation: 4,
    title: const Text(
      "Kickoff",
      style: TextStyle(color: secondaryColor),

    ),
    actions: <Widget>[
      IconButton(
        icon: const Icon(Icons.logout),
        tooltip: 'تسجيل خروج',
        onPressed: () async {
            localFile.clearLoginData();
            KickoffApplication.data.clear();
            ProfileBaseScreen.path = "";
            var appDir = (await getTemporaryDirectory()).path;
            Directory(appDir).delete(recursive: true);
            ProfileBaseScreen.courts.clear();
            AnnouncementsHome.announcements.clear();
            Navigator.popAndPushNamed(context, '/loginPlayer');
        },
      ),
    ],
    backgroundColor: mainSwatch,
  )
  :AppBar(
    leading: const Icon(Icons.sports_soccer),
    elevation: 4,
    title: const Text(
      "Kickoff",
      style: TextStyle(color: secondaryColor),
    ),
    actions: <Widget>[
      IconButton(
        icon: const Icon(Icons.logout),
        tooltip: 'تسجيل خروج',
        onPressed: () async {
            localFile.clearLoginData();
            KickoffApplication.data.clear();
            ProfileBaseScreen.path = "";
            ProfileBaseScreen.courts.clear();
            var appDir = (await getTemporaryDirectory()).path;
            Directory(appDir).delete(recursive: true);
            Navigator.popAndPushNamed(context, '/login');
        },
      ),
    ],
    backgroundColor: mainSwatch,
  );
}
