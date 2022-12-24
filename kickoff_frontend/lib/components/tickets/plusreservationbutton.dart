import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';
import 'package:kickoff_frontend/httpshandlers/ticketsrequests.dart';

import '../../application/screens/profile.dart';
import '../../constants.dart';

class PlusReservationButton extends StatefulWidget {
  const PlusReservationButton({super.key});

  @override
  State<StatefulWidget> createState() => _PlusReservationButtonState();
}

class _PlusReservationButtonState extends State<PlusReservationButton> {
  final GlobalKey<FormState> _key = GlobalKey();
  final FixtureTicket _fixtureTicket = FixtureTicket();
  TimeOfDay _from = const TimeOfDay(hour: -1, minute: -1);
  TimeOfDay _to = const TimeOfDay(hour: -1, minute: -1);

  @override
  Widget build(BuildContext context) => FloatingActionButton(
      backgroundColor: KickoffApplication.player
        ?playerColor
        :courtOwnerColor,
      tooltip: "إضافة حجز",
      child: const Icon(Icons.add_card_rounded, size: 35),
      onPressed: () => showModalBottomSheet(
            elevation: 4,
            context: context,
            builder: (context) => SingleChildScrollView(
                child: Form(
              key: _key,
              child: Container(
                margin: const EdgeInsets.symmetric(
                    vertical: 25.0, horizontal: 25.0),
                child: Column(
                  children: [
                    const Text("أضف حجزاً",
                        style: TextStyle(color: courtOwnerColor, fontSize: 32)),
                    (!KickoffApplication.player) ? _formField('اسم اللاعب صاحب الحجز', Icons.person) : Container(),
                    _reservationTimePicker(true),
                    _reservationTimePicker(false),
                    _submitButton()
                  ],
                ),
              ),
            )),
          ));

  _formField(label, icon) => TextFormField(
        maxLength: 32,
        maxLines: 1,
        keyboardType: TextInputType.name,
        decoration: InputDecoration(
          focusColor: courtOwnerColor,
          floatingLabelAlignment: FloatingLabelAlignment.center,
          labelText: label,
          suffixIcon: Icon(icon, color: courtOwnerColor),
          labelStyle: const TextStyle(color: courtOwnerColor),
          border: const UnderlineInputBorder(),
        ),
        validator: (input) =>
            (input!.isEmpty) ? 'لا يمكنك ترك هذا الحقل فارغاً.' : null,
        onSaved: (value) => _fixtureTicket.pname = value!,
      );

  _reservationTimePicker(initTime) => Container(
        margin: const EdgeInsets.only(top: 10),
        child: ElevatedButton.icon(
          onPressed: _pickTimeReservation(initTime),
          icon: const Icon(Icons.timer, color: courtOwnerColor),
          label: Text((initTime) ? 'ميعاد بدأ الحجز' : 'ميعاد انتهاء الحجز'),
          style: ElevatedButton.styleFrom(
              foregroundColor: courtOwnerColor,
              backgroundColor: secondaryColor,
              padding:
                  const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
        ),
      );

  _pickTimeReservation(initTime) => () async {
        FToast toast = FToast();
        toast.init(context);
        var selected = await showTimePicker(
          helpText: 'اختر الساعة فقط',
          initialEntryMode: TimePickerEntryMode.inputOnly,
          initialTime: TimeOfDay.now(),
          context: context,
        );
        if (initTime) {
          if (selected!.minute > 0) {
            toast.showToast(
              toastDuration: const Duration(seconds: 4),
              gravity: ToastGravity.CENTER,
              child:
                  customToast("من فضلك اختر الساعة فقط. الدقائق غير محسوبة."),
            );
          } else {
            setState(() => _from = selected);
            toast.showToast(
              toastDuration: const Duration(seconds: 2),
              gravity: ToastGravity.CENTER,
              child: customToast("تم اختيار الوقت بنجاح"),
            );
          }
        } else {
          (selected!.minute == 0)
              ? setState(() {
                  _to = selected;
                  toast.showToast(
                    toastDuration: const Duration(seconds: 2),
                    gravity: ToastGravity.CENTER,
                    child: customToast("تم اختيار الوقت بنجاح"),
                  );
                })
              : toast.showToast(
                  toastDuration: const Duration(seconds: 4),
                  gravity: ToastGravity.CENTER,
                  child: customToast(
                      'من فضلك اختر الساعة فقط. الدقائق غير محسوبة.'),
                );
        }
      };

  _formatTime(TimeOfDay time) =>
      DateFormat("HH").format(DateFormat.jm().parse(time.format(context)));

  _formatDate(DateTime date) => DateFormat.yMd().format(date);

  _submitButton() => Container(
        alignment: Alignment.bottomCenter,
        margin: const EdgeInsets.only(top: 15),
        child: ElevatedButton.icon(
            label: const Text('حفظ'),
            icon: const Icon(Icons.schedule_send),
            style: ElevatedButton.styleFrom(
                backgroundColor: courtOwnerColor,
                padding:
                    const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
            onPressed: () async {
              // Validate name and money constraints
              if (!_key.currentState!.validate()) {
                return;
              }
              // Validate time constraints
              if (_from.hour == -1 || _to.hour == -1) {
                FToast toast = FToast();
                toast.init(context);
                toast.showToast(
                  toastDuration: const Duration(seconds: 4),
                  gravity: ToastGravity.CENTER,
                  child: customToast(
                      'لم تقم بتحديد مواعيد الحجز بعناية. حاول مرة أخرى.'),
                );
                return;
              }
              // Handle the overlapping fixtures reservation.
              DateTime finishDate = ReservationsHome.selectedDate;
              if (_from.hour < _to.hour) {
                finishDate.add(const Duration(days: 1));
              }
              _key.currentState!.save();  // Set name
              if (KickoffApplication.player) {
                _fixtureTicket.pname = KickoffApplication.data["name"];
                _fixtureTicket.pid = KickoffApplication.playerId;
              }
              print("Selected: ${ReservationsHome.selectedCourt}");
              print("CID: ${ProfileBaseScreen.courts[ReservationsHome.selectedCourt].cid}");
              String cid = ProfileBaseScreen.courts[ReservationsHome.selectedCourt].cid;
              _fixtureTicket.cid = cid;
              _fixtureTicket.coid = KickoffApplication.ownerId;
              _fixtureTicket.startDate = _formatDate(ReservationsHome.selectedDate);
              _fixtureTicket.endDate = _formatDate(finishDate);
              _fixtureTicket.startTime = _formatTime(_from);
              _fixtureTicket.endTime = _formatTime(_to);
              await TicketsHTTPsHandler.sendTicket(_fixtureTicket);
              ReservationsHome.reservations =
                  await TicketsHTTPsHandler.getCourtReservations(
                      ProfileBaseScreen.courts[ReservationsHome.selectedCourt].cid,
                      KickoffApplication.ownerId,
                      _formatDate(ReservationsHome.selectedDate));
              KickoffApplication.update();
              Navigator.pop(context);
            }),
      );
}
