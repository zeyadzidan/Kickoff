import 'dart:io';
import 'dart:math';

import 'package:cached_network_image/cached_network_image.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
import 'package:kickoff_frontend/components/classes/Party.dart';
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';
import 'package:kickoff_frontend/httpshandlers/ticketsrequests.dart';
import 'package:kickoff_frontend/application/screens/player/showPartyPlayers.dart';

import '../../../constants.dart';
import '../../../httpshandlers/Parties Requests.dart';
import 'makePartyButton.dart';

class PlayerReservationsHome extends StatefulWidget {
  const PlayerReservationsHome({super.key});

  static String _resState = "Booked";
  static List<FixtureTicket> _reservations = <FixtureTicket>[];
  static List<Party> parties = <Party>[];
  static List<FilePickerResult> _results = <FilePickerResult>[];
  static List<bool> _expanded = <bool>[];
  static bool _ascending = true;
  static clearData(){
    _reservations.clear();
    parties.clear();
    _results.clear();
    _expanded.clear();
  }

  @override
  State<StatefulWidget> createState() => _PlayerReservationsHomeState();

  static buildReservations() async {
    _reservations = await TicketsHTTPsHandler.getPlayerReservations(
        KickoffApplication.playerId, _resState, _ascending); // PlayerID.
    _expanded = List.generate(_reservations.length, (index) => false);
    _results = List.generate(_reservations.length,
        (index) => const FilePickerResult(<PlatformFile>[]));
    if(_resState=="Party Created"){
      parties= await PartiesHTTPsHandler.getPartiesCreated(KickoffApplication.playerId);
    }
    if(_resState=="Party joined"){
      parties= await PartiesHTTPsHandler.getPartiesJoined(KickoffApplication.playerId);
    }
  }
}

class _PlayerReservationsHomeState extends State<PlayerReservationsHome> {
  _resState() => PlayerReservationsHome._resState;

  _setResState(state) =>
      setState(() => PlayerReservationsHome._resState = state);

  _reservations() => PlayerReservationsHome._reservations;

  FilePickerResult _getResult(index) => PlayerReservationsHome._results[index];

  // A setState method can be used here.
  _setResult(index, result) => PlayerReservationsHome._results[index] = result;

  _expanded(index) => PlayerReservationsHome._expanded[index];

  _setExpanded(index, value) =>
      setState(() => PlayerReservationsHome._expanded[index] = value);

