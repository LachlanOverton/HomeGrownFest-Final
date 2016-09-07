package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;


public class activity_prize_list extends Activity {

    private ListView listView;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DBHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_list);

        listView = (ListView) findViewById(R.id.list);

        List<Prize> prizes = dbHelper.getAllPrizes();
        // Loop through the List and check if the Prize is still valid
        for (Iterator<Prize> iter = prizes.listIterator(); iter.hasNext(); ) {
            Prize p = iter.next();
            Cursor cursor = dbHelper.getPrizeData(p.getId()); // Results from the Query
            String wondate = "";
            long difference;

            if (cursor.moveToFirst()) {
                wondate = cursor.getString(cursor.getColumnIndex("wondate")); // Converting results into a String
            }

            if (wondate != null) {
                // Do time difference
                difference = TimeDifference(wondate);

                if (difference > p.getDuration()) {
                    Toast.makeText(this, "The Prize: " + p.getName() + " has been removed! Make sure to use them up while they last!", Toast.LENGTH_LONG).show();
                    dbHelper.deletePrize(p.getId());
                    iter.remove();
                }
            }
        }

        Prize[] data = prizes.toArray(new Prize[0]); // Filtered array of prizes
        PrizeAdapter adapter = new PrizeAdapter(this, R.layout.activity_prize_prize_list, data);
        listView.setAdapter(adapter);
    }

    // Used for working out the duration of the prize
    public long TimeDifference(String shakeTime) {
        Date currentDateTime = new Date(); // Date object for time of shake
        Date prizeWonDateTime = new Date(); // Date object for checking if the prize is still valid
        long timeDifference; // Return value

        // Get the current time and format it like the parsed shakeTime (dd/MM/yyyy hh:mm:ss)
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        String currentTime = dateFormat.format(new Date(System.currentTimeMillis()));

        // Formats the strings again to be used with Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            prizeWonDateTime = formatter.parse(shakeTime);
            currentDateTime = formatter.parse(currentTime);
        } catch (java.text.ParseException e) {
            Log.e("Shake", "Date Parse Exception: " + e.toString());
        }

        // Maths to work out the time difference
        long difference = currentDateTime.getTime() - prizeWonDateTime.getTime();
        long seconds = difference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        timeDifference = days;
        return timeDifference;
    }
}
