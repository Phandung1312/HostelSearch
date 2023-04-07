package com.androidexam.stayfinder.ui.schedule;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.repositories.ScheduleReposity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewScheduleViewModel extends BaseViewModel {
    ScheduleReposity scheduleReposity;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<List<Schedule>> listSchedule = new MutableLiveData<>();

    @Inject
    public ViewScheduleViewModel(ScheduleReposity scheduleReposity) {
        this.scheduleReposity = scheduleReposity;
    }
    public void GetAllScheduleByAccountName(String accountName){
        compositeDisposable.add(scheduleReposity.getScheduleByAccountName(accountName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lst -> {
                            listSchedule.postValue(lst);
                        },
                        throwable ->{
                            Log.d("ERROR get all schedules of user (ViewSCheduleViewModel class)",throwable.getMessage());
                        }
                ));
    }
    public LiveData<List<Schedule>> loadListSchedule(){
        return this.listSchedule;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}
