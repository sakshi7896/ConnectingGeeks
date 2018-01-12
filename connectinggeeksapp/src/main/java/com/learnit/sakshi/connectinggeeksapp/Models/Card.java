package com.learnit.sakshi.connectinggeeksapp.Models;

/**
 * Created by Sakshi Jain on 08-12-2017.
 */

public class Card {
    private String title,desc;
    private long timeStamp;
    public Card(){

    }
    public Card(long timeStamp, String title, String desc) {
        this.timeStamp=timeStamp;
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
