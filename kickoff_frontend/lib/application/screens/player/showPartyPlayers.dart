

import 'dart:convert';
import 'dart:core';


import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application/screens/BuildComponentsPlayer.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
import 'package:kickoff_frontend/components/classes/partyPlayer.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/courts/CourtsInSearch.dart';
import 'package:kickoff_frontend/httpshandlers/SignUpRequestPlayer.dart';
import 'package:http/http.dart' as http;

import '../../../components/classes/Party.dart';
class showPartyPlayers extends StatefulWidget {
  static List<PartyPlayer> partyPlayers= [];
  static Party party =Party();
  @override
  State<showPartyPlayers> createState() => _showPartyPlayersState() ;
}


class _showPartyPlayersState extends  State<showPartyPlayers> {
  // static List<dynamic> courts= RoundedButton().courts;
  // List<dynamic> displayList =List.from(LoginScreen.courtsSearch);
  void updateList(String value){
    //this function is used to filter out list
    setState(() {
      // displayList =LoginScreen.courtsSearch.where((element) => element["courtOwnerName"].toLowerCase().contains(value.toLowerCase())).toList();
    });
  }
  // static final String _url = "http://${KickoffApplication.userIP}:8080";
  // Future<void> getListview(String CourtOwnerId)
  // async {
  //   var response = await http
  //       .get(Uri.parse('$_url/search/CourtOwner/${CourtOwnerId}'));
  //   print(response.body);
  //   setState(() => profileData = json.decode(response.body));
  //   KickoffApplication.dataPlayer= profileData;
  //   KickoffApplication.ownerId = profileData["id"].toString();
  //   ReservationsHome.reservations.clear();
  //   ProfileBaseScreen.courts =
  //   await CourtsHTTPsHandler.getCourts(KickoffApplication.ownerId);
  //   ProfileBaseScreen.isExpanded = List<bool>.generate(ProfileBaseScreen.courts.length, (index) => false);
  //   await ReservationsHome.buildTickets();
  //   await AnnouncementsHome.buildAnnouncements();
  //   ProfileBaseScreenPlayer.isSubscribed= await SubscriptionHTTPsHandler.issubscriber(KickoffApplication.playerId,KickoffApplication.ownerId);
  //   ProfileBaseScreenPlayer.subscribersCount= await SubscriptionHTTPsHandler.getSubscribersCount(KickoffApplication.ownerId);
  //   Navigator.pushNamed(context, '/profilePlayer');
  // }

  @override
  Widget build(BuildContext context)
  {
    return Scaffold(
      appBar:AppBar(
        title: Text("${showPartyPlayers.party.Pname}'s Party"),
        leading: IconButton(
            onPressed:()=> (
            Navigator.pop(context)
            ),
            icon: Icon(Icons.arrow_back_outlined),
      ) ,
      ),
      body:
      Container(
        padding: const EdgeInsets.fromLTRB(10, 15, 0, 0),
        child:Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
              child: ListView.builder(
                  itemCount: showPartyPlayers.partyPlayers.length ,
                  itemBuilder: (context,index)=> Padding(
                    padding: const EdgeInsets.fromLTRB(0, 0, 0, 10),

                    child: ListTile(
                      title: Text("${showPartyPlayers.partyPlayers[index].Pname}",style: TextStyle(color: Colors.black,fontWeight: FontWeight.bold,),
                      ),
                      leading:
                      CircleAvatar(
                        radius: 30,
                        child: ClipOval
                          (child:showPartyPlayers.partyPlayers[index].Pimg==""
                            ?Container()
                            :CachedNetworkImage(
                          imageUrl: showPartyPlayers.partyPlayers[index].Pimg,
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
                      // onTap: ()
                      // {
                      //   // getListview(displayList[index]["id"].toString());
                      // },
                      // subtitle: Text("${displayList[index]["distance"].toString()} km", style: TextStyle(color: Colors.black,),
                      // ),
                      // trailing: Text("${displayList[index]["rating"].toString()} \u{2B50}",style: TextStyle(color: Colors.black),
                      // ),
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
