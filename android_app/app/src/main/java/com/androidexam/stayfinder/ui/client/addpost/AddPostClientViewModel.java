package com.androidexam.stayfinder.ui.client.addpost;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.request.ImageRequest;
import com.androidexam.stayfinder.data.repositories.HostelRepository;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class AddPostClientViewModel extends BaseViewModel {
    HostelRepository hostelRepository;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Inject
    public AddPostClientViewModel(HostelRepository hostelRepository){
        this.hostelRepository = hostelRepository;
    }
    public LiveData<Hostel> addHostelAndPost(String name, int capacity,double area,String address,double rentPrice,double depositPrice, int status, String description, int roomTypeId, double electricPrice,double waterPrice, int accountId, String title ,String content){
        MutableLiveData<Hostel> hostel = new MutableLiveData<>();
        compositeDisposable.add(hostelRepository.addHostelAndPost(name, capacity, area, address, rentPrice, depositPrice, status, description, roomTypeId, electricPrice, waterPrice, accountId, title, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hostelData -> {
                    hostel.postValue(hostelData);
                },throwable -> {
                    Log.d("check add hostel and post",throwable.getMessage());
                }));
        return hostel;
    }
    public LiveData<ArrayList<ImageRequest>> addImages(int hostelId, ArrayList<File> files){
        MutableLiveData<ArrayList<ImageRequest>> imageRequest = new MutableLiveData<>();
        compositeDisposable.add(hostelRepository.addImages(hostelId,files)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data->{
                    Log.d("check add images 1",data.get(0).getUrl())                                                                                                                                                                                                                             ;
                    imageRequest.postValue(data);
                },throwable -> {
                    Log.d("check add images",throwable.getMessage());
                }));
        return imageRequest;
    }
}
