package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class activity_terms_conditions extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(
                R.layout.activity_terms_conditions, container, false);

        Button acceptbutton = (Button) v.findViewById(R.id.btn_tc_accept);

        Button declinebutton = (Button) v.findViewById(R.id.btn_tc_decline);

        acceptbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Accept Button Code

            }
        });

        declinebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO: Decline Button Code

            }
        });

        return v;
    }

}
