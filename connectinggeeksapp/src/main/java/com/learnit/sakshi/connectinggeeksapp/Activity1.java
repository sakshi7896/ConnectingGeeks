package com.learnit.sakshi.connectinggeeksapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity1 extends AppCompatActivity {
//    private Button mbtn;
//    private DatabaseReference mDatabase;
//    private EditText mnamef;
private RecyclerView mcardList;
private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        mdatabase=FirebaseDatabase.getInstance().getReference().child("Blog");

        mcardList=(RecyclerView) findViewById(R.id.card_list);
        mcardList.setHasFixedSize(true);

        mcardList.setLayoutManager(new LinearLayoutManager(this));

//        mbtn =(Button) findViewById(R.id.Firebasebutton);
//        mDatabase= FirebaseDatabase.getInstance().getReference();
//        mnamef= (EditText)findViewById(R.id.name);

//        mbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //create a child in root obj
//                //assign value to child

//                mDatabase.child("Name").setValue("Sakshi jain");
//
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Card, CardViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Card, CardViewHolder>(
            Card.class,
                R.layout.card_row,
                CardViewHolder.class,
                mdatabase
        ) {
            @Override
            protected void onBindViewHolder(CardViewHolder holder, int position, Card model) {
                holder.settitle(model.getTitle());
                holder.setDesc(model.getDesc());

            }

            @Override
            public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }
        };
        mcardList.setAdapter(firebaseRecyclerAdapter);
    }
    public static class CardViewHolder extends RecyclerView.ViewHolder {
        View mview;
        public CardViewHolder(View itemView) {
            super(itemView);

            mview=itemView;

        }
        public void settitle(String title){

            TextView post_title = (TextView)mview.findViewById(R.id.title);
            post_title.setText(title);
        }

        public void setDesc(String desc)
        {
            TextView post_desc= (TextView) mview.findViewById(R.id.desc);
            post_desc.setText(desc);
        }
    }
}
