package com.androidexam.stayfinder.ui.setting;

import android.view.View;

import androidx.navigation.Navigation;

import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.base.fragment.Inflate;
import com.androidexam.stayfinder.databinding.SettingClass;
import com.androidexam.stayfinder.ui.profile.ProfileFragmentDirections;

public class SettingFragment extends BaseFragment<SettingClass> {
    public SettingFragment() {
        super(SettingClass::inflate);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListeners() {
        dataBinding.tvViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot()).navigate(SettingFragmentDirections.actionSettingFragmentToScheduleFragment());
            }
        });
    }

    @Override
    public void initData() {
        dataBinding.setAccount(mainActivity.account);
    }
}
