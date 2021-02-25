import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:xmeme_frontend_ui/widgets/meme_details.dart';

Color mainColor = Colors.amber;
Color secondaryColor = Colors.black87;

class MemeStream extends StatefulWidget {
  @override
  _MemeStreamState createState() => _MemeStreamState();
}

class _MemeStreamState extends State<MemeStream> {
  List memeList = List.empty();
  var data;

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  fetchData() async {
    var response = await http.get("https://cwodxmeme.herokuapp.com/memes");

    if (response.statusCode == 200) {
      memeList = json.decode(response.body) as List;
      data = json.decode(response.body);
    }

    setState(() {});
  }

  showGridWidget(int index) {
    String imageUrl = memeList[index]['url'].toString();
    String createrName = memeList[index]['name'].toString();
    String memeCaption = memeList[index]['caption'].toString();

    return Card(
      clipBehavior: Clip.antiAlias,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.circular(20.0),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          ListTile(
            title: RichText(
              text: TextSpan(
                children: [
                  WidgetSpan(
                    child: Icon(Icons.account_circle_rounded),
                  ),
                  TextSpan(
                      style: TextStyle(
                        fontSize: 25.0,
                        fontWeight: FontWeight.bold,
                        letterSpacing: 4,
                      ),
                      text: ' ' + createrName),
                ],
              ),
            ),
            subtitle: RichText(
              text: TextSpan(
                children: [
                  WidgetSpan(
                    child: Icon(Icons.notes_sharp),
                  ),
                  TextSpan(
                    style: TextStyle(fontSize: 15.0),
                    text: '   ' + memeCaption,
                  ),
                ],
              ),
            ),
          ),
          FadeInImage(
            height: 385,
            fit: BoxFit.fill,
            image: NetworkImage(imageUrl),
            placeholder: AssetImage('assets/image/placeholder.jpg'),
          ),
          SizedBox(
            height: 8,
          ),
          MaterialButton(
            height: 40.0,
            minWidth: double.minPositive,
            color: Colors.black38,
            onPressed: () {},
            child: Text(
              "EDIT",
              style: TextStyle(
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
            ),
          )
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: mainColor,
      appBar: AppBar(
        centerTitle: true,
        elevation: 10.5,
        title: Text(
          "MEME  STREAM",
          style: TextStyle(fontSize: 22.0, fontWeight: FontWeight.bold),
        ),
      ),
      body: CustomScrollView(
        slivers: <Widget>[
          SliverAppBar(
            backgroundColor: Colors.amber,
            expandedHeight: 300,
            flexibleSpace: FlexibleSpaceBar(
              background: MemeDetails(),
            ),
          ),
          SliverGrid(
            gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,
              childAspectRatio: 1.5,
              crossAxisSpacing: 20,
              mainAxisSpacing: 30,
            ),
            delegate: SliverChildBuilderDelegate(
              (BuildContext context, int index) {
                if (index >= memeList.length) {
                  return null;
                } else {
                  return showGridWidget(index);
                }
              },
            ),
          )
        ],
      ),
    );
  }
}
