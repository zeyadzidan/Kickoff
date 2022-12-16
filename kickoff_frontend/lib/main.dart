import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:firebase_core/firebase_core.dart';

import 'application/screens/dataloading.dart';

Future main() async {
  // WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp();
  Loading.loadData();
  runApp(KickoffApplication(profileData: data));
}
