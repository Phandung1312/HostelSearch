package com.androidexam.stayfinder.ui.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.common.ImageConvertResult;
import com.androidexam.stayfinder.data.apis.AccountAPI;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.repositories.AccountRepository;
import com.androidexam.stayfinder.databinding.LoginClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends BaseViewModel {
    AccountRepository accountRepository;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<Account> data = new MutableLiveData<>();
    MutableLiveData<Boolean> isNew = new MutableLiveData<>();
    @Inject
    public LoginViewModel(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public void setData(Account account){
        if(isNew.getValue()){
            compositeDisposable.add(accountRepository.getAccountBySignUp(account)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(acc -> {
                                data.postValue(account);
                            }
                    ));
        }
        else{
            compositeDisposable.add(accountRepository.getAccountByLogin(account.getAccountName(), account.getPassword())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(acc -> {
                                data.postValue(account);
                            }
                    ));
        }
    }
    public LiveData<Account> loadData(){
        return this.data;
    }
    public void firebaseSignInWithGoogle(String email, String password){
        isNew = accountRepository.firebaseSignInWithGoogle(email, password);
    }
    public LiveData<Boolean> isNewAccount(){
        return isNew;
    }
    public void convertUrlToByteArr(Context context, String url, ImageConvertResult<byte[]> imageConvertResult){
           Glide.with(context)
                   .asBitmap()
                   .load(url)
                   .into(new CustomTarget<Bitmap>() {
                       @Override
                       public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                           ByteArrayOutputStream stream = new ByteArrayOutputStream();
                           resource.compress(Bitmap.CompressFormat.PNG, 100, stream);
                           byte[] byteArray = stream.toByteArray();
                           imageConvertResult.onSuccess(byteArray);
                       }

                       @Override
                       public void onLoadCleared(@Nullable Drawable placeholder) {
                           imageConvertResult.onError();
                       }
                   });
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
