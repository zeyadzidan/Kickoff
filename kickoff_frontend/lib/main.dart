import 'dart:async';

import 'package:flutter/material.dart';
import 'package:kickoff_frontend/Screens/login/login.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:kickoff_frontend/components/login.dart';

String loginData = "";
bool loading = true;
bool firstTime = true;
late Map<String, dynamic> profileData;
bool finsh = false;
void main() async {
  runApp(const MyApp());
  loginData = await localFile.readLoginData();
  firstTime = (loginData == "0");
  print("loginData");
  print(loginData);
  print("loginData");
  loading = false;
  if (!firstTime) {
    print("yakaaaata");
    int idx = loginData.indexOf(":");
    String email = loginData.substring(0, idx).trim();
    String pass = loginData.substring(idx + 1).trim();
    print("email");
    print(email);
    print("pass");
    print(pass);
    profileData = await RoundedLogin.save2(email, pass);
    print("lol");
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
      // You can also call here any function.
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
    print("hello");
    print(firstTime);
    print("hello");
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
            // bottomNavigationBar: NavBar.navBar(),
            home: Container(
              color: Colors.white,
              child: Center(
                  child: SizedBox(
                      width: 200,
                      height: 200,
                      child: CircularProgressIndicator())),
            ),
          )
        : MaterialApp(
            home: firstTime
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
                    // bottomNavigationBar: NavBar.navBar(),
                    home: LoginScreen(),
                  )
                : MaterialApp(
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
                    // bottomNavigationBar: NavBar.navBar(),
                    home: KickoffApplication(
                      Data: profileData,
                    ),
                  ),
          );
  }
}
