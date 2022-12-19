import 'dart:io';
import 'dart:math';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';
import 'package:kickoff_frontend/httpshandlers/ticketsrequests.dart';

import '../../constants.dart';

class PlayerReservationsHome extends StatefulWidget {
  const PlayerReservationsHome({super.key});

  static String _resState = "Booked";
  static List<FixtureTicket> _reservations = <FixtureTicket>[];
  static List<FilePickerResult> _results = <FilePickerResult>[];
  static List<bool> _expanded = <bool>[];

  @override
  State<StatefulWidget> createState() => _PlayerReservationsHomeState();

  static _buildReservations() async {
    // TODO: Figure out a way to get the player id.
    _reservations = await TicketsHTTPsHandler.getPlayerReservations(
        1, _resState); // PlayerID.
    _expanded = List.generate(_reservations.length, (index) => false);
  }
}

class _PlayerReservationsHomeState extends State<PlayerReservationsHome> {
  _resState() => PlayerReservationsHome._resState;

  _setResState(state) =>
      setState(() => PlayerReservationsHome._resState = state);

  _reservations() => PlayerReservationsHome._reservations;

  _getResult(index) => PlayerReservationsHome._results[index];

  // A setState method can be used here.
  _setResult(index, result) => PlayerReservationsHome._results[index] = result;

  _expanded(index) => PlayerReservationsHome._expanded[index];

  _setExpanded(index, value) =>
      setState(() => PlayerReservationsHome._expanded[index] = value);

  @override
  Widget build(BuildContext context) {
    PlayerReservationsHome._buildReservations();
    return Column(
      children: [
        Container(
          padding: const EdgeInsets.symmetric(vertical: 0.0, horizontal: 15.0),
          margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
          decoration: BoxDecoration(
              shape: BoxShape.rectangle,
              borderRadius: BorderRadius.circular(100),
              color: courtOwnerColor.withOpacity(0.3)),
          child: Expanded(
            child: SingleChildScrollView(
              scrollDirection: Axis.horizontal,
              child: Container(
                  child: _reservationsStatesNavBar(),
                  margin: const EdgeInsets.symmetric(
                      horizontal: 0.0, vertical: 10.0)),
            ),
          ),
        ),
        _viewReservations(),
      ],
    );
  }

  _reservationsStatesNavBar() => GNav(
        tabs: [
          _createGButton(Icons.book_online, Colors.green),
          _createGButton(Icons.pending, Colors.yellow),
          _createGButton(Icons.timer_off_outlined, Colors.red),
          _createGButton(Icons.access_time, Colors.cyan),
        ],
        selectedIndex: _getSelectedState(),
        onTabChange: _select,
        duration: const Duration(seconds: 1),
        color: playerColor,
        activeColor: secondaryColor,
        tabBackgroundColor: Colors.black.withAlpha(25),
      );

  _createGButton(icon, color) => GButton(
        icon: icon,
        backgroundColor: color,
        text: _resState(),
        iconSize: 5,
        textSize: 5,
      );

  _getSelectedState() => (_resState().isEqual('Booked'))
      ? 0
      : (_resState().isEqual('Pending'))
          ? 1
          : (_resState().isEqual('Expired'))
              ? 2
              : 3;

  _select(index) => setState(() {
        _setResState((index == 0)
            ? 'Booked'
            : (index == 1)
                ? 'Pending'
                : (index == 2)
                    ? 'Expired'
                    : 'Awaiting Confirmation');
      });

  _viewReservations() => Expanded(
        child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            child: ExpansionPanelList(
              animationDuration: const Duration(milliseconds: 300),
              expandedHeaderPadding: EdgeInsets.zero,
              dividerColor: playerColor,
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
                                  children: List<Text>.generate(
                                      _reservations()[index]
                                          .asPlayerView()
                                          .length,
                                      (j) => Text(_reservations()[index]
                                          .asPlayerView()[j])),
                                ),
                                (_reservations()[index]
                                        .state
                                        .Equals('Pending)'))
                                    ? Column(children: [
                                        _uploadReceipt(index),
                                        _sendReceipt(index)
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

  _uploadReceipt(index) {
    return Container(
      margin: const EdgeInsets.only(top: 15),
      child: ElevatedButton.icon(
        label: Text((_getResult(index) == null)
            ? 'Upload Receipt'
            : _getResult(index).names[0]!),
        icon: const Icon(Icons.add_a_photo),
        style: ElevatedButton.styleFrom(
            backgroundColor: courtOwnerColor,
            padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
        onPressed: () async {
          _setResult(
              index,
              await FilePicker.platform.pickFiles(
                  type: FileType.custom,
                  allowedExtensions: ['png', 'jpg', 'jpeg']));
          if (_getResult(index) != null) {
            KickoffApplication.update();
          }
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
              backgroundColor: playerColor,
              padding:
                  const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
          onPressed: () async {
            if (_getResult(index) != null) {
              Random random = Random();
              File file = File(_getResult(index)!.files.last.path!);
              final path =
                  'files/${KickoffApplication.data["id"].toString()}.${random.nextInt(10000000)}.${_getResult(index)!.files.last.extension}';
              await TicketsHTTPsHandler.uploadReceipt(file, path);
            }
            KickoffApplication.update();
          },
        ),
      );
}