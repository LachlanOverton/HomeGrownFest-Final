package com.prototypes.stu.homegrown;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Stuart on 28/09/2014.
 */
public class activity_navigation extends Activity {

    private static int selecteditem = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_menu);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_main_menu mainmenu = new fragment_main_menu();
        fragmentTransaction.replace(R.id.mainfragment, mainmenu);
        fragmentTransaction.commit();
    }

    public void MainMenuClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_main_menu mainmenu = new fragment_main_menu();
        fragmentTransaction.replace(R.id.mainfragment, mainmenu);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void CompetitionsClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_competitions_menu compmenu = new fragment_competitions_menu();
        fragmentTransaction.replace(R.id.mainfragment, compmenu);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void EventsListClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_events_list eventslist = new fragment_events_list();
//        EventsFavoritesFragment eventslist = new EventsFavoritesFragment();
        fragmentTransaction.replace(R.id.mainfragment, eventslist);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void GamesCompetitionsClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_games_menu gamescompetitions = new fragment_games_menu();
        fragmentTransaction.replace(R.id.mainfragment, gamescompetitions);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void ContactUsClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_contact_us contactus = new fragment_contact_us();
        fragmentTransaction.replace(R.id.mainfragment, contactus);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void EventMapClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_event_map eventmap = new fragment_event_map();
        fragmentTransaction.replace(R.id.mainfragment, eventmap);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void EventFavoriteClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_event_favourites_list favorites = new fragment_event_favourites_list();
        fragmentTransaction.replace(R.id.mainfragment, favorites);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void EventCaldendarClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragment_event_calendar favorites = new fragment_event_calendar();
        fragmentTransaction.replace(R.id.mainfragment, favorites);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void setSelecteditem(int val)
    {
        this.selecteditem = val;
    }

    public void onBackPressed() {
        super.onBackPressed();

        if (selecteditem == 0) {
            Intent goToMainActivity = new Intent((getApplicationContext()), activity_navigation.class);
            goToMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Will clear out your activity history stack till now
            startActivity(goToMainActivity);
        } else if (selecteditem == 1) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragment_events_list eventslist = new fragment_events_list();
            fragmentTransaction.replace(R.id.mainfragment, eventslist);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            selecteditem = 0;
        } else if(selecteditem == 2){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragment_games_menu gamescompetitions = new fragment_games_menu();
            fragmentTransaction.replace(R.id.mainfragment, gamescompetitions);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            selecteditem = 0;
        }
    }
}
