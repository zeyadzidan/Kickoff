import 'dart:io';

import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:path_provider/path_provider.dart';

import '../../application/application.dart';
import '../../application/screens/profile.dart';

class KickoffAppBar {
  build(context) => KickoffApplication.Player
      ?AppBar(
    leading: IconButton(
      icon: const Icon(Icons.person),
      tooltip: 'تعديل البيانات',
      onPressed: () async {
        //to be implemented
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
            Navigator.popAndPushNamed(context, '/loginPlayer');
        },
      ),
    ],
    backgroundColor: playerColor,
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
            var appDir = (await getTemporaryDirectory()).path;
            Directory(appDir).delete(recursive: true);
            Navigator.popAndPushNamed(context, '/login');
        },
      ),
    ],
    backgroundColor: courtOwnerColor,
  );
}
