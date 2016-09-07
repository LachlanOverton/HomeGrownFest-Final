package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

/**
 * Created by Stuart on 16/10/14.
 */
public class fragment_colourgnome_menu extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(
                R.layout.fragment_comp_colouragnome_menu, container, false);

        activity_navigation nav = new activity_navigation();
        nav.setSelecteditem(2);

        ImageView gnomeman = (ImageView) v.findViewById(R.id.btn_gnome_man);
        gnomeman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), activity_colourgnome_main.class);
                intent.putExtra("gnome", 1);
                startActivity(intent);
            }
        });

        ImageView gnomeboy = (ImageView) v.findViewById(R.id.btn_gnome_boy);
        gnomeboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), activity_colourgnome_main.class);
                intent.putExtra("gnome", 2);
                startActivity(intent);
            }
        });

        ImageView gnomedog = (ImageView) v.findViewById(R.id.btn_gnome_dog);
        gnomedog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), activity_colourgnome_main.class);
                intent.putExtra("gnome", 3);
                startActivity(intent);
            }
        });

        ImageView gnomewoman = (ImageView) v.findViewById(R.id.btn_gnome_lady);
        gnomewoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity().getApplicationContext(), activity_colourgnome_main.class);
                intent.putExtra("gnome", 4);
                startActivity(intent);
            }
        });
//

        return v;

    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }
}
