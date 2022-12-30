import 'dart:io';
import 'dart:math';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/components/classes/announcement.dart';

import '../../../constants.dart';
import '../../../httpshandlers/Parties Requests.dart';

class makePartyButton extends StatefulWidget {
  makePartyButton( {required id,super.key}){
    ID = id;
  }
  static String ID="";
  @override
  State<StatefulWidget> createState() => _makePartyButtonState();
}

class _makePartyButtonState extends State<makePartyButton> {
  final GlobalKey<FormState> _key = GlobalKey();
  FilePickerResult? _result;
  late List<dynamic> textFields = <String>[];

  @override
  Widget build(BuildContext context) {
    return  SingleChildScrollView(
                      child: Form(
                        key: _key,
                        child: Container(
                          margin: const EdgeInsets.symmetric(
                              vertical: 25.0, horizontal: 25.0),
                          child: Column(
                            children: [
                              Text("Make A Party",
                                  style: TextStyle(
                                      color: mainSwatch , fontSize: 32 , fontWeight: FontWeight.bold,)),
                              _formField('Number of wanted players', Icons.person_add_sharp),
                              _formField('Number of all players', Icons.person),
                              _submitButton(makePartyButton.ID)
                            ],
                          ),
                        ),
                      )
                  );

  }
  _formField(label, icon) => TextFormField(
        // maxLength: (label == 'وصف الإعلان') ? 150 : 32,
        // maxLines: (label == 'وصف الإعلان') ? 3 : 1,
        decoration: InputDecoration(
          focusColor: mainSwatch,
          floatingLabelAlignment: FloatingLabelAlignment.center,
          labelText: label,
          suffixIcon: Icon(icon, color: mainSwatch),
          labelStyle: TextStyle(color: mainSwatch),
          border: const UnderlineInputBorder(),
        ),
      keyboardType: TextInputType.number,
        validator: (input) =>
            (input!.isEmpty) ? 'لا يمكنك ترك هذا الحقل فارغاً.' : null,
        onSaved: (value) => textFields.add(value!),
      );

  _submitButton(id) => Container(
        alignment: Alignment.bottomCenter,
        margin: const EdgeInsets.only(top: 15),
        child: ElevatedButton.icon(
            label: const Text('Submit'),
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
              print(id);
              print(textFields[0]);
              print(textFields[1]);
              PartiesHTTPsHandler.createParty(id,textFields[0],textFields[1]);
              Navigator.pop(context);
              KickoffApplication.update();
            }),
      );
}
