package com.learnit.sakshi.connectinggeeksapp.Models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.learnit.sakshi.connectinggeeksapp.R;

/**
 * Created by Sakshi Jain on 07-12-2017.
 */

public class descriptionFlashCard {

    String eventName;
    String desc;
    String imageUri;

    public descriptionFlashCard(String eventName, String desc, String imageUri) {
        this.eventName = eventName;
        this.desc = desc;
        this.imageUri = imageUri;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        View view;
        TextView eventTitle, eventDesc;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            eventTitle=view.findViewById(R.id.event_title);
            eventDesc=view.findViewById(R.id.description);
        }
        void setData(descriptionFlashCard event)
        {
            eventTitle.setText(event.getEventName());
            eventDesc.setText(event.getDesc());
        }
    }

}




