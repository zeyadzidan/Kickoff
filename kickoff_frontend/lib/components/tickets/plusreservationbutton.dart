import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
import 'package:kickoff_frontend/httpshandlers/ticketsrequests.dart';

import '../../constants.dart';

class PlusReservationButton extends StatefulWidget {
  const PlusReservationButton({super.key});

  @override
  State<StatefulWidget> createState() => _PlusReservationButtonState();
}

class _PlusReservationButtonState extends State<PlusReservationButton> {
  final GlobalKey<FormState> _key = GlobalKey();
  late List<String> _ticket = <String>[];
  TimeOfDay _from = TimeOfDay.now().replacing(minute: 00);
  late TimeOfDay _to =
      TimeOfDay.now().replacing(hour: (_from.hour + 1) % 24, minute: 00);

  @override
  Widget build(BuildContext context) => FloatingActionButton(
      backgroundColor: CourtOwnerColor,
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
                        style: TextStyle(color: CourtOwnerColor, fontSize: 32)),
                    _formField('اسم اللاعب صاحب الحجز', Icons.person),
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
          focusColor: CourtOwnerColor,
          floatingLabelAlignment: FloatingLabelAlignment.center,
          labelText: label,
          suffixIcon: Icon(icon, color: CourtOwnerColor),
          labelStyle: const TextStyle(color: CourtOwnerColor),
          border: const UnderlineInputBorder(),
        ),
        validator: (input) =>
            (input!.isEmpty) ? 'لا يمكنك ترك هذا الحقل فارغاً.' : null,
        onSaved: (value) => _ticket.add(value!),
      );

  _reservationTimePicker(initTime) => Container(
        margin: const EdgeInsets.only(top: 10),
        child: ElevatedButton.icon(
          onPressed: _pickTimeReservation(initTime),
          icon: const Icon(Icons.timer, color: CourtOwnerColor),
          label: Text((initTime) ? 'ميعاد بدأ الحجز' : 'ميعاد انتهاء الحجز'),
          style: ElevatedButton.styleFrom(
              foregroundColor: CourtOwnerColor,
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
          initialTime: (initTime) ? _from : _to,
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
          if (selected!.hour % 24 > _from.hour % 24) {
            (selected.minute == 0)
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
          } else {
            toast.showToast(
              toastDuration: const Duration(seconds: 4),
              gravity: ToastGravity.CENTER,
              child: customToast(
                  'أقل عدد ساعات للحجز هو ساعة واحدة. حاول مرة أخرى.'),
            );
          }
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
                backgroundColor: CourtOwnerColor,
                padding:
                    const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
            onPressed: () async {
              // Validate name and money constraints
              if (!_key.currentState!.validate()) {
                return;
              }
              _key.currentState!.save();
              _ticket.add((ReservationsHome.selectedCourt + 1).toString());
              _ticket.add(KickoffApplication.ownerId);
              _ticket.add(_formatDate(ReservationsHome.selectedDate));
              _ticket.add(_formatDate(ReservationsHome.selectedDate));
              _ticket.add(_formatTime(_from));
              _ticket.add(_formatTime(_to));
              await TicketsHTTPsHandler.sendTicket(_ticket);
              ReservationsHome.reservations =
                  await TicketsHTTPsHandler.getCourtReservations(
                      (ReservationsHome.selectedCourt + 1),
                      KickoffApplication.ownerId,
                      _formatDate(ReservationsHome.selectedDate));
              _ticket = [];
              KickoffApplication.update();
              Navigator.pop(context);
            }),
      );

  customToast(text) => Container(
        padding: const EdgeInsets.symmetric(horizontal: 24.0, vertical: 12.0),
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(25.0),
          color: Colors.green,
        ),
        child: Text(
          text,
          style: const TextStyle(color: secondaryColor),
        ),
      );
}
