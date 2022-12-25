


import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';


import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/httpshandlers/announcements-requests.dart';

import '../../application/application.dart';
class PlusAnnouncementButton3 extends StatefulWidget {
  const PlusAnnouncementButton3({super.key});

  @override
  State<StatefulWidget> createState() => postbutton();
}

class postbutton extends State<PlusAnnouncementButton3> {
  String name = KickoffApplication.data["name"];
  bool foundPhoto = KickoffApplication.data.containsKey("image");
  String utl = KickoffApplication.data.containsKey("image")
      ? KickoffApplication.data["image"]
      : "";
  @override
  Widget build(BuildContext context) {
    return Stack(
      children: <Widget>[
        AnnouncementsHome.announcements.length>0?
            ListView.builder(
            shrinkWrap: true,
                itemCount:  AnnouncementsHome.announcements.length,
                itemBuilder: (context,index)=>Padding(
                    padding: const EdgeInsets.fromLTRB(2.0,2.0,2.0,6),
                  child: Card(
                    elevation: 2.0,
                    child: Padding(
                      padding: const EdgeInsets.all(10.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: <Widget>[
                          GestureDetector(
                            child: Row(
                              children: <Widget>[
                                Padding(
                                  padding:const EdgeInsets.all(8.0),
                                  child: Container(
                                    width: 40,
                                    height: 40,
                                    //   child: Image.asset(utl),
                                    child: ClipOval(
                                      child: CachedNetworkImage(
                                        imageUrl: utl,
                                        width: 100,
                                        height: 100,
                                        fit: BoxFit.cover,
                                        progressIndicatorBuilder:
                                            (context, url, downloadProgress) =>
                                            CircularProgressIndicator(
                                                value: downloadProgress.progress),
                                        errorWidget: (context, url, error) =>
                                            Icon(Icons.error),
                                      ),
                                    ),
                                  ),
                                ),
                                Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: <Widget>[
                                    Text(name,style: TextStyle(fontSize: 22,fontWeight: FontWeight.bold),),
                                  ],
                                ),
                              ],
                            ),
                          ),
                          GestureDetector(
                            child: Padding(
                              padding: const EdgeInsets.fromLTRB(8,10,4,10),
                              child: Text((AnnouncementsHome.announcements[index].body as String).length > 200 ? '${AnnouncementsHome.announcements[index].body.substring(0, 132)} ...' : AnnouncementsHome.announcements[index].body,
                                style: TextStyle(fontSize: 16,),
                                maxLines: 3,),
                            ),
                          ),
                          AnnouncementsHome.announcements[index].img != ""?
                          CachedNetworkImage(
                            imageUrl: AnnouncementsHome.announcements[index].img,
                            width: double.infinity,
                            fit: BoxFit.cover,
                            progressIndicatorBuilder:
                                (context, url, downloadProgress) =>
                                CircularProgressIndicator(
                                    value: downloadProgress.progress),
                            errorWidget: (context, url, error) =>
                                Icon(Icons.error),
                          )
                              : Container(
                            height: 0,
                          ),
                        ],
                      ),
                    ),
                  ),
                )
            ): Container(
           child: Center(
             child: Column(
               mainAxisAlignment: MainAxisAlignment.center,
               children:<Widget> [
                 Padding(
                     padding: const EdgeInsets.all(14.0),
                   child: Text("there is no posts",
                   style: TextStyle(fontSize: 16,color: Colors.green[700]),textAlign: TextAlign.center,),
                 )
               ],
             ),
           ),
        )
      ],
    );
  }
}