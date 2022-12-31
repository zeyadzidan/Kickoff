

import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import '../application/application.dart';
import '../application/screens/ProfileappearToPlayer.dart';

class Rating {
  // static final String _url =
  //     "http://${KickoffApplication.userIP}:8080/rating/addRating";

  static Future getratings(pid) async{
    var response = await http.get(
        Uri.parse('http://${KickoffApplication.userIP}:8080/rating/getRatings/$pid'));
    ProfileBaseScreenPlayer.ratings  = jsonDecode(response.body) as List<dynamic>;
    print(ProfileBaseScreenPlayer.ratings);
    // var res = await http.post(Uri.parse(_url),
    //     headers: {"Content-Type": "application/json"},
    //     body: json.encode({
    //       "courtOwnerId": CourtOnwerID,
    //       "playerId": PlayerID,
    //       "review": review,//TODO: make this dynamic
    //       "stars":stars
    //     }));
  }
  static final String url2 =
      "http://${KickoffApplication.userIP}:8080/rating/addRating";
  static Future postrating(CourtOnwerID,PlayerID,review,stars) async{
    var res = await http.post(Uri.parse(url2),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
                "courtOwnerId": CourtOnwerID,
                "playerId": PlayerID,
                "review": review,//TODO: make this dynamic
                "stars":stars
        }));
    print(res.body);
    // FieldValue arrayUnion(List<dynamic> elements) =>
    //     FieldValue._(FieldValueType.arrayUnion, elements);
    // courtsSearch= jsonDecode(res.body) as List<dynamic>;
    // LoginScreen.courtsSearch=jsonDecode(res.body) as List<dynamic>;
    // print(courtsSearch);
    print("lol");

  }

}
