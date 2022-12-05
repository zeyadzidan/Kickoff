import 'dart:convert';

import 'package:http/http.dart' as http;

class CourtsHTTPsHandler {
  static const String port = "http://192.168.1.49:8080/";

  static Future<List> getCourts(coid) async {
    http.Response rsp = await http.get(Uri.parse("${port}courtOwnerAgent/CourtOwner/$coid/Courts"));
    // TODO: Creation of courts list.
    List<Object> courts = json.decode(rsp.body).map((entry) => (entry['cid'])).toList();
    print(courts);
    return courts;
  }

  static Future getCourtFixtures(cid, courtOwnerId, date) async {
    var rsp = await http.post(Uri.parse('${port}BookingAgent/reservationsOnDate'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "courtId": cid,
          "courtOwnerId": courtOwnerId // TODO: Consider saving the court owner id
          ,
          "date": date
        }));
    List<Object> courtFixtures = json.decode(rsp.body).map((entry) => (entry['cid'])).toList();
    print(courtFixtures);
    return courtFixtures;
  }

  // static Future<List> getCourtFixtures() async {
  //   http.Response rsp = await http.get(Uri.parse('${port}BookingAgent/reservationsOnDate'));
  //   List<Object> objects = json.decode(rsp.body).map((entry) => (entry['cid'])).toList();
  //   print(objects);
  //   print(rsp);
  //   return [];
  // }
}