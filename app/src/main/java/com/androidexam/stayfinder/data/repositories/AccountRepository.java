package com.androidexam.stayfinder.data.repositories;

import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.services.AccountRemoteService;

import javax.inject.Inject;

public class AccountRepository {
    private AccountRemoteService accountRemoteService;

    @Inject
    public AccountRepository(AccountRemoteService accountRemoteService) {
        this.accountRemoteService = accountRemoteService;
    }
    public MutableLiveData<Account> getAccountByLogin(Account account){

    }
}
