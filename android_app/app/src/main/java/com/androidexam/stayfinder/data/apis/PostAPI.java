package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.PostRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Query;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostAPI {
        @GET("post")
        Observable<List<Post>> getAllPost();

        @GET("post/{postId}")
        Observable<List<Post>> getPostById(
                        @Path("postId") Long postId);

        @GET("post/account-post/{accountName}")
        Observable<List<Post>> getPostByAccountName(
                        @Path("accountName") String accountName);

        @GET("post/account/{accountName}")
        Observable<List<PostRequest>> getPostByAccountNameAndStatus(
                        @Path("accountName") String accountName,
                        @Query("status") int status);

        @PUT("post/{postId}/status/{status}")
        Observable<Boolean> changeStatusPost(
                        @Path("postId") int postId,
                        @Path("status") int status);
}
