import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';

/// Reservation ticket builder class.
class Ticket {
  GestureDetector build(fixtureTicket) {
    List<dynamic> body = _buildBody(fixtureTicket);
    return GestureDetector(
      // onDoubleTap: _setPending(),
      child: Container(
        decoration: BoxDecoration(
            shape: BoxShape.rectangle,
            borderRadius: BorderRadius.circular(25),
            color: (_buildBody(fixtureTicket)[0] == 'Pending')
                ? Colors.yellow.withOpacity(0.3)
                : (_buildBody(fixtureTicket)[0] == 'Active')
                  ? kPrimaryColor.withOpacity(0.3)
                  : Colors.red.withOpacity(0.3) // Expired
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
      ),
    );
  }

  _buildBody(fixtureTicket) => [
    fixtureTicket.isPending,
    'Player Name: ${fixtureTicket.pname}',
    'Start Date: ${fixtureTicket.startDate}',
    'End Date: ${fixtureTicket.endDate}',
    'State: ${fixtureTicket.state}',
    'Money Paid: ${fixtureTicket.paidAmount} EGP',
    'Total Cost: ${fixtureTicket.totalCost} EGP',
  ];
}