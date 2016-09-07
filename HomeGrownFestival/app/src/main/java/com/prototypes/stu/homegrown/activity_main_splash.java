package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


public class activity_main_splash extends Activity {

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        countDownTimer = new CountDownTimer(2000, 1000) {
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
        Toast.makeText(getBaseContext(), "Menu", Toast.LENGTH_SHORT).show();
        Intent createintent = new Intent(this, activity_navigation.class);
        startActivity(createintent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
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

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.layout_splash));
        System.gc();
    }

    private void unbindDrawables(View view)
    {
        if(view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if(view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for(int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup)view).getChildAt(i));
            }
            ((ViewGroup)view).removeAllViews();
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        countDownTimer.cancel();

        finish();
    }
}
