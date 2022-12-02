import 'package:flutter/material.dart';
import 'package:kickoff_frontend/Screens/login/login.dart';
import 'package:kickoff_frontend/components/Sign_up_location.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/fixtures/widgets/application.dart';
import 'NavBar.dart';
import 'Profile.dart';


void main() => runApp(
  Builder(
    builder: (context) =>
        const KickoffApplication()
  )
);


class MyApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
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
    );
  }
}