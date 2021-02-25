import 'dart:ui';

import 'package:flutter/material.dart';
import 'package:http/http.dart';
import 'package:xmeme_frontend_ui/screens/meme_stream.dart';

class MemeDetails extends StatefulWidget {
  @override
  _MemeDetailsState createState() => _MemeDetailsState();
}

class _MemeDetailsState extends State<MemeDetails> {
  String name;
  String link;
  String caption;

  makePostRequest(String memeName, String memeUrl, String memeCaption) async {
    // set up POST request arguments
    String url = 'https://cwodxmeme.herokuapp.com/memes';
    Map<String, String> headers = {"Content-type": "application/json"};
    String json =
        '{"name": "$memeName", "url": "$memeUrl", "caption": "$memeCaption"}';

    // make POST request
    // ignore: unused_local_variable
    Response response = await post(url, headers: headers, body: json);

    Navigator.push(
        context, MaterialPageRoute(builder: (context) => MemeStream()));
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          Text(
            "Create New Meme",
            style: TextStyle(fontSize: 22.0, fontWeight: FontWeight.bold),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 32.0),
            child: Column(
              children: <Widget>[
                SizedBox(height: 16),
                TextField(
                  keyboardType: TextInputType.emailAddress,
                  textAlign: TextAlign.center,
                  onChanged: (value) {
                    name = value;
                  },
                  decoration: InputDecoration(
                      filled: true,
                      hintText: 'Enter Your Name.',
                      border: InputBorder.none),
                ),
                SizedBox(height: 10),
                TextField(
                  keyboardType: TextInputType.emailAddress,
                  textAlign: TextAlign.center,
                  onChanged: (value) {
                    link = value;
                  },
                  decoration: InputDecoration(
                      filled: true,
                      hintText: 'Enter Your Meme URL.',
                      border: InputBorder.none),
                ),
                SizedBox(height: 10),
                TextField(
                  keyboardType: TextInputType.emailAddress,
                  textAlign: TextAlign.center,
                  onChanged: (value) {
                    caption = value;
                  },
                  decoration: InputDecoration(
                    filled: true,
                    hintText: 'Enter Caption For Your Meme .',
                    border: InputBorder.none,
                  ),
                ),
                SizedBox(height: 20),
                MaterialButton(
                  height: 40.0,
                  minWidth: double.minPositive,
                  color: Colors.black38,
                  onPressed: () {
                    makePostRequest(name, link, caption);
                  },
                  child: Text(
                    "SUBMIT",
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                )
              ],
            ),
          )
        ],
      ),
    );
  }
}
