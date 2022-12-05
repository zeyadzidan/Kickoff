import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application.dart';

import '../constants.dart';

class CourtsHTTPsHandler {
  static const String port = "http://$ip:8080/";

  static Future<Map <String, dynamic>> getCourts(coid) async {
    http.Response rsp = await http
        .get(Uri.parse("${port}courtOwnerAgent/CourtOwner/$coid/Courts"));
    // TODO: Creation of courts list.
    print(rsp.body);
    return {};
  }

  static Future getCourtFixtures(cid, courtOwnerId, date) async {
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
