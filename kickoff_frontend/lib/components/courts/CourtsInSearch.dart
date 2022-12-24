

class CourtModel
{
  int ? id;
  String ? courtOwnerName;
  String ? courtOwnerPicture;
  double ? distance;
  double ? rating ;
  CourtModel(this.id,this.courtOwnerName,this.courtOwnerPicture,this.distance,this.rating);
  factory CourtModel.fromJson(dynamic json) {
    print("yes");
    print(json);
    return CourtModel(json['id'] as int,json['courtOwnerName'] as String, json['courtOwnerPicture'] as String,json['distance'] as double,json['rating'] as double);

  }
}