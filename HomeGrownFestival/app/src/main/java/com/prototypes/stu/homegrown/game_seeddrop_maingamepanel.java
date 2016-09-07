
package com.prototypes.stu.homegrown;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.prototypes.stu.homegrown.model.Bucket;
import com.prototypes.stu.homegrown.model.Coordinate;
import com.prototypes.stu.homegrown.model.Droid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;


public class game_seeddrop_maingamepanel extends SurfaceView implements SurfaceHolder.Callback {

	public static final String TAG = game_seeddrop_maingamepanel.class.getSimpleName();
	public MainThread thread;
    private Bitmap bitmap;
    private Handler hand;               //creating the handler for the repeating the addition of droidz
    public static Handler UIHandler;    //Handler for adding to the UI from a thread
    private static int interval = 2300; //How long between droid spawn
    private static double DOWNSPEED = 1.5;
    private int x;			            // the X coordinate
    private int y;			            // the Y coordinate
    private boolean touched = false;	// if droid is touched/picked up
    private int type;                   //type of droid -- to be able to seperate the different types of seeds
    private int matches1 = 0;           // add to individual bucket score
    private int matches2 = 0;
    private int matches3 = 0;
    private int score = 0;              //score to be displayed
    private long timeleft = 25000;      //How much time is left in the countdown -- In Millis
    private long timeDisplay;           //time to be displayed -- timeleft / 1000
    private String text;                //Text to be displayed in end of game display
    private long otherTime;             //
    private Canvas canvas;              //The Canvas which everything is drawn on
    private boolean isshown;            //if the restart dialog is displayed
    private int chooseRandoBack = 1;
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


    //getting the display height and width
    DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
    int widtht = metrics.widthPixels;
    int heigh2 = metrics.heightPixels;


    Bitmap buck1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_seeddrop_hole);   //define bucket bitmap
    Bitmap buck3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_seeddrop_hole); //spaceship
    Bitmap buck4 = BitmapFactory.decodeResource(getResources(), R.drawable.img_seeddrop_hole);//car
    Rect buck1Bounds = new Rect(0,0, buck1.getWidth(), buck1.getHeight());              //define rectangles around the bucket    Rect buck2Bounds = new Rect(0,0, buck2.getWidth(), buck2.getHeight());
    Rect buck2Bounds = new Rect(0,0, buck3.getWidth(), buck3.getHeight());
    Rect buck3Bounds = new Rect(0,0, buck4.getWidth(), buck4.getHeight());

    Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.seeddropbg1); //declare background bitmap
//    Bitmap back2 = BitmapFactory.decodeResource(getResources(), R.drawable.seeddropbg5); //declare background bitmap
//    Bitmap back3 = BitmapFactory.decodeResource(getResources(), R.drawable.seeddropbg9); //declare background bitmap
//    Bitmap back4 = BitmapFactory.decodeResource(getResources(), R.drawable.img_seeddrop_background1); //declare background bitmap

    Bitmap resizedBack = Bitmap.createScaledBitmap(back, widtht,heigh2 , false);    //declare resizable background bitmap
