package com.androidexam.stayfinder.ui.admin.post;

import com.androidexam.stayfinder.base.viewmodel.BaseViewModel;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.repositories.HostelRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class PostDetailAdminViewModel extends BaseViewModel {
    HostelRepository hostelRepository;
    private Observable<Hostel> hostel;
    @Inject
    public PostDetailAdminViewModel(HostelRepository hostelRepository){
        this.hostelRepository = hostelRepository;
    }
    public void setHostel(String id) {
        hostel = hostelRepository.getHostelById(id);
    }
    public Observable<Hostel> loadHostel(){
        return hostel;
    }
}
