import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/application/screens/reservations.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../../constants.dart';
import '../../httpshandlers/courtsrequests.dart';
import '../../httpshandlers/loginrequestsCourtOwner.dart' as cLogin;
import '../../httpshandlers/loginrequestsplayer.dart' as pLogin;
import 'announcements.dart';

String loginData = "";
bool loading = true;
bool firstTime = true;
bool finish = false;
Map<String, dynamic> data = {};

class Loading {
  static loadData() async {
    KickoffApplication.userIP = getIP();
    loginData = await localFile.readLoginData();
    print("loginData is ");
    print(loginData);
    firstTime = (loginData == "0");
    loading = (loginData != "0");
    if (!firstTime) {
      int idx = loginData.indexOf(":");
      int idy = loginData.indexOf("::");
      String email = loginData.substring(0, idx).trim();
      String pass = loginData.substring(idx + 1, idy).trim();
      String isPlayer = loginData.substring(idy + 2).trim();
      print(email);
      print(pass);
      print(isPlayer);
      if (isPlayer == "1") {
        //the user is player
        KickoffApplication.player = true;
        data = await pLogin.RoundedLogin.save2(email, pass);
        KickoffApplication.playerId = "${data["id"]}";
        print(data);
        print(data["xAxis"]);
        print(data["yAxis"]);
        await pLogin.RoundedLogin.getCourtsinSearch(
            data["xAxis"], data["yAxis"]);
        AnnouncementsHome.buildFullAnnouncements();
        AnnouncementsHome.isExpanded = List<bool>.generate(AnnouncementsHome.announcements.length, (index) => false);
        print(AnnouncementsHome.announcements.length);

      } else {
        //The user is court Owner
        KickoffApplication.player = false;
        data = await cLogin.RoundedLogin.save2(email, pass);
        int id = data["id"];
        KickoffApplication.ownerId = "${data["id"]}";
        ProfileBaseScreen.courts = await CourtsHTTPsHandler.getCourts(id);
        await ReservationsHome.buildTickets();
        await AnnouncementsHome.buildAnnouncements();
      }
    }
    finish = true;
  }
}
