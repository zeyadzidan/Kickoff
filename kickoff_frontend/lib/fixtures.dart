import 'package:flutter/material.dart';

import 'fixturestate.dart';

class Fixtures extends StatefulWidget {
  final List fixturesList;
  const Fixtures(this.fixturesList, {super.key});

  @override
  State<Fixtures> createState() => FixturesState(fixturesList, fixturesList.length);
}