  _flipAscending() => setState(() =>
      PlayerReservationsHome._ascending = !PlayerReservationsHome._ascending);

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          padding: const EdgeInsets.symmetric(vertical: 0.0, horizontal: 15.0),
          margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
          decoration: BoxDecoration(
              shape: BoxShape.rectangle,
              borderRadius: BorderRadius.circular(100),
              color: mainSwatch.withOpacity(0.3)),
          child: SingleChildScrollView(
            scrollDirection: Axis.horizontal,
            child: Container(
                child: _reservationsStatesNavBar(),
                margin: const EdgeInsets.symmetric(
                    horizontal: 0.0, vertical: 10.0)),
          ),
        ),
        Row(mainAxisAlignment: MainAxisAlignment.end, children: [
          IconButton(
            onPressed: () {
              _flipAscending();
              // await PlayerReservationsHome.buildReservations();
              KickoffApplication.update();
            },
            icon: const Icon(Icons.repeat_on_rounded),
          )
        ]),
        (_getSelectedState()==4||_getSelectedState()==5)
            ?_viewParty()
            :_viewReservations(),
      ],
    );
  }

  _reservationsStatesNavBar() => GNav(
        tabs: [
          _createGButton(Icons.book_online, Colors.green),
          _createGButton(Icons.pending, Colors.orange),
          _createGButton(Icons.timer_off_outlined, Colors.red),
          _createGButton(Icons.access_time, Colors.cyan),
          _createGButton2(Icons.person_add_rounded,"Parties Created", Colors.cyan),
          _createGButton2(Icons.person_pin_sharp,"Parties joined", Colors.cyan),
        ],
        selectedIndex: _getSelectedState(),
        onTabChange: _select,
        duration: const Duration(seconds: 1),
        color: mainSwatch,
        activeColor: secondaryColor,
        tabBackgroundColor: Colors.black.withAlpha(25),
      );

  _createGButton(icon, color) => GButton(
        icon: icon,
        backgroundColor: color,
        text: _resState(),
        textSize: 2,
      );
  _createGButton2(icon, text ,color) => GButton(
    icon: icon,
    backgroundColor: color,
    text: text,
    textSize: 2,
  );
  _getSelectedState() => (_resState() == 'Booked')
      ? 0
      : (_resState() == ('Pending'))
          ? 1
          : (_resState() == ('Expired'))
              ? 2
              : (_resState() == ('Awaiting'))
                ? 3
                : (_resState() == ('Party Created'))
                  ? 4
                  : 5;

  _select(index) async {
    _setResState((index == 0)
        ? 'Booked'
        : (index == 1)
            ? 'Pending'
            : (index == 2)
                ? 'Expired'
                : (index == 3)
                  ? 'Awaiting'
                  : (index == 4)
                    ? 'Party Created'
                    : 'Party joined');
    await PlayerReservationsHome.buildReservations();
    setState(() {});
  }

  _viewReservations() => Expanded(
        child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            child: ExpansionPanelList(
              animationDuration: const Duration(milliseconds: 300),
              expandedHeaderPadding: EdgeInsets.zero,
              dividerColor: mainSwatch,
              elevation: 4,
              children: List<ExpansionPanel>.generate(
                  PlayerReservationsHome._reservations.length,
                  (index) => ExpansionPanel(
                        headerBuilder: (_, isExpanded) => Container(
                          padding: const EdgeInsets.symmetric(
                              vertical: 15, horizontal: 30),
                          child: Text(
                              '${_reservations()[index].startTime} - ${_reservations()[index].endTime}, ${_reservations()[index].startDate}'),
                        ),
                        body: Container(
                            padding: const EdgeInsets.symmetric(
                                vertical: 50, horizontal: 30),
                            child: Column(
                              children: [
                                Column(
                                  children: List<Widget>.generate(
                                      _reservations()[index]
                                          .asPlayerView()
                                          .length,
                                      (j) => _reservations()[index]
                                          .asPlayerView()[j]),
                                ),
                                (_reservations()[index].state == ('Pending'))
                                    ? Column(children: [
                                        _uploadReceipt(index),
                                        _sendReceipt(index),
                                        _makeParty(index)
                                      ])
                                    : Container(),
                              ],
                            )),
                        isExpanded: _expanded(index),
                        canTapOnHeader: true,
                      )),
              expansionCallback: (i, isExpanded) =>
                  _setExpanded(i, !_expanded(i)),
            )),
      );

  _viewParty() =>Stack(
    children: <Widget>[
      PlayerReservationsHome.parties.length > 0
          ? ListView.builder(
          shrinkWrap: true,
          itemCount: PlayerReservationsHome.parties.length,
          itemBuilder: (context, index) => Padding(
            padding: const EdgeInsets.fromLTRB(2.0, 2.0, 2.0, 6),
            child: Card(
              elevation: 2.0,
              shape: RoundedRectangleBorder(
                  side: BorderSide(
                    // border color
                      color: courtOwnerColor,
                      // border thickness
                      width: 2)),
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
                                child: PlayerReservationsHome.parties[index].Pimg!=""?CachedNetworkImage(
                                  imageUrl: PlayerReservationsHome.parties[index].Pimg,
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
                          Expanded(
                            child: Text(
                              "${PlayerReservationsHome.parties[index].Pname} >> ${PlayerReservationsHome.parties[index].COname}",
                              style: TextStyle(
                                  fontSize: 17,
                                  fontWeight: FontWeight.bold),
                            ),
                          ),
                          SizedBox(width: 10,),
                          Text(
                            "${int.parse(PlayerReservationsHome.parties[index].fullplaces)-int.parse(PlayerReservationsHome.parties[index].emptyplaces)} / ${PlayerReservationsHome.parties[index].fullplaces}",
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
                          child: Text("${PlayerReservationsHome.parties[index].timeFrom} -> ${PlayerReservationsHome.parties[index].timeTo}",
                            style: TextStyle(
                              fontSize: 15,
                              color: Colors.black.withAlpha(100),
                            ),),
                        ),
                        Expanded(child: Container()),
                        Text("${PlayerReservationsHome.parties[index].Cname}",
                          style: TextStyle(
                            fontSize: 15,
                            color: Colors.black.withAlpha(100),
                          ),),
                        Expanded(child: Container()),
                        Text("${PlayerReservationsHome.parties[index].Date}",
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
                                showPartyPlayers.partyPlayers=await PartiesHTTPsHandler.getPlayersJoinedParty(PlayerReservationsHome.parties[index].id);
                                showPartyPlayers.party=PlayerReservationsHome.parties[index];
                                Navigator.pushNamed(context, "/Party");                              },
                            ),
                          ),
                          Expanded(child: Container()),
                          SizedBox(
                            height: 50,
                            width: 150,
                            child: (_getSelectedState()==4)
                                ?ElevatedButton.icon(
                                  icon: const Icon(Icons.delete),
                                  label: const Text('Delete Party'),
                                  style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.red,
                                      padding:
                                      const EdgeInsets.symmetric(
                                          vertical: 10)),
                                  onPressed: () async {
                                    await PartiesHTTPsHandler
                                        .deleteParty(PlayerReservationsHome.parties[index].id);
                                    PlayerReservationsHome.parties =
                                    await PartiesHTTPsHandler.getPartiesCreated(
                                        KickoffApplication.playerId);
                                    setState(() {});
                                  },
                                )
                                :ElevatedButton.icon(
                                  icon: const Icon(Icons.exit_to_app),
                                  label: const Text('Leave Party'),
                                  style: ElevatedButton.styleFrom(
                                      backgroundColor: Colors.red,
                                      padding:
                                      const EdgeInsets.symmetric(
                                          vertical: 10)),
                                  onPressed: () async {
                                    await PartiesHTTPsHandler
                                        .leaveParty(PlayerReservationsHome.parties[index].id,KickoffApplication.playerId);
                                    PlayerReservationsHome.parties =
                                      await PartiesHTTPsHandler.getPartiesJoined(
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
                  "there is no Parties",
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

  _uploadReceipt(index) {
    return Container(
      margin: const EdgeInsets.only(top: 15),
      child: ElevatedButton.icon(
        label: Text((_getResult(index).files.isEmpty)
            ? 'Upload Receipt'
            : _getResult(index).names[0]!),
        icon: const Icon(Icons.add_a_photo),
        style: ElevatedButton.styleFrom(
            backgroundColor: mainSwatch,
            padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
        onPressed: () async {
          _setResult(
              index,
              await FilePicker.platform.pickFiles(
                  type: FileType.custom,
                  allowedExtensions: ['png', 'jpg', 'jpeg']));
          KickoffApplication.update();
        },
      ),
    );
  }

  _sendReceipt(index) => Container(
        alignment: Alignment.bottomCenter,
        margin: const EdgeInsets.only(top: 15),
        child: ElevatedButton.icon(
          label: const Text('Send Receipt'),
          icon: const Icon(Icons.schedule_send),
          style: ElevatedButton.styleFrom(
              backgroundColor: mainSwatch,
              padding:
                  const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
          onPressed: () async {
            Random random = Random();
            File file = File(_getResult(index).files.last.path!);
            final path =
                'files/${KickoffApplication.data["id"].toString()}.${random.nextInt(10000000)}.${_getResult(index).files.last.extension}';
            await TicketsHTTPsHandler.uploadReceipt(file, path);
            KickoffApplication.update();
          },
        ),
      );

  _makeParty(index) => Container(
    alignment: Alignment.bottomCenter,
    margin: const EdgeInsets.only(top: 15),
    child: ElevatedButton.icon(
      label: const Text('Make Party'),
      icon: const Icon(Icons.person_add_sharp),
      style: ElevatedButton.styleFrom(
          backgroundColor: mainSwatch,
          padding:
          const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
      onPressed: () async {
        showModalBottomSheet(context: context,
            builder: (context)=>makePartyButton(id: PlayerReservationsHome._reservations[index].ticketId));
        KickoffApplication.update();
      },
    ),
  );
}
