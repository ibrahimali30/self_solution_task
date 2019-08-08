package com.ibrahim.selfsoultiontask.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Task implements Serializable {
    private String content;
    private String deadLine;
    private long time;
    private String expectedWorkingHours;
    String title ;
    String memberUid;
    String meberEmail;

    @Exclude
    String taskUid;
//    private enum status {completed , newTask , unassign , overdue}


    public Task() {
    }

    public Task(String content, String deadLine,  String expectedWorkingHours, String title , String meberEmail) {
        this.content = content;
        this.deadLine = deadLine;
        this.expectedWorkingHours = expectedWorkingHours;
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public long getTime() {
        return time;
    }

    public String getExpectedWorkingHours() {
        return expectedWorkingHours;
    }

    public String getTitle() {
        return title;
    }

    public String getMemberUid() {
        return memberUid;
    }

    public String getMeberEmail() {
        return meberEmail;
    }

    public String getTaskUid() {
        return taskUid;
    }

    public void setTaskUid(String taskUid) {
        this.taskUid = taskUid;
    }



    public Map<String , Object> asMap(){
        Map<String , Object> map = new HashMap<>();
        map.put("content" , content);
        map.put("title" , title);
        map.put("deadLine" , deadLine);
        map.put("time" , ServerValue.TIMESTAMP);
        map.put("expectedWorkingHours" , expectedWorkingHours);
        map.put("meberEmail" , meberEmail);
//        map.put("status" , status.overdue);


        return map ;
    }

    @Override
    public String toString() {
        return "Task{" +
                "content='" + content + '\'' +
                ", deadLine=" + deadLine +
                ", time='" + time + '\'' +
                ", expectedWorkingHours=" + expectedWorkingHours +
                '}';
    }
}
