package com.androidexam.stayfinder.data.repositories;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.services.AccountRemoteService;
import com.google.firebase.auth.FirebaseAuth;
import javax.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

public class AccountRepository {
    AccountRemoteService accountRemoteService;
    FirebaseAuth firebaseAuth;
    @Inject
    public AccountRepository(AccountRemoteService accountRemoteService, FirebaseAuth firebaseAuth) {
        this.accountRemoteService = accountRemoteService;
        this.firebaseAuth = firebaseAuth;
    }
    public Observable<Account> getAccountByLogin(String email, String password){
        return accountRemoteService.getAccountByLogin(email,password);
    }
    public Observable<Account> getAccountBySignUp(Account account){
        return accountRemoteService.getAccountBySignUp(account);
    }
    public MutableLiveData<Boolean> firebaseSignInWithGoogle(String email, String password){
        MutableLiveData<Boolean> isNew = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        isNew.setValue(false);
                    }
                    else{
                        isNew.setValue(true);
                        firebaseAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(task2 ->{
                                    if(task.isSuccessful()){
                                        Log.d("Check sign up","OK");
                                    }
                                    else{
                                        Log.d("Check sign up","Not OK");
                                    }
                                });
                    }
                });
        return isNew;
    }

}
