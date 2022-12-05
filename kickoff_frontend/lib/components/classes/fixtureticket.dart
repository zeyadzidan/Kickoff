/// A single ticket has the following attributes:
/// player name and id,
/// court owner name and id,
/// court name, number, or id,
/// money paid,
/// time.

class FixtureTicket {
  late String ticketId;
  late String pname;
  late String pid;
  late String coname;
  late String coid;
  late String cname;
  late String cid;
  late String paidAmount;
  late String totalCost;
  late String state;
  late String startDate;
  late String endDate;

  asList() => [
    pname, paidAmount, totalCost, state, startDate, endDate, ticketId
  ];
}