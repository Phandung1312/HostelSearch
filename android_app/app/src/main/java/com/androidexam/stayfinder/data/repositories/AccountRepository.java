package com.androidexam.stayfinder.data.repositories;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.data.models.firebase.UserFirebase;
import com.androidexam.stayfinder.data.models.request.SignUpRequest;
import com.androidexam.stayfinder.data.services.AccountRemoteService;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;
import io.reactivex.rxjava3.core.Observable;

public class AccountRepository {
    AccountRemoteService accountRemoteService;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    @Inject
    public AccountRepository(AccountRemoteService accountRemoteService,
                             FirebaseAuth firebaseAuth,
                             FirebaseDatabase firebaseDatabase) {
        this.accountRemoteService = accountRemoteService;
        this.firebaseAuth = firebaseAuth;
        this.firebaseDatabase = firebaseDatabase;
    }
    public Observable<Account> getAccountByLogin(String email, String password){
        return accountRemoteService.getAccountByLogin(email,password);
    }
    public Observable<Account> getAccountBySignUp(SignUpRequest signUpRequest){
        return accountRemoteService.getAccountBySignUp(signUpRequest);
    }
    public MutableLiveData<Boolean> firebaseSignInWithGoogle(GoogleSignInAccount googleSignInAccount){
        MutableLiveData<Boolean> isNew = new MutableLiveData<>();
        firebaseAuth.signInWithEmailAndPassword(googleSignInAccount.getEmail(),
                        googleSignInAccount.getId())
                .addOnCompleteListener(loginTask -> {
                    if(loginTask.isSuccessful()){
                        isNew.setValue(false);
                    }
                    else{
                        isNew.setValue(true);
                        firebaseAuth.createUserWithEmailAndPassword(googleSignInAccount.getEmail(),
                                        googleSignInAccount.getId())
                                .addOnCompleteListener(signUpTask ->{
                                    if(signUpTask.isSuccessful()){
                                        FirebaseUser mUser = firebaseAuth.getCurrentUser();
                                        DatabaseReference reference = firebaseDatabase.getReference("User")
                                                .child(mUser.getUid());
                                        String imageUrl = googleSignInAccount.getPhotoUrl() != null ? googleSignInAccount.getPhotoUrl().toString() : null;
                                        UserFirebase userFirebase = new UserFirebase(googleSignInAccount.getEmail(),
                                                googleSignInAccount.getDisplayName(),
                                                mUser.getUid(),
                                                imageUrl);
                                        reference.setValue(userFirebase).addOnCompleteListener(userTask ->{
                                           if(userTask.isSuccessful()){
                                               Log.d("Check user task","OK");
                                           }
                                           else {
                                               Log.d("Check user task","Not ok");
                                           }
                                        });
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
