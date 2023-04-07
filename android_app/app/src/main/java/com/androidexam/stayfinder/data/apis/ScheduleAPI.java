package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Schedule;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ScheduleAPI {
    @POST("schedules/account/")
    Observable<List<Schedule>> getScheduleByAccountName(
            @Body String accountName
    );
}
