package com.androidexam.stayfinder.di;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    public Context providesContext(Application application){
        return application.getApplicationContext();
    }
    //API google
    @Provides
    public GoogleSignInOptions providesGoogleSignInOptions(){
        return new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
    }
    @Provides
    @Singleton
    public GoogleSignInClient providesGoogleSignInClient(Context context, GoogleSignInOptions gso){
        return GoogleSignIn.getClient(context, gso);
    }
    @Provides
    public Gson providesGson(){
        return new GsonBuilder().create();
    }
}
