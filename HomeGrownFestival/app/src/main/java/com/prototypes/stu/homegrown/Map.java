package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;



import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Map extends Activity {

    //////////////////////////////////////////////////////////////////////////////////////
    ////////////////////EXAMPLE CODE FOR CLASS WITH LAYOUT/ACTIVITY///////////////////////
    //////////////////////////////////////////////////////////////////////////////////////
    //    this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);       //
    //    latitude = -31.942492;                                                        //
    //    longitude = 115.847516;                                                       //
    //    double lat = -32.0060101;                                                     //
    //    double lng = 115.8546596;                                                     //
    //    //        GoogleMap map;                                                      //
    //    GoogleMap map = ((MapFragment) getFragmentManager()                           //
    //            .findFragmentById(R.id.map)).getMap();                                //
    //    Map.InitializeMap(map, latitude, longitude);                                  //
    //                                                                                  //
    //    Map.addMarker(map, "Start", "Start", 0.5f, 0.5f, latitude, longitude);        //
    //    Map.addMarker(map, "End", "End", 0.5f, 0.5f, lat, lng);                       //
    //    String key = getApplicationContext().getResources().getString(R.string.key);  //
    //    map = Map.addJourney(map, latitude, longitude, lat, lng, key);                //
    //////////////////////////////////////////////////////////////////////////////////////


//    static GoogleMap InitializeMap(GoogleMap map, double lat, double lng)
//    {
//        //Tracker don't work :(
//        GPSTracker tracker = new GPSTracker(MapActivity.getAppContext());
//        if (tracker.canGetLocation) {
//            tracker.getLocation();
//        } else {
//            Log.println(Log.VERBOSE, "Getlocation broke", "Getlocation broke");
//        }
//
//        try {
//            MapsInitializer.initialize(MapActivity.getAppContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//
//        try {
//            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                    new LatLng(lat, lng), 14));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//    }

    static GoogleMap addMarker(GoogleMap map, String title, String snippet, float anchorA, float anchorB, double lat, double lng)
    {
        map.addMarker(new MarkerOptions()
                .title(title)
                .snippet(snippet)
                .anchor(anchorA, anchorB)
                .position(new LatLng(lat, lng)));
        return map;
    }
//Dosent work
//    static GoogleMap addMarkerCurrPos(GoogleMap map, String title, String snippet, float anchorA, float anchorB)
//    {
//        GPSTracker tracker = new GPSTracker(MapActivity.getAppContext());
//        tracker.canGetLocation = true;
//        if (tracker.canGetLocation = true) {
//            tracker.getLocation();
//        } else {
//            Log.println(Log.VERBOSE, "Getlocation broke", "Getlocation broke");
//        }
//        double lat = tracker.getLatitude();
//        double lng = tracker.getLongitude();
//        map.addMarker(new MarkerOptions()
//                .title(title)
//                .snippet(snippet)
//                .anchor(anchorA, anchorB)
//                .position(new LatLng(lat, lng)));
//        return map;
//    }

    static GoogleMap addJourney(GoogleMap map, double lat1, double lng1, double lat2, double lng2, String k)
    {
        Journey j = new Journey();
        //Getting API key
//        String key = MapActivity.getAppContext().getResources().getString(R.string.key);
        String path = "origin=" + lat1 + "," + lng1 + "&destination=" + lat2+ "," + lng2;
        //Creating Google maps directions data URL
        String url = "https://maps.googleapis.com/maps/api/directions/xml?" + path +
                "&key=" + k;
        GetMapDirections r = new GetMapDirections();
        String[] urlArr = new String[1];
        urlArr[0] = url;
        //Get directions data from google
        Document doc = r.doInBackground(url);
        //Read the data
        ArrayList<LatLng> directionPoint = j.getDirection(doc);
        //Set what the directions line looks like
        PolylineOptions rectLine = new PolylineOptions().width(5).color(
                Color.BLUE);
        //Creates the poly line's shape
        for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
        }
        //Adds poly line to map
        map.addPolyline(rectLine);
        return map;
    }

    static class GetMapDirections extends AsyncTask<String, Void, Document> {

        //Gets xml data from google and turns it into a Document
        @Override
        protected Document doInBackground(String... strings) {
            Document doc = null;
            String urlString = strings[0];
            HttpURLConnection urlConnection;

            try {
                //Getting URL for Google magic
                URL url = new URL(urlString.toString());

                //Connecting to URL code block
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
                urlConnection.connect();

                //Turns XML to Document
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                doc = db.parse(urlConnection.getInputStream());

            } catch (Exception e) { e.printStackTrace(); }
            //Returns document
            return doc;
        }
    }


}
