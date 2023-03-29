package com.androidexam.stayfinder.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public  class FirebaseModule {

    @Provides
    public FirebaseDatabase providesFirebaseDatabase(){
        return FirebaseDatabase.getInstance();
    }
    @Provides
    @Singleton
    public FirebaseAuth providesFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }
}
