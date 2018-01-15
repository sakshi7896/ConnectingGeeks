package com.learnit.sakshi.connectinggeeksapp.models;

/**
 * Created by Sakshi Jain on 08-12-2017.
 */

public class Card {
    private String userName;
    private String title,desc;
    private long timeStamp;
    public Card(){

    }
    public Card(long timeStamp, String title, String desc, String userName) {
        this.timeStamp=timeStamp;
        this.title = title;
        this.desc = desc;
        this.userName=userName;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
