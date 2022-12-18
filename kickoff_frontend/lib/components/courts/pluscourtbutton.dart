import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';

import '../../constants.dart';
import '../../httpshandlers/courtsrequests.dart';

class PlusCourtButton extends StatefulWidget {
  const PlusCourtButton({super.key});

  @override
  State<StatefulWidget> createState() => _PlusCourtButtonState();
}

class _PlusCourtButtonState extends State<PlusCourtButton> {
  final GlobalKey<FormState> _key = GlobalKey();
  final List<String> _court = <String>[];
  TimeOfDay _open = TimeOfDay.now().replacing(minute: 00);
  late TimeOfDay _midday = _open.replacing(hour: (_open.hour + 6) % 24);
  late TimeOfDay _close =
  _open.replacing(hour: (_open.hour + 12) % 24, minute: 00);

  @override
  Widget build(BuildContext context) =>
      FloatingActionButton(
          backgroundColor: courtOwnerColor,
          child: const Icon(Icons.add, size: 35),
          onPressed: () =>
              showModalBottomSheet(
                elevation: 4,
                context: context,
                builder: (context) =>
                    SingleChildScrollView(
                        child: Form(
                          key: _key,
                          child: Container(
                            margin: const EdgeInsets.symmetric(
                                vertical: 25.0, horizontal: 25.0),
                            child: Column(
                              children: [
                                const Text("أضف ملعباً جديداً",
                                    style: TextStyle(
                                        color: courtOwnerColor, fontSize: 32)),
                                _formField('اسم الملعب', Icons.stadium, false),
                                _formField(
                                    'وصف الملعب', Icons.description, false),
                                _formField(
                                    'سعر الساعة صباحاً', Icons.monetization_on,
                                    true),
                                _formField(
                                    'سعر الساعة مساءً', Icons.monetization_on,
                                    true),
                                _formField(
                                    'أقل عدد ساعات للحجز', Icons.timer, true),
                                _buildCourtTimePicker('Starting'),
                                _buildCourtTimePicker('Midday'),
                                _buildCourtTimePicker('Finishing'),
                                _submitButton()
                              ],
                            ),
                          ),
                        )),
              ));

  _formField(label, icon, digits) =>
      TextFormField(
        maxLength: (label == 'أقل عدد ساعات للحجز') ? 2 : 32,
        inputFormatters:
        (digits) ? [FilteringTextInputFormatter.digitsOnly] : null,
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
        onSaved: (value) => _court.add(value!),
      );

  _buildCourtTimePicker(beingPicked) =>
      Container(
        margin: const EdgeInsets.only(top: 10),
        child: ElevatedButton.icon(
          onPressed: _pickTimeCourt(beingPicked),
          icon: const Icon(Icons.timer, color: courtOwnerColor),
          label: Text((beingPicked == 'Starting')
              ? 'اختر موعد فتح الملعب'
              : (beingPicked == 'Midday')
              ? 'اختر موعد بداية الساعات الليلية'
              : 'اختر موعد غلق الملعب'),
          style: ElevatedButton.styleFrom(
              foregroundColor: courtOwnerColor,
              backgroundColor: secondaryColor,
              padding:
              const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
        ),
      );

  _pickTimeCourt(beingPicked) =>
          () async {
        FToast toast = FToast();
        toast.init(context);
        var selected = await showTimePicker(
          helpText: 'اختر الساعة فقط',
          initialEntryMode: TimePickerEntryMode.inputOnly,
          initialTime: (beingPicked == 'Starting') ? _open : (beingPicked ==
              'Midday') ? _midday : _close,
          context: context,
        );
        if (beingPicked == 'Started') {
          if (selected!.minute > 0) {
            toast.showToast(
              toastDuration: const Duration(seconds: 4),
              gravity: ToastGravity.CENTER,
              child:
              customToast("من فضلك اختر الساعة فقط. الدقائق غير محسوبة."),
            );
          } else {
            setState(() => _open = selected);
            toast.showToast(
              toastDuration: const Duration(seconds: 2),
              gravity: ToastGravity.CENTER,
              child: customToast("تم اختيار الوقت بنجاح"),
            );
          }
        } else if (beingPicked == 'Finishing') {
          (selected?.minute == 0)
              ? setState(() {
            _close = selected!;
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
          (selected?.minute == 0)
              ? setState(() {
            _midday = selected!;
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

  _format(TimeOfDay time) =>
      DateFormat("HH").format(DateFormat.jm().parse(time.format(context)));

  _submitButton() =>
      Container(
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
              _key.currentState!.save();
              _court.add(_format(_open));
              _court.add(_format(_midday));
              _court.add(_format(_close));
              await CourtsHTTPsHandler.sendCourt(_court);
              ProfileBaseScreen.courts = await CourtsHTTPsHandler.getCourts(
                  KickoffApplication.ownerId);
              KickoffApplication.update();
              Navigator.pop(context);
            }),
      );
}
