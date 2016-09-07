package com.prototypes.stu.homegrown;

/**
 * Created by matthewhill on 28/10/14.
 */
public class HighScore {
    int score;
    int level;
    String date;

    public HighScore(int score, int level, String date) {
        this.score = score;
        this.level = level;
        this.date = date;
    }

    public HighScore(){ }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
