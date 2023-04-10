package com.androidexam.stayfinder.data.services;

import com.androidexam.stayfinder.data.apis.HostelAPI;
import com.androidexam.stayfinder.data.models.Content;
import com.androidexam.stayfinder.data.models.Hostel;

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
}
