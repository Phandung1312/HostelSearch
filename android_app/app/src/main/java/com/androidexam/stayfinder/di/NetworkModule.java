package com.androidexam.stayfinder.di;



import com.androidexam.stayfinder.common.Utils;
import com.androidexam.stayfinder.data.apis.AccountAPI;
import com.androidexam.stayfinder.data.apis.PostAPI;
import com.androidexam.stayfinder.data.apis.ScheduleAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Singleton
    @Provides
    public Retrofit providesRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Utils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }
    @Provides
    public  AccountAPI providesAccountAPI(Retrofit retrofit){
        return retrofit.create(AccountAPI.class);
    }
    @Provides
    public PostAPI providesPostAPI(Retrofit retrofit){
        return retrofit.create(PostAPI.class);
    }
    @Provides
    public ScheduleAPI providesScheduleAPI(Retrofit retrofit){
        return retrofit.create(ScheduleAPI.class);
    }
}
