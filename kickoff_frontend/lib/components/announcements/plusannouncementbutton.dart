import 'dart:io';
import 'dart:math';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/components/classes/announcement.dart';

import '../../constants.dart';
import '../../httpshandlers/announcements-requests.dart';

class PlusAnnouncementButton extends StatefulWidget {
  const PlusAnnouncementButton({super.key});

  @override
  State<StatefulWidget> createState() => _PlusAnnouncementButtonState();
}

class _PlusAnnouncementButtonState extends State<PlusAnnouncementButton> {
  final GlobalKey<FormState> _key = GlobalKey();
  FilePickerResult? _result;
  late List<dynamic> _announcement = <String>[];

  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
        backgroundColor: mainSwatch,
        tooltip: "إضافة إعلان",
        child: const Icon(Icons.add_comment_rounded, size: 35),
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
                              Text("أضف إعلاناً",
                                  style: TextStyle(
                                      color: mainSwatch, fontSize: 32)),
                              _formField('العنوان', Icons.title),
                              _formField('وصف الإعلان', Icons.announcement),
                              Container(
                                margin: const EdgeInsets.only(top: 15),
                                child: ElevatedButton.icon(
                                  label:
                                  Text((_result == null) ? '' : _result!
                                      .names[0]!),
                                  icon: const Icon(Icons.add_a_photo),
                                  style: ElevatedButton.styleFrom(
                                      backgroundColor: mainSwatch,
                                      padding: const EdgeInsets.symmetric(
                                          vertical: 20, horizontal: 15)),
                                  onPressed: () async {
                                    _result =
                                    await FilePicker.platform.pickFiles(
                                        type: FileType.custom,
                                        allowedExtensions: [
                                          'png',
                                          'jpg',
                                          'jpeg'
                                        ]);
                                    if (_result != null) {
                                      KickoffApplication.update();
                                    }
                                  },
                                ),
                              ),
                              _submitButton()
                            ],
                          ),
                        ),
                      )),
            ));
  }
  _formField(label, icon) => TextFormField(
        maxLength: (label == 'وصف الإعلان') ? 150 : 32,
        maxLines: (label == 'وصف الإعلان') ? 3 : 1,
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
        onSaved: (value) => _announcement.add(value!),
      );

  _submitButton() => Container(
        alignment: Alignment.bottomCenter,
        margin: const EdgeInsets.only(top: 15),
        child: ElevatedButton.icon(
            label: const Text('إرسال'),
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
              _key.currentState!.save();
              Announcement announcement = Announcement();
              announcement.coid = KickoffApplication.ownerId;
              announcement.title = _announcement[0];
              announcement.body = _announcement[1];
              if (_result != null) {
                Random random = Random();
                File file = File(_result!.files.last.path!);
                final path =
                    'files/${KickoffApplication.data["id"].toString()}.${random.nextInt(10000000)}.${_result!.files.last.extension}';
                announcement.img = await AnnouncementHTTPsHandler.uploadAnnouncementImageFile(
                    file, path);
              }
              announcement.date = DateFormat.yMd().format(DateTime.now());
              await AnnouncementHTTPsHandler.sendAnnouncement(announcement);
              AnnouncementsHome.announcements =
                  await AnnouncementHTTPsHandler.getAnnouncements(
                      KickoffApplication.ownerId);
              AnnouncementsHome.isExpanded = List<bool>.generate(AnnouncementsHome.announcements.length, (index) => false);
              _announcement = [];
              Navigator.pop(context);
              _result=null;
              KickoffApplication.update();
            }),
      );
}
