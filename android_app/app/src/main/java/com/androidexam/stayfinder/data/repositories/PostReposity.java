package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.PostRequest;
import com.androidexam.stayfinder.data.services.PostRemoteService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;

public class PostReposity {
    PostRemoteService postRemoteService;

    @Inject
    public PostReposity(PostRemoteService postRemoteService) {
        this.postRemoteService = postRemoteService;
    }
    public Observable<List<Post>> getAllPost(){
        return postRemoteService.getAllPost();
    }

    public Observable<List<Post>> getPostByAccountName(String accountName){
        return postRemoteService.getPostByAccountName(accountName);
    }
    public Observable<List<PostRequest>> getPostByAccountNameAndStatus(String accountName, int status){
        return postRemoteService.getPostByAccountNameAndStatus(accountName, status);
    }

}
