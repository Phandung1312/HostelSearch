package com.androidexam.stayfinder.data.services;



import com.androidexam.stayfinder.data.apis.AccountAPI;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.request.LoginRequest;
import com.androidexam.stayfinder.data.models.request.SignUpRequest;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.internal.operators.completable.CompletableDisposeOn;

public class AccountRemoteService {
    AccountAPI accountAPI;

    @Inject
    public AccountRemoteService(AccountAPI accountAPI) {
        this.accountAPI = accountAPI;
    }
    public Observable<Account> getAccountByLogin(String email, String password){
        return accountAPI.getAccountByLogin(
                new LoginRequest(email, password)
        );
    }
    public Observable<Account> getAccountBySignUp(Account account){
        return accountAPI.getAccountBySignUp(
                new SignUpRequest(account.getAccountName(),
                        account.getPassword(),
                        account.getUserName(),
                        account.getAvatar())
        );
    }
}
