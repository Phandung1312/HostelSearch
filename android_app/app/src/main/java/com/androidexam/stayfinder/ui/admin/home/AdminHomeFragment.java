package com.androidexam.stayfinder.ui.admin.home;

import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.apis.PostAPI;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.data.models.Schedule;
import com.androidexam.stayfinder.data.repositories.PostRepository;
import com.androidexam.stayfinder.data.services.PostRemoteService;
import com.androidexam.stayfinder.databinding.AdminHomeClass;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class AdminHomeFragment extends BaseFragment<AdminHomeClass> {
    private ArrayList<Hostel> hostels;
    private AdminHomeViewModel adminHomeViewModel;
    public AdminHomeFragment() {
        super(AdminHomeClass::inflate);
    }
    @Override
    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dataBinding.rvPost.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {
        setAdapter();
    }
    private void setAdapter(){
        hostels = new ArrayList<Hostel>();
        PostAdminAdapter adapter = new PostAdminAdapter(hostels);
        dataBinding.rvPost.setAdapter(adapter);
        adminHomeViewModel.setPostData();
        adminHomeViewModel.loadData().observe(getViewLifecycleOwner(),postList -> {
            for(Hostel post : postList){
                hostels.add(post);
            }
            adapter.notifyDataSetChanged();
        });
    }

}
