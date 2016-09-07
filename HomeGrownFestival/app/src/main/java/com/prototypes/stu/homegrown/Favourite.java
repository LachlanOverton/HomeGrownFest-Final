package com.prototypes.stu.homegrown;

/**
 * Created by seklar on 24/10/14.
 */
public class Favourite {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    private String value;

    public Favourite(int newid, String newvalue)
    {
        this.id = newid;
        this.value = newvalue;
    }

    public Favourite()
    {

    }
}
