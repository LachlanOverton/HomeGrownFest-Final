package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Stuart on 28/09/2014.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    Context context;
    int layoutResourceId;
    Event data[] = null;

    public EventAdapter(Context context, int layoutResourceId, Event[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        EventHolder holder = null; // Custom class which will hold all

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new EventHolder();
            holder.eventName = (TextView)row.findViewById(R.id.eventName);
            holder.eventStartDate = (TextView)row.findViewById(R.id.eventStartDate);
            holder.eventEndDate = (TextView)row.findViewById(R.id.eventEndDate);
            holder.eventShortDesc = (TextView)row.findViewById(R.id.eventShortDesc);
            holder.eventAddress = (TextView)row.findViewById(R.id.eventVenue);
            holder.eventSuburb = (TextView)row.findViewById(R.id.eventSuburb);
            holder.eventPostcode = (TextView)row.findViewById(R.id.eventPostcode);
            holder.eventImage = (ImageView)row.findViewById(R.id.eventImage);

            row.setTag(holder);
        }

        else
        {
            holder = (EventHolder)row.getTag();
        }

        Event event = data[position];
        holder.eventName.setText(event.name);
        holder.eventStartDate.setText(event.startdate);
        holder.eventShortDesc.setText(event.shortdesc);
        holder.eventEndDate.setText(event.enddate);
        holder.eventStartTime = event.starttime;
        holder.eventEndTime = event.endtime;
        holder.eventLongDesc = event.longdesc;
        holder.eventAddress.setText(event.streetaddress);
        holder.eventSuburb.setText(event.suburb);
        holder.eventPostcode.setText(event.postcode);
        holder.eventPhone = event.phone;
        holder.eventFees = event.fee;
        holder.eventGeo = event.geo;
        holder.eventCategory = event.category;
        holder.eventBookable = event.bookable;
        holder.eventId = event.id;
        Random randomeventimage = new Random();
        int imagenumber;
        imagenumber = randomeventimage.nextInt(5 - 0 + 1) + 1;

        switch(imagenumber) {
            case 0:
                holder.eventImage.setBackgroundResource(R.drawable.event_image_1);
                break;
            case 1:
                holder.eventImage.setBackgroundResource(R.drawable.event_image_2);
                break;
            case 2:
                holder.eventImage.setBackgroundResource(R.drawable.event_image_3);
                break;
            case 3:
                holder.eventImage.setBackgroundResource(R.drawable.event_image_4);
                break;
            case 4:
                holder.eventImage.setBackgroundResource(R.drawable.event_image_5);
                break;
            default:
                holder.eventImage.setBackgroundResource(R.drawable.event_image_1);
                break;
        }



        row.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                EventHolder e = (EventHolder)view.getTag();

                FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("eventname",e.eventName.getText()+"");
                bundle.putString("eventstartdate",e.eventStartDate.getText()+"");
                bundle.putString("eventenddate",e.eventEndDate.getText()+"");
                bundle.putString("eventstarttime",e.eventStartTime);
                bundle.putString("eventendtime",e.eventEndTime);
                bundle.putString("eventlongdesc",e.eventLongDesc);
                bundle.putString("eventaddress",e.eventAddress.getText()+"");
                bundle.putString("eventsuburb",e.eventSuburb.getText()+"");
                bundle.putString("eventpostcode",e.eventPostcode.getText()+"");
                bundle.putString("eventphone",e.eventPhone);
                bundle.putString("eventfees",e.eventFees);
                bundle.putString("eventgeo",e.eventGeo);
                bundle.putString("eventbookable", e.eventBookable);
                bundle.putString("eventid", e.eventId);


                fragment_event_detail pageone = new fragment_event_detail();
                pageone.setArguments(bundle);
                fragmentTransaction.replace(R.id.mainfragment,pageone);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                activity_navigation navigation = new activity_navigation();
                navigation.setSelecteditem(1);
                // navigation.setSelecteditem(1);

                Toast.makeText(context, e.eventName.getText() + " ", Toast.LENGTH_SHORT).show();

                //Intent myIntent = new Intent(context, EventsDetailFragment.class);

                //context.startActivity(myIntent);

            }
        });

        return row;
    }


    // Custom class to store the data to be displayed in the elements from the event_list_item.xml layout file.
    static class EventHolder
    {
        TextView eventName;
        TextView eventShortDesc;
        TextView eventStartDate;
        TextView eventEndDate;
        String eventStartTime;
        String eventEndTime;
        String eventLongDesc;
        TextView eventSuburb;
        TextView eventAddress;
        TextView eventPostcode;
        String eventPhone;
        String eventFees;
        String eventGeo;
        String eventCategory;
        String eventBookable;
        String eventId;
        ImageView eventImage;
    }
}
