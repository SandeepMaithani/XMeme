import 'package:flutter/material.dart';
import 'package:xmeme_frontend_ui/screens/meme_stream.dart';

void main() {
  runApp(XMemeUi());
}

class XMemeUi extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Welcome to XMeme",
      theme: ThemeData(
        primaryColor: Colors.deepPurpleAccent,
        accentColor: Colors.black,
        fontFamily: "Pacifico",
      ),
      debugShowCheckedModeBanner: false,
      home: MemeStream(),
    );
  }
}
