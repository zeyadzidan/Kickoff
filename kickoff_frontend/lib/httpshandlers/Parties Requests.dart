import 'dart:convert';
import 'dart:io';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:http/http.dart' as http;

import '../application/application.dart';
import '../components/classes/Party.dart';

class PartiesHTTPsHandler {
  static final String _url = "http://${KickoffApplication.userIP}:8080";

  static Future createParty(Rid , empty , full) async {
    var response = await http.post(
        Uri.parse('$_url/party/createparty'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          "reservationId": Rid,
          "emptyplaces": empty,
          "fullplaces": full,
        }));
    print(response.body);
  }

  static Future<List<Party>> getPartiesCourtOwner(coid) async {
    var response = await http.post(
        Uri.parse('$_url/party/get_parties_of_courtOwner'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          "id": coid,
        }));
    print(response.body);
    // List<dynamic> announcementsMap = json.decode(response.body);
    // Announcement announcement;
    // List<Announcement> announcements = <Announcement>[];
    // print(response.body);
    // for (Map<String, dynamic> map in announcementsMap) {
    //   announcement = Announcement();
    //   announcement.coid = map['courtOwnerId'].toString();
    //   announcement.aid = map['id'].toString();
    //   announcement.body = map['body'].toString();
    //   if (map.containsKey('cni')) {
    //     announcement.img = map['cni'].toString();
    //   }
    //   if (map.containsKey('courtPic ')) {
    //     announcement.Pimg = map['courtPic'].toString();
    //   }
    //   announcement.date = map['date'].toString();
    //   announcement.time = map['time'].toString().substring(0,(map['time'].toString().lastIndexOf(":")));
    //   announcement.name = map['name'].toString();
    //   announcements.add(announcement);
    // }
    return [];
  }

  static Future<List<Party>> getPartiesCreated(pid) async {
    var response = await http.post(
        Uri.parse('$_url/party/get_parties_created_by_player'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          "id": pid,
        }));
    print(response.body);
    // Announcement announcement;
    // List<Announcement> announcements = <Announcement>[];
    // List<dynamic> announcementsMap = json.decode(response.body);
    // print(response.body);
    // for (Map<String, dynamic> map in announcementsMap) {
    //   announcement = Announcement();
    //   announcement.coid = map['courtOwnerId'].toString();
    //   announcement.aid = map['id'].toString();
    //   announcement.body = map['body'].toString();
    //   if (map.containsKey('cni')) {
    //     announcement.img = map['cni'].toString();
    //   }
    //   if (map.containsKey('courtPic')) {
    //     announcement.Pimg = map['courtPic'].toString();
    //   }
    //   announcement.date = map['date'].toString();
    //   announcement.time = map['time'].toString().substring(0,(map['time'].toString().lastIndexOf(":")));
    //   announcement.name = map['name'].toString();
    //   announcements.add(announcement);
    // }

    return [];
  }

  static Future<List<Party>> getPartiesJoined(pid) async {
    var response = await http.post(
        Uri.parse('$_url/party/get_parties_joined_by_player'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode({
          "id": pid,
        }));
    print(response.body);
    // Announcement announcement;
    // List<Announcement> announcements = <Announcement>[];
    // List<dynamic> announcementsMap = json.decode(response.body);
    // print(response.body);
    // for (Map<String, dynamic> map in announcementsMap) {
    //   announcement = Announcement();
    //   announcement.coid = map['courtOwnerId'].toString();
    //   announcement.aid = map['id'].toString();
    //   announcement.body = map['body'].toString();
    //   if (map.containsKey('cni')) {
    //     announcement.img = map['cni'].toString();
    //   }
    //   if (map.containsKey('courtPic')) {
    //     announcement.Pimg = map['courtPic'].toString();
    //   }
    //   announcement.date = map['date'].toString();
    //   announcement.time = map['time'].toString().substring(0,(map['time'].toString().lastIndexOf(":")));
    //   announcement.name = map['name'].toString();
    //   announcements.add(announcement);
    // }

    return [];
  }

  static Future joinParty(PartyID,pid) async {
    var response = await http.post(
        Uri.parse(
            '$_url/party/joinparty'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "id": PartyID,
          "pid": pid,
        }));
    print(response.body);
  }

  static Future leaveParty(PartyID,pid) async {
    var response = await http.post(
        Uri.parse(
            '$_url/party/leaveparty'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "id": PartyID,
          "pid": pid,
        }));
    print(response.body);
  }

  static Future deleteParty(PartyID) async {
    var response = await http.post(
        Uri.parse(
            '$_url/party/deleteparty'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "id": PartyID,
        }));
    print(response.body);
  }

  static Future<String> uploadAnnouncementImageFile(
      File file, final path) async {
    UploadTask? uploadTask;
    final ref = FirebaseStorage.instance.ref().child(path);
    uploadTask = ref.putFile(file);
    final snapshot = await uploadTask.whenComplete(() {});
    final imageUrl = await snapshot.ref.getDownloadURL();
    return imageUrl;
  }
}
