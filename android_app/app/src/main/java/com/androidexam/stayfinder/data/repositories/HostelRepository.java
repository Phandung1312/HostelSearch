package com.androidexam.stayfinder.data.repositories;

import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.services.HostelRemoteService;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class HostelRepository {
    HostelRemoteService hostelRemoteService;
    public HostelRepository(HostelRemoteService hostelRemoteService){
        this.hostelRemoteService = hostelRemoteService;
    }
    public Observable<List<Hostel>> getAllHostel(){
        return hostelRemoteService.getAllHostel();
    }
}
