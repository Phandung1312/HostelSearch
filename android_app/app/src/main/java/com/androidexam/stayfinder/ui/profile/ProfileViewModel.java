package com.androidexam.stayfinder.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.repositories.PostReposity;
import com.androidexam.stayfinder.data.repositories.ScheduleReposity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfileViewModel extends BaseViewModel {
    PostReposity postReposity;
    ScheduleReposity scheduleReposity;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<List<Post>> listPost = new MutableLiveData<>();
    MutableLiveData<List<Schedule>> listSchedule = new MutableLiveData<>();

    @Inject
    public ProfileViewModel(PostReposity postReposity, ScheduleReposity scheduleReposity) {
        this.postReposity = postReposity;
        this.scheduleReposity = scheduleReposity;
    }
    public void GetAllPostByAccountName(String accountName){
        compositeDisposable.add(postReposity.getPostByAccountName(accountName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lst -> {
                            listPost.postValue(lst);
                        },
                        throwable ->{
                            Log.d("ERROR get all posts of user (ProfileViewModel class)",throwable.getMessage());
                        }
                ));
    }
    public void GetAllScheduleByAccountName(String accountName){
        compositeDisposable.add(scheduleReposity.getScheduleByAccountName(accountName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lst -> {
                            listSchedule.postValue(lst);
                        },
                        throwable ->{
                            Log.d("ERROR get all schedules of user (ProfileViewModel class)",throwable.getMessage());
                        }
                ));
    }
    public LiveData<List<Schedule>> loadListSchedule(){
        return this.listSchedule;
    }
    public LiveData<List<Post>> loadListPost(){
        return this.listPost;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}
