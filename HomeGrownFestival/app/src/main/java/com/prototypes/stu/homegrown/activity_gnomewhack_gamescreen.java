package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class activity_gnomewhack_gamescreen extends Activity {

    private int[] locationsActive = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //0 = Location is an empty hole. 1 = Location has a gnome active.
    private int[] charactersActive = {0, 0, 0, 0, 0}; // The currently active characters. This is to stop the same character from appearing on screen at once. NOT USED.
    public CountDownTimer gametimer;
    RelativeLayout relativeLayout;
    private ImageView[] locations = {null, null, null, null, null, null, null, null, null, null, null, null}; // Container for the imageview locations.

    // GAME SETTINGS
    public double difficultyModifier = 1.025; // This value is used to modify the game difficulty by being multiplied against the SPAWNTIME and GNOMEACTIVETIME to make them disappear faster.
    public int level; // Stores the players current level.
    public int gameMode = 1; // 1 = Normal Mode, 2 = Bonus Mode
    public int bonusModeMultiplier = 2; // Multiplier for playerscore in bonus mode. Default 2.
    public int maxlevel = 75; // The maximum level the player is able to reach.
    private static final int GAMETIME = 15000; //Game time of 30 seconds
    private static final int SPAWNTIME = 1250; //Default spawn rate of gnomes
    private int adjustedSpawntime; // Difficulty adjusted SPAWNTIME (SPAWNTIME/level/difficultyModfier).
    private int adjustedActivetime; // Difficulty adjusted GNOMEACTIVETIME (GNOMEACTIVETIME x level x difficultyModfier).
    private static final int GNOMEACTIVETIME = 3000; //Default length of time gnome is on screen before disappearing.

    // SCORES
    private int missedGnomePenalty = 100; // Points lost when a gnome times out instead of being tapped.
    private int animalTappedScore = 25; // Points when a dog or cat are tapped.
    private int gnomeTappedScore = 50; // Points when a gnome is tapped.

    public int LIVES = 0; //Default Player lives
    public int score = 0; //Player current score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        View background = findViewById(R.id.gamewhack_gamescreen_background);


        // Get the Player's current level, lives and score from the previous level.
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        level = bundle.getInt("Level");
        LIVES = bundle.getInt("Lives");
        score = bundle.getInt("Score");

        scoreUpdate();

        Random randombg = new Random();
        int bgnumber;
        bgnumber = randombg.nextInt(6 - 0 + 1) + 1;

        switch(bgnumber)
        {
            case 0 :
                background.setBackgroundResource(R.drawable.gnomewhack_bg1);
                break;
            case 1 :
                background.setBackgroundResource(R.drawable.gnomewhack_bg1);
                break;
            case 2 :
                background.setBackgroundResource(R.drawable.gnomewhack_bg1);
                break;
            case 3 :
                background.setBackgroundResource(R.drawable.gnomewhack_bg1);
                break;
            case 4 :
                background.setBackgroundResource(R.drawable.gnomewhack_bg1);
                break;
            case 5 :
                if(level == 1) {
                    background.setBackgroundResource(R.drawable.gnomewhack_bg1);
                }
                else
                {
                    background.setBackgroundResource(R.drawable.gnomewhack_bg2);
                    gameMode = 2;
                }
                break;
            default :
                background.setBackgroundResource(R.drawable.gnomewhack_bg1);
                break;
        }

        // Makes sure that the player doesn't go higher than the max level.
        if(level >= maxlevel)
        {
            level = maxlevel;
        }

        // Sets the difficulty.
        setDifficulty();

        // Set up the display text.
        TextView textView = (TextView)findViewById(R.id.leveltext);
        if(gameMode == 1)
        {
            textView.setText("" + level);
        }
        else
        {
            textView.setText("" + level + "(Bonus Level)");
        }


        livesUpdate();

        Log.d("***", "Start Timer" + level);

        // Set up the game timer
        gametimer = new CountDownTimer(GAMETIME, adjustedSpawntime) {

            @Override
            public void onTick(long millisUntilFinished) {
                final int spot = randomSpot();
                //Loop through spots until it finds an inactive one.

                Log.d("***","Random Spot Generated = " + spot + " - LEVEL " + level);

                int character = getCharacter(locations[spot]);

                Log.d("***","Using Character #" + character);

                if(character == 0) {
                    locations[spot].setTag(0);
                }
                else if (character == 1) {
                    locations[spot].setTag(1);
                }
                else if (character == 2) {
                    locations[spot].setTag(2);
                }
                else if (character == 3) {
                    locations[spot].setTag(3);
                }
                else if (character == 4) {
                    locations[spot].setTag(4);
                }
                else if (character == 5) {
                    locations[spot].setTag(5);
                }
                else if (character == 6) {
                    locations[spot].setTag(6);
                }

                CountDownTimer gnomelifetime = new CountDownTimer(adjustedActivetime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        if(locationsActive[spot] != 0) {
                            Object typetag = locations[spot].getTag();
                            int type = Integer.parseInt(typetag.toString());
                            gnomeDisappear(type, locations[spot]);
                            locationsActive[spot] = 0;
                            Log.d("***", "Gnome in Location #" + spot + " - TIMED OUT");
                        }
                        else
                        {
                            Log.d("***", "Gnome in Location #" + spot + " - ALREADY TAPPED");
                        }
                    }
                }.start();
            }

            @Override
            public void onFinish() {
                // Advance to next level if lives left.
                gametimer.cancel();
                Intent intent = new Intent(getBaseContext(), activity_gnomewhack_intermission.class);
                intent.putExtra("Level", level);
                intent.putExtra("Lives", LIVES);
                intent.putExtra("Score", score);
                startActivity(intent);
                finish();
            }
        }.start();

        locations[0] = (ImageView) findViewById(R.id.row1location1);
        locations[1] = (ImageView) findViewById(R.id.row1location2);
        locations[2] = (ImageView) findViewById(R.id.row1location3);
        locations[3] = (ImageView) findViewById(R.id.row1location4);
        locations[4] = (ImageView) findViewById(R.id.row2location1);
        locations[5] = (ImageView) findViewById(R.id.row2location2);
        locations[6] = (ImageView) findViewById(R.id.row2location3);
        locations[7] = (ImageView) findViewById(R.id.row2location4);
        locations[8] = (ImageView) findViewById(R.id.row3location1);
        locations[9] = (ImageView) findViewById(R.id.row3location2);
        locations[10] = (ImageView) findViewById(R.id.row3location3);
        locations[11] = (ImageView) findViewById(R.id.row3location4);


        // Cycle through each imageview and set up their onclicklisteners
        for(int x = 0; x < 12; x++)
        {
            final int a = x;
            locations[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(locationsActive[a] == 0)
                    {
//                        Toast.makeText(getApplicationContext(),"Location #" + a + " is active.", Toast.LENGTH_SHORT).show();
//                        locationsActive[a] = 1;
                    }
                    else if(locationsActive[a] == 1)
                    {
                        //Toast.makeText(getApplicationContext(),"Location #" + a + " is not active.", Toast.LENGTH_SHORT).show();
                        Object typetag = locations[a].getTag();
                        int type = Integer.parseInt(typetag.toString());
                        gnomeTapped(type, locations[a], a);
                        Log.d("***", "Gnome in Location #" + a + " - WAS TAPPED. Type " + type);
                        //locationsActive[a] = 0;
                    }
                }
            });
        }

    }

    private void setDifficulty()
    {
        adjustedActivetime = GNOMEACTIVETIME;
        adjustedSpawntime = SPAWNTIME;
        for(int x = level-1; x > 0; x--)
        {
            double adjust1 = adjustedActivetime / difficultyModifier;
            double adjust2 = adjustedSpawntime / difficultyModifier;
            adjustedActivetime = (int)adjust1;
            adjustedSpawntime = (int)adjust2;
        }
    }

    // Update the score text.
    private void scoreUpdate()
    {
        TextView textView = (TextView)findViewById(R.id.scoretext);
        textView.setText("" + score);
    }

    // Update the lives text
    private void livesUpdate()
    {
        TextView livesText = (TextView)findViewById(R.id.livestext);
        livesText.setText("" + LIVES);
    }

    // Checks if the player has lives remaining
    private void livesCheck()
    {
        if(LIVES == 1)
        {
            LIVES = 0;
            Fail();
        }
        else
        {
            LIVES = LIVES - 1;
        }
        livesUpdate();
    }

    // Checks that the player's score is greater than 0
    private void scoreCheck(int type)
    {
        if(gameMode == 1) {
            switch (type) {
                case 1:
                    livesCheck();
                    break;
                case 2:
                    score = score + 50;
                    break;
                default:
                    score = score + 100;
                    break;
            }
        }
        else if(gameMode == 2)
        {
            switch (type) {
                case 1:
                    livesCheck();
                    break;
                case 2:
                    score = score + (50 * bonusModeMultiplier);
                    break;
                default:
                    score = score + (100 * bonusModeMultiplier);
                    break;
            }
        }
        scoreUpdate();
    }

    // Ends the game taking the player to the fail screen
    private void Fail()
    {
        gametimer.cancel();
        Log.d("***", "TIMER CANCELLED!");
        Intent intent = new Intent(this, activity_gnomewhack_failscreen.class);
        intent.putExtra("Score", score);
        intent.putExtra("Level", level);
        startActivity(intent);
        finish();
    }


    // Handles the gnomes being tapped
    private void gnomeTapped(int type, ImageView tappedLocationImageView, int locationIn)
    {
        final int newtype = type;
        final int location;
        location = locationIn;
        AnimationDrawable hitgnome;
        switch (type)
        {
            case 0 :
                tappedLocationImageView.setBackgroundResource(R.drawable.dog1hit);
                hitgnome = (AnimationDrawable)tappedLocationImageView.getBackground();
                hitgnome.start();
                locationsActive[location] = 0;
                CountDownTimer animationtime = new CountDownTimer(500,500)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {

                        scoreCheck(newtype);
                    }
                }.start();

                break;
            case 1 :
                tappedLocationImageView.setBackgroundResource(R.drawable.cat1hit);
                hitgnome = (AnimationDrawable)tappedLocationImageView.getBackground();
                hitgnome.start();
                locationsActive[location] = 0;
                CountDownTimer animationtime1 = new CountDownTimer(500,500)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        locationsActive[location] = 0;
                        scoreCheck(newtype);
                    }
                }.start();
                break;
            case 2 :
                tappedLocationImageView.setBackgroundResource(R.drawable.gnome1hit);
                hitgnome = (AnimationDrawable)tappedLocationImageView.getBackground();
                hitgnome.start();
                locationsActive[location] = 0;
                CountDownTimer animationtime2 = new CountDownTimer(500,500)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        locationsActive[location] = 0;
                        scoreCheck(newtype);
                    }
                }.start();
                break;
            case 3 :
                tappedLocationImageView.setBackgroundResource(R.drawable.gnome2hit);
                hitgnome = (AnimationDrawable)tappedLocationImageView.getBackground();
                hitgnome.start();
                locationsActive[location] = 0;
                CountDownTimer animationtime3 = new CountDownTimer(500,500)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        locationsActive[location] = 0;
                        scoreCheck(newtype);
                    }
                }.start();
                scoreCheck(type);
                break;
            case 4 :
                tappedLocationImageView.setBackgroundResource(R.drawable.gnome3hit);
                hitgnome = (AnimationDrawable)tappedLocationImageView.getBackground();
                hitgnome.start();
                locationsActive[location] = 0;
                CountDownTimer animationtime4 = new CountDownTimer(500,500)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        locationsActive[location] = 0;
                        scoreCheck(newtype);
                    }
                }.start();
                break;
            case 5 :
                tappedLocationImageView.setBackgroundResource(R.drawable.gnome4hit);
                hitgnome = (AnimationDrawable)tappedLocationImageView.getBackground();
                hitgnome.start();
                locationsActive[location] = 0;
                CountDownTimer animationtime5 = new CountDownTimer(500,500)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        locationsActive[location] = 0;
                        scoreCheck(newtype);
                    }
                }.start();
                break;
            case 6 :
                tappedLocationImageView.setBackgroundResource(R.drawable.gnome5hit);
                hitgnome = (AnimationDrawable)tappedLocationImageView.getBackground();
                hitgnome.start();
                locationsActive[location] = 0;
                CountDownTimer animationtime6 = new CountDownTimer(500,500)
                {

                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        locationsActive[location] = 0;
                        scoreCheck(newtype);
                    }
                }.start();
                break;
            default :
                tappedLocationImageView.setBackgroundResource(R.drawable.empty_hole);
                break;
        }
