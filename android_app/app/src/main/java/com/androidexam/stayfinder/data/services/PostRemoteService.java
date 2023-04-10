package com.androidexam.stayfinder.data.services;
import com.androidexam.stayfinder.data.apis.PostAPI;
import com.androidexam.stayfinder.data.models.Post;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PostRemoteService {
    PostAPI postAPI;
    @Inject
    public PostRemoteService(PostAPI postAPI) {
        this.postAPI = postAPI;
    }
    public Observable<List<Post>> getAllPost(){
        return postAPI.getAllPost();
    }
}
