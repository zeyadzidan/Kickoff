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
    'Player Name: $pname',
    'Start Time: $startTime',
    'Finish Time: $endTime',
    'State: $state',
    'Paid Amount: $paidAmount',
    'Total Cost: $totalCost'
  ];
}