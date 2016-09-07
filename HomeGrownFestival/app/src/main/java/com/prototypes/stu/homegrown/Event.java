package com.prototypes.stu.homegrown;

/**
 * Created by Stuart on 28/09/2014.
 */
public class Event {

    String name; // Name of the event;
    String id; // GUID based string format ID;
    String agegroup; // Age group ie. Youth, Adults, Seniors
    String startdate; // Start date of the event
    String enddate; // End date of the event
    String starttime; // Start time of the event
    String endtime; // End time of the event
    String streetaddress; // Address of the event/venue including street number and streetname ie. 123 Streetname Street
    String suburb; // Suburb of the event/venue
    String postcode; // Postcode of the event/venue
    String phone; // Phone number of the event/venue. Format XX-XXXX-XXXX
    String geo; // Google Maps resolvable latitude/longitude. Format LATITUDE, LONGITUDE.
    String shortdesc; // Short description of the event
    String longdesc; // Long description of the event may need to be striped of HTML tags
    String fee; // Details of Fees or if the event is free
    String bookable;
    String category;


    // Setters and Getters.


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBookable() {
        return bookable;
    }

    public void setBookable(String bookable) {
        this.bookable = bookable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(String agegroup) {
        this.agegroup = agegroup;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String  getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String  getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String  getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGeo() {
        return geo;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return streetaddress;
    }

    public void setAddress(String address) {
        this.streetaddress = address;
    }

    public String getShortDesc() {
        return shortdesc;
    }

    public void setShortDesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getLongDesc() {
        return longdesc;
    }

    public void setLongDesc(String longdesc) {
        this.longdesc = longdesc;
    }


    // No Longer Required.

    @Override
    public String toString() {
        return id + "\n" + name + "\n" + streetaddress + ", " + suburb + ", " + postcode + "\n" + phone + "\n" + shortdesc
                + "\n" + longdesc + "\n" + agegroup + "\n" + starttime + " - " + endtime + "\n" + startdate + " - " + enddate + "\n" + geo + "\n" + fee;
    }
}
