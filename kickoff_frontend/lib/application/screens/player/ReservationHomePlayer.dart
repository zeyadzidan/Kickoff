// import 'package:flutter/material.dart';
// import 'package:google_nav_bar/google_nav_bar.dart';
// import 'package:intl/intl.dart';
// import 'package:kickoff_frontend/application/screens/profile.dart';
// import 'package:kickoff_frontend/components/tickets/reservation-view.dart';
// import 'package:kickoff_frontend/httpshandlers/ticketsrequests.dart';
//
// import 'package:kickoff_frontend/components/classes/fixtureticket.dart';
// import 'package:kickoff_frontend/constants.dart';
// import 'package:kickoff_frontend/application/application.dart';
//
// class ReservationsHomePlayer extends StatefulWidget {
//   ReservationsHomePlayer({super.key}) {
//     buildTickets();
//     isExpanded = List<bool>.generate(reservations.length, (index) => false);
//   }
//
//   static List<FixtureTicket> reservations = [];
//   static List<bool> isExpanded = [];
//   static int _selectedCourt = 0;
//   static DateTime _selectedDate = DateTime.now();
//   static bool _ascendingView = true;
//
//   static get selectedCourt => ReservationsHomePlayer._selectedCourt;
//
//   static get selectedDate => ReservationsHomePlayer._selectedDate;
//
//   static get ascendingView => ReservationsHomePlayer._ascendingView;
//
//   static buildTickets() async {
//     ReservationsHomePlayer.reservations =
//     await TicketsHTTPsHandler.getCourtReservations(
//         ReservationsHomePlayer.selectedCourt + 1,
//         KickoffApplication.ownerId,
//         DateFormat.yMd().format(ReservationsHomePlayer.selectedDate));
//   }
//
//   @override
//   State<ReservationsHomePlayer> createState() => _ReservationsHomePlayerState();
// }
//
// class _ReservationsHomePlayerState extends State<ReservationsHomePlayer> {
//   @override
//   Widget build(BuildContext context) {
//     return Column(
//       children: [_buildCourts(), _buildDatePicker(), ReservationsView()],
//     );
//   }
//
//   _onTabSelect(index) async {
//     ReservationsHomePlayer._selectedCourt = index;
//     await ReservationsHomePlayer.buildTickets();
//     KickoffApplication.update();
//   }
//
//   _pickDate() async {
//     final DateTime? dateTime = await showDatePicker(
//       context: context,
//       initialDate: ReservationsHomePlayer._selectedDate,
//       firstDate: DateTime(DateTime.now().year - 5),
//       lastDate: DateTime(DateTime.now().year + 5),
//     );
//
//     if (dateTime != null) {
//       ReservationsHomePlayer._selectedDate = dateTime;
//       setState(() {});
//     }
//   }
//
//   _buildCourts() => Container(
//       padding: const EdgeInsets.symmetric(vertical: 0.0, horizontal: 15.0),
//       decoration: BoxDecoration(
//           shape: BoxShape.rectangle,
//           borderRadius: BorderRadius.circular(100),
//           color: playerColor.withOpacity(0.3)),
//       margin: const EdgeInsets.only(top: 20.0, left: 20.0, right: 20.0),
//       child: SingleChildScrollView(
//         scrollDirection: Axis.horizontal,
//         child: Container(
//           margin: const EdgeInsets.symmetric(horizontal: 0.0, vertical: 10.0),
//           child: GNav(
//             selectedIndex: ReservationsHomePlayer._selectedCourt,
//             onTabChange: _onTabSelect,
//             duration: const Duration(milliseconds: 300),
//             activeColor: Colors.white,
//             color: playerColor,
//             tabBackgroundColor: Colors.black.withAlpha(25),
//             tabs: List<GButton>.generate(
//                 ProfileBaseScreen.courts.length,
//                     (index) => GButton(
//                   backgroundColor: playerColor,
//                   icon: Icons.stadium,
//                   text: "   ${ProfileBaseScreen.courts[index].cname}",
//                 )),
//           ),
//         ),
//       ));
//
//   _buildDatePicker() => MaterialButton(
//     padding: const EdgeInsets.symmetric(vertical: 20, horizontal: 30),
//     onPressed: _pickDate,
//     child: Row(
//       mainAxisAlignment: MainAxisAlignment.center,
//       children: [
//         const Icon(Icons.calendar_month),
//         Text(
//             '  ${DateFormat.yMMMMEEEEd().format(ReservationsHomePlayer._selectedDate)}'),
//       ],
//     ),
//   );
// }
