import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/classes/court.dart';

class CourtsHTTPsHandler {
  static final String _url = "http://${KickoffApplication.userIP}:8080/";

  static Future<List<Court>> getCourts(coid) async {
    print(coid);
    http.Response rsp = await http
        .get(Uri.parse("${_url}courtOwnerAgent/CourtOwner/$coid/Courts"));
    List<dynamic> courtsMap = json.decode(rsp.body);
    List<Court> courts = [];
    Court court;
    int time;
    print(rsp.body);
    for (Map<String, dynamic> map in courtsMap) {
      court = Court();
      court.cid = map['id'].toString();
      court.cname = map['cname'].toString();
      court.state = map['state'].toString();
      court.description = map['description'].toString();
      time = int.parse(map['swh'].toString().split(':')[0]);
      court.startingWorkingHours =
          (time % 24 < 12) ? '$time صباحاً' : '${time % 24} مساءً';
      time = int.parse(map['ewh'].toString().split(':')[0]);
      court.finishWorkingHours =
          (time % 24 < 12) ? '$time صباحاً' : '${time % 24} مساءً';
      court.minBookingHours = map['minBookingHours'].toString();
      court.morningCost = map['morningCost'].toString();
      court.nightCost = map['nightCost'].toString();
      time = int.parse(map['endMorning'].toString().split(':')[0]);
      court.morningFinish =
          (time % 24 < 12) ? '$time صباحاً' : '${time % 24} مساءً';
      courts.add(court);
    }
    return courts;
  }

  static Future sendCourt(courtInfo) async {
    print(courtInfo);
    print(KickoffApplication.ownerId);
    var response = await http.post(
        Uri.parse('${_url}courtOwnerAgent/CourtOwner/CreateCourt'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "ownerID": KickoffApplication.ownerId,
          "courtName": courtInfo[0],
          "description": courtInfo[1],
          "morningCost": courtInfo[2],
          "nightCost": courtInfo[3],
          "minBookingHours": courtInfo[4],
          "startWorkingHours": courtInfo[5],
          "endMorningHours": courtInfo[6],
          "finishWorkingHours": courtInfo[7],
        }));
    print(response.body);
  }
}
