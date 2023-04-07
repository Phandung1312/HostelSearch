package com.androidexam.stayfinder.data.services;

import com.androidexam.stayfinder.data.apis.PostAPI;
import com.androidexam.stayfinder.data.models.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class PostRemoteService {
    PostAPI postAPI;
    public Observable<List<Post>> getPostByAccountName(String accountName){
        return postAPI.getPostByAccountName(
                accountName
        );
    }
}
