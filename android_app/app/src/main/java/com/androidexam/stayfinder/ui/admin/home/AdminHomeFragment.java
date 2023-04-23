package com.androidexam.stayfinder.ui.admin.home;

import android.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TimePicker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.androidexam.stayfinder.ui.chat.chatdetail.ChatDetailViewModel;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.Time;

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
    private ArrayList<Hostel> hostelApprovals;
    private ArrayList<Hostel> hostelNotApprovals;
    AdminHomeViewModel adminHomeViewModel;
    PostAdminAdapter adapterApproval;
    PostAdminAdapter adapterNotApproval;
    public AdminHomeFragment() {
        super(AdminHomeClass::inflate);
    }
    @Override
    public void initView() {
        adminHomeViewModel = new ViewModelProvider(this).get(AdminHomeViewModel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        dataBinding.rvPostNotApproval.setLayoutManager(new GridLayoutManager(getContext(),2));
        dataBinding.rvPostApproval.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void initListeners() {
        dataBinding.btnSearchDistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.fragment_search_district, null);
                mDialog.setView(mView);

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);
                dialog.show();
            }
        });
        dataBinding.btnSearchMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.fragment_search_money, null);
                mDialog.setView(mView);

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);
                dialog.show();
            }
        });
    }

    @Override
    public void initData() {
        setAdapter();
    }
    private void setAdapter(){
        hostelApprovals = new ArrayList<>();
        hostelNotApprovals = new ArrayList<>();
        adapterApproval = new PostAdminAdapter(hostelApprovals);
        adapterNotApproval = new PostAdminAdapter(hostelNotApprovals);
        dataBinding.rvPostApproval.setAdapter(adapterApproval);
        dataBinding.rvPostNotApproval.setAdapter(adapterNotApproval);
        adminHomeViewModel.setPostData();
        adminHomeViewModel.loadData().observe(getViewLifecycleOwner(),postList -> {
            for(Hostel hostel : postList){
                if(hostel.getPost().getStatus() == 1){
                    hostelApprovals.add(hostel);
                }else if(hostel.getPost().getStatus() == 2){
                    hostelNotApprovals.add(hostel);
                }
            }
            adapterApproval.notifyDataSetChanged();
            adapterNotApproval.notifyDataSetChanged();
        });
    }

}
