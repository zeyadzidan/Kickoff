/// A single ticket has the following attributes:
/// player name and id,
/// court owner name and id,
/// court name, number, or id,
/// money paid,
/// time.

class FixtureTicket {
  late String ticketId;
  late String pname;
  late String coname;
  late String coid;
  late String cname;
  late String cid;
  late String paidAmount;
  late String totalCost;
  late String state;
  late String startDate;
  late String endDate;
  late String startTime;
  late String endTime;

  asList() => [
    pname, paidAmount, totalCost, state, startTime, endTime, ticketId
  ];
}