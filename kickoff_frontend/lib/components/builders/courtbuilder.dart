import '../classes/court.dart';

class CourtBuilder {
  late Court _court = Court();

  CourtBuilder buildCname(value) => _court.cname = value;

  CourtBuilder buildCid(value) => _court.cid = value;

  Court buildCourt() => _court = Court();
}
