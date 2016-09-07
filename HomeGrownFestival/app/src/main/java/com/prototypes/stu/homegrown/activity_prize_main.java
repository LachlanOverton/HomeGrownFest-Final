package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class activity_prize_main extends Activity {

    // Value used to determine whether user shook the device
    // Higher value = the more force needed to fire the sensor
    private static final int ACCELERATION_THRESHOLD = 300000;

    private SensorManager sensorManager; // Monitors the accelerometer
    private float acceleration; // Acceleration
    private float currentAcceleration; // Current acceleration
    private float lastAcceleration; // Last acceleration

    private TextView timerValueText; // Timer text view

    private long timeDifferenceValue; // Holds the value of the time difference calculation
    private long hoursLeft;

    private static final int[] CHANCE_ARRAY = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}; // Holds the values for the winning chance (10% chance)
    private static final String URL = "http://users.on.net/~drace/prizes.xml";

    private ConnectivityManager cm;
    private Context context;

    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Initialise the Acceleration values
        acceleration = 0.0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;
        enableAccelerometerListening(); // Listen for shake

        context = this;

        super.onCreate(savedInstanceState);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        setContentView(R.layout.activity_prize_main);
        timerValueText = (TextView) findViewById(R.id.txt_TimeLeft);

        dbHelper = new DBHelper(this);
        CheckStoredTime();

        Button viewPrizeList = (Button) findViewById(R.id.btn_viewPrizes);
        viewPrizeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.countPrizes() > 1) {
                    Intent myIntent = new Intent(activity_prize_main.this, activity_prize_list.class);
                    startActivity(myIntent);
                    disableAccelerometerListening();
                } else {
                    Intent myIntent = new Intent(activity_prize_main.this, activity_prize_emptylist.class);
                    startActivity(myIntent);
                    disableAccelerometerListening();
                }
            }
        });
    }

    public static boolean getMobileState(Context context) {
        ConnectivityManager connect = null;
        connect = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

        if (connect != null) {
            NetworkInfo result = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (result != null && result.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    // When the app is sent to the background, stop listening for sensor events
    protected void onPause() {
        super.onPause();
        disableAccelerometerListening(); // Don't listen for shake
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CheckStoredTime();
    }

    private void enableAccelerometerListening() {
        // Initialise the SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void disableAccelerometerListening() {
        // Stop listening for sensor events
        if (sensorManager != null) {
            sensorManager.unregisterListener(sensorEventListener,
                    sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER));
            sensorManager = null;
        }
    }

    // Event handler for accelerometer events
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        // Use accelerometer to determine whether user shook device
        public void onSensorChanged(SensorEvent event) {
            // Get x, y, and z values for the SensorEvent
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Save previous acceleration value
            lastAcceleration = currentAcceleration;

            // Calculate the current acceleration
            currentAcceleration = x * x + y * y + z * z;

            // Calculate the change in acceleration
            acceleration = currentAcceleration * (currentAcceleration - lastAcceleration);

            // if the acceleration is above a certain threshold
            if (acceleration > ACCELERATION_THRESHOLD) {
                Log.v("timed", readShakeTimeToString());
                if (getMobileState(getApplicationContext()) || cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                    if (TimeDifference(readShakeTimeToString()) >= 1 || readShakeTimeToString() == "") {
                        // What will happen when there is a connection
                        timerValueText = (TextView) findViewById(R.id.txt_TimeLeft);
                        timerValueText.setTextColor(Color.BLACK); // Reset colour back to black
                        timerValueText.setBackgroundColor(Color.TRANSPARENT);

                        Random random = new Random();
                        int randomNumber = random.nextInt(10 - 0 + 1) + 0;

                        if (randomNumber < CHANCE_ARRAY.length) {
                            Log.v("Shake", "" + randomNumber);
                            if (CHANCE_ARRAY[randomNumber] == 1) {
                                Log.v("timed", "execute");
                                new RetrieveFeedTask().execute();
                            } else {
                                timerValueText.setText("Try Again");
                            }
                        }

                        if (dbHelper.getShakeTimeData().getCount() == 0) {
                            Log.v("timed", "insert shake time");
                            dbHelper.insertShakeTime(shakeTimeToString());
                        } else {
                            Log.v("timed", "update shake time");
                            dbHelper.updateShakeTime(shakeTimeToString());
                        }
                        timerValueText.setText("Thanks for shaking. Check back in 24hrs");
                    } else {
                        CheckStoredTime();
                    }
                } else {
                    Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public void CheckStoredTime() {
        String timeShaken = readShakeTimeToString();

        if(timeShaken == "") {
            timerValueText.setText("Shake and Win!");
            enableAccelerometerListening();
        }
        else {
            TimeDifference(timeShaken);

            if(timeDifferenceValue < 1) {
                timerValueText.setText("Try again in "+hoursLeft+" hours!");
                disableAccelerometerListening();
            }
        }
    }

    // Compares the time between the current time and the stored shake time
    public long TimeDifference(String shakeTime) {
        Date storedDateTime = new Date(); // Date object for parsed shakeTime
        Date currentDateTime = new Date(); // Date object for time of shake
        long timeDifference; // Return value

        // Get the current time and format it like the parsed shakeTime (dd/MM/yyyy hh:mm:ss)
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        String currentTime = dateFormat.format(new Date(System.currentTimeMillis()));

        // Formats the strings again to be used with Date
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            storedDateTime = formatter.parse(shakeTime);
            currentDateTime = formatter.parse(currentTime);
        } catch (java.text.ParseException e) {
            Log.e("Shake", "Date Parse Exception: " + e.toString());
        }

        // Maths to work out the time difference
        long difference = currentDateTime.getTime() - storedDateTime.getTime();
        long seconds = difference / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        timeDifference = days;
        timeDifferenceValue = timeDifference;
        hoursLeft = 24 - hours;
        return timeDifference;
    }

    public String shakeTimeToString() {
        String shakeTime;
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        shakeTime = dateFormat.format(new Date(System.currentTimeMillis()));
        return shakeTime;
    }

    public String readShakeTimeToString() {
        String shakeTime = "";
        Cursor c = dbHelper.getShakeTimeData();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            shakeTime = c.getString(c.getColumnIndex("shakeTime"));
        }
        c.close();
        return shakeTime;
    }

    private class RetrieveFeedTask extends AsyncTask<Void, Void, List<Prize>> {
        private Exception exception;

        @Override
        protected List<Prize> doInBackground(Void... voids) {
            List<Prize> prizes = null;
            XMLPullParserHandler parser = new XMLPullParserHandler(getApplicationContext());

            try {
                prizes = parser.parseprizes(URL); // Parses the xml from the provided URL and returns the data as List<Prize>
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return prizes;
        }

        @Override
        protected void onPostExecute(List<Prize> prizes) {
            try {
                // What will happen after the .xml file has been read from the website
                Prize[] array = prizes.toArray(new Prize[0]);
                int numberOfPrizes = array.length;
                int randomNumber;

                // Generate a Random Number for a prize
                Random random = new Random();
                randomNumber = random.nextInt(numberOfPrizes);

                // Check to see if the date is within the Start and End date
                Date currentDate = new Date(System.currentTimeMillis());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date startDate = new Date();
                Date endDate = new Date();
                TimeZone timeZone = TimeZone.getDefault();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setTimeZone(timeZone);

                try {
                    startDate = formatter.parse(array[randomNumber].getStartDate());
                    endDate = formatter.parse(array[randomNumber].getEndDate());
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                if (currentDate.getTime() > startDate.getTime() && currentDate.getTime() < endDate.getTime()) {
                    // Check to see if the prize has been won before
                    if (dbHelper.checkPrizeData(array[randomNumber].getId()) == false) {
                        // Insert a random prize
                        Log.v("Shake", "Insert Prize");
                        dbHelper.insertPrize(
                                array[randomNumber].getId(),
                                array[randomNumber].getName(),
                                array[randomNumber].getShortDescription(),
                                array[randomNumber].getLongDescription(),
                                array[randomNumber].getVendor(),
                                array[randomNumber].getStartDate(),
                                array[randomNumber].getEndDate(),
                                array[randomNumber].getDuration(),
                                readShakeTimeToString()
                        );
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        System.gc();
    }
}