import 'package:cached_network_image/cached_network_image.dart';
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
  final bool _isPlayer = KickoffApplication.player;

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
        scrollDirection: Axis.vertical,
        child: ExpansionPanelList(
          animationDuration: const Duration(milliseconds: 300),
          expandedHeaderPadding: EdgeInsets.zero,
          dividerColor: mainSwatch,
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
                            AnnouncementsHome.announcements[index].img != ""
                                ? CachedNetworkImage(
                                    imageUrl: AnnouncementsHome.announcements[index].img,
                                    width: double.infinity,
                                    fit: BoxFit.cover,
                                  progressIndicatorBuilder:
                                      (context, url, downloadProgress) =>
                                      CircularProgressIndicator(
                                          value: downloadProgress.progress),
                                  errorWidget: (context, url, error) =>
                                      Icon(Icons.error),
                                  )
                                : Container(
                                    height: 0,
                                  ),
                            (_isPlayer)
                                ? Container(
                                    height: 0,
                                  )
                                : Container(
                                    margin: const EdgeInsets.only(top: 10.0),
                                    child: SizedBox(
                                      height: 70,
                                      width: 150,
                                      child: ElevatedButton.icon(
                                        icon: const Icon(Icons.delete),
                                        label: const Text('مسح الإعلان'),
                                        style: ElevatedButton.styleFrom(
                                            backgroundColor: Colors.red,
                                            padding: const EdgeInsets.symmetric(
                                                vertical: 10)),
                                        onPressed: () async {
                                          await AnnouncementHTTPsHandler
                                              .deleteAnnouncement(
                                                  AnnouncementsHome
                                                      .announcements[index]
                                                      .aid);
                                          AnnouncementsHome.announcements =
                                              await AnnouncementHTTPsHandler
                                                  .getAnnouncements(
                                                      KickoffApplication
                                                          .ownerId);
                                          AnnouncementsHome.isExpanded =
                                              List<bool>.generate(
                                                  AnnouncementsHome
                                                      .announcements.length,
                                                  (index) => false);
                                          setState(() {});
                                        },
                                      ),
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
}
