

import 'dart:core';


import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/components/courts/CourtsInSearch.dart';
class SearchScreen extends StatefulWidget {
  @override
  State<SearchScreen> createState() => _SearchScreenState() ;
}


class _SearchScreenState extends  State<SearchScreen> {
 static List<CourtModel> courts= [
   CourtModel("Real Madrid", "https://tse2.mm.bing.net/th?id=OIP.Cf49DKmLu4W8RoFSAr3cjgHaEK&pid=Api&P=0",203.4, 10.0),
   CourtModel("Barcelona", "https://tse2.mm.bing.net/th?id=OIP.hqvB9m3gjaRljBpaW8xyZwHaEK&pid=Api&P=0",555, 8.5),
   CourtModel("AC Milan", "https://tse4.mm.bing.net/th?id=OIP.On8Gpq2m224QNu38wFjU0QHaEK&pid=Api&P=0",1000, 6.0),
 ];
  List<CourtModel> displayList =List.from(courts);

  void updateList(String value){
    //this function is used to filter out list

    setState(() {
      displayList =courts.where((element) => element.CourtOwnerName!.toLowerCase().contains(value.toLowerCase())).toList();
    });
  }

@override
  Widget build(BuildContext context)
{
   return Scaffold(
      body:
      Stack(
        children: [
          Positioned(
              top: 100,
              right: -50,
              child: Container(
                width: 100,
                height: 100,
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(50),

                    boxShadow: const <BoxShadow>[
                      BoxShadow(
                        color: Colors.black,
                        blurRadius: 5,
                      ),
                    ],
                    color: PlayerColor),
              )),

          Positioned(
            top: -80,
            left: -80,
            child: Container(
              width: 200,
              height: 200,
              decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(100),
                  boxShadow: const <BoxShadow>[
                    BoxShadow(
                      color: Colors.black,
                      blurRadius: 5,
                    ),
                  ],
                  color: PlayerColor),
              child: Padding(
                padding: const EdgeInsets.fromLTRB(55, 100, 0, 0),
              ),
            ),

          ),

          Positioned(
              bottom: -100,
              left: -100,
              child: Container(
                width: 290,
                height: 290,
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(145),
                    boxShadow: const <BoxShadow>[
                      BoxShadow(
                        color: Colors.black,
                        blurRadius: 5,
                      ),
                    ],
                    color: PlayerColor),
              )),
          Container(
            padding: const EdgeInsets.fromLTRB(0, 70, 0, 0),

            child:Column(
              mainAxisAlignment: MainAxisAlignment.start,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Padding(
                  padding: const EdgeInsets.fromLTRB(20, 0, 0, 0),
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
                  height: 25.0,
                ),
                TextField(
                  onChanged: (value )=> updateList(value),
                  style: TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                    filled: true,
                    hintText: "Search",
                    fillColor: PlayerColor.withAlpha(50),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8.0),
                      borderSide: BorderSide.none,
                    ) ,
                    prefixIcon: Icon(Icons.search),
                    prefixIconColor:PlayerColor,
                  ),
                ),
                SizedBox(height: 10.0,),
                Expanded(

                  child: ListView.builder(

                      itemCount: displayList.length ,

                      itemBuilder: (context,index)=> Padding(
                        padding: const EdgeInsets.fromLTRB(0, 0, 0, 10),
                        child: ListTile(
                          title: Text(displayList[index].CourtOwnerName!,style: TextStyle(color: Colors.black,fontWeight: FontWeight.bold),
                          ),
                          subtitle: Text("${displayList[index].Distance!}km", style: TextStyle(color: Colors.black,),
                          ),
                          trailing: Text("${displayList[index].rating!} \u{2B50}",style: TextStyle(color: Colors.black),
                          ),
                          leading:
                              CircleAvatar(
                                radius: 30,
                                child: ClipOval
                                  (child:Image.network(
                                    width: 100,
                                    height: 100,
                                    fit: BoxFit.cover,
                                    displayList[index].CourtOwnerPicture!)),
                              ),
                        ),
                      )
                  ),

                ),
              ],
            ) ,

          ),
        ],

      ),
   );
}



}
