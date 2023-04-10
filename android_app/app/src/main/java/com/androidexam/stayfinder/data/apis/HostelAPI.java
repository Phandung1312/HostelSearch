package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface HostelAPI {
    @GET("hostel")
    Observable<Content> getAllHostel(
    );
    @GET("hostel")
    Observable<Hostel> getHostelById(
            @Field("id") String id
    );
}
