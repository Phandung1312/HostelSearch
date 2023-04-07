package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostAPI {
    @POST("account/post/account-post")
    Observable<List<Post>> getPostByAccountName(
            @Body String accountName
    );
    @POST("post/account/")
    Observable<List<Post>> getPostByAccountNameAndStatus(
            @Body String accountName,
            @Body int status
    );
}