package com.androidexam.stayfinder.ui.client;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.repositories.HostelRepository;
import com.androidexam.stayfinder.data.repositories.PostRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ClientHomeViewModel extends BaseViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    HostelRepository hostelRepository;
    PostRepository postRepository;
    MutableLiveData<List<Post>> posts = new MutableLiveData<>();
    @Inject
    public ClientHomeViewModel(HostelRepository hostelRepository,PostRepository postRepository){
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
        compositeDisposable.add(hostelRepository.searchHostel(address, areaMin, areaMax, minRent, maxRent, capacity, idRoomType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(content->{
                    hostelSearch.setValue(content.getContent());
                },throwable -> {
                    Log.d("check search post failure ",throwable.getMessage());
                }));
        return hostelSearch;
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
