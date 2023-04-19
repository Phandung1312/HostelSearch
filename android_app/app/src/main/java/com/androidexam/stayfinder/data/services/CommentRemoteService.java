package com.androidexam.stayfinder.data.services;

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
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"),commentRequest.getFile());
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", commentRequest.getFile().getName(),
                    requestFile);
            RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), commentRequest.getUsername());
            RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), commentRequest.getContent());
            String convertPostId = Integer.toString(commentRequest.getPostId());
            RequestBody postIdBody = RequestBody.create(MediaType.parse("text/plain"), convertPostId);
            return commentAPI.addComment(
                    usernameBody,
                    contentBody,
                    postIdBody,
                    body
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
