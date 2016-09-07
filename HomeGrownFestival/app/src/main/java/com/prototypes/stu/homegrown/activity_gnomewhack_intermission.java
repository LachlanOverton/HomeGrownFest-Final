package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.widget.Toast;


public class activity_gnomewhack_intermission extends Activity {

    int level = 0;
    int lives = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermission);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        level = bundle.getInt("Level");
        lives = bundle.getInt("Lives");
        score = bundle.getInt("Score");

        CountDownTimer countDownTimer = new CountDownTimer(10, 10) {
            public void onFinish() {
                finishedCountdown();
            }

            @Override
            public void onTick(long arg0) {

            }
        }.start();
    }

    public void finishedCountdown()
    {
        Toast.makeText(getBaseContext(), "Next Level", Toast.LENGTH_SHORT).show();
        Intent createintent = new Intent(this, activity_gnomewhack_gamescreen.class);
        createintent.putExtra("Level", level + 1);
        createintent.putExtra("Lives", lives);
        createintent.putExtra("Score", score);
        startActivity(createintent);
        finish();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
