

import 'dart:core';


import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/courts/CourtsInSearch.dart';
import 'package:kickoff_frontend/components/login/SignUpRequestPlayer.dart';

import '../../httpshandlers/loginrequestsplayer.dart';
class SearchScreen extends StatefulWidget {

  @override
  State<SearchScreen> createState() => _SearchScreenState() ;
}


class _SearchScreenState extends  State<SearchScreen> {
 // static List<dynamic> courts= RoundedButton().courts;
  List<dynamic> displayList =List.from(RoundedLogin.courtsSearch);
  void updateList(String value){
    //this function is used to filter out list
    setState(() {
      displayList =RoundedLogin.courtsSearch.where((element) => element["courtOwnerName"].toLowerCase().contains(value.toLowerCase())).toList();
    });
  }
@override
  Widget build(BuildContext context)
{
  print(displayList);
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
                          title: Text("${displayList[index]["courtOwnerName"].toString()}",style: TextStyle(color: Colors.black,fontWeight: FontWeight.bold),
                          ),
                          subtitle: Text("${displayList[index]["distance"].toString()}km", style: TextStyle(color: Colors.black,),
                          ),
                          trailing: Text("${displayList[index]["rating"].toString()} \u{2B50}",style: TextStyle(color: Colors.black),
                          ),
                          leading:
                              CircleAvatar(
                                radius: 30,
                                child: ClipOval
                                  (child:Image.network(
                                  displayList[index]["courtOwnerPicture"],
                                    width: 100,
                                    height: 100,
                                    fit: BoxFit.cover,
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
