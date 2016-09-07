/**
 * 
 */
package com.prototypes.stu.homegrown.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;



public class Droid {
    private Bitmap bitmap; // the picture
    private int x;			// the X coordinate
	private int y;			// the Y coordinate
	private boolean touched;	// if droid is touched/picked up
    private int type; //type of droid -- to be able to seperate the different types of seeds

    public Droid(Bitmap bitmap, int x, int y, int type) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
        this.type = type;
    }

    public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
    public int getType(){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
	public boolean isTouched() {
		return touched;
	}
	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

    public void removie(){
        setTouched(false);
    }

	public void update(double downSpeed) {
		if (!touched) {
            setY(getY() + (int)downSpeed);
		}
	}

	public void handleActionDown(int eventX, int eventY) {

        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2))) {
				// droid touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}

    public void move(int eventX, int eventY){
        if (touched){
            setX(eventX);
            setY(eventY);
        }
    }
}
