package com.androidexam.stayfinder.ui.favourite;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.repositories.HostelRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class FavouriteViewModel extends BaseViewModel {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    HostelRepository hostelRepository;
    MutableLiveData<List<Hostel>> hostels = new MutableLiveData<>();
    @Inject
    public FavouriteViewModel(HostelRepository hostelRepository) {
        this.hostelRepository = hostelRepository;
    }
    public LiveData<List<Hostel>> getHostel(String username){
        compositeDisposable.add(hostelRepository.getFavouriteHostels(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( response -> {
                    hostels.postValue(response);
                },throwable -> {
                    Log.d("Check error favourite",throwable.getMessage());
                }));
        return this.hostels;
    }
    public void addFavouriteHostel(String username, int postId){
        compositeDisposable.add(hostelRepository.addFavouriteHostel(username, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                    Log.d("Status add favourite", response.toString());
                },throwable -> {
                    Log.d("Check error add favourite",throwable.getMessage());
                }));
    }
    public void unFavouriteHostel(String username, int postId){
        compositeDisposable.add(hostelRepository.unFavouriteHostel(username, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                    Log.d("Status un favourite", response.toString());
                },throwable -> {
                    Log.d("Check error unFavouriteHostel",throwable.getMessage());
                }));
    }
    public  LiveData<Boolean> checkFavouriteHostel(String username, int postId){
        MutableLiveData<Boolean> isCorrect = new MutableLiveData<>();
        compositeDisposable.add(hostelRepository.checkFavourite(username, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response ->{
                    isCorrect.postValue(response);
                },throwable -> {
                    Log.d("Check error checkFavouriteHostel",throwable.getMessage());
                }));
        return isCorrect;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
