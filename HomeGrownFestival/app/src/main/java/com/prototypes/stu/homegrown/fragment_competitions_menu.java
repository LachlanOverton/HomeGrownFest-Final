package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by seklar on 28/10/14.
 */
public class fragment_competitions_menu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(
                R.layout.activity_games_menu, container, false);


        Button colourgnome = (Button) v.findViewById(R.id.btn_comp_colourgnome);

        Button shakewin = (Button) v.findViewById(R.id.btn_comp_shakewin);

        colourgnome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragment_colourgnome_menu compmenu = new fragment_colourgnome_menu();
                fragmentTransaction.replace(R.id.mainfragment, compmenu);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        shakewin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createintent = new Intent(getActivity(), activity_prize_main.class);
                startActivity(createintent);
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
