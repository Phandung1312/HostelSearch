package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.models.request.ScheduleRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ScheduleAPI {
    @GET("schedules/account/{accountName}")
    Observable<List<ScheduleRequest>> getScheduleByOwnerAccountName(
            @Path("accountName") String accountName
    );
    @GET("schedules/account-renter/{accountName}")
    Observable<List<ScheduleRequest>> getScheduleByRenterAccountName(
            @Path("accountName") String accountName
    );
}
