package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Created by seklar on 15/10/14.
 */
public class fragment_event_calendar extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(
                R.layout.fragment_events_calendar, container, false);

        activity_navigation nav = new activity_navigation();
        nav.setSelecteditem(2);
        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        System.gc();
    }
}
