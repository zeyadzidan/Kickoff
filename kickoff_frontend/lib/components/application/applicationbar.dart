import 'dart:io';

import 'package:flutter/material.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:path_provider/path_provider.dart';

import '../../application/application.dart';
import '../../application/screens/profile.dart';

class KickoffAppBar {
  build(context) => AppBar(
        leading: const Icon(Icons.sports_soccer),
        elevation: 4,
        title: const Text(
          "Kickoff",
          style: TextStyle(color: Colors.white),
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
              Navigator.pop(context);
            },
          ),
        ],
        backgroundColor: Colors.green,
      );
}
