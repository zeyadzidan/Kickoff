import 'package:flutter/material.dart';
import 'package:kickoff_frontend/application/application.dart';
import 'package:kickoff_frontend/application/screens/profile.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:kickoff_frontend/httpshandlers/ratingrequests.dart';

import 'ProfileappearToPlayer.dart';

class Ratings extends StatefulWidget {
  Ratings({super.key});
  @override
  State<Ratings> createState() => _RatingsState();
}

class _RatingsState extends State<Ratings> {

  double stars =0;

  static TextEditingController review = TextEditingController();
  @override
  Widget build(BuildContext context) {

    return Scaffold(
        appBar: AppBar(
          title: const Text("Reviews"),
          backgroundColor:(KickoffApplication.player)? playerColor:courtOwnerColor,
          centerTitle: true,
        ),
      body: Container(
        padding: const EdgeInsets.fromLTRB(10, 15, 0, 0),
        child:Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // SizedBox(height: 10.0,),
            Expanded(
              child: ListView.builder(
                  itemCount: ProfileBaseScreenPlayer.ratings.length ,
                  itemBuilder: (context,index)=> Padding(
                    padding: const EdgeInsets.fromLTRB(0, 0, 0, 10),
                    child: ListTile(
                      title: Text("${ProfileBaseScreenPlayer.ratings[index]["playerName"].toString()}",style: TextStyle(color: (KickoffApplication.player)? playerColor:courtOwnerColor,fontWeight: FontWeight.bold,),
                      ),
                      onTap: ()
                      {
                        print("Review");
                      },
                      subtitle: Text("${ProfileBaseScreenPlayer.ratings[index]["review"].toString()}", style: TextStyle(color: Colors.black,),
                      ),
                      trailing: Text("${ProfileBaseScreenPlayer.ratings[index]["stars"].toString()} \u{2B50}",style: TextStyle(color: Colors.amber),
                      ),
                    ),
                  )
              ),
            ),
          ],
        ) ,
      ),
         floatingActionButton:(KickoffApplication.player)?  FloatingActionButton(
            onPressed: () => showModalBottomSheet(
                elevation: 4,
                isScrollControlled: true,
                context: context,
                builder: (context) =>
                    Padding(
                      padding: MediaQuery.of(context).viewInsets,
                      child: SingleChildScrollView(
                        physics: const ScrollPhysics(),
                        child: Column(
                          children: [
                            const SizedBox(height: 10),
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
                                color: Colors.amber,
                              ),
                              onRatingUpdate: (rating) {
                                print(rating);
                                stars = rating as double;
                              },
                            ),
                            Container(
                                width: 500,
                                margin: EdgeInsets.fromLTRB(20.0, 20.0, 40.0, 50.0),
                                child: TextField(
                                    keyboardType: TextInputType.multiline,
                                    maxLines: 3,
                                    cursorColor: Colors.green,
                                    maxLength: 255,
                                    controller: review,
                                    decoration: InputDecoration(
                                        enabledBorder: UnderlineInputBorder(
                                          borderSide: BorderSide(color: Colors.grey),
                                        ),
                                        focusedBorder: UnderlineInputBorder(
                                          borderSide: BorderSide(color: Colors.green),
                                        ))

                                )
                            ),
                            Container(
                             margin: const EdgeInsets.fromLTRB(0, 0, 0, 30),
                              child: ElevatedButton(
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.green
                                ),
                                onPressed: () async {
                                  print("Submit");
                                 await Rating.postrating(KickoffApplication.ownerId, KickoffApplication.playerId, review.text, stars);
                                 /// to do
                                  await Rating.getratings(KickoffApplication.ownerId);
                                  // KickoffApplication.dataPlayer ["rating"] = await http.get(Uri.parse('$_url/search/CourtOwner/${CourtOwnerId}'));
                                      KickoffApplication.update();

                                  Navigator.pop(context);

                                },
                                child: const Text('Submit',
                                style: TextStyle(fontWeight: FontWeight.bold),),
                              ),
                            )

                          ],
                        ),
                      ),
                    )
            ),
            child: const Icon(Icons.star)
        ):null,
    );
  }
}