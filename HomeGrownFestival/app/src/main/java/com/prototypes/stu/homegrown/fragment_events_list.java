package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;
import java.util.Timer;

/**
 * Created by Stuart on 28/09/2014.
 */
public class fragment_events_list extends Fragment {

    ListView listView;
    String url = "http://users.on.net/~drace/events.xml";
    Context thiscontext;
    private Timer timer;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(
                R.layout.activity_event_list, container, false);
        listView = (ListView)v.findViewById(R.id.list);
        //textView = (TextView)v.findViewById(R.id.error_text);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
//                Object o = listView.getItemAtPosition(position);
//                Toast.makeText(thiscontext,id + " ", Toast.LENGTH_SHORT).show();
//            }
//        });

//        Button button = (Button)v.findViewById(R.id.btn_eventlist_favorites);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                FragmentTransaction ft = getFragmentManager().beginTransaction();
////                ft.replace(R.id.mainfragment, new EventsFavoritesFragment());
////                ft.addToBackStack(null);
////                ft.commit();
//            }
//        });

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        thiscontext = getActivity();

        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (getMobileState(getActivity()) || cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) { // This line will cause the app to crash on Tablets without a mobile data connection.
            new RetrieveFeedTask().execute();
        } else {
            Toast.makeText(getActivity(),"Unable to retrieve data. No Mobile data or WiFi connection detected. Please check your connection settings.", Toast.LENGTH_LONG).show();
            //TextView textView = (TextView)getActivity().findViewById(R.id.error_text);
            //textView.setText("Unable to retrieve data. No Mobile data or WiFi connection detected. Please check your connection settings.");
        }

    }

    public static boolean getMobileState(Context pcontext)
    {
        ConnectivityManager connect = null;
        connect = (ConnectivityManager)pcontext.getSystemService(pcontext.CONNECTIVITY_SERVICE);

        if(connect != null) {
            NetworkInfo result = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (result != null && result.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }

        }
        else
            return false;
    }

//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        new RetrieveFeedTask().execute();
//    }

    // AsyncTask to retrieve data from the url.
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
        protected void onPostExecute(List<Event> events)
        {
            Event[] array = events.toArray(new Event[0]);

            // Custom adapter used for displaying the name, startdate and shortdesc in event_list_item.xml
            EventAdapter adapter = new EventAdapter(thiscontext, R.layout.activity_list_item, array);
            //ArrayAdapter<Event> adapter =
            //new ArrayAdapter<Event>(this,R.layout.event_list_item, events);
            listView.setAdapter(adapter);
        }

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }

}
