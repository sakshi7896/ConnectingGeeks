package com.learnit.sakshi.connectinggeeksapp;

/**
 * Created by Sakshi Jain on 08-12-2017.
 */

public class Card {
    private String title,desc;
    public Card(){

    }
    public Card(String title, String desc) {
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
}
