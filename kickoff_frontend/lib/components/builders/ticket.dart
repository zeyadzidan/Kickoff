import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';

/// Reservation ticket builder class.
class Ticket {
  Container build(fixtureTicket) {
    List<dynamic> body = _buildBody(fixtureTicket);
    return Container(
      decoration: BoxDecoration(
          shape: BoxShape.rectangle,
          borderRadius: BorderRadius.circular(25),
          color: (_buildBody(fixtureTicket)[0])
              ? Colors.yellow.withOpacity(0.3)
              : kPrimaryColor.withOpacity(0.3)
      ),
      padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 0),
      margin: const EdgeInsets.symmetric(vertical: 5, horizontal: 10),
      alignment: Alignment.center,
      child: Column(
        children: List<Text>.generate(body.length - 1, (index) =>
            Text(
                body[index + 1].toString()
            )),
      ),
    );
  }

  _buildBody(fixtureTicket) => [
    fixtureTicket.isPending,
    'Player Name: ${fixtureTicket.pname}',
    'Start Date: ${fixtureTicket.startDate}',
    'End Date: ${fixtureTicket.endDate}',
    'Pending Status: ${
      (fixtureTicket.isPending) ? 'Pending' : 'Active'
    }',
    'Money Paid: ${fixtureTicket.paidAmount} EGP',
  ];
}