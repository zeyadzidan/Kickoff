import '../classes/fixtureticket.dart';

class FixtureTicketBuilder {
  late FixtureTicket _fixtureTicket;

  FixtureTicket buildPname(name) => _fixtureTicket.pname = name;
  FixtureTicket buildPid(pid) => _fixtureTicket.pid = pid;
  FixtureTicket buildConame(name) => _fixtureTicket.coname = name;
  FixtureTicket buildCoid(coid) => _fixtureTicket.coid = coid;
  FixtureTicket buildCname(name) => _fixtureTicket.cname = name;
  FixtureTicket buildCid(cid) => _fixtureTicket.cid = cid;
  FixtureTicket buildPaidAmount(amount) => _fixtureTicket.paidAmount = amount;
  FixtureTicket validate(value) => _fixtureTicket.isValidated = value;
  FixtureTicket setDate(date) => _fixtureTicket.date = date;
}