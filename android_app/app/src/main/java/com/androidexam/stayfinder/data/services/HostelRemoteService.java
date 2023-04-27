package com.androidexam.stayfinder.data.services;

import static okhttp3.MultipartBody.FORM;

import android.util.Log;

import com.androidexam.stayfinder.data.apis.HostelAPI;
import com.androidexam.stayfinder.data.apis.PostAPI;
import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.FavouriteRequest;
import com.androidexam.stayfinder.data.models.request.HostelRequest;
import com.androidexam.stayfinder.data.models.request.ImageRequest;
import com.androidexam.stayfinder.data.models.request.PostRequest;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class HostelRemoteService {
    HostelAPI hostelAPI;

    @Inject
    public HostelRemoteService(HostelAPI hostelAPI) {
        this.hostelAPI = hostelAPI;
    }

    public Observable<Content> getAllHostel() {
        return hostelAPI.getAllHostel();
    }
    public Observable<Hostel> getHostelById(int id) {return hostelAPI.getHostelById(id);}
    public Observable<List<Hostel>> getFavouriteHostels(String username){
        return hostelAPI.getFavouriteHostels(username);
    }
    public Observable<Boolean> unFavouriteHostel(String username, int postId){
        return hostelAPI.unFavouriteHostel(new FavouriteRequest(username, postId));
    }
    public Observable<Boolean> addFavouriteHostel(String username, int postId){
        return hostelAPI.addFavouriteHostel(new FavouriteRequest(username, postId));
    }
    public Observable<Boolean> checkFavourite(String username, int postId){
        return hostelAPI.checkFavourite(username, postId);
    }
    public Observable<Content> searchHostel(String address , int areaMin, int areaMax, int minRent, int maxRent, int capacity, int idRoomType){
        if(idRoomType != -1){
            return hostelAPI.searchHostel(address, areaMin, areaMax, minRent, maxRent, capacity, idRoomType);
        }else{
            return hostelAPI.searchHostel(address, areaMin, areaMax, minRent, maxRent, capacity);
        }
    }
    public Observable<Content> searchHostelAdmin(String address , int areaMin, int areaMax, int minRent, int maxRent, int capacity, int idRoomType){
        if(idRoomType != -1){
            return hostelAPI.searchHostelAdmin(address, areaMin, areaMax, minRent, maxRent, capacity, idRoomType);
        }else{
            return hostelAPI.searchHostelAdmin(address, areaMin, areaMax, minRent, maxRent, capacity);
        }
    }
    public Observable<Hostel> addHostelAndPost(String name, int capacity,double area,String address,double rentPrice,double depositPrice, int status, String description, int roomTypeId, double electricPrice,double waterPrice, int accountId, String title,String content){
        HostelRequest hostelRequest = new HostelRequest(name,capacity,area,address,rentPrice,depositPrice,status,description,roomTypeId,electricPrice,waterPrice,new PostRequest(title,content,accountId));
        Gson gson = new Gson();
        String json = gson.toJson(hostelRequest);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        return hostelAPI.addHostelAndPost(body);
    }

    public Observable<ArrayList<ImageRequest>> addImages(int hostelId, ArrayList<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(FORM);
        builder.addFormDataPart("hostelId", String.valueOf(hostelId));
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("files" , file.getName(), requestBody);
        }
        MultipartBody multipartBody = builder.build();
        return hostelAPI.addImages(
                multipartBody
        );
    }
}
