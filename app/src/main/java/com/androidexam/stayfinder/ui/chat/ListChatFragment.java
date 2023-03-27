package com.androidexam.stayfinder.ui.chat;

import android.view.View;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.ListChatClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ListChatFragment extends BaseFragment<ListChatClass> {
    public ListChatFragment(){super(ListChatClass::inflate);}
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
