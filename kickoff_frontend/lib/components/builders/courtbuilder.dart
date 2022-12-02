import '../../fixtures/classes/court.dart';

class CourtBuilder {
  late Court _court;

  Court buildOwner(value) => _court.cOwner = value;
  Court buildCoid(value) => _court.coid = value;
  Court buildCname(value) => _court.cname = value;
  Court buildCid(value) => _court.cid = value;
  Court buildDescription(value) => _court.description = value;
}