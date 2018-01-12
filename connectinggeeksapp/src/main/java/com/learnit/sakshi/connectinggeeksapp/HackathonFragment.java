package com.learnit.sakshi.connectinggeeksapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.learnit.sakshi.connectinggeeksapp.Models.Card;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HackathonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HackathonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HackathonFragment extends Fragment {
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

    private OnFragmentInteractionListener mListener;

//    public HackathonFragment() {
//        super();
//        // Required empty public constructor
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HackathonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HackathonFragment newInstance(String param1, String param2) {
        HackathonFragment fragment = new HackathonFragment();
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
        View view= inflater.inflate(R.layout.fragment_hackathon, container, false);

        mdatabase= FirebaseDatabase.getInstance().getReference().child("Blog").child(eventType);
        Query personsQuery = mdatabase.orderByKey();
        mcardList=(RecyclerView) view.findViewById(R.id.card_list_hackathon);
        mcardList.setHasFixedSize(true);

        mcardList.setLayoutManager(new LinearLayoutManager(getActivity()));
        FirebaseRecyclerOptions cardOptions = new FirebaseRecyclerOptions.Builder<Card>().setQuery(personsQuery, Card.class).build();

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
                                .setPositiveButton("Delete",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,int id) {
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mfirebaseadapter.stopListening();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
