package com.prototypes.stu.homegrown.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by NickHenry on 16/09/2014.
 */
public class Bucket {

    private int buckType;
    private int matches;
    private Bitmap buckBit;
    private int x;
    private int y;


    public Bucket(int buckType, int matches, Bitmap buckBit, int x, int y){

        this.buckType = buckType;
        this.matches = matches;
        this.buckBit = buckBit;
        this.x = x;
        this.y = y;

    }

    public Bitmap getbuckBit() {
        return buckBit;
    }
    public void setBitmap(Bitmap buckBit) {
        this.buckBit = buckBit;
    }
    public int getMatches() {
        return matches;
    }
    public void setMatches(int matches) {
        this.matches = matches;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getbuckType(){ return buckType;}
    public void setType(int buckType){
        this.buckType = buckType;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(buckBit, x - (buckBit.getWidth() / 2), y - (buckBit.getHeight() / 2), null);
        // bitmap.draw(canvas);
    }

}
