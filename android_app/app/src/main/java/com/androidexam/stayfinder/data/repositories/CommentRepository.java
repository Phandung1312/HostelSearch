package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Comment;
import com.androidexam.stayfinder.data.models.request.CommentRequest;
import com.androidexam.stayfinder.data.services.CommentRemoteService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class CommentRepository {
    CommentRemoteService commentRemoteService;
    @Inject
    public CommentRepository(CommentRemoteService commentRemoteService){
        this.commentRemoteService = commentRemoteService;
    }
    public Observable<List<Comment>> getCommentByPostId(int postId){
        return commentRemoteService.getCommentByPostId(postId);
    }
    public Observable<Boolean> deleteCommentById(int id){return  commentRemoteService.deleteCommentById(id);}
    public Observable<Comment> addComment(CommentRequest commentRequest){return commentRemoteService.addComment(commentRequest);}
}
