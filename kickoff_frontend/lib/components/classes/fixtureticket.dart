/// A single ticket has the following attributes:
/// player name and id,
/// court owner name and id,
/// court name, number, or id,
/// money paid,
/// time.

class FixtureTicket {
  String ticketId = '';
  String pname = '';
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

  asView() => [
        'اسم اللاعب صاحب الحجز: $pname',
        'موعد بداية الحجز: $startTime',
        'موعد نهاية الحجز: $endTime',
        'حالة الحجز: ${(state == 'Pending') ? 'يحتاج تأكيداً' : (state == 'Booked') ? 'مؤكد' : 'منتهي'}',
        'العربون: $paidAmount جنيهاً مصرياً',
        'التكلفة الإجمالية: $totalCost جنيهاً مصرياً'
      ];
}
