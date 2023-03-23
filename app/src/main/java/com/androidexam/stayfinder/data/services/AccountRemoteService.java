package com.androidexam.stayfinder.data.services;

import android.database.Observable;

import com.androidexam.stayfinder.data.apis.AccountAPI;
import com.androidexam.stayfinder.data.models.Account;

import javax.inject.Inject;

public class AccountRemoteService {
    private AccountAPI accountAPI;

    @Inject
    public AccountRemoteService(AccountAPI accountAPI) {
        this.accountAPI = accountAPI;
    }
    public Observable<Account> getAccountByLogin(Account account){
        return accountAPI.getAccountByLogin(
                account.getAccountName(),
                account.getPassword(),
                account.getUserName(),
                account.getAvatar()
        );
    }
}
