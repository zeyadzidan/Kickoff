import 'dart:convert';
import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:firebase_storage/firebase_storage.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/components/courts/court-view.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:url_launcher/url_launcher_string.dart';

import '../../components/classes/court.dart';

class ProfileBaseScreen extends StatefulWidget {
  ProfileBaseScreen({super.key}) {
    isExpanded = List<bool>.generate(courts.length, (index) => false);
  }

  static String? path = "";
  static List<Court> courts = <Court>[];
  static List<bool> isExpanded = <bool>[];

  @override
  State<ProfileBaseScreen> createState() => _ProfileBaseScreenState();
}

class _ProfileBaseScreenState extends State<ProfileBaseScreen> {
  double rating = double.parse("${KickoffApplication.data["rating"]}");
  int rating2 = double.parse("${KickoffApplication.data["rating"]}").toInt();
  int subscribers = 0;
  String name = KickoffApplication.data["userName"];
  String phone = KickoffApplication.data["phoneNumber"];
  String address = KickoffApplication.data["location"];
  double xaxis = KickoffApplication.data["xAxis"];
  double yaxis = KickoffApplication.data["yAxis"];
  int id = KickoffApplication.data["id"];
  bool foundPhoto = KickoffApplication.data.containsKey("image");
  String tempUrl = "";
  String utl = KickoffApplication.data.containsKey("image")
      ? KickoffApplication.data["image"]
      : "";

  bool localPhoto = ProfileBaseScreen.path == "" ? false : true;

  void uploadimage(File file, final path) async {
    UploadTask? uploadTask;
    final ref = FirebaseStorage.instance.ref().child(path);
    uploadTask = ref.putFile(file);
    final snapshot = await uploadTask.whenComplete(() {});
    final Url = await snapshot.ref.getDownloadURL();
    String url = "http://$ip:8080/courtOwnerAgent/CourtOwner/addImage";
    await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "ownerID": id,
          "imageURL": Url.toString(),
        }));
    KickoffApplication.data["image"] = Url;
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        margin: const EdgeInsets.all(20),
        child: Column(
          children: [
            Container(
              decoration: BoxDecoration(
                  boxShadow: const <BoxShadow>[
                    BoxShadow(
                      color: primaryColor,
                      blurRadius: 3,
                    ),
                  ],
                  shape: BoxShape.rectangle,
                  borderRadius: BorderRadius.circular(5),
                  color: Colors.green.shade100),
              child: Column(
                children: [
                  Container(
                    margin: const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
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
                                  loadingBuilder:
                                      (context, child, loadingProgress) {
                                    if (loadingProgress == null) {
                                      return child;
                                    } else {
                                      return const Center(
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
                                  backgroundImage:
                                      Image.file(File(ProfileBaseScreen.path!))
                                          .image)
                            ] else ...[
                              MaterialButton(
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
                                    uploadimage(file, path2);
                                    setState(() {
                                      ProfileBaseScreen.path =
                                          result.files.last.path;
                                      localPhoto = true;
                                    });
                                  }
                                },
                                color: primaryColor,
                                textColor: Colors.white,
                                padding: const EdgeInsets.all(20),
                                shape: const CircleBorder(),
                                child: const Icon(
                                  Icons.add,
                                  size: 40,
                                ),
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
                                        "$rating2 \u{2B50} ",
                                        //remember to remove the 2 in milestone 2
                                        style: const TextStyle(
                                          fontSize: 20,
                                          fontWeight: FontWeight.bold,
                                          color: Colors.black,
                                        ),
                                      ),
                                      const Text(
                                        "تقييم",
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
                                        "$subscribers \u{1F464}",
                                        style: const TextStyle(
                                            letterSpacing: 0.4,
                                            fontSize: 20,
                                            fontWeight: FontWeight.bold,
                                            color: Colors.black),
                                      ),
                                      const Text(
                                        "المشتركون",
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
                    child: Container(
                      margin: const EdgeInsets.symmetric(vertical: 5, horizontal: 20),
                      child: Column(
                        children: [
                          Align(
                            alignment: Alignment.centerLeft,
                            child: Text(
                              name,
                              style: const TextStyle(
                                letterSpacing: 0.4,
                                fontSize: 20,
                                color: Colors.black,
                              ),
                            ),
                          ),
                          Padding(
                            padding: const EdgeInsets.symmetric(vertical: 10.0),
                            child: Align(
                              alignment: Alignment.centerLeft,
                              child: InkWell(
                                  child: Text(
                                    " \u{1F4DE} $phone ",
                                    style: const TextStyle(
                                      letterSpacing: 0.4,
                                      fontSize: 15,
                                    ),
                                  ),
                                  onTap: () => launchUrlString("tel://$phone")),
                            ),
                          ),
                          Align(
                            alignment: Alignment.centerLeft,
                            child: InkWell(
                                child: Text(
                                  " \u{1F5FA} $address",
                                  style: const TextStyle(
                                    letterSpacing: 0.4,
                                    fontSize: 15,
                                  ),
                                ),
                                onTap: () => launchUrlString(
                                    'https://www.google.com/maps/place/$xaxis+$yaxis')),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
            CourtsView(),
          ],
        ));
  }
}
