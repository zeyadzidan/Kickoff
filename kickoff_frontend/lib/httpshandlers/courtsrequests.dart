import 'dart:convert';

import 'package:http/http.dart' as http;

class CourtsHTTPsHandler {
  static String url = "http://192.168.1.49:8080/{PathOfTicketRequest}";

  static Future<List> getCourts() async {
    http.Response rsp = await http.get(Uri.parse("{Backend URL of Courts}"));
    // TODO: Creation of courts list.
    return [];
  }

  static Future sendInfo(cid, date) async {
    var rsp = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "Court Owner ID": null // TODO: Consider saving the court owner id
          ,
          "Court ID": cid,
          "Date": date
        }));
  }

  static Future<List> getCourtFixtures() async {
    http.Response rsp = await http.get(Uri.parse("{Backend URL of The Court Fixtures}"));
    // TODO: Creation of tickets list.
    return [];
  }
}