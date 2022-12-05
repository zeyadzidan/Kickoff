import 'dart:io';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application.dart';
import 'package:url_launcher/url_launcher_string.dart';
import 'package:http/http.dart' as http;
class ProfileBaseScreen extends StatefulWidget {
  const ProfileBaseScreen({Key? key}) : super(key: key);

  @override
  State<ProfileBaseScreen> createState() => _ProfileBaseScreenState();
}

String? path = "";


class _ProfileBaseScreenState extends State<ProfileBaseScreen> {
  double rating = KickoffApplication.profileData["rating"];
  int rating2 = KickoffApplication.profileData["rating"].toInt();
  int subscribers = 0;
  String name = KickoffApplication.profileData["userName"];
  String phone = KickoffApplication.profileData["phoneNumber"];
  String address = KickoffApplication.profileData["location"];
  double xaxis = KickoffApplication.profileData["xAxis"];
  double yaxis = KickoffApplication.profileData["yAxis"];
  int id = KickoffApplication.profileData["id"];
  bool foundPhoto = KickoffApplication.profileData.containsKey("image");
  String tempUrl = "";
  String utl = KickoffApplication.profileData.containsKey("image")
      ? KickoffApplication.profileData["image"]
      : "";
  bool localPhoto = path == "" ? false : true;
  void uploadimage(File file, final path) async {
    UploadTask? uploadTask;
    final ref = FirebaseStorage.instance.ref().child(path);
    uploadTask = ref.putFile(file);
    final snapshot = await uploadTask!.whenComplete(() {});
    final Url = await snapshot.ref.getDownloadURL();
    String url = "http://${ip}:8080/courtOwnerAgent/CourtOwner/addImage";
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "ownerID": id,
          "imageURL": Url.toString(),
        }));
    KickoffApplication.profileData["image"] = Url;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        margin: const EdgeInsets.all(20),
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(0, 30, 0, 0),
              child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    if (foundPhoto) ...[
                      CircleAvatar(
                        radius: 40,
                        backgroundColor: Colors.green,
                        child: ClipOval(
                          child: Image.network(
                            utl,
                            width: 100,
                            height: 100,
                            fit: BoxFit.cover,
                            frameBuilder: (context, child, frame,
                                wasSynchronouslyLoaded) {
                              return child;
                            },
                            loadingBuilder: (context, child, loadingProgress) {
                              if (loadingProgress == null) {
                                return child;
                              } else {
                                return Center(
                                  child: CircularProgressIndicator(
                                    color: Colors.white,
                                  ),
                                );
                              }
                            },
                          ),
                        ),
                      )
                    ] else ...[
                      if (localPhoto) ...[
                        CircleAvatar(
                            radius: 40,
                            backgroundColor: Colors.green,
                            backgroundImage: Image.file(File(path!)).image)
                      ] else ...[
                        MaterialButton(
                          onPressed: () async {
                            FilePickerResult? result = await FilePicker.platform
                                .pickFiles(
                                    type: FileType.custom,
                                    allowedExtensions: ['png', 'jpg']);

                            if (result != null) {
                              File file = File(result.files.last.path!);
                              final path2 =
                                  'files/${KickoffApplication.profileData["id"].toString()}.${result?.files.last.extension}';
                              print(result);
                              print(result?.files.last.path);
                              uploadimage(file, path2);
                              setState(() {
                                path = result?.files.last.path;
                                localPhoto = true;
                              });
                            }
                          },
                          color: Colors.blue,
                          textColor: Colors.white,
                          child: Icon(
                            Icons.add,
                            size: 40,
                          ),
                          padding: EdgeInsets.all(20),
                          shape: CircleBorder(),
                        )
                      ]
                    ],
                    Padding(
                      padding: const EdgeInsets.fromLTRB(0, 12, 20, 0),
                      child: Row(children: [
                        SizedBox(
                          height: 60,
                          child: TextButton(
                            onPressed: () {
                              print("Show Reviews");
                            },
                            child: Column(
                              children: [
                                Text(
                                  "${rating2} \u{2B50} ", //rememper to remove the 2 in milestone 2
                                  style: TextStyle(
                                    fontSize: 20,
                                    fontWeight: FontWeight.bold,
                                    color: Colors.black,
                                  ),
                                ),
                                Text(
                                  "Reviews",
                                  style: TextStyle(
                                    fontSize: 15,
                                    letterSpacing: 0.8,
                                    color: Colors.black,
                                  ),
                                )
                              ],
                            ),
                          ),
                        ),
                        const SizedBox(
                          width: 20,
                        ),
                        SizedBox(
                          height: 60,
                          child: TextButton(
                            onPressed: () {
                              print("Show Subscribers");
                            },
                            child: Column(
                              children: [
                                Text(
                                  "${subscribers} \u{1F464}",
                                  style: TextStyle(
                                      letterSpacing: 0.4,
                                      fontSize: 20,
                                      fontWeight: FontWeight.bold,
                                      color: Colors.black),
                                ),
                                Text(
                                  "Subscribers",
                                  style: TextStyle(
                                    letterSpacing: 0.4,
                                    fontSize: 15,
                                    color: Colors.black,
                                  ),
                                )
                              ],
                            ),
                          ),
                        ),
                      ]),
                    ),
                  ]),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(vertical: 10),
              child: SizedBox(
                child: Column(
                  children: [
                    Align(
                      alignment: Alignment.centerLeft,
                      child: Text(
                        name,
                        style: TextStyle(
                          letterSpacing: 0.4,
                          fontSize: 20,
                          color: Colors.black,
                        ),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.symmetric(vertical: 10.0),
                      child: Align(
                        alignment: Alignment.centerLeft,
                        child: InkWell(
                            child: Text(
                              " \u{1F4DE} ${phone} ",
                              style: TextStyle(
                                letterSpacing: 0.4,
                                fontSize: 15,
                              ),
                            ),
                            onTap: () => launchUrlString("tel://${phone}")),
                      ),
                    ),
                    Align(
                      alignment: Alignment.centerLeft,
                      child: InkWell(
                          child: Text(
                            " \u{1F5FA} ${address}",
                            style: TextStyle(
                              letterSpacing: 0.4,
                              fontSize: 15,
                            ),
                          ),
                          onTap: () => launchUrlString(
                              'https://www.google.com/maps/place/${xaxis}+${yaxis}')),
                    ),
                  ],
                ),
              ),
            ),
          ],
        ));
  }
}