package com.androidexam.stayfinder.data.apis;


import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.request.LoginRequest;
import com.androidexam.stayfinder.data.models.request.SignUpRequest;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface AccountAPI {

    @POST("account/login")
    Observable<Account> getAccountByLogin(
            @Body LoginRequest loginRequest
            );
    @POST("account")
    @Multipart
    @FormUrlEncoded
    Observable<Account> getAccountBySignUp(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Part MultipartBody.Part file
            );
    @POST("account")
    @FormUrlEncoded
    Observable<Account> getAccountBySignUp(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name
    );
}
