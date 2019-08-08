package com.ibrahim.selfsoultiontask.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Project {

    private String title ;
    @Exclude
    private String uid ;
    private String description ;
    private long date;

    public Project() {
    }

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public Map<String , Object> asMap(){
        Map<String , Object> map = new HashMap<>();
        map.put("title" , title);
        map.put("description" , description);
        map.put("date" , ServerValue.TIMESTAMP);

        return map ;
    }
}
