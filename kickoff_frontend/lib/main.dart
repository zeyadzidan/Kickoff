// <<<<<<< HEAD
// import 'dart:async';
// =======
// >>>>>>> Sprint-Two
// import 'package:flutter/material.dart';
// import 'package:kickoff_frontend/application/application.dart';
// <<<<<<< HEAD
// import 'package:kickoff_frontend/screens/login.dart';
// import 'package:kickoff_frontend/screens/reservations.dart';
// import 'httpshandlers/courtsrequests.dart';
//
// String loginData = "";
// bool loading = true;
// bool firstTime = true;
// bool finish = false;
// Map<String, dynamic> data = {};
//
// Future main() async {
//   WidgetsFlutterBinding.ensureInitialized();
//   await Firebase.initializeApp();
//   runApp(const MyApp());
//   loginData = await localFile.readLoginData();
//   firstTime = (loginData == "0");
//   loading = (loginData == "0") ? false : true;
//   if (!firstTime) {
//     int idx = loginData.indexOf(":");
//     String email = loginData.substring(0, idx).trim();
//     String pass = loginData.substring(idx + 1).trim();
//     data = await RoundedLogin.save2(email, pass);
//     int id = data["id"];
//     KickoffApplication.courts = await CourtsHTTPsHandler.getCourts(id);
//     await ReservationsHome.buildTickets("main", id);
//     finish = true;
//   } else {
//     finish = true;
//   }
// }
//
// class MyApp extends StatefulWidget {
//   const MyApp({Key? key}) : super(key: key);
//
//   @override
//   State<MyApp> createState() => _MyAppState();
// }
//
// class _MyAppState extends State<MyApp> {
//   int counter = 0;
//   late Timer _timer;
//   updateCounter() {
//     _timer = Timer.periodic(const Duration(seconds: 2), (timer) {
//       counter++;
//       setState(() {
//         if (!data.isEmpty && finish) {
//           firstTime = (loginData == "0");
//           loading = false;
//           _timer.cancel();
//         } else if (loginData == "0") {
//           _timer.cancel();
//         }
//         ;
//       });
//     });
//   }
//
//   @override
//   Widget build(BuildContext context) {
//     if (loading && data.isEmpty) {
//       print("gowa if");
//       updateCounter();
//     }
//     return loading
//         ? MaterialApp(
//             title: 'KickOff',
//             debugShowCheckedModeBanner: false,
//             theme: ThemeData(
//               // This is the theme of your application.
//               //
//               // Try running your application with "flutter run". You'll see the
//               // application has a blue toolbar. Then, without quitting the app, try
//               // changing the primarySwatch below to Colors.green and then invoke
//               // "hot reload" (press "r" in the console where you ran "flutter run",
//               // or simply save your changes to "hot reload" in a Flutter IDE).
//               // Notice that the counter didn't reset back to zero; the application
//               // is not restarted.
//               primarySwatch: Colors.green,
//               primaryColor: kPrimaryColor,
//             ),
//             home: Container(
//               color: Colors.white,
//               child: Stack(children: [
//                 Positioned(
//                     top: 100,
//                     right: -50,
//                     child: Container(
//                       width: 100,
//                       height: 100,
//                       decoration: BoxDecoration(
//                           borderRadius: BorderRadius.circular(50),
//                           boxShadow: const <BoxShadow>[
//                             BoxShadow(
//                               color: Colors.black,
//                               blurRadius: 5,
//                             ),
//                           ],
//                           color: kPrimaryColor),
//                     )),
//                 Positioned(
//                     top: -50,
//                     left: -50,
//                     child: Container(
//                       width: 190,
//                       height: 190,
//                       decoration: BoxDecoration(
//                           borderRadius: BorderRadius.circular(95),
//                           boxShadow: const <BoxShadow>[
//                             BoxShadow(
//                               color: Colors.black,
//                               blurRadius: 5,
//                             ),
//                           ],
//                           color: kPrimaryColor),
//                     )),
//                 Positioned(
//                     bottom: -100,
//                     left: -100,
//                     child: Container(
//                       width: 300,
//                       height: 300,
//                       decoration: BoxDecoration(
//                           borderRadius: BorderRadius.circular(150),
//                           boxShadow: const <BoxShadow>[
//                             BoxShadow(
//                               color: Colors.black,
//                               blurRadius: 5,
//                             ),
//                           ],
//                           color: kPrimaryColor),
//                     )),
//                 Center(
//                     child: SizedBox(
//                   width: 200,
//                   height: 200,
//                   child: Container(
//                       height: 175,
//                       width: 175,
//                       child: const Image(
//                           image: AssetImage('assets/images/pic4.PNG'))),
//                 )),
//               ]),
//             ),
//           )
//         : MaterialApp(
//             home: firstTime
//                 ? LoginScreen()
//                 : KickoffApplication(profileData: data));
//   }
// =======

import 'package:flutter/cupertino.dart';
import 'package:firebase_core/firebase_core.dart';
import 'application/application.dart';
import 'application/screens/dataloading.dart';
Future main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp();
  await Loading.loadData();
  runApp(KickoffApplication(profileData: data));
}
