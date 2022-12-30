import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/components/announcements/announcement-view.dart';
import 'package:kickoff_frontend/httpshandlers/announcements-requests.dart';

import '../../components/announcements/viewposts.dart';
import '../../components/classes/Party.dart';
import '../../components/classes/announcement.dart';
import '../../constants.dart';
import '../../httpshandlers/Parties Requests.dart';
import '../application.dart';

class AnnouncementsHome extends StatefulWidget {
  AnnouncementsHome({super.key,required full}) {
    if(full){
      buildFullAnnouncements();
    }
    else{
      buildAnnouncements();
    }
  isFull=full;
  isExpanded = List<bool>.generate(announcements.length, (index) => false);
  }

  static List<Announcement> announcements = [];
  static List<Party> parties = [];
  static bool isFull=false;
  static List<bool> isExpanded = [];

  static buildAnnouncements() async {
    AnnouncementsHome.announcements =
        await AnnouncementHTTPsHandler.getAnnouncements(
            KickoffApplication.ownerId);
    AnnouncementsHome.parties =
        await PartiesHTTPsHandler.getPartiesCourtOwner(
            KickoffApplication.ownerId);

  }

  static buildFullAnnouncements() async {
    AnnouncementsHome.announcements =
    await AnnouncementHTTPsHandler.getAnnouncementsbySubscriptions(
        KickoffApplication.playerId);
    AnnouncementsHome.parties =
    await PartiesHTTPsHandler.getPartiesSubscribedNotJoined(
        KickoffApplication.playerId);
  }

  @override
  State<AnnouncementsHome> createState() => _AnnouncementsHomeState();
}

class _AnnouncementsHomeState extends State<AnnouncementsHome> {
  int page=0;
  @override
  Widget build(BuildContext context) {
    if (page==1){
      PlusAnnouncementButton3.isParty=true;
    }
    else
      PlusAnnouncementButton3.isParty=false;
    return Column(
    children: [
      _reservationsStatesNavBar(),
      PlusAnnouncementButton3(),
    ],
  );
  }
  _reservationsStatesNavBar() => Container(
    padding: const EdgeInsets.symmetric(vertical: 10.0, horizontal:20),
    width: 300,
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
    margin: const EdgeInsets.symmetric(vertical: 10),

    child: GNav(
      gap: 8,
      tabs: [
        _createGButton(Icons.book_online,"Anouncements", Colors.green),
        _createGButton(Icons.pending,"Parties", Colors.orange),
      ],
      selectedIndex:page ,
      onTabChange: (index){
        if(AnnouncementsHome.isFull){
          AnnouncementsHome.buildFullAnnouncements();
        }
        else{
          AnnouncementsHome.buildAnnouncements();
        }
          setState(() {
          page=index;
          if (index==1){
            PlusAnnouncementButton3.isParty=true;
          }
          else
            PlusAnnouncementButton3.isParty=false;
      });
      },
      duration: const Duration(seconds: 1),
      color: mainSwatch,
      activeColor: secondaryColor,
      tabBackgroundColor: Colors.black.withAlpha(25),
    ),
  );

  _createGButton(icon,text, color) => GButton(
    icon: icon,
    backgroundColor: color,
    text: text,
    textSize: 2,
  );
}
