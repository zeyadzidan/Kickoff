import 'package:flutter/material.dart';
import 'package:kickoff_frontend/Screens/login/login.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/localFile.dart';
import 'package:kickoff_frontend/components/login.dart';

String loginData = "";
bool first_time = true;
void main() async {
  runApp(const MyApp());
  loginData = await localFile.readLoginData();
  first_time = (loginData == "0");
  print(loginData);
  print(first_time);
  // if (!first_time) {
  //   int idx = loginData.indexOf(":");
  //   String email = loginData.substring(0, idx).trim();
  //   String pass = loginData.substring(idx + 1).trim();
  //   print("email");
  //   print(email);
  //   print("pass");
  //   print(pass);
  //   String url = "http://localhost:8080/login/courtOwner";
  //
  //   KickoffApplication.profileData = await RoundedLogin.save2(email, pass);
  // }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return first_time
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
            home: KickoffApplication(),
          );
  }
}
