import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/announcements/announcement-view.dart';
import 'package:kickoff_frontend/httpshandlers/announcements-requests.dart';

import '../../components/classes/announcement.dart';
import '../application.dart';

class AnnouncementsHome extends StatefulWidget {
  AnnouncementsHome({super.key}) {
    buildAnnouncements();
    isExpanded = List<bool>.generate(announcements.length, (index) => false);
  }

  static List<Announcement> announcements = [];
  static List<bool> isExpanded = [];

  static buildAnnouncements() async {
    AnnouncementsHome.announcements =
        await AnnouncementHTTPsHandler.getAnnouncements(
            KickoffApplication.ownerId);
  }

  @override
  State<AnnouncementsHome> createState() => _AnnouncementsHomeState();
}

class _AnnouncementsHomeState extends State<AnnouncementsHome> {
  @override
  Widget build(BuildContext context) {
    return const AnnouncementsView();
  }
}
