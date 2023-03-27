package com.androidexam.stayfinder.data.apis;


import com.androidexam.stayfinder.data.models.Account;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AccountAPI {

    @POST("url.php")
    Observable<Account> getAccountByLogin(
            @Field("username") String username,
            @Field("password") String password
    );
    @GET("url.php")
    Observable<Account> getAccountBySignUp(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name,
            @Field("avatar") byte[] avatar
    );
}
