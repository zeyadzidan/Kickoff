import 'dart:convert';
import 'dart:core';

import 'package:http/http.dart' as http;

class NewTicket {
  static String url = "http://192.168.1.49:8080/{PathOfTicketRequest}";
  static Future sendTicket(ticketInfo) async {
    var rsp = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "Player Name": ticketInfo[0],
          "Money Paid": ticketInfo[1],
          "Court Number": ticketInfo[2],
          "Date": ticketInfo[3],
          "Start Time": ticketInfo[4],
          "End Time": ticketInfo[5],
        }));
  }
}
