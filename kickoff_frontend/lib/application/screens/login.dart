import 'package:flutter/material.dart';
import 'package:kickoff_frontend/components/login/BuildLoginPlayer.dart';
import 'package:kickoff_frontend/components/login/CancelButton.dart';
import 'package:kickoff_frontend/constants.dart';

import '../../components/login/BuildSignUpPlayer.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({super.key});
  static List<dynamic> courtsSearch=[];
  @override
  State<LoginScreen> createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen>
    with SingleTickerProviderStateMixin {
  bool isLogin = true;
  late Animation<double> containerSize;
  AnimationController? animationController;
  Duration animationDuration = const Duration(milliseconds: 270);

  @override
  void initState() {
    super.initState();
    animationController =
        AnimationController(vsync: this, duration: animationDuration);
  }

  @override
  void dispose() {
    animationController!.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    double viewInset = MediaQuery.of(context)
        .viewInsets
        .bottom; // we are using this to determine Keyboard is opened or not
    double defaultLoginSize = size.height - (size.height * 0.3);
    double defaultRegisterSize = size.height - (size.height * 0.1);

    containerSize =
        Tween<double>(begin: size.height * 0.1, end: defaultRegisterSize)
            .animate(CurvedAnimation(
                parent: animationController!, curve: Curves.linear));
    return Scaffold(
      body: Stack(
        children: [
          // Lets add some decorations
          Positioned(
              top: 100,
              right: -50,
              child: Container(
                width: 100,
                height: 100,
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(50),
                    
                    boxShadow: const <BoxShadow>[
                      BoxShadow(
                        color: Colors.black,
                        blurRadius: 5,
                      ),
                    ],
                    color: playerColor),
              )),

          // Positioned(
          //     top: -50,
          //     left: -50,
          //       child: Container(
          //         width: 200,
          //         height: 200,
          //         decoration: BoxDecoration(
          //             borderRadius: BorderRadius.circular(100),
          //             boxShadow: const <BoxShadow>[
          //               BoxShadow(
          //                 color: Colors.black,
          //                 blurRadius: 5,
          //               ),
          //             ],
          //             color: PlayerColor),
          //          child: Padding(
          //            padding: const EdgeInsets.fromLTRB(55, 100, 0, 0),
          //              child :InkWell(
          //                onTap: (){
          //                  Navigator.pushNamed( context,'/login');
          //                },
          //                child: Text("I am a CourtOwner?!"
          //                  ,style: const TextStyle(
          //                    fontSize: 20,
          //                    fontWeight: FontWeight.bold,
          //                    color: Colors.white,
          //                 ),
          //                ),
          //              ),
          //     ),
          //       ),
          //
          //     ),

          SizedBox(
            width: 210,
            child: InkWell(
              onTap: (){
                Navigator.popAndPushNamed(context, '/login');
              },
              borderRadius: BorderRadius.circular(100),
              child: Stack(
                clipBehavior: Clip.none, children: <Widget>[
                circleSizer()
              ],
              ),
            ),
          ),

          Positioned(
              bottom: -160,
              left: -100,
              child: Container(
                width: 290,
                height: 290,
                decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(145),
                    boxShadow: const <BoxShadow>[
                      BoxShadow(
                        color: Colors.black,
                        blurRadius: 5,
                      ),
                    ],
                    color: playerColor),
              )),

          // Cancel Button
          CancelButton(
            isLogin: isLogin,
            animationDuration: animationDuration,
            size: size,
            animationController: animationController,
            tapEvent: isLogin
                ? null
                : () {
                    // returning null to disable the button
                    animationController!.reverse();
                    setState(() {
                      isLogin = !isLogin;
                    });
                  },
          ),

          // Login Form
          Padding(
            padding: EdgeInsets.only(top: size.height *0.1),
            child: LoginFormPlayer(
                isLogin: isLogin,
                animationDuration: animationDuration,
                size: size,
                defaultLoginSize: defaultLoginSize),
          ),
          // Register Container
          AnimatedBuilder(
            animation: animationController!,
            builder: (context, child) {
              if (viewInset == 0 && isLogin) {
                return buildRegisterContainer();
              } else if (!isLogin) {
                return buildRegisterContainer();
              }

              // Returning empty container to hide the widget
              return Container();
            },
          ),

          // Register Form
          RegisterFormPlayer(
              isLogin: isLogin,
              animationDuration: animationDuration,
              size: size,
              defaultLoginSize: defaultRegisterSize),
        ],
      ),
    );
  }

  Widget buildRegisterContainer() {
    return Align(
      alignment: Alignment.bottomCenter,
      child: Container(
        width: double.infinity,
        height: containerSize.value,
        decoration: const BoxDecoration(
            borderRadius: BorderRadius.only(
              topLeft: Radius.circular(100),
              topRight: Radius.circular(100),
            ),
            color: secondaryColor),
        alignment: Alignment.center,
        child: GestureDetector(
          onTap: !isLogin
              ? null
              : () {
                  animationController!.forward();
                  setState(() {
                    isLogin = !isLogin;
                  });
                },
          child: isLogin
              ? const Text(
                  "SignUp",
                  style: TextStyle(color: playerColor, fontSize: 18),
                )
              : null,
        ),
      ),
    );
  }
}

class circleSizer extends StatefulWidget {
  const circleSizer({Key? key}) : super(key: key);

  @override
  State<circleSizer> createState() => _circleSizerState();
}

class _circleSizerState extends State<circleSizer> with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _animation;

  @override
  void initState() {
    super.initState();
    _controller =
        AnimationController(duration: const Duration(seconds: 2), vsync: this);
    _animation = Tween(begin: 0.0,end: 10.0,).animate(_controller)
      ..addListener(() {
        setState(() {
          // The state that has changed here is the animation object’s value.
        });

      });
    _animation.addStatusListener((status) {
      if (status == AnimationStatus.completed) {
        _controller.reverse();
      } else if (status == AnimationStatus.dismissed) {
        _controller.forward();
      }
    });
    _controller.forward();
    // _controller.reverse();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Positioned(
      top: -50,
      left: -50,
      child: Container(
        width: 200+_animation.value,
        height: 200+_animation.value,
        decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(100),
            boxShadow: const <BoxShadow>[
              BoxShadow(
                color: Colors.black,
                blurRadius: 5,
              ),
            ],
            color: playerColor),
        child: Padding(
          padding: const EdgeInsets.fromLTRB(55, 100, 0, 0),
          child :Text("I am a CourtOwner?!"
            ,style: const TextStyle(
              fontSize: 20.0,
              fontWeight: FontWeight.bold,
              color: Colors.white,
            ),
          ),
        ),
      ),
    );
  }
}
