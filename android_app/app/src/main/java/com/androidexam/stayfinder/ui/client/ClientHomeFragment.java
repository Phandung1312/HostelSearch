package com.androidexam.stayfinder.ui.client;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.data.models.Post;
import com.androidexam.stayfinder.databinding.HomeClientClass;
import com.androidexam.stayfinder.ui.admin.home.PostAdminAdapter;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClientHomeFragment extends BaseFragment<HomeClientClass> {
    private ArrayList<Post> posts;
    ClientHomeViewModel clientHomeViewModel;
    PostClientAdapter adapter;
    private int areaMin ;
    private int areaMax ;
    private int minRent ;
    private int maxRent ;
    private String address = "";
    private int idRoomType;
    private int capacity ;
    private ArrayList<String> district = new ArrayList<>();

    public ClientHomeFragment() {
        super(HomeClientClass::inflate);
    }

    @Override
    public void initView() {
        clientHomeViewModel = new ViewModelProvider(this).get(ClientHomeViewModel.class);
        dataBinding.rvPost.setLayoutManager(new GridLayoutManager(getContext(),2));
        resetDistrictData();
        resetMoneyData();
    }
    private void resetDistrictData(){
        this.idRoomType = -1;
        this.capacity = 100000;
        district.clear();
    }
    private void resetMoneyData(){
        this.areaMax = 10000000;
        this.areaMin = 0;
        this.minRent = 0;
        this.maxRent = 1000000000;
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
                Button btnTimPhong = mView.findViewById(R.id.btn_tim_phong);
                Button btnHuy = mView.findViewById(R.id.btn_huy);
                CheckBox cbCamLe = mView.findViewById(R.id.cb_cam_le);
                CheckBox cbHaiChau = mView.findViewById(R.id.cb_hai_chau);
                CheckBox cbHoaVang = mView.findViewById(R.id.cb_hoa_vang);
                CheckBox cbLieuChieu = mView.findViewById(R.id.cb_lieu_chieu);
                CheckBox cbSonTra = mView.findViewById(R.id.cb_son_tra);
                CheckBox cbThanhKhe = mView.findViewById(R.id.cb_thanh_khe);
                RadioGroup rgRoom = mView.findViewById(R.id.rg_room);
                RadioButton rbChungCu = mView.findViewById(R.id.rb_chung_cu);
                RadioButton rbKtx = mView.findViewById(R.id.rb_ktx);
                EditText etCapacity = mView.findViewById(R.id.et_capacity);
                cbCamLe.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked == true){
                        district.add("Cam Le");
                    }else{
                        district.remove("Cam Le");
                    }
                });
                cbHaiChau.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked == true){
                        district.add("Hai Chau");
                    }else{
                        district.remove("Hai Chau");
                    }
                });
                cbHoaVang.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked == true){
                        district.add("Hoa Vang");
                    }else{
                        district.remove("Hoa Vang");
                    }
                });
                cbLieuChieu.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked == true){
                        district.add("Lieu Chieu");
                    }else{
                        district.remove("Lieu Chieu");
                    }
                });
                cbSonTra.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked == true){
                        district.add("Sơn Tra");
                    }else{
                        district.remove("Son Tra");
                    }
                });
                cbThanhKhe.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if(isChecked == true){
                        district.add("Thanh Khe");
                    }else{
                        district.remove("Thanh Khe");
                    }
                });
                rgRoom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        Log.d("check district", "change");
                        if(rbChungCu.isChecked()){
                            idRoomType = 1;
                        }else if(rbKtx.isChecked()){
                            idRoomType = 3;
                        }else{
                            idRoomType =2;
                        }
                    }
                });

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);
                dialog.show();
                btnTimPhong.setOnClickListener(v -> {
                    if(!etCapacity.getText().toString().equals("")){
                        capacity = Integer.parseInt(etCapacity.getText().toString());
                    }
                    clientHomeViewModel.searchData(address,areaMin,areaMax,minRent,maxRent,capacity,idRoomType)
                            .observe(getViewLifecycleOwner(),hostels -> {
                                posts.clear();
                                for(Hostel hostel : hostels){
                                    if(hostel.getPost().getStatus() == 1){
                                        Post post = hostel.getPost();
                                        post.setHostel(hostel);
                                        posts.add(post);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            });
                    dialog.dismiss();
                });
                btnHuy.setOnClickListener(v -> {
                    resetDistrictData();
                    dialog.dismiss();
                });
            }
        });
        dataBinding.btnSearchMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.fragment_search_money, null);
                mDialog.setView(mView);
                Button btnTimPhong = mView.findViewById(R.id.btn_tim_phong);
                Button btnHuy = mView.findViewById(R.id.btn_huy);
                EditText etMinArea  = mView.findViewById(R.id.et_money_area_min);
                EditText etMaxArea = mView.findViewById(R.id.et_money_area_max);
                EditText etMinMoney = mView.findViewById(R.id.et_money_room_min);
                EditText etMaxMoney = mView.findViewById(R.id.et_money_room_max);
                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);
                dialog.show();
                btnHuy.setOnClickListener(v -> {
                    resetMoneyData();
                    dialog.dismiss();
                });
                btnTimPhong.setOnClickListener(v -> {
                    if(!etMaxArea.getText().toString().equals("")){
                        areaMax = Integer.parseInt(etMaxArea.getText().toString());
                    }
                    if(!etMinArea.getText().toString().equals("")){
                        areaMin = Integer.parseInt(etMinArea.getText().toString());
                    }if(!etMinMoney.getText().toString().equals("")){
                        minRent = Integer.parseInt(etMinMoney.getText().toString());
                    }if(!etMaxMoney.getText().toString().equals("")){
                        maxRent = Integer.parseInt(etMaxMoney.getText().toString());
                    }
                    clientHomeViewModel.searchData(address,areaMin,areaMax,minRent,maxRent,capacity,idRoomType)
                            .observe(getViewLifecycleOwner(),hostels -> {
                                posts.clear();
                                for(Hostel hostel : hostels){
                                    if(hostel.getPost().getStatus() == 1){
                                        Post post = hostel.getPost();
                                        post.setHostel(hostel);
                                        posts.add(post);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            });
                    dialog.dismiss();
                });
            }
        });
        dataBinding.btnAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(dataBinding.getRoot()).navigate(R.id.addPostClientFragment);
            }
        });
        dataBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                address = s.toString();
                clientHomeViewModel.searchData(address,areaMin,areaMax,minRent,maxRent,capacity,idRoomType)
                        .observe(getViewLifecycleOwner(),hostels -> {
                            posts.clear();
                            for(Hostel hostel : hostels){
                                if(hostel.getPost().getStatus() == 1){
                                    Post post = hostel.getPost();
                                    post.setHostel(hostel);
                                    posts.add(post);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        });
            }
        });
    }

    @Override
    public void initData() {
        setAdapter();
    }
    private void setAdapter(){
        posts = new ArrayList<>();
        adapter = new PostClientAdapter(posts,clientHomeViewModel,getViewLifecycleOwner());
        dataBinding.rvPost.setAdapter(adapter);
        clientHomeViewModel.setPostData();
        clientHomeViewModel.loadData().observe(getViewLifecycleOwner(),postList -> {
            for(Post post : postList){
                if(post.getStatus() == 1){
                    posts.add(post);
                }
            }
            adapter.notifyDataSetChanged();
        });
    }
}
