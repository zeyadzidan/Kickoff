/// A single ticket has the following attributes:
/// player name and id,
/// court owner name and id,
/// court name, number, or id,
/// money paid,
/// time.

class FixtureTicket {
  late String _pname;
  late String _pid;
  late String _coname;
  late String _coid;
  late String _cname;
  late String _cid;
  late String _paidAmount;
  late bool _isValidated;
  late DateTime _date;


  String get pname => _pname;

  set pname(String value) {
    _pname = value;
  }

  set pid(String value) {
    _pid = value;
  }

  set coname(String value) {
    _coname = value;
  }

  set coid(String value) {
    _coid = value;
  }

  set cname(String value) {
    _cname = value;
  }

  set cid(String value) {
    _cid = value;
  }

  set paidAmount(String value) {
    _paidAmount = value;
  }

  set isValidated(bool value) {
    _isValidated = value;
  }

  set date(DateTime value) {
    _date = value;
  }

  String get pid => _pid;

  String get coname => _coname;

  String get coid => _coid;

  String get cname => _cname;

  String get cid => _cid;

  String get paidAmount => _paidAmount;

  bool get isValidated => _isValidated;

  DateTime get date => _date;
}