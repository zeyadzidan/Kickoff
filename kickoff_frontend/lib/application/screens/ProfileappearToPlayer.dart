import 'dart:convert';
import 'dart:io';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:file_picker/file_picker.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/player/ReservationButtonPlayer.dart';
import 'package:kickoff_frontend/application/screens/player/ReservationHomePlayer.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/components/courts/court-view.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:url_launcher/url_launcher_string.dart';

import '../../components/classes/court.dart';

class ProfileBaseScreenPlayer extends StatefulWidget {
  ProfileBaseScreenPlayer({super.key}) {
    isExpanded = List<bool>.generate(courts.length, (index) => false);
  }
  static String? path = "";
  static List<Court> courts = <Court>[];
  static List<bool> isExpanded = <bool>[];
  static int _selectedPage = 0;

  @override
  State<ProfileBaseScreenPlayer> createState() => _ProfileBaseScreenStatePlayer();

  // static onTapSelect(index) =>
  //     _currentState.setState(() => _selectedPage = index);
}

class _ProfileBaseScreenStatePlayer extends State<ProfileBaseScreenPlayer> {
  double rating = double.parse("${KickoffApplication.dataPlayer["rating"]}");
  int rating2 = double.parse("${KickoffApplication.dataPlayer["rating"]}").toInt();
  int subscribers = 0;
  String name = KickoffApplication.dataPlayer["name"];
  String phone = KickoffApplication.dataPlayer["phoneNumber"];
  String address = KickoffApplication.dataPlayer["location"];
  double xaxis = KickoffApplication.dataPlayer["xAxis"];
  double yaxis = KickoffApplication.dataPlayer["yAxis"];
  int id = KickoffApplication.dataPlayer["id"];
  bool foundPhoto = KickoffApplication.dataPlayer.containsKey("image");
  String tempUrl = "";
  String utl = KickoffApplication.dataPlayer.containsKey("image")
      ? KickoffApplication.dataPlayer["image"]
      : "";

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          tooltip: 'back',
          onPressed: ()
          {
            Navigator.pop(context);
          },
        ),
      ),
      body: Center(
        child: ProfileBaseScreenPlayer._selectedPage == 0
          ?Container(
            margin: const EdgeInsets.all(20),
            child: Column(
              children: [
                Container(
                  decoration: BoxDecoration(
                      boxShadow: const <BoxShadow>[
                        BoxShadow(
                          color: playerColor,
                          blurRadius: 3,
                        ),
                      ],
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(5),
                      color: playerColor.shade100),
                  child: Column(
                    children: [
                      Container(
                        margin:
                        const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
                        padding: const EdgeInsets.fromLTRB(0, 30, 0, 0),
                        child: Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                                CircleAvatar(
                                  radius: 40,
                                  backgroundColor: playerColor,
                                  child:
                                  foundPhoto?
                                  ClipOval(
                                    child: CachedNetworkImage(
                                      imageUrl: utl,
                                      width: 100,
                                      height: 100,
                                      fit: BoxFit.cover,
                                      progressIndicatorBuilder:
                                          (context, url, downloadProgress) =>
                                          CircularProgressIndicator(
                                              value: downloadProgress.progress),
                                      errorWidget: (context, url, error) =>
                                          Icon(Icons.error),
                                    ),
                                  ):Container(),
                                ),
                              Padding(
                                padding: const EdgeInsets.fromLTRB(0, 6, 20, 0),
                                child: Row(children: [
                                  SizedBox(
                                    height: 70,
                                    child: TextButton(
                                      onPressed: () {
                                        print("Show Reviews");
                                      },
                                      child: Column(
                                        children: [
                                          Text(
                                            "$rating2 \u{2B50} ",
                                            //remember to remove the 2 in milestone 2
                                            style: const TextStyle(
                                              fontSize: 20,
                                              fontWeight: FontWeight.bold,
                                              color: Colors.black,
                                            ),
                                          ),
                                          const Text(
                                            "Rating",
                                            style: TextStyle(
                                              fontSize: 15,
                                              letterSpacing: 0.8,
                                              color: Colors.black,
                                            ),
                                          )
                                        ],
                                      ),
                                    ),
                                  ),
                                  const SizedBox(
                                    width: 20,
                                  ),
                                  SizedBox(
                                    height: 70,
                                    child: TextButton(
                                      onPressed: () {
                                        print("Show Subscribers");
                                      },
                                      child: Column(
                                        children: [
                                          Text(
                                            "$subscribers \u{1F464}",
                                            style: const TextStyle(
                                                letterSpacing: 0.4,
                                                fontSize: 20,
                                                fontWeight: FontWeight.bold,
                                                color: Colors.black),
                                          ),
                                          const Text(
                                            "Subscribers",
                                            style: TextStyle(
                                              letterSpacing: 0.4,
                                              fontSize: 15,
                                              color: Colors.black,
                                            ),
                                          )
                                        ],
                                      ),
                                    ),
                                  ),
                                ]),
                              ),
                            ]),
                      ),
                      Padding(
                        padding: const EdgeInsets.symmetric(vertical: 10),
                        child: Container(
                          margin: const EdgeInsets.symmetric(
                              vertical: 5, horizontal: 20),
                          child: Column(
                            children: [
                              Align(
                                alignment: Alignment.centerLeft,
                                child: Text(
                                  name,
                                  style: const TextStyle(
                                    letterSpacing: 0.4,
                                    fontSize: 20,
                                    color: Colors.black,
                                  ),
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.symmetric(vertical: 10.0),
                                child: Align(
                                  alignment: Alignment.centerLeft,
                                  child: InkWell(
                                      child: Text(
                                        " \u{1F4DE} $phone ",
                                        style: const TextStyle(
                                          letterSpacing: 0.4,
                                          fontSize: 15,
                                        ),
                                      ),
                                      onTap: () => launchUrlString("tel://$phone")),
                                ),
                              ),
                              Align(
                                alignment: Alignment.centerLeft,
                                child: InkWell(
                                    child: Text(
                                      " \u{1F5FA} $address",
                                      style: const TextStyle(
                                        letterSpacing: 0.4,
                                        fontSize: 15,
                                      ),
                                    ),
                                    onTap: () => launchUrlString(
                                        'https://www.google.com/maps/place/$xaxis+$yaxis')),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                CourtsView(),
              ],
            )
        ):ProfileBaseScreenPlayer._selectedPage == 1
            ?Container()
            :ReservationsHomePlayer()

      ),
      floatingActionButton: ProfileBaseScreenPlayer._selectedPage == 2
        ?const PlusReservationButtonPlayer()
        :null,
      bottomNavigationBar: _buildPlayerNavBar(),
    );
  }
  _buildPlayerNavBar() => Container(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
      decoration: BoxDecoration(
          boxShadow: const <BoxShadow>[
            BoxShadow(
              color: playerColor,
              blurRadius: 3,
            ),
          ],
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: playerColor.shade100),
      margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 10.0),
      child: GNav(
          gap: 5,
          activeColor: Colors.white,
          color: playerColor,
          tabBackgroundColor: Colors.black.withAlpha(25),
          duration: const Duration(milliseconds: 300),
          tabs: const <GButton>[
            GButton(
              backgroundColor: playerColor,
              text: "Profile",
              icon: Icons.person,
            ),
            GButton(
              backgroundColor: playerColor,
              text: "News Feed",
              icon: Icons.new_releases_sharp,
            ),
            GButton(
              backgroundColor: playerColor,
              text: "Reservations",
              icon: Icons.stadium,
            ),
          ],
          selectedIndex: ProfileBaseScreenPlayer._selectedPage,
          onTabChange: (x) => setState(() {ProfileBaseScreenPlayer._selectedPage = x;
          print(ProfileBaseScreenPlayer._selectedPage);})
          )
  );
}
