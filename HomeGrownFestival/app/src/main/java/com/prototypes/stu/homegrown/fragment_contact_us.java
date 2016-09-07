package com.prototypes.stu.homegrown;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Stuart on 5/10/2014.
 */
public class fragment_contact_us extends Fragment implements View.OnClickListener {

    EditText nametext;
    EditText emailtext;
    EditText messagetext;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */
        View v = inflater.inflate(
                R.layout.activity_contact_us, container, false);

        Button sendmail = (Button)v.findViewById(R.id.sendemailbutton);

        nametext = (EditText)v.findViewById(R.id.contact_name);
        emailtext = (EditText)v.findViewById(R.id.contact_email);
        messagetext = (EditText)v.findViewById(R.id.contact_message);


        sendmail.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()) {
            case R.id.sendemailbutton:

                final Intent emailIntent = new Intent(Intent.ACTION_SEND);

                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {emailtext.getText().toString()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "City of Gosnells - Contact Us");
                emailIntent.putExtra(Intent.EXTRA_TEXT, messagetext.getText().toString() + ". My return email address is:" + emailtext.getText().toString() + "Sent by " + nametext.getText().toString() + ".");

                startActivity(Intent.createChooser(emailIntent, "Send Mail"));

                break;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }
}
