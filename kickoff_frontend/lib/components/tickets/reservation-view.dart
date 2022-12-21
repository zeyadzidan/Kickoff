import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application/application.dart';

import '../../application/screens/reservations.dart';
import '../../constants.dart';
import '../../httpshandlers/ticketsrequests.dart';

class ReservationsView extends StatefulWidget {
  ReservationsView({super.key}) {
    for (int i = 0; i < ReservationsHome.reservations.length; i++) {
      _keys.add(GlobalKey());
    }
  }

  static final List<GlobalKey<FormState>> _keys = <GlobalKey<FormState>>[];

  @override
  State<StatefulWidget> createState() => _ReservationsViewState();
}

class _ReservationsViewState extends State<ReservationsView> {
  @override
  Widget build(BuildContext context) => Expanded(
        child: SingleChildScrollView(
            scrollDirection: Axis.vertical,
            child: ExpansionPanelList(
              animationDuration: const Duration(milliseconds: 300),
              expandedHeaderPadding: EdgeInsets.zero,
              dividerColor: playerColor,
              elevation: 4,
              children: List<ExpansionPanel>.generate(
                  ReservationsHome.reservations.length,
                  (index) => ExpansionPanel(
                        headerBuilder: (_, isExpanded) => Container(
                          padding: const EdgeInsets.symmetric(
                              vertical: 15, horizontal: 30),
                          decoration: BoxDecoration(
                            shape: BoxShape.rectangle,
                            borderRadius: BorderRadius.circular(25),
                            color: (ReservationsHome
                                        .reservations[index].state ==
                                    'Pending')
                                ? Colors.yellow.withOpacity(0.5)
                                : (ReservationsHome.reservations[index].state ==
                                        'Booked')
                                    ? playerColor.withOpacity(0.5)
                                    : (ReservationsHome
                                                .reservations[index].state ==
                                            'Expired')
                                        ? Colors.red.withOpacity(0.5)
                                        : Colors.blue.withOpacity(0.5),
                          ),
                          child:
                              Text(ReservationsHome.reservations[index].pname),
                        ),
                        body: Container(
                            padding: const EdgeInsets.symmetric(
                                vertical: 50, horizontal: 30),
                            child: Column(
                              children: [
                                Column(
                                  children: List<Text>.generate(
                                      ReservationsHome.reservations[index]
                                          .asView()
                                          .length,
                                      (j) => Text(ReservationsHome
                                          .reservations[index]
                                          .asView()[j])),
                                ),
                                (ReservationsHome.reservations[index].state ==
                                            'Pending' ||
                                        ReservationsHome
                                                .reservations[index].state ==
                                            'Awaiting Confirmation')
                                    ? Form(
                                        key: ReservationsView._keys[index],
                                        child: TextFormField(
                                          keyboardType: TextInputType.number,
                                          decoration: const InputDecoration(
                                              suffixIcon: Icon(
                                                  Icons.monetization_on,
                                                  color: playerColor),
                                              labelText: "العربون",
                                              labelStyle:
                                                  TextStyle(color: playerColor),
                                              floatingLabelAlignment:
                                                  FloatingLabelAlignment.center,
                                              focusColor: playerColor,
                                              border: UnderlineInputBorder(),
                                              prefixText: 'جنيهاً مصرياً'),
                                          validator: (input) {
                                            if (input! == 0 || input == '') {
                                              return "لا يمكن ترك هذا الحقل فارغاً.";
                                            }
                                          },
                                          onSaved: (input) {
                                            ReservationsHome.reservations[index]
                                                .state = 'Booked';
                                            ReservationsHome.reservations[index]
                                                .paidAmount = input!;
                                          },
                                        ),
                                      )
                                    : Container(),
                                (ReservationsHome.reservations[index].state ==
                                            'Awaiting Confirmation' &&
                                        ReservationsHome.reservations[index]
                                                .receiptUrl !=
                                            '')
                                    ? Container(
                                        margin:
                                            const EdgeInsets.only(top: 10.0),
                                        child: Image.network(
                                          ReservationsHome
                                              .reservations[index].receiptUrl,
                                          width: 100,
                                          height: 100,
                                          fit: BoxFit.cover,
                                          frameBuilder: (context, child, frame,
                                              wasSynchronouslyLoaded) {
                                            return child;
                                          },
                                          loadingBuilder: (context, child,
                                              loadingProgress) {
                                            if (loadingProgress == null) {
                                              return child;
                                            } else {
                                              return const Center(
                                                child:
                                                    CircularProgressIndicator(
                                                  color: Colors.white,
                                                ),
                                              );
                                            }
                                          },
                                        ),
                                      )
                                    : Container(
                                        height: 0,
                                      ),
                                (ReservationsHome.reservations[index].state ==
                                        'Pending')
                                    ? Container(
                                        alignment: Alignment.bottomCenter,
                                        margin: const EdgeInsets.only(top: 15),
                                        child: ElevatedButton.icon(
                                          label: const Text('تأكيد الحجز'),
                                          icon: const Icon(Icons.schedule_send),
                                          style: ElevatedButton.styleFrom(
                                              backgroundColor: Colors.green,
                                              padding:
                                                  const EdgeInsets.symmetric(
                                                      vertical: 20,
                                                      horizontal: 15)),
                                          onPressed: () async {
                                            // Validate name and money constraints
                                            if (!ReservationsView
                                                ._keys[index].currentState!
                                                .validate()) {
                                              return;
                                            }
                                            ReservationsView
                                                ._keys[index].currentState!
                                                .save();
                                            await TicketsHTTPsHandler
                                                .bookTicket(ReservationsHome
                                                    .reservations[index]);
                                            KickoffApplication.update();
                                          },
                                        ),
                                      )
                                    : Container(),
                                Container(
                                  margin: const EdgeInsets.only(
                                      top: 70.0, right: 300.0),
                                  child: ElevatedButton.icon(
                                    icon: const Icon(Icons.delete),
                                    label: const Text('مسح الحجز'),
                                    style: ElevatedButton.styleFrom(
                                        backgroundColor: Colors.red,
                                        padding: const EdgeInsets.symmetric(
                                            vertical: 20, horizontal: 15)),
                                    onPressed: () async {
                                      await TicketsHTTPsHandler.deleteTicket(
                                          ReservationsHome.reservations[index]);
                                      ReservationsHome.reservations =
                                          await TicketsHTTPsHandler
                                              .getCourtReservations(
                                                  (ReservationsHome
                                                          .selectedCourt +
                                                      1),
                                                  KickoffApplication.ownerId,
                                                  DateFormat.yMd().format(
                                                      ReservationsHome
                                                          .selectedDate));
                                      KickoffApplication.update();
                                    },
                                  ),
                                ),
                              ],
                            )),
                        isExpanded: ReservationsHome.isExpanded[index],
                        canTapOnHeader: true,
                      )),
              expansionCallback: (i, isExpanded) =>
                  setState(() => ReservationsHome.isExpanded[i] = !isExpanded),
            )),
      );
}
