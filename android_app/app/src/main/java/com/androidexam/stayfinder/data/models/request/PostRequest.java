package com.androidexam.stayfinder.data.models.request;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Image;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

public class PostRequest {
    //    @SerializedName("username")
    private int id;


    private Account account;
    private String title;
    private String content;
    private int numOfFav;
    private boolean status;
    private Timestamp postTime;
    //    private List<Image> images;
    private Hostel hostel;

    public PostRequest(int id, Account account, String title, String content, int numOfFav, boolean status, Timestamp postTime, Hostel hostel) {
        this.id = id;
        this.account = account;
        this.title = title;
        this.content = content;
        this.numOfFav = numOfFav;
        this.status = status;
        this.postTime = postTime;
        this.hostel = hostel;
    }


}
