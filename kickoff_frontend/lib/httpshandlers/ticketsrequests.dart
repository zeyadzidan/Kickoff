import 'dart:convert';
import 'dart:core';

import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';

import '../constants.dart';

class Tickets {
  static String url = "http://$ip:8080";
  static Future sendTicket(FixtureTicket ticket) async {
    var response = await http.post(Uri.parse('$url/BookingAgent/setPending'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "playerName": ticket.pname,
          "courtId": ticket.cid,
          "courtOwnerId": ticket.coid,
          "startDate": ticket.startDate,
          "endDate": ticket.endDate,
          "startHour": ticket.startTime,
          "finishHour": ticket.endTime,
        }));

    print(response.body);
  }

  static Future bookTicket(FixtureTicket ticket) async {
    var response = await http.post(Uri.parse('$url/BookingAgent/booking'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "reservationId": ticket.ticketId,
          "moneyPaid": ticket.paidAmount
        }));
    print(response.body);
  }

  static Future<List<FixtureTicket>> getCourtFixtures(
      cid, courtOwnerId, date) async {
    print("ENTERED REQUEST");
    var rsp = await http.post(Uri.parse('$url/BookingAgent/reservationsOnDate'),
        headers: {"Content-Type": "application/json"},
        body: json.encode(
            {"courtId": cid, "courtOwnerId": courtOwnerId, "date": date}));
    print(rsp.body);
    List<dynamic> fixturesMap = json.decode(rsp.body);
    List<FixtureTicket> reservations = [];
    FixtureTicket ticket; // The court model
    for (Map<String, dynamic> map in fixturesMap) {
      ticket = FixtureTicket();
      ticket.ticketId = map['id'].toString();
      ticket.pname = map['playerName'].toString();
      ticket.cid = map['courtID'].toString();
      ticket.startDate = map['startDate'].toString();
      ticket.endDate = map['endDate'].toString();
      ticket.startTime = map['timeFrom'].toString();
      ticket.endTime = map['timeTo'].toString();
      ticket.state = map['state'].toString();
      ticket.paidAmount = map['moneyPayed'].toString();
      ticket.totalCost = map['totalCost'].toString();
      reservations.add(ticket);
    }
    return reservations;
  }
}
