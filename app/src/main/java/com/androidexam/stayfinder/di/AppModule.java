package com.androidexam.stayfinder.di;

import android.app.Application;
import android.content.Context;

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
}
