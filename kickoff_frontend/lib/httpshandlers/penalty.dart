import 'dart:convert';
import 'dart:core';

import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';

class PenaltyHTTPsHandler {
  static final String _url = "http://${KickoffApplication.userIP}:8080";

  static Future report(reporter, reported, player) async {
    var response = await http.post(
        Uri.parse(player ? '$_url/report/PtoP' : '$_url/report/CtoP'),
        headers: {"Content-Type": "application/json"},
        body: player
            ? json.encode({"coid": reporter, "pid": reported})
            : json.encode({"pid1": reporter, "pid2": reported}));
    print(response.body);
  }
}
