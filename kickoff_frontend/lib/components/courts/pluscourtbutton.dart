import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
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
  late TimeOfDay _close =
      TimeOfDay.now().replacing(hour: (_open.hour + 12) % 24, minute: 00);

  @override
  Widget build(BuildContext context) => FloatingActionButton(
      backgroundColor: CourtOwnerColor,
      child: const Icon(Icons.add, size: 35),
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
                    const Text("أضف ملعباً جديداً",
                        style: TextStyle(color: CourtOwnerColor, fontSize: 32)),
                    _formField('اسم الملعب', Icons.stadium, false),
                    _formField('وصف الملعب', Icons.description, false),
                    _formField(
                        'سعر الساعة صباحاً', Icons.monetization_on, true),
                    _formField('سعر الساعة مساءً', Icons.monetization_on, true),
                    _formField('أقل عدد ساعات للحجز', Icons.timer, true),
                    _buildCourtTimePicker(true),
                    _buildCourtTimePicker(false),
                    _submitButton()
                  ],
                ),
              ),
            )),
          ));

  _formField(label, icon, digits) => TextFormField(
        maxLength: (label == 'أقل عدد ساعات للحجز') ? 2 : 32,
        inputFormatters:
            (digits) ? [FilteringTextInputFormatter.digitsOnly] : null,
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
        onSaved: (value) => _court.add(value!),
      );

  _buildCourtTimePicker(initTime) => Container(
        margin: const EdgeInsets.only(top: 10),
        child: ElevatedButton.icon(
          onPressed: _pickTimeCourt(initTime),
          icon: const Icon(Icons.timer, color: CourtOwnerColor),
          label: Text((initTime)
              ? 'ميعاد فتح الملعب: ${_open.format(context)}'
              : 'ميعاد غلق الملعب: ${_close.format(context)}'),
          style: ElevatedButton.styleFrom(
              foregroundColor: CourtOwnerColor,
              backgroundColor: secondaryColor,
              padding:
                  const EdgeInsets.symmetric(vertical: 20, horizontal: 15)),
        ),
      );

  _pickTimeCourt(initTime) => () async {
        var selected = await showTimePicker(
          helpText: 'اختر الساعة فقط',
          initialEntryMode: TimePickerEntryMode.inputOnly,
          initialTime: (initTime) ? _open : _close,
          context: context,
        );
        if (initTime) {
          if (selected!.minute > 0) {
            showDialog(
                context: context,
                builder: (BuildContext context) => const AlertDialog(
                      title:
                          Text('من فضلك اختر الساعة فقط.\nالدقائق غير محسوبة.'),
                    ));
          } else {
            setState(() => _open = selected);
          }
        } else {
          if (selected!.hour % 24 > _open.hour % 24) {
            (selected.minute == 0)
                ? setState(() => _close = selected)
                : showDialog(
                    context: context,
                    builder: (BuildContext context) => const AlertDialog(
                          title: Text(
                              'من فضلك اختر الساعة فقط.\nالدقائق غير محسوبة.'),
                        ));
          } else {
            showDialog(
                context: context,
                builder: (BuildContext context) => const AlertDialog(
                      title: Text(
                          'أقل عدد ساعات للحجز هو ساعة واحدة.\nحاول مرة أخرى.'),
                    ));
          }
        }
      };

  _format(TimeOfDay time) =>
      DateFormat("HH").format(DateFormat.jm().parse(time.format(context)));

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
              _court.add(_format(_open));
              _court.add(_format(_close));
              await CourtsHTTPsHandler.sendCourt(_court);
              ProfileBaseScreen.courts = await CourtsHTTPsHandler.getCourts(
                  KickoffApplication.ownerId);
              KickoffApplication.update();
              Navigator.pop(context);
            }),
      );
}
