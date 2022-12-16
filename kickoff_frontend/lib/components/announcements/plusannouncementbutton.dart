import 'dart:io';
import 'dart:convert';
import 'dart:math';
import 'package:http/http.dart' as http;

import 'package:cached_network_image/src/cached_image_widget.dart';
import 'package:file_picker/file_picker.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/components/announcements/announcement-view.dart';
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
  late List<String> _announcement = <String>[];
  void uploadimage(File file, final path) async {
    UploadTask? uploadTask;
    final ref = FirebaseStorage.instance.ref().child(path);
    uploadTask = ref.putFile(file);
    final snapshot = await uploadTask.whenComplete(() {});
    final Url = await snapshot.ref.getDownloadURL();

    //2ib3t elrequest hina :-

    // String url = "http://$ip:8080/courtOwnerAgent/CourtOwner/addImage";
    // await http.post(Uri.parse(url),
    //     headers: {"Content-Type": "application/json"},
    //     body: json.encode({
    //       "ownerID": id,
    //       "imageURL": Url.toString(),
    //     }));
  }

  @override
  Widget build(BuildContext context) => FloatingActionButton(
      backgroundColor: primaryColor,
      child: const Icon(Icons.add, size: 35),
      onPressed: () => showModalBottomSheet(
            elevation: 4,
            context: context,
            builder: (context) => SizedBox(
                height: MediaQuery.of(context).size.height / 2,
                child: Expanded(
                  child: SingleChildScrollView(
                      child: Form(
                    key: _key,
                    child: Container(
                      margin: const EdgeInsets.symmetric(
                          vertical: 25.0, horizontal: 25.0),
                      child: Column(
                        children: [
                          const Text("أضف إعلاناً",
                              style:
                                  TextStyle(color: primaryColor, fontSize: 32)),
                          _formField('العنوان', Icons.title),
                          _formField('وصف الإعلان', Icons.announcement),
                          Container(
                            margin: const EdgeInsets.only(top: 15),
                            child: ElevatedButton.icon(
                              label: const Text(''),
                              icon: const Icon(Icons.add_a_photo),
                              style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.green,
                                  padding: const EdgeInsets.symmetric(
                                      vertical: 20, horizontal: 15)),
                              onPressed:
                                  () async {
                                    Random random = new Random();
                                    FilePickerResult? result =
                                    await FilePicker.platform.pickFiles(
                                        type: FileType.custom,
                                        allowedExtensions: ['png', 'jpg']);
                                    if (result != null) {
                                      File file = File(result.files.last.path!);
                                      final path2 =
                                          'files/${KickoffApplication.data["id"].toString()}.${random.nextInt(10000000)}.${result.files.last.extension}';
                                      print(result);
                                      print(result.files.last.path);
                                      uploadimage(file, path2);
                                      setState(() {
                                            //4oof hati3rdha 2zay lw 3ayz
                                      });
                                    }
                                  }, // TODO: Handle attachments
                            ),
                          ),
                          _submitButton()
                        ],
                      ),
                    ),
                  )),
                )),
          ));

  _formField(label, icon) => TextFormField(
        maxLength: (label == 'وصف الملعب') ? 150 : 32,
        maxLines: (label == 'وصف الملعب') ? 3 : 1,
        decoration: InputDecoration(
          focusColor: primaryColor,
          floatingLabelAlignment: FloatingLabelAlignment.center,
          labelText: label,
          suffixIcon: Icon(icon, color: primaryColor),
          labelStyle: const TextStyle(color: primaryColor),
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
            label: const Text('حفظ'),
            icon: const Icon(Icons.schedule_send),
            style: ElevatedButton.styleFrom(
                backgroundColor: primaryColor,
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
              announcement.cni = _announcement[2]
                  as CachedNetworkImage; // TODO: DEPLOY ATTACHMENTS
              announcement.date = DateFormat.yMd().format(DateTime.now());
              await AnnouncementHTTPsHandler.sendAnnouncement(announcement);
              AnnouncementsHome.announcements =
                  await AnnouncementHTTPsHandler.getAnnouncements(
                      KickoffApplication.ownerId);
              _announcement = [];
              KickoffApplication.update();
              Navigator.pop(context);
            }),
      );
}
