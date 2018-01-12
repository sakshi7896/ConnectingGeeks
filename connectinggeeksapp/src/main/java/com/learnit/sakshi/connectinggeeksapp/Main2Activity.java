package com.learnit.sakshi.connectinggeeksapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.learnit.sakshi.connectinggeeksapp.Models.Card;

import java.util.Date;

public class Main2Activity extends AppCompatActivity implements ActionBar.TabListener, HackathonFragment.OnFragmentInteractionListener,WorkshopFragment.OnFragmentInteractionListener,CodingFragment.OnFragmentInteractionListener{


    private ViewPager viewPager;
    private PageViewAdapter tabPagerAdapter;
    private ActionBar actionBar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.add_event:
                addNewEvent(actionBar.getSelectedNavigationIndex());
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        viewPager = (ViewPager) findViewById(R.id.pagerTabView);
        tabPagerAdapter = new PageViewAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        for (int i = 0; i < tabPagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab().setText(tabPagerAdapter.getTabNames()[i])
                    .setTabListener(this));
        }
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int postion) {
                actionBar.setSelectedNavigationItem(postion);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }



    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void addNewEvent(final int event)
    {

        final Context context = this;
        final String eventType = tabPagerAdapter.getTabNames()[event];
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.add_event_dialog, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText eventNameEditText = (EditText) promptsView
                .findViewById(R.id.add_event_name);

        final EditText eventDescEditText = (EditText) promptsView
                .findViewById(R.id.add_event_desc);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Add Event",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                String eventName = eventNameEditText.getText().toString().trim();
                                String eventDesc = eventDescEditText.getText().toString().trim();
                                if(eventName.length()==0 || eventDesc.length()==0)
                                    dialog.cancel();

                                DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
                                long timeStamp = new Date().getTime();
                                String key = mdatabase.child(eventType).push().getKey();
                                Card card = new Card(timeStamp,eventName,eventDesc);
                                mdatabase.child(eventType).child(key).setValue(card);

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }
}
