import 'dart:async';
import 'dart:io';

import 'package:flutter/material.dart';
import 'package:path_provider/path_provider.dart';

class localFile {
  //get path
  static Future<String> get _localPath async {
    final directory = await getApplicationDocumentsDirectory();

    return directory.path;
  }

  //create and open file
  static Future<File> get _localFile async {
    final path = await _localPath;
    return File('$path/Data.txt');
  }

  // static Future<List> readLoginData() async {
  //   String s = _readLoginData() as String;
  //   List parts;
  //   if (s != "0") {
  //     int idx = s.indexOf(":");
  //     parts = [s.substring(0, idx).trim(), s.substring(idx + 1).trim()];
  //   } else
  //     parts = ["0"];
  //   return parts;
  // }

  static Future<String> readLoginData() async {
    try {
      final file = await _localFile;

      // Read the file
      final contents = await file.readAsString();
      print("contents");
      print(contents);
      print("contents");
      return (contents);
    } catch (e) {
      // If encountering an error, return 0
      return "0";
    }
  }

  static Future<File> writeLoginData(email, pass) async {
    final file = await _localFile;
    print("katbnnnnnaaa");
    print("${email}:${pass}");
    // Write the file
    return file.writeAsString("${email}:${pass}");
  }
}
