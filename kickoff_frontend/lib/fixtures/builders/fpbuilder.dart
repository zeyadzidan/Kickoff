import 'package:flutter/material.dart';

/// Fixture panel builder class to build a fixture panel in a panel-list.
class FixturePanelBuilder {
  ExpansionPanel build(String header, String body, bool isExpanded) => ExpansionPanel(
      headerBuilder: (_, isExpanded) => GestureDetector(
        child: Container(
          padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 30),
          child: Text(header),
        ),
      ),
      body: Container(
        padding: const EdgeInsets.symmetric(vertical: 50, horizontal: 30),
        child: Text(body)
      ),
      isExpanded: isExpanded,
      canTapOnHeader: true,
    );
}