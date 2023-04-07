package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Schedule;
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

    public Observable<List<Schedule>> getScheduleByAccountName(String accountName){
        return scheduleRemoteService.getScheduleByAccountName(accountName);
    }

}
