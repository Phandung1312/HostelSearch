package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.Comment;
import com.androidexam.stayfinder.data.models.request.CommentRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommentAPI {
    @GET("comment/post/{postId}")
    Observable<List<Comment>> getCommentByPostId(
            @Path("postId")  int postId
    );
    @DELETE("comment/{id}")
    Observable<Boolean> deleteCommentById(
            @Path("id") int id
    );
    @POST("comment")
    Observable<Comment> addComment(
            @Body MultipartBody body
            );
    @POST("comment")
    @FormUrlEncoded
    Observable<Comment> addComment(
            @Field("username") String username,
            @Field("content") String content,
            @Field("postId") int postId
    );
}
