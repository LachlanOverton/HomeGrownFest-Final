package com.prototypes.stu.homegrown;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Stuart on 28/09/2014.
 */
public class fragment_menu_blank extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        View v = inflater.inflate(
                R.layout.activity_menu, container, false);

        getActivity().getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActivity().getActionBar().setCustomView(R.layout.ab_layout);

        return v;
    }

}
