import 'dart:convert';
import 'dart:core';

import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';

class TicketsHTTPsHandler {
  static final String _url = "http://${KickoffApplication.userIP}:8080";
  
  static Future deleteTicket(FixtureTicket ticket) async {
    String cancellation;
    if (ticket.state == 'Booked') {
      cancellation = '$_url/BookingAgent/cancelBooking';
    } else {
      cancellation = '$_url/BookingAgent/cancelPending';
    }

    print(cancellation);

    print("TICKET: ${ticket.ticketId}");

    var response = await http.post(Uri.parse(cancellation),
      headers: {"Content-Type": "application/json"},
      body: json.encode({
        "id": ticket.ticketId,
      })
    );
    print(response.body);
  }

  static Future sendTicket(List<String> ticket) async {
    var response = await http.post(Uri.parse('$_url/BookingAgent/setPending'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "playerName": ticket[0],
          "courtId": ticket[1],
          "courtOwnerId": ticket[2],
          "startDate": ticket[3],
          "endDate": ticket[4],
          "startHour": ticket[5],
          "finishHour": ticket[6],
        }));
    print(response.body);
  }

  static Future bookTicket(FixtureTicket ticket) async {
    var response = await http.post(Uri.parse('$_url/BookingAgent/booking'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "reservationId": ticket.ticketId,
          "moneyPaid": ticket.paidAmount
        }));
    print(response.body);
  }

  static Future<List<FixtureTicket>> getCourtReservations(
      cid, courtOwnerId, date) async {
    var rsp = await http.post(
        Uri.parse('$_url/BookingAgent/reservationsOnDate'),
        headers: {"Content-Type": "application/json"},
        body: json.encode(
            {"courtId": cid, "courtOwnerId": courtOwnerId, "date": date}));
    print(rsp.body);
    List<FixtureTicket> reservations = [];
    if (rsp.body != 'Court Not found') {
      List<dynamic> fixturesMap = json.decode(rsp.body);
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
    }
    return reservations;
  }
}
