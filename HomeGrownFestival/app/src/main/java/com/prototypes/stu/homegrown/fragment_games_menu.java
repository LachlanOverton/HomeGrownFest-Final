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
 * Created by Stuart on 28/09/2014.
 */
public class fragment_games_menu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(
                R.layout.activity_games_competitions, container, false);

        Button whackgnome = (Button)v.findViewById(R.id.btn_games_gnomewhack);
        whackgnome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity().getApplicationContext(), activity_gnomewhack_splash.class);
                startActivity(intent);

            }
        });

        Button seeddrop = (Button)v.findViewById(R.id.btn_games_seeddrop);
        seeddrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),activity_seeddrop_menu.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }

}
