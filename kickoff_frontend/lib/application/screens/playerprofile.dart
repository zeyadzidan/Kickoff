import 'dart:convert';
import 'dart:io';
import 'package:cached_network_image/cached_network_image.dart';
import 'package:file_picker/file_picker.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/courts/court-view.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:url_launcher/url_launcher_string.dart';

import '../../components/classes/court.dart';

class playerProfile extends StatefulWidget {

  @override
  State<playerProfile> createState() => _playerProfileState();
}

class _playerProfileState extends State<playerProfile> {
  String name = KickoffApplication.data["name"];
  String phone = KickoffApplication.data["phoneNumber"];
  int id = KickoffApplication.data["id"];
  bool foundPhoto = KickoffApplication.data.containsKey("image");
  String utl = KickoffApplication.data.containsKey("image")
      ? KickoffApplication.data["image"]
      : "";
  // bool localPhoto = ProfileBaseScreen.path == "" ? false : true;

  Future uploadimage(File file, final path) async {
    UploadTask? uploadTask;
    final ref = FirebaseStorage.instance.ref().child(path);
    uploadTask = ref.putFile(file);
    final snapshot = await uploadTask.whenComplete(() {});
    final Url = await snapshot.ref.getDownloadURL();
    String url = "http://$ip:8080/player/addImage";
    await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "playerId": id,
          "imageURL": Url.toString(),
        }));
    KickoffApplication.data["image"] = Url;
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        children: [
          UserAccountsDrawerHeader(
            accountName: Text(name),
            accountEmail: Text(phone),
            currentAccountPicture: CircleAvatar(
              child: ClipOval(
                child: foundPhoto
                    ?CachedNetworkImage(
                      imageUrl: utl,
                      fit: BoxFit.cover,
                      width: 100,
                      height: 100,
                      progressIndicatorBuilder:
                        (context, url, downloadProgress) =>
                          CircularProgressIndicator(
                            value: downloadProgress.progress),
                      errorWidget: (context, url, error) =>
                        Icon(Icons.error),
                )
                    :MaterialButton(
                      onPressed: () async {
                        FilePickerResult? result =
                        await FilePicker.platform.pickFiles(
                            type: FileType.custom,
                            allowedExtensions: ['png', 'jpg']);

                        if (result != null) {
                          File file = File(result.files.last.path!);
                          final path2 =
                              'files/${KickoffApplication.data["id"].toString()}.${result.files.last.extension}';
                          print(result);
                          print(result.files.last.path);
                          await uploadimage(file, path2);

                          setState(() {
                            foundPhoto=true;
                            utl=KickoffApplication.data["image"];
                          });
                        }
                      },
                      color: playerColor,
                      textColor: secondaryColor,
                      shape: const CircleBorder(),
                      child: const Icon(
                        Icons.add,
                        size: 40,
                      ),
                    )


              ),
            ),
            decoration: BoxDecoration(
              color: playerColor,
            ),
          ),
        ],
      ),

    );
  }
}
