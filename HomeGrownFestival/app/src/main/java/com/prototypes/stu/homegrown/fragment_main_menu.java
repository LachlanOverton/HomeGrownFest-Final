package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterViewAnimator;

/**
 * Created by Stuart on 28/09/2014.
 */
public class fragment_main_menu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        return inflater.inflate(
                R.layout.activity_main_menu, container, false);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }
}
