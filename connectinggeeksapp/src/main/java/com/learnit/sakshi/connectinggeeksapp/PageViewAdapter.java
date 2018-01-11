package com.learnit.sakshi.connectinggeeksapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by chanc on 11-01-2018.
 */

public class PageViewAdapter extends FragmentPagerAdapter {
    public PageViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new EventFragment();
            case 1:
                return null;
            case 2:
                return  null;
            case 3:
                return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
