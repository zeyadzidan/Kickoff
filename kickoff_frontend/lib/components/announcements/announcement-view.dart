import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/announcements.dart';
import 'package:kickoff_frontend/httpshandlers/announcements-requests.dart';

import '../../constants.dart';

class AnnouncementsView extends StatefulWidget {
  const AnnouncementsView({super.key});

  @override
  State<StatefulWidget> createState() => _AnnouncementsViewState();
}

class _AnnouncementsViewState extends State<AnnouncementsView> {
  @override
  Widget build(BuildContext context) => SingleChildScrollView(
      scrollDirection: Axis.vertical,
      child: ExpansionPanelList(
        animationDuration: const Duration(milliseconds: 300),
        expandedHeaderPadding: EdgeInsets.zero,
        dividerColor: primaryColor,
        elevation: 4,
        children: List<ExpansionPanel>.generate(
            AnnouncementsHome.announcements.length,
            (index) => ExpansionPanel(
                  headerBuilder: (_, isExpanded) => Container(
                    padding: const EdgeInsets.symmetric(
                        vertical: 15, horizontal: 30),
                    decoration: BoxDecoration(
                      shape: BoxShape.rectangle,
                      borderRadius: BorderRadius.circular(25),
                    ),
                    child: Text(AnnouncementsHome.announcements[index].title),
                  ),
                  body: Container(
                      padding: const EdgeInsets.symmetric(
                          vertical: 50, horizontal: 30),
                      child: Column(
                        children: [
                          Text(AnnouncementsHome.announcements[index].body),
                          Image.network(
                            AnnouncementsHome.announcements[index].img,
                            width: 100,
                            height: 100,
                            fit: BoxFit.cover,
                            frameBuilder: (context, child, frame,
                                wasSynchronouslyLoaded) {
                              return child;
                            },
                            loadingBuilder: (context, child, loadingProgress) {
                              if (loadingProgress == null) {
                                return child;
                              } else {
                                return const Center(
                                  child: CircularProgressIndicator(
                                    color: Colors.white,
                                  ),
                                );
                              }
                            },
                          ),
                          Container(
                            margin:
                                const EdgeInsets.only(top: 70.0, right: 300.0),
                            child: ElevatedButton.icon(
                              icon: const Icon(Icons.delete),
                              label: const Text('مسح الإعلان'),
                              style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.red,
                                  padding: const EdgeInsets.symmetric(
                                      vertical: 20, horizontal: 15)),
                              onPressed: () async {
                                await AnnouncementHTTPsHandler
                                    .deleteAnnouncement(AnnouncementsHome
                                        .announcements[index].aid);
                                AnnouncementsHome.announcements =
                                    await AnnouncementHTTPsHandler
                                        .getAnnouncements(
                                            KickoffApplication.ownerId);
                                setState(() {

                                });
                              },
                            ),
                          ),
                        ],
                      )),
                  isExpanded: AnnouncementsHome.isExpanded[index],
                  canTapOnHeader: true,
                )),
        expansionCallback: (i, isExpanded) =>
            setState(() => AnnouncementsHome.isExpanded[i] = !isExpanded),
      ));
}
