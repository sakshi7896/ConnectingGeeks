package com.learnit.sakshi.connectinggeeksapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chanc on 11-01-2018.
 */

public class PageViewAdapter extends FragmentPagerAdapter {
    private String[] tabNames = { "Hackathons", "Workshops", "Coding"};



    public PageViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return HackathonFragment.newInstance(tabNames[position],"");
            case 1:
                return WorkshopFragment.newInstance(tabNames[position],"");
            case 2:
                return  CodingFragment.newInstance(tabNames[position],"");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    public String[] getTabNames() {
        return tabNames;
    }

    public void setTabNames(String[] tabNames) {
        this.tabNames = tabNames;
    }
}
