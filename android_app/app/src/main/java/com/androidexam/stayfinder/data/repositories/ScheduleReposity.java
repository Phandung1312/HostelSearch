package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.models.request.ScheduleRequest;
import com.androidexam.stayfinder.data.services.ScheduleRemoteService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleReposity {
    ScheduleRemoteService scheduleRemoteService;

    @Inject
    public ScheduleReposity(ScheduleRemoteService scheduleRemoteService) {
        this.scheduleRemoteService = scheduleRemoteService;
    }

    public Observable<List<ScheduleRequest>> getScheduleByOwnerAccountName(String accountName){
        return scheduleRemoteService.getScheduleByOwnerAccountName(accountName);
    }
    public Observable<List<ScheduleRequest>> getScheduleByRenterAccountName(String accountName){
        return scheduleRemoteService.getScheduleByRenterAccountName(accountName);
    }

}
