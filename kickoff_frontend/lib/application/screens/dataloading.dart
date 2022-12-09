import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/localFile.dart';

import '../../constants.dart';
import '../../httpshandlers/courtsrequests.dart';
import '../../httpshandlers/loginrequests.dart';

String loginData = "";
bool loading = true;
bool firstTime = true;
bool finish = false;
Map<String, dynamic> data = {};

class Loading {
  static loadData() async {
    KickoffApplication.userIP = getIP();
    print('${getIP()}');
    loginData = await localFile.readLoginData();
    firstTime = (loginData == "0");
    loading = false;
    if (!firstTime) {
      int idx = loginData.indexOf(":");
      String email = loginData.substring(0, idx).trim();
      String pass = loginData.substring(idx + 1).trim();
      data = await RoundedLogin.save2(email, pass);
      int id = data["id"];
      ProfileBaseScreen.courts = await CourtsHTTPsHandler.getCourts(id);
      // await ReservationsHome.buildTickets("main");
    }
    finish = true;
  }
}
