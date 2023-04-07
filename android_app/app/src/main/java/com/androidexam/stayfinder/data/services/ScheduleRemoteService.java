package com.androidexam.stayfinder.data.services;

import com.androidexam.stayfinder.data.apis.ScheduleAPI;
import com.androidexam.stayfinder.data.models.Schedule;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class ScheduleRemoteService {
    ScheduleAPI scheduleAPI;
    public Observable<List<Schedule>> getScheduleByAccountName(String accountName){
        return scheduleAPI.getScheduleByAccountName(
                accountName
        );
    }
}
