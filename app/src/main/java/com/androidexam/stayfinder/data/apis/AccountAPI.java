package com.androidexam.stayfinder.data.apis;

import android.database.Observable;

import com.androidexam.stayfinder.data.models.Account;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountAPI {

    @POST("url")
    Observable<Account> getAccountByLogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Field("avatar") byte[] avatar

    );
}
