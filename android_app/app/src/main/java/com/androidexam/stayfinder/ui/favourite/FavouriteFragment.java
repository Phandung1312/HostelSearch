package com.androidexam.stayfinder.ui.favourite;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.androidexam.stayfinder.activities.MainActivity;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.data.models.Hostel;
import com.androidexam.stayfinder.databinding.FavoriteClass;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavouriteFragment extends BaseFragment<FavoriteClass> {
//    private List<Hostel> hostels;
    FavouriteViewModel favouriteViewModel;
    public FavouriteFragment() {
        super(FavoriteClass::inflate);
    }

    @Override
    public void initView() {
        favouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        favouriteViewModel.getHostel(mainActivity.account.getAccountName())
                .observe(getViewLifecycleOwner(),response ->{
            if(response.size() == 0){
                dataBinding.layoutEmpty.setVisibility(View.VISIBLE);
            }
            else{
                setAdapter(response);
            }
        });
    }
    private void setAdapter(List<Hostel> hostels){
//        this.hostels = hostels;
        FavouriteHostelAdapter adapter = new FavouriteHostelAdapter(
                mainActivity.getApplicationContext(),
                hostels,
                favouriteViewModel,
                getViewLifecycleOwner(),
                mainActivity.account.getAccountName()
        );
        dataBinding.rvFavorites.setAdapter(adapter);
    }
}
