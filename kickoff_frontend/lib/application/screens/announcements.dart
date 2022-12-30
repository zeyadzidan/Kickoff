import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/announcements/announcement-view.dart';
import 'package:kickoff_frontend/httpshandlers/announcements-requests.dart';

import '../../components/announcements/viewposts.dart';
import '../../components/classes/announcement.dart';
import '../application.dart';

class AnnouncementsHome extends StatefulWidget {
  AnnouncementsHome({super.key,required full}) {
    if(full){
      buildFullAnnouncements();
    }
    else{
      buildAnnouncements();
    }
    isExpanded = List<bool>.generate(announcements.length, (index) => false);
  }

  static List<Announcement> announcements = [];
  static List<bool> isExpanded = [];

  static buildAnnouncements() async {
    AnnouncementsHome.announcements =
        await AnnouncementHTTPsHandler.getAnnouncements(
            KickoffApplication.ownerId);
  }

  static buildFullAnnouncements() async {
    AnnouncementsHome.announcements =
    await AnnouncementHTTPsHandler.getAnnouncementsbySubscriptions(
        KickoffApplication.playerId);

  }

  @override
  State<AnnouncementsHome> createState() => _AnnouncementsHomeState();
}

class _AnnouncementsHomeState extends State<AnnouncementsHome> {
  @override
  Widget build(BuildContext context) => PlusAnnouncementButton3();
}
