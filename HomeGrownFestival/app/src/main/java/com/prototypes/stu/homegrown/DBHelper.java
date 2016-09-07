package com.prototypes.stu.homegrown;

import com.prototypes.stu.homegrown.Favourite;
import com.prototypes.stu.homegrown.HighScore;
import com.prototypes.stu.homegrown.Prize;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "homegrowndatabase";
    public static final String SHAKE_TABLE_NAME = "shake";
    public static final String PRIZES_TABLE_NAME = "prizes";
    public static final String EVENT_FAVOURITES_TABLE_NAME = "favouriteevents";
    public static final String GNOMEWHACK_GAME_HIGHSCORES_TABLE_NAME = "gnomewhackscores";
    public static final String KEY_ID = "id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL Commands to create the Shake and Prizes table
        db.execSQL("CREATE TABLE IF NOT EXISTS shake (id INTEGER PRIMARY KEY, shakeTime TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS prizes (id INTEGER PRIMARY KEY, name TEXT, short_desc TEXT, long_desc TEXT, vendor TEXT, startdate TEXT, enddate TEXT, duration INTEGER, wondate TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS favouriteevents (id INTEGER PRIMARY KEY AUTOINCREMENT, eventid TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS gnomewhackscores (id INTEGER PRIMARY KEY AUTOINCREMENT, score INTEGER, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Check if favourite is already in Database
    public boolean checkFavourite(String eventid) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("SELECT eventid FROM favouriteevents WHERE eventid ="+eventid+"", null);

        if(res.getCount() >= 1) {
            return true;
        } else {
            return false;
        }
    }

    // Insert new high score
    public boolean insertNewHighScore(int score) {
        String date;
        TimeZone timeZone = TimeZone.getDefault();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setTimeZone(timeZone);
        date = dateFormat.format(new Date(System.currentTimeMillis()));

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("score", score);
        contentValues.put("date", date);

        db.insert(GNOMEWHACK_GAME_HIGHSCORES_TABLE_NAME, null, contentValues);
        return true;
    }

    // Get Top 5 High Scores
    public List<HighScore> getTop5HighScores() {
     ArrayList<HighScore> highScores = new ArrayList<HighScore>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM gnomewhackscores ORDER BY score DESC LIMIT 5", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            HighScore highScore = new HighScore();
            highScore.setScore(Integer.parseInt(res.getString(res.getColumnIndex("score"))));
            highScore.setDate(res.getString(res.getColumnIndex("date")));
            highScores.add(highScore);

            res.moveToNext();
        }
        return highScores;
    }

    // Get all Favourites in Table
    public List<Favourite> getAllFavourites() {
        ArrayList<Favourite> favourites = new ArrayList<Favourite>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM favouriteevents", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Favourite favourite = new Favourite();
            favourite.setId(Integer.parseInt(res.getString(res.getColumnIndex("id"))));
            favourite.setValue(res.getString(res.getColumnIndex("eventid")));
            favourites.add(favourite);
            res.moveToNext();
        }
        return favourites;
    }

    // Get all Prizes in the Table
    public List<Prize> getAllPrizes() {
        ArrayList<Prize> prizes = new ArrayList<Prize>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM prizes", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Prize prize = new Prize();
            prize.setId(Integer.parseInt(res.getString(res.getColumnIndex("id"))));
            prize.setName(res.getString(res.getColumnIndex("name")));
            prize.setShortDescription(res.getString(res.getColumnIndex("short_desc")));
            prize.setLongDescription(res.getString(res.getColumnIndex("long_desc")));
            prize.setVendor(res.getString(res.getColumnIndex("vendor")));
            prize.setStartDate(res.getString(res.getColumnIndex("startdate")));
            prize.setEndDate(res.getString(res.getColumnIndex("enddate")));
            prize.setDuration(Integer.parseInt(res.getString(res.getColumnIndex("duration"))));
            prizes.add(prize);
            res.moveToNext();
        }
        return prizes;
    }

    // Count prizes
    public Integer countPrizes() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT id FROM prizes", null);
        return res.getCount();
    }

    // (Claim) Delete Prize
    public Integer deletePrize(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("prizes", "id = ? ", new String[]{Integer.toString(id)});
    }

    // Get Prize Row Data
    public Cursor getPrizeData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM prizes WHERE id=" + id + "", null);
        return res;
    }

    // Check if Prize Exists
    public Boolean checkPrizeData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM prizes WHERE id=" + id + "", null);

        if (res.getCount() > 0) {
            return true;
        }
        return false;
    }

    // Insert a new Prize into the Database
    public boolean insertPrize(int prizeID, String name, String short_desc, String long_desc, String vendor, String startdate, String enddate, int duration, String wonDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", prizeID);
        contentValues.put("name", name);
        contentValues.put("short_desc", short_desc);
        contentValues.put("long_desc", long_desc);
        contentValues.put("vendor", vendor);
        contentValues.put("startdate", startdate);
        contentValues.put("enddate", enddate);
        contentValues.put("duration", duration);
        contentValues.put("wondate", wonDate);

        db.insert(PRIZES_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertFavourite(String eventid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("eventid", eventid);
        if(checkFavourite(eventid) == false) {
            db.insert(EVENT_FAVOURITES_TABLE_NAME, null, cv);
        }
        return true;
    }

    // Get Prize Won Date
    public Cursor getPrizeWonDate(int prizeID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT wondate FROM prizes WHERE id=" + prizeID + "", null);
        return res;
    }

    // Get Shake Time Data
    public Cursor getShakeTimeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM shake WHERE id=" + 1 + "", null);
        return res;
    }

    // Insert a new Shake Time into the Database
    public boolean insertShakeTime(String shakeTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", 1);
        contentValues.put("shakeTime", shakeTime);

        db.insert(SHAKE_TABLE_NAME, null, contentValues);
        return true;
    }

    // Update Shake Time
    public boolean updateShakeTime(String shakeTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("shakeTime", shakeTime);
        db.update(SHAKE_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(1)});
        return true;
    }
}