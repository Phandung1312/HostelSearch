package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.services.AccountRemoteService;
import com.androidexam.stayfinder.data.services.PostRemoteService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PostRepository {
    PostRemoteService postRemoteService;
    @Inject
    public PostRepository(PostRemoteService postRemoteService) {
        this.postRemoteService = postRemoteService;
    }
    public Observable<List<Post>> getAllPost(){
        return postRemoteService.getAllPost();
    }
}
