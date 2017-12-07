package com.learnit.sakshi.connectinggeeksapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;
import com.learnit.sakshi.connectinggeeksapp.Models.descriptionFlashCard;

public class Activity2 extends AppCompatActivity {

    RecyclerView flashCardRecyclerView;
    private DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<descriptionFlashCard,descriptionFlashCard.ViewHolder> flashCardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        flashCardRecyclerView = findViewById(R.id.flash_card_recycler_view);
//        flashCardAdapter = new FirebaseRecyclerAdapter<descriptionFlashCard, descriptionFlashCard.ViewHolder>() {
//            @Override
//            protected void onBindViewHolder(descriptionFlashCard.ViewHolder holder, int position, descriptionFlashCard model) {
//
//            }
//
//            @Override
//            public descriptionFlashCard.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                return null;
//            }
//        };

//        flashCardAdapter=new FirebaseRecyclerAdapter<descriptionFlashCard, descriptionFlashCard.ViewHolder>(
//                descriptionFlashCard.class,
//                R.layout.description_flash_card,
//                descriptionFlashCard.ViewHolder.class,
//                mDatabase
//
//        );

    }
}
