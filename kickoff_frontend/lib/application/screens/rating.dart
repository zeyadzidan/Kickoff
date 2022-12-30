import 'package:flutter/material.dart';
import 'package:kickoff_frontend/constants.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';

class Ratings extends StatefulWidget {
  Ratings({super.key});
  @override
  State<Ratings> createState() => _RatingsState();
}

class _RatingsState extends State<Ratings> {
  List<dynamic> displayList = [
    {"stars": 4, "review": "The cour is ver good", "player": "7amo"},
    {"stars": 2, "review": "The cour is ver good", "player": "7amo"},
    {"stars": 5, "review": "", "player": "7amo"},
    {"stars": 1, "review": "The cour is ver good", "player": "7amo"}
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text("Reviews"),
          backgroundColor: playerColor,
          centerTitle: true,
        ),
      body: Container(
        padding: const EdgeInsets.fromLTRB(10, 15, 0, 0),
        child:Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const SizedBox(
              height: 5.0,
            ),
            // SizedBox(height: 10.0,),
            Expanded(
              child: ListView.builder(
                  itemCount: displayList.length ,
                  itemBuilder: (context,index)=> Padding(
                    padding: const EdgeInsets.fromLTRB(0, 0, 0, 10),

                    child: ListTile(
                      title: Text("${displayList[index]["player"].toString()}",style: TextStyle(color: Colors.green,fontWeight: FontWeight.bold,),
                      ),
                      onTap: ()
                      {
                        print("Review");
                      },
                      subtitle: Text("${displayList[index]["review"].toString()}", style: TextStyle(color: Colors.black,),
                      ),
                      trailing: Text("${displayList[index]["stars"].toString()} \u{2B50}",style: TextStyle(color: Colors.green),
                      ),
                    ),
                  )
              ),
            ),
          ],
        ) ,
      ),
        floatingActionButton:  FloatingActionButton(
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
                            ),
                            Container(
                             margin: const EdgeInsets.fromLTRB(0, 0, 0, 30),
                              child: ElevatedButton(
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Colors.green
                                ),
                                onPressed: () {
                                  print("Submit");
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
        )
    );
  }
}