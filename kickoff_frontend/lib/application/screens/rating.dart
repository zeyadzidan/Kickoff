import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';

class Ratings extends StatefulWidget {
  Ratings({super.key});

  @override
  State<Ratings> createState() => _RatingsState();
}

class _RatingsState extends State<Ratings> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Reviews"),
          backgroundColor: playerColor,
          centerTitle: true,
        ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            Container(
              height: 180,
              color: Colors.yellow,
            ),
            Container(
              height: 180,
              color: Colors.red,
            ),
            Container(
              height: 180,
              color: Colors.greenAccent,
            ),
            Container(
              height: 180,
              color: Colors.orangeAccent,
            ),
            Container(
              height: 180,
              color: Colors.blue,
            ),
          ],
        ),
      ),
        floatingActionButton:  FloatingActionButton(
            onPressed: () => showModalBottomSheet(
                elevation: 4,
                context: context,
                builder: (context) =>
                    SingleChildScrollView(
                      physics: const ScrollPhysics(),
                      child: Column(
                        children: [
                          RatingBar.builder(
                            initialRating: 0,
                            itemSize: 40,
                            minRating: 1,
                            direction: Axis.horizontal,
                            allowHalfRating: false,
                            itemCount: 5,
                            itemPadding: const EdgeInsets.symmetric(horizontal: 4.0),
                            itemBuilder: (context, _) => const Icon(
                              Icons.star,
                              color: Colors.green,
                            ),
                            onRatingUpdate: (rating) {
                              print(rating);
                            },
                          ),
                          Container(
                              width: 500,
                              margin: EdgeInsets.fromLTRB(20.0, 20.0, 40.0, 50.0),
                              child: const TextField(
                                  keyboardType: TextInputType.multiline,
                                  maxLines: 3,
                                  cursorColor: Colors.green,
                                  maxLength: 255,
                                  decoration: InputDecoration(
                                      enabledBorder: UnderlineInputBorder(
                                        borderSide: BorderSide(color: Colors.grey),
                                      ),
                                      focusedBorder: UnderlineInputBorder(
                                        borderSide: BorderSide(color: Colors.green),
                                      ))
                              )
                          )
                        ],
                      ),
                    )
            ),
            child: const Icon(Icons.star)
        )
    );
  }
}