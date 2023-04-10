package com.androidexam.stayfinder.data.services;

import com.androidexam.stayfinder.data.apis.ScheduleAPI;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.models.request.ScheduleRequest;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRemoteService {
    ScheduleAPI scheduleAPI;
    @Inject
    public ScheduleRemoteService(ScheduleAPI scheduleAPI){
        this.scheduleAPI = scheduleAPI;
    }
    public Observable<List<ScheduleRequest>> getScheduleByOwnerAccountName(String accountName){
        return scheduleAPI.getScheduleByOwnerAccountName(
                accountName
        );
    }
    public Observable<List<ScheduleRequest>> getScheduleByRenterAccountName(String accountName){
        return scheduleAPI.getScheduleByRenterAccountName(
                accountName
        );
    }
}
