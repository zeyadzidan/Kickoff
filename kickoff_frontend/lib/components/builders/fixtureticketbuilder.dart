import '../classes/fixtureticket.dart';

class FixtureTicketBuilder {
  final FixtureTicket _fixtureTicket = FixtureTicket();

  FixtureTicketBuilder buildId(id) => _fixtureTicket.ticketId = id;
  FixtureTicketBuilder buildPname(name) => _fixtureTicket.pname = name;
  FixtureTicketBuilder buildPid(pid) => _fixtureTicket.pid = pid;
  FixtureTicketBuilder buildConame(name) => _fixtureTicket.coname = name;
  FixtureTicketBuilder buildCoid(coid) => _fixtureTicket.coid = coid;
  FixtureTicketBuilder buildCname(name) => _fixtureTicket.cname = name;
  FixtureTicketBuilder buildCid(cid) => _fixtureTicket.cid = cid;
  FixtureTicketBuilder buildPaidAmount(amount) => _fixtureTicket.paidAmount = amount;
  FixtureTicketBuilder buildTotalCost(cost) => _fixtureTicket.totalCost = cost;
  FixtureTicketBuilder buildState(state) => _fixtureTicket.state = state;
  FixtureTicketBuilder buildStartDate(date) => _fixtureTicket.startDate = date;
  FixtureTicketBuilder buildEndDate(date) => _fixtureTicket.endDate = date;

  buildTicket() => _fixtureTicket;
}