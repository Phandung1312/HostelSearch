package com.androidexam.stayfinder.ui.setting;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.repositories.AccountRepository;


import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class SettingViewModel extends BaseViewModel {
    AccountRepository accountRepository;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<Account> account = new MutableLiveData<Account>();

    @Inject
    public SettingViewModel(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public void UpdateUsername(String accountName, String newUsername){
        compositeDisposable.add(accountRepository.updateUsername(accountName, newUsername)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(acc -> {
                            account.postValue(acc);
                        },
                        throwable ->{
                            Log.d("ERROR update username of user (SettingViewModel class)",throwable.getMessage());
                        }
                ));
    }
    public LiveData<Account> loadAccount(){
        return this.account;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

}
