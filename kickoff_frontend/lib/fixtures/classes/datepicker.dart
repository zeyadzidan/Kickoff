import 'package:flutter/material.dart';

class DatePicker {
  static pickDate(context) => showDatePicker(
    context: context,
    initialDate: DateTime.now(),
    firstDate: DateTime(DateTime.now().year - 5),
    lastDate: DateTime(DateTime.now().year + 5),
  );
}