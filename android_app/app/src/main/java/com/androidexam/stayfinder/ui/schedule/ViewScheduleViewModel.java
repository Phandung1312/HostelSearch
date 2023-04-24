package com.androidexam.stayfinder.ui.schedule;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.models.request.ScheduleRequest;
import com.androidexam.stayfinder.data.repositories.ScheduleReposity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ViewScheduleViewModel extends BaseViewModel {
    ScheduleReposity scheduleReposity;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<List<ScheduleRequest>> listOwnerSchedule = new MutableLiveData<>();
    MutableLiveData<List<ScheduleRequest>> listRenterSchedule = new MutableLiveData<>();

    @Inject
    public ViewScheduleViewModel(ScheduleReposity scheduleReposity) {
        this.scheduleReposity = scheduleReposity;
    }
    public void GetAllScheduleByAccountName(String accountName){
        compositeDisposable.add(scheduleReposity.getScheduleByOwnerAccountName(accountName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lst -> {
                            listRenterSchedule.postValue(lst);
                        },
                        throwable ->{
                            Log.d("ERROR get all owner schedules of user (ViewSCheduleViewModel class)",throwable.getMessage());
                        }
                ));
        compositeDisposable.add(scheduleReposity.getScheduleByRenterAccountName(accountName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lst -> {
                            listOwnerSchedule.postValue(lst);
                        },
                        throwable ->{
                            Log.d("ERROR get all renter schedules of user (ViewSCheduleViewModel class)",throwable.getMessage());
                        }
                ));
    }
    public LiveData<List<ScheduleRequest>> loadListOwnerSchedule(){
        return this.listOwnerSchedule;
    }
    public LiveData<List<ScheduleRequest>> loadListRenterSchedule(){
        return this.listRenterSchedule;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}
