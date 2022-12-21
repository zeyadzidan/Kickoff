

import 'dart:convert';
import 'dart:core';


import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application/screens/login.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/courts/CourtsInSearch.dart';
import 'package:kickoff_frontend/components/login/SignUpRequestPlayer.dart';
import 'package:http/http.dart' as http;
import '../../httpshandlers/courtsrequests.dart';
import '../../httpshandlers/loginrequestsplayer.dart';
import '../application.dart';
import 'announcements.dart';
class SearchScreen extends StatefulWidget {

  @override
  State<SearchScreen> createState() => _SearchScreenState() ;
}


class _SearchScreenState extends  State<SearchScreen> {
 // static List<dynamic> courts= RoundedButton().courts;
  List<dynamic> displayList =List.from(LoginScreen.courtsSearch);
  late Map<String, dynamic> profileData;
  void updateList(String value){
    //this function is used to filter out list
    setState(() {
      displayList =LoginScreen.courtsSearch.where((element) => element["courtOwnerName"].toLowerCase().contains(value.toLowerCase())).toList();
    });
  }
  static final String _url = "http://${KickoffApplication.userIP}:8080";
  Future<void> getListview(String CourtOwnerId)
  async {
    var response = await http
        .get(Uri.parse('$_url/search/CourtOwner/${CourtOwnerId}'));
    print(response.body);
    setState(() => profileData = json.decode(response.body));
    KickoffApplication.dataPlayer= profileData;
    var id = profileData["id"].toString();
    ProfileBaseScreen.courts =
    await CourtsHTTPsHandler.getCourts(id);
    // await ReservationsHome.buildTickets(); //TODO: to get reservations of court
    // await AnnouncementsHome.buildAnnouncements();//TODO : to get announcment of court
    Navigator.pushNamed(context, '/profilePlayer');
  }
@override
  Widget build(BuildContext context)
{
     return Scaffold(
      body:
          Container(
            padding: const EdgeInsets.fromLTRB(10, 15, 0, 0),
            child:Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Padding(
                  padding: const EdgeInsets.fromLTRB(0, 0, 0, 0),
                  child: Text(
                    "Search for a Court",
                    style: TextStyle(
                        color: Colors.black,
                        fontSize: 22.0,
                        fontWeight: FontWeight.bold
                    ),
                  ),
                ),
                SizedBox(
                  height: 23.0,
                ),
                TextField(
                  onChanged: (value )=> updateList(value),
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                    filled: true,
                    hintText: "Search",
                    fillColor: playerColor.withAlpha(50),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8.0),
                      borderSide: BorderSide.none,
                    ) ,
                    prefixIcon: Icon(Icons.search),
                    prefixIconColor:playerColor,
                  ),
                ),
                SizedBox(height: 10.0,),
                Expanded(
                    child: ListView.builder(
                        itemCount: displayList.length ,
                        itemBuilder: (context,index)=> Padding(
                          padding: const EdgeInsets.fromLTRB(0, 0, 0, 10),

                          child: ListTile(
                            title: Text("${displayList[index]["courtOwnerName"].toString()}",style: TextStyle(color: Colors.black,fontWeight: FontWeight.bold,),
                            ),
                            onTap: ()
                            {
                              getListview(displayList[index]["id"].toString());
                            },
                            subtitle: Text("${displayList[index]["distance"].toString()}km", style: TextStyle(color: Colors.black,),
                            ),
                            trailing: Text("${displayList[index]["rating"].toString()} \u{2B50}",style: TextStyle(color: Colors.black),
                            ),
                            leading:
                                CircleAvatar(
                                  radius: 30,
                                  child: ClipOval
                                    (child:displayList[index]["courtOwnerPicture"]==null
                                      ?Container()
                                      :CachedNetworkImage(
                                      imageUrl: displayList[index]["courtOwnerPicture"],
                                      width: 100,
                                      height: 100,
                                      fit: BoxFit.cover,
                                    progressIndicatorBuilder:
                                      (context, url, downloadProgress) =>
                                        CircularProgressIndicator(
                                            value: downloadProgress.progress),
                                    errorWidget:
                                      (context, url, error) =>
                                        Icon(Icons.error),
                                      )),
                                ),
                          ),
                        )
                    ),
                  ),
              ],
            ) ,
          ),
   );
}



}
