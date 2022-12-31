import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:kickoff_frontend/constants.dart';

class RatingButton extends StatefulWidget {
  RatingButton({super.key});

  @override
  State<RatingButton> createState() => _RatingButtonState();
}

class _RatingButtonState extends State<RatingButton> {
  @override
  Widget build(BuildContext context) {
    return FloatingActionButton(
        onPressed: () => showModalBottomSheet(
            elevation: 4,
            isScrollControlled: true,
            context: context,
            builder: (context) => Padding(
                  padding: MediaQuery.of(context).viewInsets,
                  child: SingleChildScrollView(
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
                          itemPadding:
                              const EdgeInsets.symmetric(horizontal: 4.0),
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
                                      borderSide:
                                          BorderSide(color: Colors.grey),
                                    ),
                                    focusedBorder: UnderlineInputBorder(
                                      borderSide:
                                          BorderSide(color: Colors.green),
                                    ))))
                      ],
                    ),
                  ),
                )),
        child: const Icon(Icons.star));
  }
}
