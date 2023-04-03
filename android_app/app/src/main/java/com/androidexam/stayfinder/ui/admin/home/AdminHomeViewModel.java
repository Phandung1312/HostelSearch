package com.androidexam.stayfinder.ui.admin.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.repositories.HostelRepository;
import com.androidexam.stayfinder.data.repositories.PostRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdminHomeViewModel extends BaseViewModel {
    HostelRepository hostelRepository;
    MutableLiveData<List<Hostel>> hostels;
    @Inject
    public AdminHomeViewModel(HostelRepository hostelRepository){
        this.hostelRepository = hostelRepository;
    }
    public MutableLiveData<List<Hostel>> loadData(){return this.hostels;}
    public void setPostData(){
        hostelRepository.getAllHostel()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Hostel>>() {
                    @Override
                    public void onNext(@NonNull List<Hostel> hostelList) {
                        Log.d("DEBUG","Success");
                        hostels.postValue(hostelList);
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG","Fail" + e.getMessage());
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }
}
