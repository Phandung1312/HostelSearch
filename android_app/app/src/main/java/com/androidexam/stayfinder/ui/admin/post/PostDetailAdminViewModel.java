package com.androidexam.stayfinder.ui.admin.post;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Comment;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.models.request.CommentRequest;
import com.androidexam.stayfinder.data.models.request.FavouriteRequest;
import com.androidexam.stayfinder.data.repositories.CommentRepository;
import com.androidexam.stayfinder.data.repositories.HostelRepository;
import com.androidexam.stayfinder.data.repositories.PostRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PostDetailAdminViewModel extends BaseViewModel {
    HostelRepository hostelRepository;
    CommentRepository commentRepository;
    PostRepository postRepository;
    private MutableLiveData<Hostel> hostel =  new MutableLiveData<>();
    private MutableLiveData<List<Comment>> comments = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    public PostDetailAdminViewModel(HostelRepository hostelRepository, CommentRepository commentRepository,PostRepository postRepository){
        this.hostelRepository = hostelRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }
    public void setHostelData(String id) {
        compositeDisposable.add(hostelRepository.getHostelById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hostelData -> {
                    hostel.setValue(hostelData);
                },throwable -> {
                    Log.d("KiemTra 1",throwable.getMessage());
                }));
    }
    public MutableLiveData<Hostel> loadHostel(){
        return hostel;
    }
    public void setCommentData(int postId){
        compositeDisposable.add(commentRepository.getCommentByPostId(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commentsData -> {
                    comments.setValue(commentsData);
                },throwable -> {
                    Log.d("KiemTra 1",throwable.getMessage());
                }));
    }
    public MutableLiveData<List<Comment>> loadComment(){return comments;}
    public LiveData<Comment> sendComment(CommentRequest commentRequest){
        MutableLiveData<Comment> comment = new MutableLiveData<>();
        compositeDisposable.add(commentRepository.addComment(commentRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    comment.postValue(data);
                },throwable -> {
                    Log.d("Add comment failure",throwable.getMessage());
                }));
        return comment;
    }
    public LiveData<Boolean> deleteComment(int id){
        MutableLiveData<Boolean> isCorrect = new MutableLiveData<>();
        compositeDisposable.add(commentRepository.deleteCommentById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    isCorrect.postValue(response);
                },throwable -> {
                    Log.d("Check error checkFavouriteHostel",throwable.getMessage());
                }));
        return isCorrect;
    }
    public LiveData<Boolean> changeStatusPost(int postId, int status){
        MutableLiveData<Boolean> isCorrect = new MutableLiveData<>();
        compositeDisposable.add(postRepository.changeStatusPost(postId, status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    isCorrect.postValue(response);
                },throwable -> {
                    Log.d("Check error changeStatusPost",throwable.getMessage());
                }));
        return isCorrect;
    }
    public LiveData<Boolean> checkFavourite(String username, int postId){
        MutableLiveData<Boolean> isCorrect = new MutableLiveData<>();
        compositeDisposable.add(hostelRepository.checkFavourite(username,postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                    isCorrect.postValue(response);
                },throwable -> {
                    Log.d("Check error check favourite",throwable.getMessage());
                }));
        return isCorrect;
    }
    public LiveData<Boolean> changeStatusFavourite(String username, int postId, boolean status){
        MutableLiveData<Boolean> isCorrect = new MutableLiveData<>();
        if(status == true){
            compositeDisposable.add(hostelRepository.addFavouriteHostel(username,postId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response->{
                        isCorrect.postValue(response);
                    },throwable -> {
                        Log.d("Check error add favourite",throwable.getMessage());
                    }));
        }else{
            compositeDisposable.add(hostelRepository.unFavouriteHostel(username,postId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response->{
                        isCorrect.postValue(response);
                    },throwable -> {
                        Log.d("Check error un favourite",throwable.getMessage());
                    }));
        }
        return isCorrect;
    }
 }