//        scoreCheck(type);
    }


    // Handles the gnomes disappearing
    private void gnomeDisappear(int type, ImageView imageView)
    {
        AnimationDrawable hidegnome;
        switch (type)
        {
            case 0 :
                imageView.setBackgroundResource(R.drawable.dog1hide);
                hidegnome = (AnimationDrawable)imageView.getBackground();
                hidegnome.start();
                score = score - missedGnomePenalty;
                scoreUpdate();
                break;
            case 1 :
                imageView.setBackgroundResource(R.drawable.cat1hide);
                hidegnome = (AnimationDrawable)imageView.getBackground();
                hidegnome.start();
                score = score - missedGnomePenalty;
                scoreUpdate();
                break;
            case 2 :
                imageView.setBackgroundResource(R.drawable.gnome1hide);
                hidegnome = (AnimationDrawable)imageView.getBackground();
                hidegnome.start();
                score = score - missedGnomePenalty;
                scoreUpdate();
                break;
            case 3 :
                imageView.setBackgroundResource(R.drawable.gnome2hide);
                hidegnome = (AnimationDrawable)imageView.getBackground();
                hidegnome.start();
                score = score - missedGnomePenalty;
                scoreUpdate();
                break;
            case 4 :
                imageView.setBackgroundResource(R.drawable.gnome3hide);
                hidegnome = (AnimationDrawable)imageView.getBackground();
                hidegnome.start();
                score = score - missedGnomePenalty;
                scoreUpdate();
                break;
            case 5 :
                imageView.setBackgroundResource(R.drawable.gnome4hide);
                hidegnome = (AnimationDrawable)imageView.getBackground();
                hidegnome.start();
                score = score - missedGnomePenalty;
                scoreUpdate();
                break;
            case 6 :
                imageView.setBackgroundResource(R.drawable.gnome5hide);
                hidegnome = (AnimationDrawable)imageView.getBackground();
                hidegnome.start();
                score = score - missedGnomePenalty;
                scoreUpdate();
                break;
            default :
                imageView.setBackgroundResource(R.drawable.empty_hole);
                break;
        }
    }

    // Display a random gnome character
    private int getCharacter(ImageView imageView) {
        Random rand = new Random();
        int n;
        n = rand.nextInt(6 - 0 + 1) + 0;
        AnimationDrawable showgnome;
        switch (n)
        {
            case 0 :
                imageView.setBackgroundResource(R.drawable.dog1show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
            case 1 :
                imageView.setBackgroundResource(R.drawable.cat1show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
            case 2 :
                imageView.setBackgroundResource(R.drawable.gnome1show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
            case 3 :
                imageView.setBackgroundResource(R.drawable.gnome2show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
            case 4 :
                imageView.setBackgroundResource(R.drawable.gnome3show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
            case 5 :
                imageView.setBackgroundResource(R.drawable.gnome4show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
            case 6 :
                imageView.setBackgroundResource(R.drawable.gnome5show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
            default :
                imageView.setBackgroundResource(R.drawable.gnome1show);
                showgnome = (AnimationDrawable)imageView.getBackground();
                showgnome.start();
                break;
        }
        return n;
    }

    private int randomSpot()
    {
        Random rand = new Random();
        int n;

        do {
             n = rand.nextInt(11 - 0 + 1) + 0;
        }
        while(locationsActive[n] == 1);

        locationsActive[n] = 1;
        return n;
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
    public void onBackPressed()
    {
        super.onBackPressed();

        gametimer.cancel();
        finish();
    }

}
