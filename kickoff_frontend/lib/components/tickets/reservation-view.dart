import 'package:flutter/material.dart';
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
              dividerColor: primaryColor,
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
                                    'يحتاج تأكيداً')
                                ? Colors.yellow.withOpacity(0.5)
                                : (ReservationsHome.reservations[index].state ==
                                        'مؤكد')
                                    ? primaryColor.withOpacity(0.5)
                                    : Colors.red.withOpacity(0.5),
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
                                        'Pending')
                                    ? Form(
                                        key: ReservationsView._keys[index],
                                        child: TextFormField(
                                          keyboardType: TextInputType.number,
                                          decoration: const InputDecoration(
                                              suffixIcon: Icon(
                                                  Icons.monetization_on,
                                                  color: primaryColor),
                                              labelText: "العربون",
                                              labelStyle: TextStyle(
                                                  color: primaryColor),
                                              floatingLabelAlignment:
                                                  FloatingLabelAlignment.center,
                                              focusColor: primaryColor,
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
