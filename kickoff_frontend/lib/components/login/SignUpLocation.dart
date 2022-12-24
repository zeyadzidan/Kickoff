import 'package:flutter/material.dart';
import 'package:open_street_map_search_and_pick/open_street_map_search_and_pick.dart';

class FindLocation extends StatelessWidget {
  const FindLocation({
    Key? key,
    required this.title,
  }) : super(key: key);
  final String title;
  static var locationAddress;
  static var xAxis;
  static var yAxis;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: SizedBox(
        child: OpenStreetMapSearchAndPick(
            center: LatLong(31.2160786, 29.9469253),
            buttonColor: const Color(0XFF4CAF50),
            buttonText: 'اختر الموقع',
            onPicked: (pickedData) {
              locationAddress = pickedData.address;
              xAxis = pickedData.latLong.latitude;
              yAxis = pickedData.latLong.longitude;
            }),
      ),
    );
  }
}
