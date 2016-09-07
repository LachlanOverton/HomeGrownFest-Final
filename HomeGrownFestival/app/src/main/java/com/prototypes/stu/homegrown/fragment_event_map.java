package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Stuart on 5/10/2014.
 */
public class fragment_event_map extends Fragment {

    double latitude;
    double longitude;
    String url = "http://users.on.net/~drace/events.xml";
    Context thiscontext;
    GPSTracker tracker;
    List<Event> listofevents = new ArrayList<Event>();
    GoogleMap map;
    private static View view;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        if(view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if(parent != null)
            {
                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.activity_event_map, container, false);
        } catch (InflateException e) {

        }

//        View v = inflater.inflate(
//                R.layout.activity_event_map, container, false);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map1)).getMap();

        new RetrieveFeedTask().execute();

        return view;
    }

    public void setList(List<Event> newevents)
    {
        this.listofevents = newevents;
    }

    public GoogleMap InitializeMap(GoogleMap map)
    {
        double lng = 0;
        double lat = 0;

        tracker = new GPSTracker(getActivity());
        if(tracker.canGetLocation)
        {
            tracker.getLocation();
            latitude = tracker.getLatitude();
            longitude = tracker.getLongitude();
        }

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(latitude, longitude), 14));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thiscontext = getActivity();
    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, List<Event>>
    {
        private Exception exception;

        // Retrieve the data in the background. Once completed pass the List<Event> to onPostExecute.
        @Override
        protected List<Event> doInBackground(Void... voids) {
            List<Event> events = null;
            XMLPullParserHandler parser = new XMLPullParserHandler(getActivity());
            try
            {
                List<Favourite> favs = null;
                events = parser.parseevents(url, 1, thiscontext, favs); // Parses the xml from the provided url and returns the data as List<Event>
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return events;
        }

        // Update the listView with the List<Event> from doInBackground

        @Override
        protected void onPostExecute(List<Event> event)
        {
            listofevents = event;
            InitializeMap(map);

            try {
                for (int i = 0; i < listofevents.size(); i++) {
                    String[] temp = listofevents.get(i).geo.split(",");
                    double[] latlng = new double[2];
                    latlng[0] = Double.parseDouble(temp[0].toString());
                    latlng[1] = Double.parseDouble(temp[1].toString());
                    Map.addMarker(map, listofevents.get(i).name, listofevents.get(i).shortdesc, 0.5f, 0.5f, latlng[0], latlng[1]);
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latlng[0], latlng[1]), 12));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }

}