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
  late bool _isPending;
  late String _startDate;
  late String _endDate;

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

  set isPending(bool value) {
    _isPending = value;
  }

  set startDate(String value) {
    _startDate = value;
  }

  set endDate(String value) {
    _endDate = value;
  }

  String get pname => _pname;

  String get pid => _pid;

  String get coname => _coname;

  String get coid => _coid;

  String get cname => _cname;

  String get cid => _cid;

  String get paidAmount => _paidAmount;

  bool get isValidated => _isValidated;

  bool get isPending => _isPending;

  String get startDate => _startDate;

  String get endDate => _endDate;
}