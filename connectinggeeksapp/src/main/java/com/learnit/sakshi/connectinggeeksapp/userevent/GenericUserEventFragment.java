package com.learnit.sakshi.connectinggeeksapp.userevent;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learnit.sakshi.connectinggeeksapp.R;
import com.learnit.sakshi.connectinggeeksapp.models.Card;

import java.util.Date;


public class GenericUserEventFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String eventType = "";
    // TODO: Rename and change types of parameters
    private FloatingActionButton fab;
    private RecyclerView mcardList;
    private DatabaseReference mdatabase;
    private FirebaseRecyclerAdapter<Card, CardViewHolder> mfirebaseadapter;
    private final FirebaseUser firebaseUser;
    private final String uid;

    public GenericUserEventFragment() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
    }

    public static GenericUserEventFragment newInstance(String param1) {
        GenericUserEventFragment fragment = new GenericUserEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            eventType = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_generic_user_event, container, false);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent(eventType);
            }
        });
        mdatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child(eventType);
        Query personsQuery = mdatabase.orderByKey();
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("Blog").child(eventType);
        mcardList=(RecyclerView) view.findViewById(R.id.card_list_hackathon);
        mcardList.setHasFixedSize(true);

        mcardList.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions cardOptions = new FirebaseRecyclerOptions.Builder<Card>().setIndexedQuery(personsQuery,dataRef, Card.class).build();

        mfirebaseadapter = new FirebaseRecyclerAdapter<Card, CardViewHolder>(cardOptions) {
            @Override
            protected void onBindViewHolder(final CardViewHolder holder, int position, Card model) {

                holder.setTitle(model.getTitle());
                holder.setDesc(model.getDesc());
                holder.model = model;
                holder.dataRef = this.getRef(position);
                holder.mview.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                getContext());


                        // set dialog message
                        alertDialogBuilder
                                .setTitle("Delete Event")
                                .setMessage("You sure want to delete the event?")
                                .setCancelable(false)
                                .setPositiveButton("Update",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
                                                //TODO : Add update query
                                                dialog.cancel();

                                            }
                                        })
                                .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        holder.dataRef.removeValue();

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
        };
        mcardList.setAdapter(mfirebaseadapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mfirebaseadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mfirebaseadapter.stopListening();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mfirebaseadapter.stopListening();

    }

    public  void addNewEvent(final String eventType)
    {
        final Context context = getContext();
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

                                DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference();
                                long timeStamp = new Date().getTime();
                                String key = mdatabase.child(eventType).push().getKey();
                                String uid = firebaseUser.getUid();
                                Card card = new Card(timeStamp,eventName,eventDesc,firebaseUser.getDisplayName());
                                DatabaseReference ref = mdatabase.child("Blog").child(eventType).child(key);
                                ref.setValue(card);
                                mdatabase.child("Users").child(uid).child(eventType).child(key).setValue(timeStamp);

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
