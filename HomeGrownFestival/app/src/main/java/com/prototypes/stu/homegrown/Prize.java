package com.prototypes.stu.homegrown;

import java.util.Date;

public class Prize {

    int id; // GUID based ID
    String name; // Name of Prize
    String shortDescription; // Short Description of Prize
    String longDescription; // Long Description of Prize
    String vendor; // Location to claim the prize
    int available; // How many prizes are available
    String startDate; // The first day that the prize can be claimed
    String endDate; // The last day that the prize can be claimed
    int duration; // The number of days that the prize is valid for

    public Prize(int id, String name, String shortDescription, String longDescription, String vendor, String startDate, String endDate, int duration) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.vendor = vendor;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public Prize() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String toString() {
        return this.getId() + "\n" + this.getName() + "\n" + this.getShortDescription() + "\n" + this.getLongDescription() + "\n" + this.getVendor() + "\n" + this.getAvailable() + "\n" + this.getStartDate() + "\n" + this.getEndDate() + "\n" + this.getDuration();
    }
}