package com.androidexam.stayfinder.data.services;

import static okhttp3.MultipartBody.*;

import android.util.Log;

import com.androidexam.stayfinder.data.apis.CommentAPI;
import com.androidexam.stayfinder.data.models.Comment;
import com.androidexam.stayfinder.data.models.request.CommentRequest;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CommentRemoteService {
    CommentAPI commentAPI;
    @Inject
    public CommentRemoteService(CommentAPI commentAPI){ this.commentAPI = commentAPI;}
    public Observable<List<Comment>> getCommentByPostId(int postId) {return commentAPI.getCommentByPostId(postId);}
    public Observable<Boolean> deleteCommentById(int id){return  commentAPI.deleteCommentById(id);}
    public Observable<Comment> addComment(CommentRequest commentRequest){
        if(commentRequest.getFile() != null){
            MultipartBody multipartBody = new Builder()
                    .setType(FORM)
                    .addFormDataPart("username", commentRequest.getUsername())
                    .addFormDataPart("content", commentRequest.getContent())
                    .addFormDataPart("postId", String.valueOf(commentRequest.getPostId()))
                    .addFormDataPart("file", commentRequest.getFile().getName(), RequestBody.create(MediaType.parse("image/*"),commentRequest.getFile()))
                    .build();

            return commentAPI.addComment(
                   multipartBody
            );
        }else{
            return commentAPI.addComment(
                    commentRequest.getUsername(),
                    commentRequest.getContent(),
                    commentRequest.getPostId()
            );
        }

    }
}
