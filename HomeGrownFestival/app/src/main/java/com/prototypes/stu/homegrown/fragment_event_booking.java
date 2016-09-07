package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by seklar on 22/10/14.
 */
public class fragment_event_booking extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(
                R.layout.activity_events_booking, container, false);

        Button sendmail = (Button) v.findViewById(R.id.btn_events_booking_submit);

        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"test@email.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Homegrown Festival - Event Booking Enquiry");
                emailIntent.putExtra(Intent.EXTRA_TEXT,"I am interested in finding out additional information about the event." );

                startActivity(Intent.createChooser(emailIntent, "Send Mail"));
            }
        });

        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        unbindDrawables(getActivity().findViewById(R.id.layout_event_booking));
        System.gc();
    }

    private void unbindDrawables(View view)
    {
        if(view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if(view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup)view).getChildAt(i));
            }
            ((ViewGroup)view).removeAllViews();
        }
    }

}
