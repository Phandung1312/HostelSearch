package com.androidexam.stayfinder.ui.admin.post;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.PostDetailAdminClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostDetailAdminFragment extends BaseFragment<PostDetailAdminClass> {

    public PostDetailAdminFragment() {
        super(PostDetailAdminClass::inflate);
    }
    @Override
    public void initView() {
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);
        navBar.setVisibility(View.GONE);
    }
    @Override
    public void initListeners() {
        dataBinding.btnBack.setOnClickListener(view ->{
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
            bottomNavigationView.setVisibility(View.VISIBLE);
            Navigation.findNavController(dataBinding.getRoot()).popBackStack();
        });
    }
    @Override
    public void initData() {
    }

}
