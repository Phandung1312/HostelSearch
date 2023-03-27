package com.androidexam.stayfinder.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import dagger.hilt.android.AndroidEntryPoint;


public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    protected Inflate<VB> inflate;
    protected VB dataBinding = null;
    public BaseFragment(Inflate<VB> inflate){
        this.inflate = inflate;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = inflate.inflate(inflater, container, false);
        initView();
        initListeners();
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    public abstract void initView();
    public abstract void initListeners();
    public abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        dataBinding = null;
    }
}
