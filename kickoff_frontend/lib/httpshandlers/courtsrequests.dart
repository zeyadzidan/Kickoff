import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application.dart';
import 'package:kickoff_frontend/components/classes/court.dart';
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';

import '../constants.dart';

class CourtsHTTPsHandler {
  static const String port = "http://$ip:8080/";

  static Future<List<Court>> getCourts(coid) async {
    http.Response rsp = await http
        .get(Uri.parse("${port}courtOwnerAgent/CourtOwner/$coid/Courts"));
    // TODO: Creation of courts list.
    print(rsp.body);
    List<dynamic> courtsMap = json.decode(rsp.body);
    print(courtsMap);
    List<Court> courts = [];
    Court court; // The court model
    for (Map<String, dynamic> map in courtsMap) {
      court = Court();
      court.cid = map['id'].toString();
      court.cname = map['name'].toString();
      courts.add(court);
    }
    return courts;
  }

  static Future<List<FixtureTicket>> getCourtFixtures(cid, courtOwnerId, date) async {
    var rsp =
        await http.post(Uri.parse('${port}BookingAgent/reservationsOnDate'),
            headers: {"Content-Type": "application/json"},
            body: json.encode({
              "courtId": cid,
              "courtOwnerId":
                  courtOwnerId // TODO: Consider saving the court owner id
              ,
              "date": date
            }));
    print(rsp.body);
    return [];
  }

  static Future sendCourt(courtInfo) async {
    var response = await http.post(Uri.parse('${port}courtOwnerAgent/CourtOwner/CreateCourt'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "ownerID": KickoffApplication.OWNER_ID,
          "courtName": courtInfo[0],
          "description": courtInfo[1],
          "morningCost": courtInfo[2],
          "nightCost": courtInfo[3],
          "minBookingHours": courtInfo[4],
          "startWorkingHours": courtInfo[5],
          "finishWorkingHours": courtInfo[6],
        }));
    print(response);
  }
}
