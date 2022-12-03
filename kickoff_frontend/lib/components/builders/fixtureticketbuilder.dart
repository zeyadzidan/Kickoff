import '../classes/fixtureticket.dart';

class FixtureTicketBuilder {
  final FixtureTicket _fixtureTicket = FixtureTicket();

  buildPname(name) => _fixtureTicket.pname = name;
  buildPid(pid) => _fixtureTicket.pid = pid;
  buildConame(name) => _fixtureTicket.coname = name;
  buildCoid(coid) => _fixtureTicket.coid = coid;
  buildCname(name) => _fixtureTicket.cname = name;
  buildCid(cid) => _fixtureTicket.cid = cid;
  buildPaidAmount(amount) => _fixtureTicket.paidAmount = amount;
  buildValidatedStatus(value) => _fixtureTicket.isValidated = value;
  buildPendingStatus(value) => _fixtureTicket.isPending = value;
  buildStartDate(date) => _fixtureTicket.startDate = date;
  buildEndDate(date) => _fixtureTicket.endDate = date;

  buildTicket() => _fixtureTicket;
}