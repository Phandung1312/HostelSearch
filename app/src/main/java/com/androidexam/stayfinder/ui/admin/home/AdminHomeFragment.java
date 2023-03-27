package com.androidexam.stayfinder.ui.admin.home;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.AdminHomeClass;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AdminHomeFragment extends BaseFragment<AdminHomeClass> {

    public AdminHomeFragment() {
        super(AdminHomeClass::inflate);
    }
    @Override
    public void initView() {
    }

    @Override
    public void initListeners() {
    }

    @Override
    public void initData() {

    }

}
