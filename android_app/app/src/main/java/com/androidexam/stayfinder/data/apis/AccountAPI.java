package com.androidexam.stayfinder.data.apis;


import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.request.LoginRequest;
import com.androidexam.stayfinder.data.models.request.SignUpRequest;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountAPI {

    @POST("account/login")
    Observable<Account> getAccountByLogin(
            @Body LoginRequest loginRequest
            );
    @POST("account")
    Observable<Account> getAccountBySignUp(
            @Body SignUpRequest signUpRequest
            );
}
