package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.request.HostelRequest;
import com.androidexam.stayfinder.data.models.request.ImageRequest;
import com.androidexam.stayfinder.data.models.request.PostRequest;
import com.androidexam.stayfinder.data.services.HostelRemoteService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class HostelRepository {
    HostelRemoteService hostelRemoteService;

    @Inject
    public HostelRepository(HostelRemoteService hostelRemoteService) {
        this.hostelRemoteService = hostelRemoteService;
    }

    public Observable<Content> getAllHostel() {
        return hostelRemoteService.getAllHostel();
    }
    public Observable<Hostel> getHostelById(int id) {return hostelRemoteService.getHostelById(id);}
    public Observable<List<Hostel>> getFavouriteHostels(String username){
        return hostelRemoteService.getFavouriteHostels(username);
    }
    public Observable<Boolean> unFavouriteHostel(String username, int postId){
        return hostelRemoteService.unFavouriteHostel(username, postId);
    }
    public Observable<Boolean> addFavouriteHostel(String username, int postId){
        return hostelRemoteService.addFavouriteHostel(username, postId);
    }
    public Observable<Boolean> checkFavourite(String username, int postId){
        return hostelRemoteService.checkFavourite(username, postId);
    }
    public Observable<Content> searchHostel(String address , int areaMin, int areaMax, int minRent, int maxRent, int capacity, int idRoomType){
        return hostelRemoteService.searchHostel(address, areaMin, areaMax, minRent, maxRent, capacity, idRoomType);
    }
    public Observable<Content> searchHostelAdmin(String address , int areaMin, int areaMax, int minRent, int maxRent, int capacity, int idRoomType){
        return hostelRemoteService.searchHostelAdmin(address, areaMin, areaMax, minRent, maxRent, capacity, idRoomType);
    }
    public Observable<Hostel> addHostelAndPost(String name, int capacity,double area,String address,double rentPrice,double depositPrice, int status, String description, int roomTypeId, double electricPrice,double waterPrice, int accountId, String title,String content){
        return hostelRemoteService.addHostelAndPost(name, capacity, area, address, rentPrice, depositPrice, status, description, roomTypeId, electricPrice, waterPrice, accountId, title, content);
    }
    public Observable<ArrayList<ImageRequest>> addImages(int hostelId, ArrayList<File> files){
        return hostelRemoteService.addImages(hostelId, files);
    }
}
