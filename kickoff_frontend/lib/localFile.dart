import 'dart:async';
import 'dart:io';

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

  static Future<String> readLoginData() async {
    try {
      final file = await _localFile;

      // Read the file
      final contents = await file.readAsString();
      print(contents);
      if (contents != "") {
        return (contents);
      } else {
        return "0";
      }
    } catch (e) {
      // If encountering an error, return 0
      return "0";
    }
  }

  static Future<File> writeLoginData(email, pass,isPlayer) async {
    final file = await _localFile;
    // Write the file
    return file.writeAsString("$email:$pass::$isPlayer");
  }

  static Future<File> clearLoginData() async {
    final file = await _localFile;
    // Write the file
    return file.writeAsString("");
  }
}
