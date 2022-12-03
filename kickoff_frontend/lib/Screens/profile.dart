import 'dart:convert';
import 'dart:io';
import 'package:flutter/material.dart';
import 'package:file_picker/file_picker.dart';
import 'package:kickoff_frontend/application.dart';
import 'package:url_launcher/url_launcher_string.dart';
import 'package:http/http.dart' as http;

class ProfileBaseScreen extends StatefulWidget {
  const ProfileBaseScreen({Key? key}) : super(key: key);

  @override
  State<ProfileBaseScreen> createState() => _ProfileBaseScreenState();
}

class _ProfileBaseScreenState extends State<ProfileBaseScreen> {
  double rating = KickoffApplication.profileData["rating"];
  int subscribers = 0;
  String name = KickoffApplication.profileData["userName"];
  String phone = KickoffApplication.profileData["phoneNumber"];
  String address = KickoffApplication.profileData["location"];
  bool emptyphoto = false;
  double xaxis = KickoffApplication.profileData["xAxis"];
  double yaxis = KickoffApplication.profileData["yAxis"];
  String? path;
  int id = KickoffApplication.profileData["id"];
  Future save(String file) async {
    String url = "http://192.168.1.7:8080/courtOwnerAgent/CourtOwner/addImage";
    print(file);
    var res = await http.post(Uri.parse(url),
        headers: {"Content-Type": "application/json"},
        body: json.encode({
          "ownerID": id,
          "imageURL": file,
        }));
    print(res.body);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
        // decoration: BoxDecoration(
        //   border: Border(
        //     bottom: BorderSide(
        //       color: Colors.black,
        //     ),
        //   ),
        // ),
        margin: const EdgeInsets.all(20),
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.fromLTRB(0, 30, 0, 0),
              child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    if (emptyphoto) ...[
                      CircleAvatar(
                          radius: 40,
                          backgroundColor: Color(0xff74EDED),
                          backgroundImage: Image.file(File(path!)).image
                              as ImageProvider<Object>
                          //     NetworkImage("https://placeimg.com/640/480/people"),
                          )
                    ] else ...[
                      MaterialButton(
                        onPressed: () async {
                          FilePickerResult? result = await FilePicker.platform
                              .pickFiles(
                                  type: FileType.custom,
                                  allowedExtensions: ['png', 'jpg']);

                          // The result will be null, if the user aborted the dialog
                          if (result != null) {
                            File file = File(result.files.first.path!);
                            path = result.files.first.path;
                            save(path!);
                          }
                          setState(() {
                            path = result?.files.first.path;
                            emptyphoto = true;
                          });
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
                                  "${rating} / 5",
                                  style: TextStyle(
                                    fontSize: 20,
                                    fontWeight: FontWeight.bold,
                                    color: Colors.black,
                                  ),
                                ),
                                Text(
                                  "Rating",
                                  style: TextStyle(
                                    fontSize: 15,
                                    letterSpacing: 0.4,
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
                              print("Show Reviews");
                            },
                            child: Column(
                              children: [
                                Text(
                                  "${subscribers}",
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
                        child: Text(
                          " \u{1F4DE} ${phone} ",
                          style: TextStyle(
                            letterSpacing: 0.4,
                            fontSize: 15,
                            // color: ,
                          ),
                        ),
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
                              color: Colors.blue,
                              decoration: TextDecoration.underline,
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
