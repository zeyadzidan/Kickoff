import 'package:cached_network_image/cached_network_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/application/screens/player/showPartyPlayers.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:kickoff_frontend/httpshandlers/Parties%20Requests.dart';
import 'package:kickoff_frontend/httpshandlers/announcements-requests.dart';

import '../../application/application.dart';

class PlusAnnouncementButton3 extends StatefulWidget {
  PlusAnnouncementButton3({super.key}){}
  static bool isParty= false;
  @override
  State<StatefulWidget> createState() => postbutton();
}

class postbutton extends State<PlusAnnouncementButton3> {
  final bool _isPlayer = KickoffApplication.player;
  @override
  Widget build(BuildContext context) {
    return PlusAnnouncementButton3.isParty?
    Stack(
      children: <Widget>[
        AnnouncementsHome.parties.length > 0
            ? ListView.builder(
                shrinkWrap: true,
                itemCount: AnnouncementsHome.parties.length,
                itemBuilder: (context, index) => Padding(
                      padding: const EdgeInsets.fromLTRB(2.0, 2.0, 2.0, 6),
                      child: Card(
                        elevation: 2.0,
                        shape: (!_isPlayer)
                            ? RoundedRectangleBorder(
                                side: BorderSide(
                                    // border color
                                    color: courtOwnerColor,
                                    // border thickness
                                    width: 2))
                            : RoundedRectangleBorder(
                            side: BorderSide(
                              // border color
                                color: playerColor,
                                // border thickness
                                width: 2)
                        ),
                        child: Padding(
                          padding: const EdgeInsets.all(10.0),
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            children: <Widget>[
                              GestureDetector(
                                child: Row(
                                  children: <Widget>[
                                    Padding(
                                      padding: const EdgeInsets.all(8.0),
                                      child: Container(
                                        width: 40,
                                        height: 40,
                                        //   child: Image.asset(utl),
                                        child: ClipOval(
                                          child: AnnouncementsHome.parties[index].Pimg!=""?CachedNetworkImage(
                                            imageUrl: AnnouncementsHome.parties[index].Pimg,
                                            width: 40,
                                            height: 40,
                                            fit: BoxFit.cover,
                                            progressIndicatorBuilder: (context,
                                                    url, downloadProgress) =>
                                                CircularProgressIndicator(
                                                    value: downloadProgress
                                                        .progress),
                                            errorWidget:
                                                (context, url, error) =>
                                                    Icon(Icons.error),
                                          ):Container(),
                                        ),
                                      ),
                                    ),
                                    Text(
                                      "${AnnouncementsHome.parties[index].Pname} >> ${AnnouncementsHome.parties[index].COname}",
                                      style: TextStyle(
                                          fontSize: 22,
                                          fontWeight: FontWeight.bold),
                                    ),
                                    Expanded(child: Container()),
                                    Text(
                                      "${int.parse(AnnouncementsHome.parties[index].fullplaces)-int.parse(AnnouncementsHome.parties[index].emptyplaces)} / ${AnnouncementsHome.parties[index].fullplaces}",
                                      style: TextStyle(
                                          fontSize: 22,
                                          fontWeight: FontWeight.bold),
                                    ),
                                  ],
                                ),
                              ),
                              Row(
                                children: [
                                  Padding(
                                    padding: const EdgeInsets.only(left: 6.0),
                                    child: Text("${AnnouncementsHome.parties[index].timeFrom} -> ${AnnouncementsHome.parties[index].timeTo}",
                                      style: TextStyle(
                                        fontSize: 15,
                                        color: Colors.black.withAlpha(100),
                                      ),),
                                  ),
                                  Expanded(child: Container()),
                                  Text("${AnnouncementsHome.parties[index].Cname}",
                                    style: TextStyle(
                                      fontSize: 15,
                                      color: Colors.black.withAlpha(100),
                                    ),),
                                  Expanded(child: Container()),
                                  Text("${AnnouncementsHome.parties[index].Date}",
                                    style: TextStyle(
                                      fontSize: 15,
                                      color: Colors.black.withAlpha(100),
                                    ),),
                                ],
                              ),
                              Container(
                                      margin: const EdgeInsets.only(top: 10.0),
                                      child: Row(
                                        children: [
                                          SizedBox(
                                            height: 50,
                                            width: 150,
                                            child: ElevatedButton.icon(
                                              icon: const Icon(Icons.person),
                                              label: const Text('Show Participants'),
                                              style: ElevatedButton.styleFrom(
                                                  backgroundColor: Colors.green,
                                                  padding:
                                                      const EdgeInsets.symmetric(
                                                          vertical: 10)),
                                              onPressed: () async {
                                                showPartyPlayers.partyPlayers=await PartiesHTTPsHandler.getPlayersJoinedParty(AnnouncementsHome.parties[index].id);
                                                showPartyPlayers.party=AnnouncementsHome.parties[index];
                                                Navigator.pushNamed(context, "/Party");
                                              },
                                            ),
                                          ),
                                        Expanded(child: Container()),
                                          SizedBox(
                                            height: 50,
                                            width: 150,
                                            child: ElevatedButton.icon(
                                              icon: const Icon(Icons.person_add_sharp),
                                              label: const Text('Join Party'),
                                              style: ElevatedButton.styleFrom(
                                                  backgroundColor: Colors.green,
                                                  padding:
                                                  const EdgeInsets.symmetric(
                                                      vertical: 10)),
                                              onPressed: () async {
                                                await PartiesHTTPsHandler
                                                    .joinParty(AnnouncementsHome.parties[index].id,KickoffApplication.playerId);
                                                AnnouncementsHome.parties =
                                                  await PartiesHTTPsHandler.getPartiesSubscribedNotJoined(
                                                    KickoffApplication.playerId);
                                                setState(() {});
                                              },
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                            ],
                          ),
                        ),
                      ),
                    ))
            : Container(
                child: Center(
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: <Widget>[
                      Padding(
                        padding: const EdgeInsets.all(14.0),
                        child: Text(
                          "there is no posts",
                          style:
                              TextStyle(fontSize: 16, color: Colors.green[700]),
                          textAlign: TextAlign.center,
                        ),
                      )
                    ],
                  ),
                ),
              )
      ],
    )
    :    Stack(
      children: <Widget>[
        AnnouncementsHome.announcements.length > 0
            ? ListView.builder(
            shrinkWrap: true,
            itemCount: AnnouncementsHome.announcements.length,
            itemBuilder: (context, index) => Padding(
              padding: const EdgeInsets.fromLTRB(2.0, 2.0, 2.0, 6),
              child: Card(
                elevation: 2.0,
                shape: (!_isPlayer)
                    ? RoundedRectangleBorder(
                    side: BorderSide(
                      // border color
                        color: courtOwnerColor,
                        // border thickness
                        width: 2))
                    : RoundedRectangleBorder(
                    side: BorderSide(
                      // border color
                        color: playerColor,
                        // border thickness
                        width: 2)
                ),
                child: Padding(
                  padding: const EdgeInsets.all(10.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: <Widget>[
                      GestureDetector(
                        child: Row(
                          children: <Widget>[
                            Padding(
                              padding: const EdgeInsets.all(8.0),
                              child: Container(
                                width: 40,
                                height: 40,
                                //   child: Image.asset(utl),
                                child: ClipOval(
                                  child: AnnouncementsHome.announcements[index].Pimg!=""?CachedNetworkImage(
                                    imageUrl: AnnouncementsHome.announcements[index].Pimg,
                                    width: 100,
                                    height: 100,
                                    fit: BoxFit.cover,
                                    progressIndicatorBuilder: (context,
                                        url, downloadProgress) =>
                                        CircularProgressIndicator(
                                            value: downloadProgress
                                                .progress),
                                    errorWidget:
                                        (context, url, error) =>
                                        Icon(Icons.error),
                                  ):Container(),
                                ),
                              ),
                            ),
                            Column(
                              crossAxisAlignment:
                              CrossAxisAlignment.start,
                              children: <Widget>[
                                Text(
                                  AnnouncementsHome.announcements[index].name,
                                  style: TextStyle(
                                      fontSize: 22,
                                      fontWeight: FontWeight.bold),
                                ),
                              ],
                            ),
                          ],
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.only(left: 6.0),
                        child: Text("${AnnouncementsHome.announcements[index].time} ${AnnouncementsHome.announcements[index].date}",
                          style: TextStyle(
                            fontSize: 15,
                            color: Colors.black.withAlpha(100),
                          ),),
                      ),
                      GestureDetector(
                        child: Padding(
                          padding:
                          const EdgeInsets.fromLTRB(8, 10, 4, 10),
                          child: Text(
                            (AnnouncementsHome.announcements[index].body
                            as String)
                                .length >
                                200
                                ? '${AnnouncementsHome.announcements[index].body.substring(0, 132)} ...'
                                : AnnouncementsHome
                                .announcements[index].body,
                            style: TextStyle(
                              fontSize: 16,
                            ),
                            maxLines: 3,
                          ),
                        ),
                      ),
                      AnnouncementsHome.announcements[index].img != ""
                          ? CachedNetworkImage(
                        imageUrl: AnnouncementsHome
                            .announcements[index].img,
                        width: double.infinity,
                        fit: BoxFit.cover,
                        progressIndicatorBuilder: (context, url,
                            downloadProgress) =>
                            CircularProgressIndicator(
                                value: downloadProgress.progress),
                        errorWidget: (context, url, error) =>
                            Icon(Icons.error),
                      )
                          : Container(
                        height: 0,
                      ),
                      (_isPlayer)
                          ? Container(
                        height: 0,
                      )
                          : Container(
                        margin: const EdgeInsets.only(top: 10.0),
                        child: SizedBox(
                          height: 70,
                          width: 150,
                          child: ElevatedButton.icon(
                            icon: const Icon(Icons.delete),
                            label: const Text('مسح الإعلان'),
                            style: ElevatedButton.styleFrom(
                                backgroundColor: Colors.red,
                                padding:
                                const EdgeInsets.symmetric(
                                    vertical: 10)),
                            onPressed: () async {
                              await AnnouncementHTTPsHandler
                                  .deleteAnnouncement(
                                  AnnouncementsHome
                                      .announcements[index]
                                      .aid);
                              AnnouncementsHome.announcements =
                              await AnnouncementHTTPsHandler
                                  .getAnnouncements(
                                  KickoffApplication
                                      .ownerId);
                              AnnouncementsHome.isExpanded =
                              List<bool>.generate(
                                  AnnouncementsHome
                                      .announcements.length,
                                      (index) => false);
                              setState(() {});
                            },
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ))
            : Container(
          child: Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.all(14.0),
                  child: Text(
                    "there is no posts",
                    style:
                    TextStyle(fontSize: 16, color: Colors.green[700]),
                    textAlign: TextAlign.center,
                  ),
                )
              ],
            ),
          ),
        )
      ],
    );
  }
}
