import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/classes/fixtureticket.dart';
import 'package:kickoff_frontend/constants.dart';

/// Fixture panel builder class to build a fixture panel in a panel-list.
class FixturePanelBuilder {
  ExpansionPanel build(FixtureTicket ticket, bool isExpanded) {
    GlobalKey<FormState> key = GlobalKey();
    return ExpansionPanel(
      headerBuilder: (_, isExpanded) =>
          GestureDetector(
            child: Container(
              padding: const EdgeInsets.symmetric(vertical: 15, horizontal: 30),
              child: Text(ticket.pname),
            ),
          ),
      body: Container(
          padding: const EdgeInsets.symmetric(vertical: 50, horizontal: 30),
          child: Column(
            children: [
              Column(
                children: List<Text>.generate(
                  ticket.asView().length,
                  (index) => Text(ticket.asView()[index])
                ),
              ),
              Form(
                key: key,
                child: TextFormField(
                  keyboardType: TextInputType.number,
                  decoration: const InputDecoration(
                      prefixIcon:
                      Icon(Icons.monetization_on, color: kPrimaryColor),
                      labelText: "Paid amount",
                      labelStyle: TextStyle(color: kPrimaryColor),
                      focusColor: kPrimaryColor,
                      border: UnderlineInputBorder(),
                      suffixText: 'EGP'),
                  validator: (input) {
                    if (input! == 0) {
                      return "This field can't be blank.";
                    }
                  },
                ),
              ),
              Container(
                alignment: Alignment.bottomCenter,
                margin: const EdgeInsets.only(top: 15),
                child: ElevatedButton.icon(
                  label: const Text('SUBMIT'),
                  icon: const Icon(Icons.schedule_send),
                  style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.green,
                      padding: const EdgeInsets.symmetric(
                          vertical: 20, horizontal: 15)),
                  onPressed: () async {
                    // Validate name and money constraints
                    if (!key.currentState!.validate()) {
                      return;
                    }
                    key.currentState!.save();
                  },
                ),
              ),
            ],
          )
      ),
      isExpanded: isExpanded,
      canTapOnHeader: true,
    );
  }
}