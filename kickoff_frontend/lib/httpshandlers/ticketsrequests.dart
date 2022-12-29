import 'dart:convert';
import 'dart:core';
import 'dart:io';

import 'package:firebase_storage/firebase_storage.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
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
    var response = await http.post(Uri.parse(cancellation),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "id": ticket.ticketId,
        }));
    print(response.body);
  }

  static Future sendTicket(FixtureTicket ticket) async {
    var response = await http.post(Uri.parse('$_url/BookingAgent/setPending'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "playerName": ticket.pname,
          if (KickoffApplication.player) "playerId": ticket.pid,
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
    var rsp =
        await http.post(Uri.parse('$_url/BookingAgent/reservationsOnDate'),
            headers: {"Content-Type": "application/json"},
            body: json.encode({
              "courtId": cid,
              "courtOwnerId": courtOwnerId,
              "date": date,
              "ascending": ReservationsHome.ascendingView
            }));
    List<FixtureTicket> reservations = [];
    try {
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
        ticket.receiptUrl = map['receiptUrl'].toString();
        reservations.add(ticket);
      }
    } on Exception catch (_) {
      print("Reservations GET Error");
      print(rsp.body);
    }
    return reservations;
  }

  static Future<List<FixtureTicket>> getPlayerReservations(
      pid, filter, ascending) async {
    var rsp = await http.post(Uri.parse('$_url/BookingAgent/playerBookings'),
        headers: {"Content-Type": "application/json"},
        body: json
            .encode({"pid": pid, "filter": filter, "ascending": ascending}));
    print(rsp.body);
    List<FixtureTicket> reservations = [];
    json.decode(rsp.body);
    if (rsp.body != 'Player not found!') {
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
        ticket.receiptUrl = map['receiptUrl'].toString();
        reservations.add(ticket);
      }
    }
    return reservations;
  }

  static Future uploadReceipt(
    File file,
    final path,
  ) async {
    UploadTask? uploadTask;
    final ref = FirebaseStorage.instance.ref().child(path);
    uploadTask = ref.putFile(file);
    final snapshot = await uploadTask.whenComplete(() {});
    final imageUrl = await snapshot.ref.getDownloadURL();
    print(imageUrl);
    var response = await http.post(Uri.parse('$_url/BookingAgent/sendReceipt'),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "reservationId": KickoffApplication.playerId /* Player ID */,
          "receiptUrl": imageUrl.toString(),
        }));
    print(response.body);
  }
}
