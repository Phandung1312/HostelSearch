package com.androidexam.stayfinder.data.services;

import com.androidexam.stayfinder.data.apis.HostelAPI;
import com.androidexam.stayfinder.data.apis.PostAPI;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class HostelRemoteService {
    HostelAPI hostelAPI;
    @Inject
    public HostelRemoteService(HostelAPI hostelAPI) {
        this.hostelAPI = hostelAPI;
    }
    public Observable<List<Hostel>> getAllHostel(){
        return hostelAPI.getAllHostel();
    }
}
