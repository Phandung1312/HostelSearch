package com.androidexam.stayfinder.ui.admin.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.repositories.HostelRepository;
import com.androidexam.stayfinder.data.repositories.PostRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AdminHomeViewModel extends BaseViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    HostelRepository hostelRepository;
    MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    PostRepository postRepository;
    @Inject
    public AdminHomeViewModel(HostelRepository hostelRepository,PostRepository postRepository){
        this.hostelRepository = hostelRepository;
        this.postRepository = postRepository;
    }
    public LiveData<List<Post>> loadData(){
        return this.posts;
    }
    public void setPostData(){
        compositeDisposable.add(postRepository.getAllPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postList -> {
                    posts.setValue(postList);
                },throwable -> {
                    Log.d("check set post failure",throwable.getMessage());
                }));
    }
    public int getSize(){
        return posts.getValue().size();
    }
    public LiveData<List<Hostel>> searchData(String address , int areaMin, int areaMax, int minRent, int maxRent, int capacity, int idRoomType){
        MutableLiveData<List<Hostel>> hostelSearch = new MutableLiveData<>();
        compositeDisposable.add(hostelRepository.searchHostelAdmin(address, areaMin, areaMax, minRent, maxRent, capacity, idRoomType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(content->{
                    hostelSearch.setValue(content.getContent());
                },throwable -> {
                    Log.d("check search post failure ",throwable.getMessage());
                }));
        return hostelSearch;
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
    public LiveData<Hostel> getHostelById(int hostelId){
        MutableLiveData<Hostel> hostel = new MutableLiveData<>();
        compositeDisposable.add(hostelRepository.getHostelById(hostelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response->{
                        hostel.setValue(response);
                },throwable -> {
                    Log.d("Check error getHostelId",throwable.getMessage());
                }));
        return hostel;
    }
}
