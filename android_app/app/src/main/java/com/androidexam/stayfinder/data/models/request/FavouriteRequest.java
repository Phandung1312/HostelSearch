package com.androidexam.stayfinder.data.models.request;

public class FavouriteRequest {
    String username ;
    int postId;

    public FavouriteRequest(String username, int postId) {
        this.username = username;
        this.postId = postId;
    }
}