//    Bitmap resizedBack2 = Bitmap.createScaledBitmap(back2, widtht,heigh2 , false);  //declare resizable background bitmap
//    Bitmap resizedBack3 = Bitmap.createScaledBitmap(back3, widtht,heigh2 , false);  //declare resizable background bitmap
//    Bitmap resizedBack4 = Bitmap.createScaledBitmap(back4, widtht,heigh2 , false);  //declare resizable background bitmap


    Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.img_seeddrop_eggplantpack);// define droid bitmap
    Bitmap largeIcon2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_seeddrop_mushroompack);
    Bitmap largeIcon3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_seeddrop_mushroompack);
    Bitmap resized  = Bitmap.createScaledBitmap(largeIcon, 50, 50, false);  //declare droidz bitmaps as resizable
    Bitmap resized2 = Bitmap.createScaledBitmap(largeIcon2, 50, 50, false);
    Bitmap resized3 = Bitmap.createScaledBitmap(largeIcon3, 50, 50, false);

    Rect droid1Bounds = new Rect(0,0, largeIcon.getWidth()/2, largeIcon.getHeight()/2);     //define rectangle around droid


    List<Coordinate> spawnpoints = new ArrayList<Coordinate>(); //creating the arraylists
    List<Droid>  droidList= new ArrayList<Droid>();
    List<Bucket> buckList = new ArrayList<Bucket>();
    List<Bitmap> backList = new ArrayList<Bitmap>();

    CountDownTimer timer = new CountDownTimer(timeleft, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            otherTime = millisUntilFinished;
        }
        @Override
        public void onFinish() {
            showDialog();
            thread.interrupt();
        }
    }; //creating the timer


    public game_seeddrop_maingamepanel(Context context)  {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		thread = new MainThread(getHolder(), this); // create the game loop thread
        setFocusable(true); // make the GamePanel focusable so it can handle events
        final android.os.Handler hadn = new android.os.Handler();

        defineBucket();

        int topdrop    = heigh2 - 500 ;     //spawn height
        int sidedrop1  = widtht - 100 ;     //many spawn widths
        int sidedrop2  = widtht - 150 ;
        int sidedrop3  = widtht - 200 ;
        int sidedrop4  = widtht - 75  ;
        int sidedrop5  = widtht - 175 ;
        int sidedrop6  = widtht - 225 ;
        int sidedrop7  = widtht - 275 ;
        int sidedrop8  = widtht - 300 ;
        Coordinate co1 = new Coordinate(sidedrop1, topdrop); //Setting the spawnpoints as Coordinates
        Coordinate co2 = new Coordinate(sidedrop2, topdrop);
        Coordinate co3 = new Coordinate(sidedrop3, topdrop);
        Coordinate co4 = new Coordinate(sidedrop4, topdrop);
        Coordinate co5 = new Coordinate(sidedrop5, topdrop);
        Coordinate co6 = new Coordinate(sidedrop6, topdrop);
        Coordinate co7 = new Coordinate(sidedrop7, topdrop);
        Coordinate co8 = new Coordinate(sidedrop8, topdrop);

        spawnpoints.add(co1);    //adding Spawnpoint coordinates to the arraylist
        spawnpoints.add(co2);
        spawnpoints.add(co3);
        spawnpoints.add(co4);
        spawnpoints.add(co5);
        spawnpoints.add(co6);
        spawnpoints.add(co7);
        spawnpoints.add(co8);

        backList.add(resizedBack );
//        backList.add(resizedBack2);
//        backList.add(resizedBack3);
//        backList.add(resizedBack4);


        Runnable runrun = new Runnable(){
            public void run(){
                if(droidList.size() <= 10){
                defineDroid();      //repeating the adding of the droidz
                hadn.postDelayed(this, interval);
                }
            }
        };
            new Thread(runrun).start();
        timer.start();
    }

    public void showDialog(){
        isshown = true;             //set boolean to say the dialog is open
        text = "Score: " + score;
        builder.setTitle("Game Over");
        builder.setMessage(text);
        builder.setCancelable(false);   //This stops people from closing the dialog but tapping outside of it
        builder.setPositiveButton("Restart",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        restart();
                        isshown = false;
                    }
                }
        );

        builder.setNegativeButton("Main Menu",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Kills this page returning it to the menu screen
                        android.os.Process.killProcess(android.os.Process.myPid());
                        isshown = false;
                    }	//Kill application
                }
        );
        builder.show();
    }

    public void restart(){
             addTime();     //restart timer
             score = 0;     // set score to 0
             isshown = false;   //make sure dialog is no longer open
             interval = 2300;   //set the interval that droidz spawn back to default

        if (chooseRandoBack < 4){
            chooseRandoBack = chooseRandoBack + 1;
        }else{
            chooseRandoBack = 1;
        }
        }

    public static void runOnUi(Runnable runnable){
        final android.os.Handler UIHandler = new android.os.Handler(Looper.getMainLooper());
        UIHandler.post(runnable);
    }

    public void defineDroid(){
        Random rand = new Random();
        int min = 0;
        int max = 3;
        int i2 = rand.nextInt((max - min + 1) - min);   //getting random number for droidz

        switch (i2) {
            case 0:                 //setting random bitmap and type for droid created
                bitmap = resized;   //eggplant
                type = 1;
                break;
            case 1:
                bitmap = resized2; //mushroom
                type = 2;
                break;
            case 2:
                bitmap = resized3;
                type = 3;
                break;
        }
        for(int i = 0 ; i < spawnpoints.size(); i++) {
            Collections.shuffle(spawnpoints);       //shuffle spawnpoint arraylist
            x = spawnpoints.get(i).getX();          //get random X coord
            y = spawnpoints.get(i).getY();          //get random Y coord
        }


        Droid droid = new Droid(bitmap, x, y, type);  //get random details
        droidList.add(droid);     //add random droid to arraylist
    }

    public void defineBucket(){
        int x1 = widtht - 155; //width of screen take 155 px
        int x2 = widtht - 100;
        int x3 = widtht - 255;
        int x4 = widtht - 50 ;
        int y1 = heigh2 - 100;

        Bucket bucket1 = new Bucket(1, matches1, buck1, x1, y1); //define the buckets
        Bucket bucket2 = new Bucket(2, matches2, buck3, x3, y1);
        Bucket bucket3 = new Bucket(3, matches3, buck4, x4, y1);
        buckList.add(bucket1);      // add the buckets to an arraylist
        buckList.add(bucket2);
        buckList.add(bucket3);
    }

    @Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {}
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and
		// we can safely start the game loop

        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface is being destroyed");
		// tell the thread to shut down and wait for it to finish
		// this is a clean shutdown
        android.os.Process.killProcess(android.os.Process.myPid());

