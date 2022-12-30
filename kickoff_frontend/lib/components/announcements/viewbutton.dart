

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../../constants.dart';

class PlusAnnouncementButton2 extends StatefulWidget {
  const PlusAnnouncementButton2({super.key});

  @override
  State<StatefulWidget> createState() => postbutton();
}

class postbutton extends State<PlusAnnouncementButton2> {
  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
        backgroundColor: courtOwnerColor,
        tooltip: "إضافة إعلان",
        child: const Icon(Icons.create, size: 35),
        onPressed: () =>{
          Navigator.pushNamed(context, '/writepost'),
        }
    );
  }
}