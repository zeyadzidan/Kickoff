import 'package:flutter/material.dart';

enum Themes { light, dark }

class AppThemes {
  static final ThemeData lightTheme = ThemeData(
    primarySwatch: Colors.green,
    primaryColor: Colors.green,
    appBarTheme: const AppBarTheme(
      backgroundColor: Colors.green,
    ),
    textSelectionTheme: const TextSelectionThemeData(
      selectionColor: Colors.grey,
      cursorColor: Colors.black,
      selectionHandleColor: Colors.greenAccent,
    ),
    backgroundColor: Colors.white70,
    dividerColor: Colors.green,
    brightness: Brightness.light,
    highlightColor: Colors.white,
    floatingActionButtonTheme: const FloatingActionButtonThemeData(
      foregroundColor: Colors.white,
      backgroundColor: Colors.green,
      splashColor: Colors.lightGreen,
    ),
    colorScheme: ColorScheme.fromSwatch().copyWith(secondary: Colors.white),
  );

  static final ThemeData darkTheme = ThemeData(
    primaryColor: Colors.greenAccent,
    scaffoldBackgroundColor: Colors.black54,
    dividerColor: Colors.greenAccent,
    brightness: Brightness.dark,
    highlightColor: Colors.white,
    backgroundColor: Colors.black87,
    textSelectionTheme:
        const TextSelectionThemeData(selectionColor: Colors.grey),
    floatingActionButtonTheme: const FloatingActionButtonThemeData(
        foregroundColor: Colors.black87,
        backgroundColor: Colors.green,
        focusColor: Colors.greenAccent,
        splashColor: Colors.lightGreen),
  );

  static ThemeData getThemeFromKey(Themes key) {
    switch (key) {
      case Themes.light:
        return lightTheme;
      case Themes.dark:
        return darkTheme;
      default:
        return lightTheme;
    }
  }
}
