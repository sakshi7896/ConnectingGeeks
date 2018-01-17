package com.learnit.sakshi.connectinggeeksapp.event;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learnit.sakshi.connectinggeeksapp.models.Card;
import com.learnit.sakshi.connectinggeeksapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class GenericEventFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String eventType = "";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView mcardList;
    private DatabaseReference mdatabase;
    private FirebaseRecyclerAdapter<Card, CardViewHolder> mfirebaseadapter;
    private final FirebaseUser firebaseUser;

    public GenericEventFragment() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    public static GenericEventFragment newInstance(String param1, String param2) {
        GenericEventFragment fragment = new GenericEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            eventType = mParam1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_generic_event, container, false);

        mdatabase= FirebaseDatabase.getInstance().getReference().child("Blog").child(eventType);
        Query personsQuery = mdatabase.orderByKey();

        mcardList=(RecyclerView) view.findViewById(R.id.event_card_list_hackathon);
//        mcardList.setHasFixedSize(true);

        mcardList.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions cardOptions = new FirebaseRecyclerOptions.Builder<Card>().setQuery(personsQuery, Card.class).build();
        final ProgressBar mProgressDialog = (ProgressBar) view.findViewById(R.id.event_progress_bar);
        mfirebaseadapter = new FirebaseRecyclerAdapter<Card, CardViewHolder>(cardOptions) {
            @Override
            protected void onBindViewHolder(final CardViewHolder holder, int position, final Card model) {

                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.model = model;
                holder.dataRef = this.getRef(position);
                holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                getContext());

                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(model.getTimeStamp());
                        String date = DateFormat.format("dd-MMM-yyyy HH:mm", cal).toString();
                        // set dialog message
                        alertDialogBuilder
                                .setTitle("Event Details : ")
                                .setMessage("Posted by : "+model.getUserName()+"\nPosted On : "+date)
                                .setCancelable(true)
                                .setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                dialog.cancel();

                                            }
                                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                        return false;
                    }
                });

            }

            @Override
            public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // return null;
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row,parent,false);

                return new CardViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                if (mProgressDialog != null) {
                    mProgressDialog.setVisibility(View.GONE);
                }
            }
        };
        mcardList.setAdapter(mfirebaseadapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(mfirebaseadapter!=null)
            mfirebaseadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mfirebaseadapter!=null)
            mfirebaseadapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mfirebaseadapter!=null)
            mfirebaseadapter.startListening();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mfirebaseadapter!=null)
            mfirebaseadapter.stopListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(mfirebaseadapter!=null)
            mfirebaseadapter.startListening();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mfirebaseadapter!=null)
            mfirebaseadapter.stopListening();

    }




    public static class CardViewHolder extends RecyclerView.ViewHolder {
        View mview;
        Card model;
        DatabaseReference dataRef;
        public CardViewHolder(View itemView) {
            super(itemView);

            mview=itemView;

        }
        public void setTitle(String title){

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
