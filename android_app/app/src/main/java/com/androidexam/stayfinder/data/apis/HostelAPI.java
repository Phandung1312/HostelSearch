package com.androidexam.stayfinder.data.apis;

import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.FavouriteRequest;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface HostelAPI {
    @GET("hostel")
    Observable<Content> getAllHostel();

    @GET("hostel")
    Observable<Hostel> getHostelById(
            @Field("id") String id
    );
    @GET("favourites/hostels")
    Observable<List<Hostel>> getFavouriteHostels(
      @Query("username") String username
    );
    @POST("favourites/unfavourites")
    Observable<Boolean> unFavouriteHostel(
            @Body FavouriteRequest favourite
    );
    @POST("favourites")
    Observable<Boolean> addFavouriteHostel(
            @Body FavouriteRequest favourite
            );
    @GET("favourites/check")
    Observable<Boolean> checkFavourite(
      @Query("username") String username,
      @Query("postId") int postId
    );
}
