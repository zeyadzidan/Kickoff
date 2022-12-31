import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/application/screens/rating.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
import 'package:kickoff_frontend/components/courts/court-view.dart';
import 'package:kickoff_frontend/components/tickets/plusreservationbutton.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:url_launcher/url_launcher_string.dart';

import '../../components/classes/court.dart';
import '../../httpshandlers/Subscription.dart';

class ProfileBaseScreenPlayer extends StatefulWidget {
  ProfileBaseScreenPlayer({super.key}) {
    isExpanded = List<bool>.generate(courts.length, (index) => false);
  }

  static String? path = "";
  static List<Court> courts = <Court>[];
  static List<bool> isExpanded = <bool>[];
  static int _selectedPage = 0;
  static bool isSubscribed = false;
  static int subscribersCount = 0;

  @override
  State<ProfileBaseScreenPlayer> createState() =>
      _ProfileBaseScreenStatePlayer();

// static onTapSelect(index) =>
//     _currentState.setState(() => _selectedPage = index);
}

class _ProfileBaseScreenStatePlayer extends State<ProfileBaseScreenPlayer> {
  double rating = double.parse("${KickoffApplication.dataPlayer["rating"]}");
  int rating2 =
      double.parse("${KickoffApplication.dataPlayer["rating"]}").toInt();
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

