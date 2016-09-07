package com.prototypes.stu.homegrown;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Stuart on 28/09/2014.
 */
public class fragment_event_detail extends Fragment {

    private ImageView iv;
    double latitude;
    double longitude;
    DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        dbHelper = new DBHelper(getActivity());

        View v = inflater.inflate(
                R.layout.activity_events_detail, container, false);

        TextView textView = (TextView) v.findViewById(R.id.eventDetailName);
        textView.setText(getArguments().getString("eventname"));

        textView = (TextView) v.findViewById(R.id.eventDetailStartDate);
        textView.setText(getArguments().getString("eventstartdate")+ " To ");

        textView = (TextView) v.findViewById(R.id.eventDetailEndDate);
        textView.setText(getArguments().getString("eventenddate"));

        textView = (TextView) v.findViewById(R.id.eventDetailStartTime);
        textView.setText(getArguments().getString("eventstarttime" + " Until "));

        textView = (TextView) v.findViewById(R.id.eventDetailEndTime);
        textView.setText(getArguments().getString("eventendtime") + "\n");

        textView = (TextView) v.findViewById(R.id.eventDetailLongDesc);
        textView.setText(getArguments().getString("eventlongdesc") + "\n");

        textView = (TextView) v.findViewById(R.id.eventDetailAddress);
        textView.setText(getArguments().getString("eventaddress"));

        textView = (TextView) v.findViewById(R.id.eventDetailSuburb);
        textView.setText(getArguments().getString("eventsuburb") + ", ");

        textView = (TextView) v.findViewById(R.id.eventDetailPostcode);
        textView.setText(getArguments().getString("eventpostcode"));

        textView = (TextView) v.findViewById(R.id.eventDetailPhone);
        textView.setText(getArguments().getString("eventphone"));

        textView = (TextView) v.findViewById(R.id.eventDetailFees);
        textView.setText(getArguments().getString("eventfees"));

        Button bookingbutton1 = (Button) v.findViewById(R.id.btnBookingCall);

        Button bookingbutton2 = (Button) v.findViewById(R.id.btnBookingEmail);

        Button bookingbutton3 = (Button) v.findViewById(R.id.btn_event_detail_addfavorite);

        bookingbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertFavourite(getArguments().getString("eventid"));
                Toast.makeText(getActivity().getApplicationContext(),""+getArguments().getString("eventname")+"\nAdded to Favourites", Toast.LENGTH_SHORT).show();
            }
        });

        bookingbutton1.setVisibility(View.GONE);
        bookingbutton2.setVisibility(View.GONE);

        if(getArguments().getString("eventbookable").equals("1"))
        {
            bookingbutton1.setVisibility(View.VISIBLE);
            bookingbutton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(getActivity().getApplicationContext(), "Call to " + getArguments().getString("eventphone"), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Call " + getArguments().getString("eventphone") + "?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Continue",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                                    callIntent.setData(Uri.parse("tel:" + "0430 878 862"));
//                                    callIntent.setData(Uri.parse("tel:" + getArguments().getString("eventphone")));
                                    startActivity(callIntent);
                                }
                            });
                    builder1.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                }
            });
            bookingbutton2.setVisibility(View.VISIBLE);
            bookingbutton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    fragment_event_booking eventsbooking = new fragment_event_booking();
                    fragmentTransaction.replace(R.id.mainfragment, eventsbooking);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }

        iv = (ImageView) v.findViewById(R.id.mapImage);

        //String geo = "-31.8448143,115.9047247";
        String geo = getArguments().getString("eventgeo");

        String values[] = geo.split(",");

        List<String> list = Arrays.asList(values);

        latitude = Double.parseDouble(list.get(0));
        longitude = Double.parseDouble(list.get(1));

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

//        mapView = (MapView)v.findViewById(R.id.location_map);
//        mapView.onCreate(savedInstanceState);
//
//        GoogleMap map;
//
//        map = mapView.getMap();
//
////        try {
//            MapsInitializer.initialize(this.getActivity());
////        } catch (GooglePlayServicesNotAvailableException e){
////            e.printStackTrace();
////        }
//
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14));
//
//        map.addMarker(new MarkerOptions()
//            .title("Position")
//            .snippet("LatLong")
//            .anchor(0.5f, 0.5f)
//            .position(new LatLng(latitude, longitude)));
        new SendTask().execute();
        return v;
    }

    private class SendTask extends AsyncTask<Bitmap, String, Bitmap>
    {

        @Override
        protected void onPostExecute(Bitmap bmp)
        {
            iv.setImageBitmap(bmp);
        }

        @Override
        protected Bitmap doInBackground(Bitmap... bitmaps) {
            Bitmap bm = getGoogleMapThumbnail(latitude,longitude);
            return bm;
        }
    };

    public static Bitmap getGoogleMapThumbnail(double lat, double lon)
    {
        String URL = "http://maps.google.com/maps/api/staticmap?center="+ lat + ","+ lon +"&zoom=17&size=400x300&scale=2&markers=color.red%7C"+lat+","+lon+"&sensor=false";
        Bitmap bmp = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);

        InputStream in = null;
        try {
            in = httpClient.execute(request).getEntity().getContent();
            bmp = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IllegalStateException e){
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }

}
