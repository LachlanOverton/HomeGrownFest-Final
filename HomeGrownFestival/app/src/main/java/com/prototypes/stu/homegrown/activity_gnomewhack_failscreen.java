package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class activity_gnomewhack_failscreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_screen);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int score = bundle.getInt("Score");
        DBHelper dbHelper = new DBHelper(this);

        dbHelper.insertNewHighScore(score);

        TextView textView = (TextView)findViewById(R.id.failscreen_score_text);
        textView.setText("" + score);
        // High Scores
        TextView highScore1 = (TextView)findViewById(R.id.games_gnomewhack_highscore1);
        TextView highScore2 = (TextView)findViewById(R.id.games_gnomewhack_highscore2);
        TextView highScore3 = (TextView)findViewById(R.id.games_gnomewhack_highscore3);
        TextView highScore4 = (TextView)findViewById(R.id.games_gnomewhack_highscore4);
        TextView highScore5 = (TextView)findViewById(R.id.games_gnomewhack_highscore5);

        Button restartbutton = (Button)findViewById(R.id.btn_games_gnomewhack_fail_restart);

        restartbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_gnomewhack_splash.class);
                startActivity(intent);
            }
        });
        Button exitbutton = (Button)findViewById(R.id.btn_games_gnomewhack_fail_exit);
        exitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_navigation.class);
                startActivity(intent);
            }
        });

        ArrayList<HighScore> highScoreList = new ArrayList<HighScore>();
        List<HighScore> tempList = null;
        tempList = dbHelper.getTop5HighScores();
        highScoreList.addAll(tempList);
        highScore1.setText("");
        highScore2.setText("");
        highScore3.setText("");
        highScore4.setText("");
        highScore5.setText("");

        if(highScoreList.get(0) != null) {
            highScore1.setText("#1 "+highScoreList.get(0).getScore());
        } else {
            highScore1.setText("");
        }

        if(highScoreList.size() > 1) {

            if(highScoreList.get(1) != null) {
                highScore2.setText("#2 "+highScoreList.get(1).getScore());
            } else {
                highScore2.setText("");
            }

            if(highScoreList.size() > 2) {
                if(highScoreList.get(2) != null) {
                    highScore3.setText("#3 "+highScoreList.get(2).getScore());
                } else {
                    highScore3.setText("");
                }

                if(highScoreList.size() > 3) {
                    if(highScoreList.get(3) != null) {
                        highScore4.setText("#4 "+highScoreList.get(3).getScore());
                    } else {
                        highScore4.setText("");
                    }

                    if(highScoreList.size() > 4) {
                        if(highScoreList.get(4) != null) {
                            highScore5.setText("#5 "+highScoreList.get(4).getScore());
                        } else {
                            highScore5.setText("");
                        }
                    }
                }
            }
        }
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

        unbindDrawables(findViewById(R.id.layout_gnomewhack_failscreen));
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
}