import 'dart:convert';

import 'package:cached_network_image/src/cached_image_widget.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/components/classes/announcement.dart';

import '../application/application.dart';

class AnnouncementHTTPsHandler {
  static final String _url = "http://${KickoffApplication.userIP}:8080";

  static Future sendAnnouncement(Announcement announcement) async {
    var response = await http.post(Uri.parse('$_url/courtOwnerAgent/CourtOwner/CreateAnnouncement'),
      headers: {'Content-Type':'application/json'},
      body: json.encode({
        "courtOwnerId": announcement.coid,
        "title": announcement.title,
        "body": announcement.body,
        "attachments": announcement.cni.imageUrl, // TODO: Not yet clear
        "date": announcement.date
      })
    );
    print(response.body);
  }

  static Future<List<Announcement>> getAnnouncements(coid) async {
    var response = await http.get(Uri.parse('$_url/courtOwnerAgent/CourtOwner/$coid/Announcements'));
    List<dynamic> announcementsMap = json.decode(response.body);
    Announcement announcement;
    List<Announcement> announcements = <Announcement>[];
    for (Map<String, dynamic> map in announcementsMap) {
      announcement = Announcement();
      announcement.coid = map['courtOwnerId'].toString();
      announcement.aid = map['announcementId'].toString();
      announcement.title = map['title'].toString();
      announcement.body = map['body'].toString();
      announcement.cni = map['attachments'].toString() as CachedNetworkImage; // TODO: Not yet clear
      announcement.date = map['date'].toString();
      announcements.add(announcement);
    }
    return announcements;
  }

  static Future deleteAnnouncement(Announcement announcement) async {
    var response = await http.post(Uri.parse('$_url/courtOwnerAgent/CourtOwner/${announcement.coid}/deleteAnnouncement'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "id": announcement.aid,
          "courtOwnerId": announcement.coid
        }));
    print(response.body);
  }
}