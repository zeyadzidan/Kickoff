import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';

import '../../constants.dart';

class CourtsView extends StatefulWidget {
  const CourtsView({super.key});

  @override
  State<StatefulWidget> createState() => _CourtsViewState();
}

class _CourtsViewState extends State<CourtsView> {
  @override
  Widget build(BuildContext context) => Expanded(
        child: Container(
          margin: const EdgeInsets.only(top: 30),
          child: SingleChildScrollView(
              scrollDirection: Axis.vertical,
              child: ExpansionPanelList(
                animationDuration: const Duration(milliseconds: 300),
                expandedHeaderPadding: EdgeInsets.zero,
                dividerColor: PlayerColor,
                elevation: 4,
                children: List<ExpansionPanel>.generate(
                    ProfileBaseScreen.courts.length,
                    (index) => ExpansionPanel(
                          headerBuilder: (_, isExpanded) => Container(
                            padding: const EdgeInsets.symmetric(
                                vertical: 15, horizontal: 30),
                            decoration: BoxDecoration(
                              shape: BoxShape.rectangle,
                              borderRadius: BorderRadius.circular(25),
                              color: (ProfileBaseScreen.courts[index].state ==
                                      'Out Of Order')
                                  ? Colors.red.withOpacity(0.5)
                                  : Colors.transparent,
                            ),
                            child: Text(ProfileBaseScreen.courts[index].cname),
                          ),
                          body: Container(
                              padding: const EdgeInsets.symmetric(
                                  vertical: 50, horizontal: 30),
                              child: Column(
                                children: [
                                  Column(
                                    children: List<Text>.generate(
                                        ProfileBaseScreen.courts[index]
                                            .asView()
                                            .length,
                                        (j) => Text(ProfileBaseScreen
                                            .courts[index]
                                            .asView()[j])),
                                  ),
                                ],
                              )),
                          isExpanded: ProfileBaseScreen.isExpanded[index],
                          canTapOnHeader: true,
                        )),
                expansionCallback: (i, isExpanded) => setState(
                    () => ProfileBaseScreen.isExpanded[i] = !isExpanded),
              )),
        ),
      );
}
