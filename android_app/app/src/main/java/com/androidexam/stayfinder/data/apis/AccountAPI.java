package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.request.LoginRequest;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface AccountAPI {

        @POST("account/login")
        Observable<Account> getAccountByLogin(
                        @Body LoginRequest loginRequest);

        @POST("account")
        @Multipart
        Observable<Account> getAccountBySignUp(
                        @Part("username") RequestBody username,
                        @Part("password") RequestBody password,
                        @Part("name") RequestBody name,
                        @Part MultipartBody.Part file);

        @POST("account")
        @FormUrlEncoded
        Observable<Account> getAccountBySignUp(
                        @Field("username") String username,
                        @Field("password") String password,
                        @Field("name") String name);

        @PUT("account")
        @FormUrlEncoded
        Observable<Account> updateUsername(
                        @Field("username") String accountName,
                        @Field("name") String newUserName);
        @PUT("account")
        @FormUrlEncoded
        Observable<Account> updateGender(
                        @Field("username") String accountName,
                        @Field("gender") boolean newGender);
        @PUT("account")
        @FormUrlEncoded
        Observable<Account> updatePhoneNumber(
                        @Field("username") String accountName,
                        @Field("phonenumber") String newPhoneNumber);
}