  // bool isSubscribed= false;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.arrow_back),
          tooltip: 'back',
          onPressed: () {
            KickoffApplication.ownerId = '';
            AnnouncementsHome.buildFullAnnouncements();
            AnnouncementsHome.isExpanded = List<bool>.generate(
                AnnouncementsHome.announcements.length, (index) => false);
            Navigator.pop(context);
            KickoffApplication.update();
          },
        ),
        title: Text(
          name,
          style: TextStyle(color: secondaryColor),
        ),
      ),
      body: Center(
          child: ProfileBaseScreenPlayer._selectedPage == 0
              ? Container(
                  margin: const EdgeInsets.all(20),
                  child: Column(
                    children: [
                      Container(
                        decoration: BoxDecoration(
                            boxShadow: <BoxShadow>[
                              BoxShadow(
                                color: mainSwatch,
                                blurRadius: 3,
                              ),
                            ],
                            shape: BoxShape.rectangle,
                            borderRadius: BorderRadius.circular(5),
                            color: mainSwatch.shade100),
                        child: Column(
                          children: [
                            Container(
                              margin: const EdgeInsets.symmetric(
                                  vertical: 5, horizontal: 20),
                              padding: const EdgeInsets.fromLTRB(0, 30, 0, 0),
                              child: Row(
                                  mainAxisAlignment:
                                      MainAxisAlignment.spaceBetween,
                                  children: [
                                    CircleAvatar(
                                      radius: 40,
                                      backgroundColor: mainSwatch,
                                      child: foundPhoto
                                          ? ClipOval(
                                              child: CachedNetworkImage(
                                                imageUrl: utl,
                                                width: 100,
                                                height: 100,
                                                fit: BoxFit.cover,
                                                progressIndicatorBuilder:
                                                    (context, url,
                                                            downloadProgress) =>
                                                        CircularProgressIndicator(
                                                            value:
                                                                downloadProgress
                                                                    .progress),
                                                errorWidget:
                                                    (context, url, error) =>
                                                        Icon(Icons.error),
                                              ),
                                            )
                                          : Container(),
                                    ),
                                    Padding(
                                      padding: const EdgeInsets.fromLTRB(
                                          0, 6, 20, 0),
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
                                                  "${ProfileBaseScreenPlayer.subscribersCount} \u{1F464}",
                                                  style: const TextStyle(
                                                      letterSpacing: 0.4,
                                                      fontSize: 20,
                                                      fontWeight:
                                                          FontWeight.bold,
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
                                        child: !ProfileBaseScreenPlayer
                                                .isSubscribed
                                            ? ElevatedButton.icon(
                                                label: const Text("Subscribe"),
                                                icon: const Icon(Icons.add),
                                                style: ElevatedButton.styleFrom(
                                                  backgroundColor: mainSwatch,
                                                  fixedSize:
                                                      const Size(150, 25),
                                                ),
                                                onPressed: () {
                                                  SubscriptionHTTPsHandler
                                                      .subscribe(
                                                          KickoffApplication
                                                              .playerId,
                                                          KickoffApplication
                                                              .ownerId);
                                                  setState(() {
                                                    ProfileBaseScreenPlayer
                                                        .isSubscribed = true;
                                                    ProfileBaseScreenPlayer
                                                        .subscribersCount += 1;
                                                  });
                                                },
                                              )
                                            : OutlinedButton.icon(
                                                label:
                                                    const Text("Unsubscribe"),
                                                icon: const Icon(Icons.add),
                                                style: OutlinedButton.styleFrom(
                                                  foregroundColor: mainSwatch,
                                                  backgroundColor: Colors.white
                                                      .withAlpha(70),
                                                  side: BorderSide(
                                                      color: mainSwatch),
                                                  fixedSize: Size(150, 25),
                                                ),
                                                onPressed: () {
                                                  SubscriptionHTTPsHandler
                                                      .unsubscribe(
                                                          KickoffApplication
                                                              .playerId,
                                                          KickoffApplication
                                                              .ownerId);
                                                  setState(() {
                                                    ProfileBaseScreenPlayer
                                                        .isSubscribed = false;
                                                    ProfileBaseScreenPlayer
                                                        .subscribersCount -= 1;
                                                  });
                                                },
                                              )
                                        // Text(
                                        //   name,
                                        //   style: const TextStyle(
                                        //     letterSpacing: 0.4,
                                        //     fontSize: 20,
                                        //     color: Colors.black,
                                        //   ),
                                        // ),
                                        ),
                                    Padding(
                                      padding: const EdgeInsets.symmetric(
                                          vertical: 10.0),
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
                                            onTap: () => launchUrlString(
                                                "tel://$phone")),
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
                  ))
              : (ProfileBaseScreenPlayer._selectedPage == 1)
                  ? AnnouncementsHome(
                      full: false,
                    )
                  : ReservationsHome()),
      floatingActionButton: ProfileBaseScreenPlayer._selectedPage == 2
          ? const PlusReservationButton()
          : ProfileBaseScreenPlayer._selectedPage == 0
              ? RatingButton()
              : null,
      bottomNavigationBar: _buildPlayerNavBar(),
    );
  }

  _buildPlayerNavBar() => Container(
      padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal: 15.0),
      decoration: BoxDecoration(
          boxShadow: <BoxShadow>[
            BoxShadow(
              color: mainSwatch,
              blurRadius: 3,
            ),
          ],
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(100),
          color: mainSwatch.shade100),
      margin: const EdgeInsets.symmetric(vertical: 20.0, horizontal: 10.0),
      child: GNav(
          gap: 5,
          activeColor: Colors.white,
          color: mainSwatch,
          tabBackgroundColor: Colors.black.withAlpha(25),
          duration: const Duration(milliseconds: 300),
          tabs: <GButton>[
            GButton(
              backgroundColor: mainSwatch,
              text: "Profile",
              icon: Icons.person,
            ),
            GButton(
              backgroundColor: mainSwatch,
              text: "News Feed",
              icon: Icons.new_releases_sharp,
            ),
            GButton(
              backgroundColor: mainSwatch,
              text: "Reservations",
              icon: Icons.stadium,
            ),
          ],
          selectedIndex: ProfileBaseScreenPlayer._selectedPage,
          onTabChange: (x) => setState(() {
                ProfileBaseScreenPlayer._selectedPage = x;
                print(ProfileBaseScreenPlayer._selectedPage);
              })));
}
