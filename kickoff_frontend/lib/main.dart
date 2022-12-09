import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/constants.dart';

import 'application/screens/dataloading.dart';
import 'application/screens/login.dart';

void main() {
  // WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp();
  Loading.loadData();
  runApp(KickoffApplication(profileData: data));
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return loading
        ? MaterialApp(
            title: 'KickOff',
            debugShowCheckedModeBanner: false,
            theme: ThemeData(
              // This is the theme of your application.
              //
              // Try running your application with "flutter run". You'll see the
              // application has a blue toolbar. Then, without quitting the app, try
              // changing the primarySwatch below to Colors.green and then invoke
              // "hot reload" (press "r" in the console where you ran "flutter run",
              // or simply save your changes to "hot reload" in a Flutter IDE).
              // Notice that the counter didn't reset back to zero; the application
              // is not restarted.
              primarySwatch: Colors.green,
              primaryColor: primaryColor,
            ),
            home: Container(
              color: Colors.white,
              child: Center(
                  child: SizedBox(
                width: 200,
                height: 200,
                child: Container(
                    height: 175,
                    width: 175,
                    child: const Image(
                        image: AssetImage('assets/images/pic4.PNG'))),
              )),
            ),
          )
        : MaterialApp(
            home: firstTime
                ? LoginScreen()
                : KickoffApplication(profileData: data));
  }
}
