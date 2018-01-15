package com.learnit.sakshi.connectinggeeksapp.userevent;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnit.sakshi.connectinggeeksapp.R;
import com.learnit.sakshi.connectinggeeksapp.event.EventPageViewAdapter;

public class UserEventWrapperFragment extends Fragment {


    private ViewPager viewPager;
    private UserEventPageViewAdapter tabPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_user_event_wrapper, container, false);

        AppCompatActivity parentActivity = (AppCompatActivity)getActivity();
        viewPager = (ViewPager) view.findViewById(R.id.pagerTabView);
        tabPagerAdapter = new UserEventPageViewAdapter(parentActivity.getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        TabLayout tabPageLayout= (TabLayout) view.findViewById(R.id.tab_page_layout);
        tabPageLayout.setupWithViewPager(viewPager);


        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
