class Court {
  late String _cOwner;  // Court Owner Name.
  late String _coid;  // Court Owner ID.
  late String _cname; // Court Name.
  late String _cid; // Court ID.
  late String _description; // Description

  set cOwner(String value) {
    _cOwner = value;
  }

  set coid(String value) {
    _coid = value;
  }

  set cid(String value) {
    _cid = value;
  }

  set cname(String value) {
    _cname = value;
  }

  set description(String value) {
    _description = value;
  }
}