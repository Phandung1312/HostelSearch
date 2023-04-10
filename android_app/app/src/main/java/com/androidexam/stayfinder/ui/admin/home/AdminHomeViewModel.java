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
    MutableLiveData<List<Hostel>> hostels = new MutableLiveData<>();
    @Inject
    public AdminHomeViewModel(HostelRepository hostelRepository){
        this.hostelRepository = hostelRepository;
    }
    public LiveData<List<Hostel>> loadData(){
        return this.hostels;
    }
    public void setPostData(){
        compositeDisposable.add(hostelRepository.getAllHostel()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(content -> {
                    hostels.setValue(content.getContent());
                },throwable -> {
                    Log.d("KiemTra 1",throwable.getMessage());
                }));
    }
    public int getSize(){
        return hostels.getValue().size();
    }

}
