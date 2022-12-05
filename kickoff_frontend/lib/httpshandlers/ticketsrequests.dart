import 'dart:convert';
import 'dart:core';

import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';

import '../constants.dart';

class Tickets {
  static String url = "http://$ip:8080";
  static Future sendTicket(FixtureTicket ticket) async {
    await http.post(Uri.parse('$url/BookingAgent/setPending'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "playerName": ticket.pname,
          "courtId": ticket.cid,
          "courtOwnerId": ticket.coid,
          "startDate": ticket.startDate,
          "endDate": ticket.endDate,
          "startHour": ticket.startTime,
          "finishHour": ticket.endTime,
        }
      )
    );
  }

  static Future bookTicket(FixtureTicket ticket) async {
    await http.post(Uri.parse('$url/BookingAgent/booking'),
      headers: {"Content-Type": "application/json"},
      body: json.encode(
        {
          "reseravtionId": ticket.ticketId,
          "moneyPaid": ticket.paidAmount
        }
      )
    );
  }
}
