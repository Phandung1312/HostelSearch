package com.androidexam.stayfinder.di;


import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;


@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    public GoogleSignInOptions providesGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    }
    @Provides
    public static GoogleSignInClient providesGoogleSignInClient(@ApplicationContext Context context, GoogleSignInOptions gso){
        return GoogleSignIn.getClient(context, gso);
    }
}
