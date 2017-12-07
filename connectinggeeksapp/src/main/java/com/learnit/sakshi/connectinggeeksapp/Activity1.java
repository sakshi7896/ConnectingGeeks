package com.learnit.sakshi.connectinggeeksapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity1 extends AppCompatActivity {
    private Button mbtn;
    private DatabaseReference mDatabase;
    private EditText mnamef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);

        mbtn =(Button) findViewById(R.id.Firebasebutton);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mnamef= (EditText)findViewById(R.id.name);

        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a child in root obj
                //assign value to child

                mDatabase.child("Name").setValue("Sakshi jain");

            }
        });
    }
}
