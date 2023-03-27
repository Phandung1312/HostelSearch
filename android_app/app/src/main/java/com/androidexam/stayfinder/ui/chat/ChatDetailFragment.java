package com.androidexam.stayfinder.ui.chat;
import android.view.View;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.ChatDetailClass;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChatDetailFragment extends BaseFragment<ChatDetailClass> {
    public ChatDetailFragment() {
        super(ChatDetailClass::inflate);
    }
    @Override
    public void initView() {
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation_view);
        navBar.setVisibility(View.GONE);
    }
    @Override
    public void initListeners() {
    }
    @Override
    public void initData() {
    }
}
