import 'dart:convert';
import 'dart:io';

import 'package:firebase_storage/firebase_storage.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/components/classes/announcement.dart';

import '../application/application.dart';

class SubscriptionHTTPsHandler {
  static final String _url = "http://${KickoffApplication.userIP}:8080/subscriber";

  static Future subscribe(pid , coid ) async {
    var response = await http.post(
        Uri.parse('$_url/subscribe'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          "pid": pid,
          "coid": coid,
        }));
    print(response.body);
  }

  static Future unsubscribe(pid , coid ) async {
    var response = await http.post(
        Uri.parse('$_url/unsubscribe'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          "pid": pid,
          "coid": coid,
        }));
    print(response.body);
  }

  static Future<bool> issubscriber(pid , coid ) async {
    var response = await http.post(
        Uri.parse('$_url/isSubscriber'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          "pid": pid,
          "coid": coid,
        }));
    print(response.body);
    return json.decode(response.body);
  }


  //
  // static Future<List<Announcement>> getAnnouncements(coid) async {
  //   var response = await http
  //       .get(Uri.parse('$_url/courtOwnerAgent/CourtOwner/$coid/Announcements'));
  //   List<dynamic> announcementsMap = json.decode(response.body);
  //   Announcement announcement;
  //   List<Announcement> announcements = <Announcement>[];
  //   print(response.body);
  //   for (Map<String, dynamic> map in announcementsMap) {
  //     announcement = Announcement();
  //     announcement.coid = map['courtOwnerId'].toString();
  //     announcement.aid = map['id'].toString();
  //     announcement.title = map['title'].toString();
  //     announcement.body = map['body'].toString();
  //     if (map.containsKey('cni')) {
  //       announcement.img = map['cni'].toString();
  //     }
  //     announcement.date = map['date'].toString();
  //     announcements.add(announcement);
  //   }
  //   return announcements;
  // }
  //
  // static Future deleteAnnouncement(String aid) async {
  //   var response = await http.post(
  //       Uri.parse(
  //           '$_url/courtOwnerAgent/CourtOwner/${KickoffApplication.ownerId}/deleteAnnouncement'),
  //       headers: {"Content-Type": "application/json"},
  //       body: json.encode({
  //         "id": aid,
  //       }));
  //   print(response.body);
  // }
  //
  // static Future<String> uploadAnnouncementImageFile(File file, final path) async {
  //   UploadTask? uploadTask;
  //   final ref = FirebaseStorage.instance.ref().child(path);
  //   uploadTask = ref.putFile(file);
  //   final snapshot = await uploadTask.whenComplete(() {});
  //   final imageUrl = await snapshot.ref.getDownloadURL();
  //   return imageUrl;
  // }
}
