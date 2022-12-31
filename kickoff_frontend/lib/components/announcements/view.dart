
import 'package:cached_network_image/cached_network_image.dart';
import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:keyboard_actions/keyboard_actions.dart';
import 'package:kickoff_frontend/application/application.dart';
import '../../constants.dart';

class Writing extends StatefulWidget {
  const Writing({super.key});

  @override
  State<StatefulWidget> createState() => writepost();
}

class writepost extends State<Writing> {

  TextEditingController writingTextController = TextEditingController();
  final FocusNode _nodeText1 = FocusNode();
  FilePickerResult? _result;
  FocusNode writingTextFocus = FocusNode();
  bool _isLoading = false;
  String name = KickoffApplication.data["name"];
  bool foundPhoto = KickoffApplication.data.containsKey("image");
  String utl = KickoffApplication.data.containsKey("image")
      ? KickoffApplication.data["image"]
      : "";
  // late File _postImageFile;
  KeyboardActionsConfig _buildConfig(BuildContext context) {
    return KeyboardActionsConfig(
      keyboardActionsPlatform: KeyboardActionsPlatform.ALL,
      keyboardBarColor: Colors.grey[200],
      nextFocus: true,
      actions: [
        KeyboardActionsItem(
          displayArrows:false,
          focusNode: _nodeText1,
        ),
        KeyboardActionsItem(
          displayArrows: false,
          focusNode: writingTextFocus,
          toolbarButtons: [
                (node) {
              return GestureDetector(
                onTap: () async {
                  print('Select Image');
                  // _getImageAndCrop();
                  // _getImageAndCrop();
                },
                child: Container(
                  color: Colors.grey[200],
                  padding: EdgeInsets.all(8.0),
                  child: Row(
                    children: <Widget>[
                      Icon(Icons.add_photo_alternate,size:28),
                      Text(
                        "Add Image",
                        style: TextStyle(color: Colors.black,fontSize: 18,fontWeight: FontWeight.bold),
                      ),
                    ],
                  ),
                ),
              );
            },
          ],
        ),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
     return Scaffold(
       appBar: AppBar(
         backgroundColor: courtOwnerColor,
         title: Text('Writing Post'),
         centerTitle: true,
         actions: [
           IconButton(
              icon: const Icon(Icons.post_add) ,
               onPressed:(){
                 Navigator.pop(context);
               },
               // child: Text('Post',style: TextStyle(fontSize: 20,color: Colors.white,fontWeight: FontWeight.bold),)
           )
         ],
       ),
       body: Stack(
           children: <Widget>[
           KeyboardActions(
             config: _buildConfig(context),
             child: Column(
               children: <Widget>[
                 Container(
                   width: size.width,
                   height: size.height - MediaQuery.of(context).viewInsets.bottom - 80,
                   child: Padding(
                     padding: const EdgeInsets.only(right:14.0,left:10.0),
                     child: Column(
                       crossAxisAlignment: CrossAxisAlignment.start,
                       children: <Widget>[
                         Row(
                           crossAxisAlignment: CrossAxisAlignment.center,
                           children: <Widget>[
                             Padding(
                                 padding:const EdgeInsets.all(8.0),
                               child: Container(
                                 width: 40,
                                 height: 40,
                                 ///to do : put image
                                 //   child: Image.asset(utl),
                                 child: ClipOval(
                                   child: CachedNetworkImage(
                                     imageUrl: utl,
                                     width: 100,
                                     height: 100,
                                     fit: BoxFit.cover,
                                     progressIndicatorBuilder:
                                         (context, url, downloadProgress) =>
                                         CircularProgressIndicator(
                                             value: downloadProgress.progress),
                                     errorWidget: (context, url, error) =>
                                         Icon(Icons.error),
                                   ),
                                 ),
                               ),
                             ),
                             Text(name,style: TextStyle(fontSize: 22,fontWeight: FontWeight.bold),),
                           ],
                         ),
                         Divider(height: 1,color: Colors.black,),
                     TextFormField(
                       autofocus: true,
                       focusNode: writingTextFocus,
                       decoration: InputDecoration(
                         border: InputBorder.none,
                         hintText: 'Writing anything.',
                         hintMaxLines: 4,
                       ),
                       controller: writingTextController,
                       keyboardType: TextInputType.multiline,
                       maxLines: null,
                     ),
                         // _postImageFile != null ? Image.file(_postImageFile,fit: BoxFit.fill,) :
                         Container(),
                       ],
                     ),
                   ),
                 )
               ],
             ),
           )
        ]
       ),
     );

  }
  // Future<void> _getImageAndCrop() async {
  //   File imageFileFromGallery = (await ImagePicker.pickImage(source: ImageSource.gallery)) as File;
  //   if (imageFileFromGallery != null) {
  //     File cropImageFile = await Utils.cropImageFile(imageFileFromGallery);//await cropImageFile(imageFileFromGallery);
  //     if (cropImageFile != null) {
  //       setState(() {
  //         _postImageFile = cropImageFile;
  //       });
  //     }
  //   }
  // }
}