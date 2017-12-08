package com.learnit.sakshi.connectinggeeksapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Activity1 extends AppCompatActivity {
//    private Button mbtn;
//    private DatabaseReference mDatabase;
//    private EditText mnamef;
private RecyclerView mcardList;
private DatabaseReference mdatabase;

//stackoverflow
//private RecyclerView mPeopleRV;
    private FirebaseRecyclerAdapter<Card, CardViewHolder> mfirebaseadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        mdatabase=FirebaseDatabase.getInstance().getReference().child("Blog");
        Query personsQuery = mdatabase.orderByKey();
        mcardList=(RecyclerView) findViewById(R.id.card_list);
        mcardList.setHasFixedSize(true);

        mcardList.setLayoutManager(new LinearLayoutManager(this));
       FirebaseRecyclerOptions cardOptions = new FirebaseRecyclerOptions.Builder<Card>().setQuery(personsQuery, Card.class).build();

        mfirebaseadapter = new FirebaseRecyclerAdapter<Card, CardViewHolder>(cardOptions) {
            @Override
            protected void onBindViewHolder(CardViewHolder holder, int position, Card model) {
                holder.settitle(model.getTitle());
                holder.setDesc(model.getDesc());
            }

            @Override
            public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // return null;
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);
                return new CardViewHolder(view);
            }
        };
        mcardList.setAdapter(mfirebaseadapter);
        //mfirebaseadapter = (FirebaseRecyclerAdapter)findViewById(R.id.mcardList);
      //  mcardList= findViewById().findViewsWithText(R.id.card_);


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
    public void onStart() {
        super.onStart();
        mfirebaseadapter.startListening();


//
//        FirebaseRecyclerAdapter<Card, CardViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Card, CardViewHolder>(
//            Card.class,
//                R.layout.card_row,
//                CardViewHolder.class,
//                mdatabase
//        ) {
//            @Override
//            protected void onBindViewHolder(CardViewHolder holder, int position, Card model) {
//                holder.settitle(model.getTitle());
//                holder.setDesc(model.getDesc());
//
//            }
//
//            @Override
//            public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                return null;
//            }
//        };
//        mcardList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mfirebaseadapter.stopListening();
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
