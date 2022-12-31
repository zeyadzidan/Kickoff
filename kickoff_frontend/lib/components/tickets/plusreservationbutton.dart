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
  FToast toast = FToast();

  @override
  Widget build(BuildContext context) => FloatingActionButton(
      backgroundColor: mainSwatch,
      tooltip: KickoffApplication.player ? "Add a reservation" : "إضافة حجز",
      child: const Icon(Icons.add_card_rounded, size: 35),
      onPressed: () => (KickoffApplication.player &&
              KickoffApplication.data['restricted'] == 'true')
          ? showDialog(
              context: context,
              builder: (ctx) => AlertDialog(
                    title: const Text("RESTRICTED"),
                    content: Text(
                        'Your account has been restricted for ${KickoffApplication.data['penaltyDaysLeft']} days.'),
                    actions: [
                      TextButton(
                        child: const Text("OK"),
                        onPressed: () {
                          Navigator.of(ctx).pop();
                        },
                      ),
                    ],
                  ))
          : showModalBottomSheet(
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
                      Text(
                          KickoffApplication.player
                              ? "Add a reservation"
                              : "أضف حجزاً",
                          style: TextStyle(color: mainSwatch, fontSize: 32)),
                      !KickoffApplication.player
                          ? _formField('اسم اللاعب صاحب الحجز', Icons.person)
                          : Container(),
                      !KickoffApplication.player
                          ? _formField('رقم الهاتف', Icons.phone)
                          : Container(),
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
          focusColor: mainSwatch,
          floatingLabelAlignment: FloatingLabelAlignment.center,
          labelText: label,
          suffixIcon: Icon(icon, color: mainSwatch),
          labelStyle: TextStyle(color: mainSwatch),
          border: const UnderlineInputBorder(),
        ),
        validator: (input) =>
            (input!.isEmpty) ? 'لا يمكنك ترك هذا الحقل فارغاً.' : null,
        onSaved: (value) => label == 'اسم اللاعب صاحب الحجز'
            ? _fixtureTicket.pname = value!
            : _fixtureTicket.pnumber = value!,
      );

  _reservationTimePicker(initTime) => Container(
        margin: const EdgeInsets.only(top: 10),
        child: ElevatedButton.icon(
          onPressed: _pickTimeReservation(initTime),
          icon: Icon(Icons.timer, color: mainSwatch),
          label: Text((initTime)
              ? KickoffApplication.player
                  ? 'Starting Time'
                  : 'ميعاد بدأ الحجز'
              : KickoffApplication.player
                  ? 'Ending Time'
                  : 'ميعاد انتهاء الحجز'),
          style: ElevatedButton.styleFrom(
              foregroundColor: mainSwatch,
              backgroundColor: secondaryColor,
              padding:
                  const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
        ),
      );

  _pickTimeReservation(initTime) => () async {
        FToast toast = FToast();
        toast.init(context);
        var selected = await showTimePicker(
          helpText:
              KickoffApplication.player ? 'Pick hour only.' : 'اختر الساعة فقط',
          initialEntryMode: TimePickerEntryMode.inputOnly,
          initialTime: TimeOfDay.now(),
          context: context,
        );
        if (initTime) {
          if (selected!.minute > 0) {
            toast.showToast(
              toastDuration: const Duration(seconds: 4),
              gravity: ToastGravity.CENTER,
              child: customToast(KickoffApplication.player
                  ? 'Hours only are valid.'
                  : "من فضلك اختر الساعة فقط. الدقائق غير محسوبة."),
            );
          } else {
            setState(() => _from = selected);
            toast.showToast(
              toastDuration: const Duration(seconds: 2),
              gravity: ToastGravity.CENTER,
              child: customToast(KickoffApplication.player
                  ? 'Success'
                  : "تم اختيار الوقت بنجاح"),
            );
          }
        } else {
          (selected!.minute == 0)
              ? setState(() {
                  _to = selected;
                  toast.showToast(
                    toastDuration: const Duration(seconds: 2),
                    gravity: ToastGravity.CENTER,
                    child: customToast(KickoffApplication.player
                        ? 'Success'
                        : "تم اختيار الوقت بنجاح"),
                  );
                })
              : toast.showToast(
                  toastDuration: const Duration(seconds: 4),
                  gravity: ToastGravity.CENTER,
                  child: customToast(KickoffApplication.player
                      ? 'Hours are only valid.'
                      : 'من فضلك اختر الساعة فقط. الدقائق غير محسوبة.'),
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
            label: Text(KickoffApplication.player ? 'Confirm' : 'تأكيد'),
            icon: const Icon(Icons.schedule_send),
            style: ElevatedButton.styleFrom(
                backgroundColor: mainSwatch,
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
                  child: customToast(KickoffApplication.player
                      ? 'You have not selected the time period. Please try again.'
                      : 'لم تقم بتحديد مواعيد الحجز بعناية. حاول مرة أخرى.'),
                );
                return;
              }
              // Handle the overlapping fixtures reservation.
              DateTime finishDate = ReservationsHome.selectedDate;
              if (_from.hour < _to.hour) {
                finishDate.add(const Duration(days: 1));
              }
              _key.currentState!.save(); // Set name
              if (KickoffApplication.player) {
                _fixtureTicket.pname = KickoffApplication.data["name"];
                _fixtureTicket.pnumber = KickoffApplication.data["phoneNumber"];
                _fixtureTicket.pid = KickoffApplication.playerId;
              }
              String cid =
                  ProfileBaseScreen.courts[ReservationsHome.selectedCourt].cid;
              _fixtureTicket.cid = cid;
              _fixtureTicket.coid = KickoffApplication.ownerId;
              _fixtureTicket.startDate =
                  _formatDate(ReservationsHome.selectedDate);
              _fixtureTicket.endDate = _formatDate(finishDate);
              _fixtureTicket.startTime = _formatTime(_from);
              _fixtureTicket.endTime = _formatTime(_to);
              String response =
                  await TicketsHTTPsHandler.sendTicket(_fixtureTicket);
              if (response == "that time have reservation") {
                FToast toast = FToast();
                toast.init(context);
                toast.showToast(
                  toastDuration: const Duration(seconds: 4),
                  gravity: ToastGravity.CENTER,
                  child: KickoffApplication.player
                      ? customToast(
                          "There is already an existing reservation in that time")
                      : customToast("يوجد حجز بهذا الموعد بالفعل"),
                );
              }
              ReservationsHome.reservations =
                  await TicketsHTTPsHandler.getCourtReservations(
                      ProfileBaseScreen
                          .courts[ReservationsHome.selectedCourt].cid,
                      KickoffApplication.ownerId,
                      _formatDate(ReservationsHome.selectedDate));
              KickoffApplication.update();
              Navigator.pop(context);
            }),
      );
}
