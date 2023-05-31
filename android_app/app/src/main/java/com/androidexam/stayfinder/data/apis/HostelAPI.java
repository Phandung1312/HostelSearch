package com.androidexam.stayfinder.data.apis;

import androidx.annotation.RawRes;

import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.FavouriteRequest;
import com.androidexam.stayfinder.data.models.request.HostelRequest;
import com.androidexam.stayfinder.data.models.request.ImageRequest;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HostelAPI {
    @GET("hostel")
    Observable<Content> getAllHostel();

    @GET("hostel/{hostelId}")
    Observable<Hostel> getHostelById(
            @Path("hostelId") int id
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
    @GET("hostel/search")
    Observable<Content> searchHostel(
            @Query("address") String address,
            @Query("areaMin") int areaMin,
            @Query("areaMax") int areaMax,
            @Query("minRent") int minRent,
            @Query("maxRent") int maxRent,
            @Query("capacity") int capacity,
            @Query("idRoomType") int idRoomType
    );
    @GET("hostel/search")
    Observable<Content> searchHostel(
            @Query("address") String address,
            @Query("areaMin") int areaMin,
            @Query("areaMax") int areaMax,
            @Query("minRent") int minRent,
            @Query("maxRent") int maxRent,
            @Query("capacity") int capacity
    );
    @GET("hostel/search-admin")
    Observable<Content> searchHostelAdmin(
            @Query("address") String address,
            @Query("areaMin") int areaMin,
            @Query("areaMax") int areaMax,
            @Query("minRent") int minRent,
            @Query("maxRent") int maxRent,
            @Query("capacity") int capacity,
            @Query("idRoomType") int idRoomType
    );
    @GET("hostel/search-admin")
    Observable<Content> searchHostelAdmin(
            @Query("address") String address,
            @Query("areaMin") int areaMin,
            @Query("areaMax") int areaMax,
            @Query("minRent") int minRent,
            @Query("maxRent") int maxRent,
            @Query("capacity") int capacity
    );
    @POST("hostel")
    Observable<Hostel> addHostelAndPost(
            @Body RequestBody hostelRequest
            );
    @POST("Hostel/uploadMultipleFiles")
    Observable<ArrayList<ImageRequest>> addImages(
            @Body MultipartBody body
    );
}
