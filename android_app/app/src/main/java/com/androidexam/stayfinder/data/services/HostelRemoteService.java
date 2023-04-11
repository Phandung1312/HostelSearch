package com.androidexam.stayfinder.data.services;

import android.util.Log;

import com.androidexam.stayfinder.data.apis.HostelAPI;
import com.androidexam.stayfinder.data.apis.PostAPI;
import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.FavouriteRequest;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class HostelRemoteService {
    HostelAPI hostelAPI;
    @Inject
    public HostelRemoteService(HostelAPI hostelAPI) {
        this.hostelAPI = hostelAPI;
    }
    public Observable<Content> getAllHostel(){
        return hostelAPI.getAllHostel();
    }
    public Observable<Hostel> getHostelById(String id) {return hostelAPI.getHostelById(id);}
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
}
