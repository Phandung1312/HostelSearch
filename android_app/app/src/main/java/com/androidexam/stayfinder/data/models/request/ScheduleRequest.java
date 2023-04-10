package com.androidexam.stayfinder.data.models.request;

import java.io.Serializable;
import java.sql.Timestamp;

public class ScheduleRequest implements Serializable {
    private int id;
    private String username;
    private String renterName;
    private String renterPhoneNumber;
    private String content;
    private Timestamp meetingTime;
    private int postId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
//
//    public String getAccountName() {
//        return accountName;
//    }
//
//    public void setAccountName(String accountName) {
//        this.accountName = accountName;
//    }

    public String getRenterName() {
        return renterName;
    }

    public void setRenterName(String renterName) {
        this.renterName = renterName;
    }

    public String getRenterPhoneNumber() {
        return renterPhoneNumber;
    }

    public void setRenterPhoneNumber(String renterPhoneNumber) {
        this.renterPhoneNumber = renterPhoneNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(Timestamp meetingTime) {
        this.meetingTime = meetingTime;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
