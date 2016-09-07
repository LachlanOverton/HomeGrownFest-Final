package com.prototypes.stu.homegrown;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stuart on 28/09/2014.
 */
public class XMLPullParserHandler {
    List<Event> events;
    List<Prize> prizes;
    private Prize prize;
    private Event event;
    private String text;
    DBHelper dbHelper;
    Context context;

    public XMLPullParserHandler(Context context) {
        events = new ArrayList<Event>();
        prizes = new ArrayList<Prize>();
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public List<Event> getEvents() {
        return events;
    }

    public List<Prize> getPrizes() { return prizes; }

    public List<Event> parseevents(String url, int type, Context context, List<Favourite> favourites) throws IOException, XmlPullParserException {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        URL input = new URL(url.toString());
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(input.openStream(), null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("event")) {
                            event = new Event();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;


                    // For each tag that exists from name, id, address, longdesc, shortdesc, starttime, endtime, startdate, enddate, suburb, postcode, phone, geo, fee, agegroup
                    // set the String in the Event Object with the data contained between each tag.

                    case XmlPullParser.END_TAG:
//                        if(tagname.equalsIgnoreCase("category")) {
//                            if(text.trim().equals("Homegrown Festival"))
//                            {
//                                break;
//                            }
                         if (tagname.equalsIgnoreCase("event")) {
                            events.add(event);
                        } else if (tagname.equalsIgnoreCase("name")) {
                            event.setName(text);
                        } else if (tagname.equalsIgnoreCase("id")) {
                            event.setId(text);
                        } else if (tagname.equalsIgnoreCase("address")) {
                            event.setAddress(text);
                        } else if (tagname.equalsIgnoreCase("longdesc")) {
                            event.setLongDesc(text);
                        } else if (tagname.equalsIgnoreCase("shortdesc")) {
                            event.setShortDesc(text);
                        } else if (tagname.equalsIgnoreCase("starttime")) {
                            event.setStarttime(text);
                        } else if (tagname.equalsIgnoreCase("endtime")) {
                            event.setEndtime(text);
                        } else if (tagname.equalsIgnoreCase("startdate")) {
                            event.setStartdate(text);
                        } else if (tagname.equalsIgnoreCase("enddate")) {
                            event.setEnddate(text);
                        } else if (tagname.equalsIgnoreCase("suburb")) {
                            event.setSuburb(text);
                        } else if (tagname.equalsIgnoreCase("postcode")) {
                            event.setPostcode(text);
                        } else if (tagname.equalsIgnoreCase("phone")) {
                            event.setPhone(text);
                        } else if (tagname.equalsIgnoreCase("geo")) {
                            event.setGeo(text);
                        } else if (tagname.equalsIgnoreCase("fee")) {
                            event.setFee(text);
                        } else if (tagname.equalsIgnoreCase("agegroup")) {
                            event.setAgegroup(text);
                        } else if (tagname.equalsIgnoreCase("category")) {
                             event.setCategory(text);
                         } else if (tagname.equalsIgnoreCase("bookable")) {
                             event.setBookable(text);
                         }
                        break;

                    default:
                        break;
                }
                eventType = parser.next(); // Begin parsing the next event instance in the xml file.
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Event> homegrownEventsList = new ArrayList<Event>();
        List<Event> finalList = new ArrayList<Event>();
        List<Favourite> favouriteList = new ArrayList<Favourite>();

        for(int i = 0; i < events.size(); i++) {
            String eventscategory = events.get(i).getCategory();
            String category = "School Holiday Program";
            if (eventscategory.trim().equals(category.trim()))
            {
//                events.remove(i);
                homegrownEventsList.add(events.get(i));
                Log.d("***", "Item Removed #" + i);
            }
        }

        if(type == 1) {
            finalList = homegrownEventsList;
        } else if (type == 2) {
            favouriteList = dbHelper.getAllFavourites();
            for(int j = 0; j < homegrownEventsList.size(); j++) {
            String eventsid = homegrownEventsList.get(j).getId();
                for(int a = 0; a < favouriteList.size(); a++)
                {
                    String sqlfavouriteid = favouriteList.get(a).getValue(); //TODO: Get list of users favorites ID's from SQLlite DB
                    if(eventsid.trim().equals(sqlfavouriteid.trim()))
                    {
                        finalList.add(homegrownEventsList.get(j));
                    }
                }

            }
        }
        return finalList;
    }

    public List<Prize> parseprizes(String url) throws IOException, XmlPullParserException {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        URL input = new URL(url.toString());
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(input.openStream(), null);

            int prizeType = parser.getEventType();
            while (prizeType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (prizeType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("prize")) {
                            prize = new Prize();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("prize")) {
                            prizes.add(prize);
                        } else if (tagname.equalsIgnoreCase("id")) {
                            prize.setId(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("name")) {
                            prize.setName(text);
                        } else if (tagname.equalsIgnoreCase("short_desc")) {
                            prize.setShortDescription(text);
                        } else if (tagname.equalsIgnoreCase("long_desc")) {
                            prize.setLongDescription(text);
                        } else if (tagname.equalsIgnoreCase("vendor")) {
                            prize.setVendor(text);
                        } else if (tagname.equalsIgnoreCase("available")) {
                            prize.setAvailable(Integer.parseInt(text));
                        } else if (tagname.equalsIgnoreCase("startdate")) {
                            prize.setStartDate(text);
                        } else if (tagname.equalsIgnoreCase("enddate")) {
                            prize.setEndDate(text);
                        } else if (tagname.equalsIgnoreCase("duration")) {
                            prize.setDuration(Integer.parseInt(text));
                        }
                        break;

                    default:
                        break;
                }
                prizeType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prizes;
    }
}
