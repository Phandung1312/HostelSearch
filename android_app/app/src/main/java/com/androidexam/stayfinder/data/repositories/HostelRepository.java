package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.services.HostelRemoteService;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class HostelRepository {
    HostelRemoteService hostelRemoteService;
    @Inject
    public HostelRepository(HostelRemoteService hostelRemoteService){
        this.hostelRemoteService = hostelRemoteService;
    }
    public Observable<Content> getAllHostel(){
        return hostelRemoteService.getAllHostel();
    }
    public Observable<Hostel> getHostelById(String id) {return hostelRemoteService.getHostelById(id);}
}