//        boolean retry = true;
//        while (retry) {
//            try {
//                thread.join();
//                retry = false;
//            } catch (InterruptedException e) {
//                // try again shutting down the thread
//                break;
//            }
//      }
		Log.d(TAG, "Thread was shut down cleanly");
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
        for(int i = 0 ; i < droidList.size(); i++) {
            Droid newdroid = droidList.get(i);

            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();

                 if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //Passing the X and Y coords to the Droid class
                     newdroid.handleActionDown(eventX, eventY);
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    newdroid.move(eventX, eventY);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // touch was released
                    if (newdroid.isTouched()) {
                        newdroid.setTouched(false);
                    }
                }
        }return true;
    }

	public void render(final Canvas canvas) {
        if (chooseRandoBack == 1){
            canvas.drawBitmap(resizedBack, 0, 0, null); //Drawing background
        }
        if (chooseRandoBack == 2){
//            canvas.drawBitmap(resizedBack2, 0, 0, null); //Drawing background
            canvas.drawBitmap(resizedBack, 0, 0, null); //Drawing background
        }
        if (chooseRandoBack == 3){
//            canvas.drawBitmap(resizedBack3, 0, 0, null); //Drawing background
            canvas.drawBitmap(resizedBack, 0, 0, null); //Drawing background
        }
        if (chooseRandoBack == 4){
//            canvas.drawBitmap(resizedBack4, 0, 0, null); //Drawing background
            canvas.drawBitmap(resizedBack, 0, 0, null); //Drawing background
        }



        Paint paint = new Paint();      //set Paint for text
        paint.setColor(Color.WHITE);      //set paint color as RED
        paint.setTextSize(16);          //set Text size

        canvas.drawText("Score: " + score, 50, 50, paint);  //draw Score display, the location and the paint to use
        canvas.drawText("Time: " + otherTime / 1000,  200,50, paint); //draw Time left

        Bucket buckets1 = buckList.get(0);  //define buckets
        Bucket buckets2 = buckList.get(1);
        Bucket buckets3 = buckList.get(2);

        buckets1.draw(canvas);              //draw buckets
        buckets2.draw(canvas);
        buckets3.draw(canvas);

        //need to repeat this to add more
        for (int i = 0; i < droidList.size(); i++) {
            Droid newdroid = droidList.get(i);
            //draw random droid element
            newdroid.draw(canvas);

            //check if dialog box is open
            if(isshown){
                //remove droidz if it is
                droidList.remove(i);
            }
            else{}
        }
    }

	public void update() {
        for(int i = 0 ; i < droidList.size(); i++) {
            int size = droidList.size();

            //define random droid
            final Droid newdroid = droidList.get(i);

            //define buckets from the ArrayList
            final Bucket buckets1 = buckList.get(0);
            final Bucket buckets2 = buckList.get(1);
            final Bucket buckets3 = buckList.get(2);

            colliding();
            newdroid.update(DOWNSPEED);
            }
        }

    public void colliding() {
        Bucket buckets1 = buckList.get(0);
        Bucket buckets2 = buckList.get(1);
        Bucket buckets3 = buckList.get(2);

        for (int i = 0; i < droidList.size(); i++) {
            final Droid newdroid = droidList.get(i);

            Rect buckRec1 = new Rect(buckets1.getX(), buckets1.getY(), buckets1.getX() + buck1Bounds.width(), buckets1.getY() + buck1Bounds.height());
            Rect buckRec2 = new Rect(buckets2.getX(), buckets2.getY(), buckets2.getX() + buck2Bounds.width(), buckets1.getY() + buck2Bounds.height());
            Rect buckRec3 = new Rect(buckets3.getX(), buckets3.getY(), buckets3.getX() + buck3Bounds.width(), buckets1.getY() + buck3Bounds.height());

            Rect droidRec1 = new Rect(newdroid.getX(), newdroid.getY(), newdroid.getX() + droid1Bounds.width(), newdroid.getY() + droid1Bounds.height());

            if (buckRec1.intersect(droidRec1)) {        //if rec around the bucket and the rec around the droid touch
                if (newdroid.getType() == buckets1.getbuckType()) { //if the types match
                    score = score + 1;      //adding to the score
                    newdroid.removie();     //setting droid SetTouch(False)
                    newdroid.setX(100000);  //set droid off screen
                    newdroid.setY(100000);
                    speedUp();              //Speeds up the interval in which the droidz spawn
                    if (droidList.size() >= 1){
                        droidList.remove(i);
                    }
                } else if (newdroid.getType() != buckets1.getbuckType()) { //if the types dont match
                    score = score - 1;
                    newdroid.setX(100000);
                    newdroid.setY(100000);
                    newdroid.removie();
                    if (droidList.size() >= 1){
                        droidList.remove(i);
                    }
                }
            }
            if (buckRec2.intersect(droidRec1)) {
                if (newdroid.getType() == buckets2.getbuckType()) {
                    score = score + 1;
                    newdroid.removie();
                    newdroid.setX(100000);
                    newdroid.setY(100000);
                    speedUp();
                    if (droidList.size() >= 1){
                        droidList.remove(i);
                    }
                } else if (newdroid.getType() != buckets2.getbuckType()) {
                    score = score - 1;
                    newdroid.removie();
                    newdroid.setX(100000);
                    newdroid.setY(100000);
                    if (droidList.size() >= 1){
                        droidList.remove(i);
                    }
                }
            }
            if (buckRec3.intersect(droidRec1)) {
                if (newdroid.getType() == buckets3.getbuckType()) {
                    score = score + 1;
                    newdroid.removie();
                    newdroid.setX(100000);
                    newdroid.setY(100000);
                    speedUp();
                    if (droidList.size() > 1){
                        droidList.remove(i);
                    }
                } else if (newdroid.getType() != buckets3.getbuckType()) {
                    score = score - 1;
                    newdroid.removie();
                    newdroid.setX(100000);
                    newdroid.setY(100000);
                    if (droidList.size() >= 1){
                        droidList.remove(i);
                    }
                }
            }
        }
    }

    public void addTime(){
        timer.cancel();     //cancel old timer
        timeleft =  otherTime + 1000; //TRY add more time
        timer.start();  //start new timer
    }

    public void speedUp(){
        if (interval > 800){  //if a droid spawn interval is greater that .8 seconds
            interval = interval - 100; // change the interval to .1 of a second less
        }
    }

}

