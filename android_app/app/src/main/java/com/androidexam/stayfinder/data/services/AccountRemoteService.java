package com.androidexam.stayfinder.data.services;

import com.androidexam.stayfinder.data.apis.AccountAPI;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.request.LoginRequest;
import com.androidexam.stayfinder.data.models.request.SignUpRequest;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDisposeOn;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AccountRemoteService {
    AccountAPI accountAPI;

    @Inject
    public AccountRemoteService(AccountAPI accountAPI) {
        this.accountAPI = accountAPI;
    }

    public Observable<Account> getAccountByLogin(String email, String password) {
        return accountAPI.getAccountByLogin(
                new LoginRequest(email, password));
    }

    public Observable<Account> getAccountBySignUp(SignUpRequest signUpRequest) {
        if (signUpRequest.getFile() != null) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), signUpRequest.getFile());
            MultipartBody.Part body = MultipartBody.Part.createFormData("file", signUpRequest.getFile().getName(),
                    requestFile);
            RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), signUpRequest.getUsername());
            RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), signUpRequest.getPassword());
            RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), signUpRequest.getName());
            return accountAPI.getAccountBySignUp(
                    usernameBody,
                    passwordBody,
                    nameBody,
                    body);
        } else {
            return accountAPI.getAccountBySignUp(
                    signUpRequest.getUsername(),
                    signUpRequest.getPassword(),
                    signUpRequest.getName());
        }
    }
    public Observable<Account> updateUsername(String accountName, String newUserName){
        return accountAPI.updateUsername(accountName, newUserName);
    }
}
