import 'dart:async';

import 'package:flutter/material.dart';
import 'package:kickoff_frontend/Screens/login/login.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:kickoff_frontend/components/login.dart';
import 'package:firebase_core/firebase_core.dart';

String loginData = "";
bool loading = true;
bool firstTime = true;
late Map<String, dynamic> profileData;
bool finsh = false;
Future main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  runApp(const MyApp());
  loginData = await localFile.readLoginData();
  firstTime = (loginData == "0");
  loading = false;
  if (!firstTime) {
    int idx = loginData.indexOf(":");
    String email = loginData.substring(0, idx).trim();
    String pass = loginData.substring(idx + 1).trim();
    profileData = await RoundedLogin.save2(email, pass);
  }
  finsh = true;
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  int counter = 0;
  late Timer _timer;
  updateCounter() {
    _timer = Timer.periodic(Duration(seconds: 1), (timer) {
      counter++;
      setState(() {
        if (loginData != "" && finsh) {
          firstTime = (loginData == "0");
          loading = false;
          _timer.cancel();
        }
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    if (loginData == "") {
      updateCounter();
    }
    return loading
        ? MaterialApp(
            title: 'Flutter Animated Login',
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
              primaryColor: kPrimaryColor,
            ),
            home: Container(
              color: Colors.white,
              child: Stack(children: [
                Positioned(
                    top: 100,
                    right: -50,
                    child: Container(
                      width: 100,
                      height: 100,
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(50),
                          boxShadow: const <BoxShadow>[
                            BoxShadow(
                              color: Colors.black,
                              blurRadius: 5,
                            ),
                          ],
                          color: kPrimaryColor),
                    )),
                Positioned(
                    top: -50,
                    left: -50,
                    child: Container(
                      width: 190,
                      height: 190,
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(95),
                          boxShadow: const <BoxShadow>[
                            BoxShadow(
                              color: Colors.black,
                              blurRadius: 5,
                            ),
                          ],
                          color: kPrimaryColor),
                    )),
                Positioned(
                    bottom: -100,
                    left: -100,
                    child: Container(
                      width: 300,
                      height: 300,
                      decoration: BoxDecoration(
                          borderRadius: BorderRadius.circular(150),
                          boxShadow: const <BoxShadow>[
                            BoxShadow(
                              color: Colors.black,
                              blurRadius: 5,
                            ),
                          ],
                          color: kPrimaryColor),
                    )),
                Center(
                    child: SizedBox(
                  width: 200,
                  height: 200,
                  child: Container(
                      height: 175,
                      width: 175,
                      child:
                          Image(image: AssetImage('assets/images/pic4.PNG'))),
                )),
              ]),
            ),
          )
        : MaterialApp(
            home: firstTime
                ? LoginScreen()
                : KickoffApplication(
                    Data: profileData,
                  ));
  }
}
