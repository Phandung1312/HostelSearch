package com.androidexam.stayfinder.data.models.request;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Image;
import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

public class PostRequest {
    private int id;
    private Account account;
    private String title;
    private String content;
    @SerializedName("numberOfFavourites")
    private int numOfFav;
    private int status;
    private Timestamp postTime;
    private List<Image> images;
    private Hostel hostel;
    private int accountId;

    public PostRequest(String title, String content, int accountId) {
        this.title = title;
        this.content = content;
        this.accountId = accountId;
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

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }


    public Hostel getHostel() {
        return hostel;
    }

    public void setHostel(Hostel hostel) {
        this.hostel = hostel;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
