import 'package:flutter/material.dart';
import 'package:url_launcher/url_launcher_string.dart';

/// A single ticket has the following attributes:
/// player name and id,
/// court owner name and id,
/// court name, number, or id,
/// money paid,
/// time.

class FixtureTicket {
  String ticketId = '';
  String pname = '';
  String pnumber = '';
  String pid = '';
  String coname = '';
  String coid = '';
  String cname = '';
  String cid = '';
  String paidAmount = '0';
  String totalCost = '';
  String state = 'Pending';
  String startDate = '';
  String endDate = '';
  String startTime = '';
  String endTime = '';
  String receiptUrl = '';

  asView() => [
        Text('اسم اللاعب صاحب الحجز: $pname'),
        InkWell(
            child: Text(
              "رقم الهاتف: \u{1F4DE} $pnumber",
            ),
            onTap: () => launchUrlString("tel://$pnumber")),
        Text('موعد بداية الحجز: $startTime'),
        Text('موعد نهاية الحجز: $endTime'),
        Text('حالة الحجز: ${(state == 'Pending') ? 'يحتاج تأكيداً' : (state == 'Booked') ? 'مؤكد' : (state == 'Expired') ? 'منتهي' : 'قيد التأكيد'}'),
        Text('العربون: $paidAmount جنيهاً مصرياً'),
        Text('التكلفة الإجمالية: $totalCost جنيهاً مصرياً')
      ];

  asPlayerView() => [
        Text('Time: $startTime - $endTime'),
        Text('Date: $startDate'),
        Text('Paid: $paidAmount EGP'),
        Text('Total Cost: $totalCost EGP')
      ];
}
