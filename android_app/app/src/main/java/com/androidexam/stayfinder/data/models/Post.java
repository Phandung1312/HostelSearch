package com.androidexam.stayfinder.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Post implements Serializable {
    private int id;
    private Account account;
    private String title;
    private String content;
    @SerializedName("numberOfFavourites")
    private int numOfFav;
    private int status;
    private Timestamp postTime;
    private List<Image> images;

    private int hostelId;
    private Hostel hostel;

    public Post(PostRequest postRequest){
        this.id = postRequest.getId();
        this.account = postRequest.getAccount();
        this.title = postRequest.getTitle();
        this.content = postRequest.getContent();
        this.numOfFav = postRequest.getNumOfFav();
        this.status = postRequest.getStatus();
        this.postTime = postRequest.getPostTime();
        this.images = postRequest.getImages();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumOfFav() {
        return numOfFav;
    }

    public void setNumOfFav(int numOfFav) {
        this.numOfFav = numOfFav;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getPostTime() {
        return postTime;
    }

    public void setPostTime(Timestamp postTime) {
        this.postTime = postTime;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public Post(Post post) {
        this.id = post.id;
        this.account = post.account;
        this.title = post.title;
        this.content = post.content;
        this.numOfFav = post.numOfFav;
        this.status = post.status;
        this.postTime = post.postTime;
        this.images = post.images;
        this.hostelId = post.hostelId;
        this.hostel = post.hostel;
    }
}
