package com.androidexam.stayfinder.ui.setting;

import android.app.AlertDialog;
import android.app.NativeActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.SettingClass;
import com.androidexam.stayfinder.databinding.UpdateNameBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingFragment extends BaseFragment<SettingClass> {
    SettingViewModel settingViewModel;
    public SettingFragment() {
        super(SettingClass::inflate);
    }

    @Override
    public void initView() {
        settingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
    }

    @Override
    public void initListeners() {
        dataBinding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(dataBinding.getRoot()).popBackStack();
            }
        });
        dataBinding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.update_name, null);
                mDialog.setView(mView);

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);
                dialog.show();

                UpdateNameBinding binding = UpdateNameBinding.inflate(getLayoutInflater());
                binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        settingViewModel.UpdateUsername(mainActivity.account.getAccountName(), String.valueOf(binding.edtName.getText()));
                        Toast.makeText(view.getContext(), "Cập nhật họ tên thành công!", Toast.LENGTH_SHORT).show();
                        dataBinding.tvName.setText(String.valueOf(binding.edtName.getText()));
                        dialog.dismiss();
                    }
                });
                binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        dataBinding.tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.update_gender, null);
                mDialog.setView(mView);

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);
                dialog.show();

                UpdateNameBinding binding = UpdateNameBinding.inflate(getLayoutInflater());
                binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        dataBinding.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View mView = inflater.inflate(R.layout.update_phone, null);
                mDialog.setView(mView);

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);
                dialog.show();

                UpdateNameBinding binding = UpdateNameBinding.inflate(getLayoutInflater());
                binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });
        dataBinding.tvLinkBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Nếu bạn dùng chức năng này, bạn sẽ bị hack tài khoản ngân hàng!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initData() {
        dataBinding.setAccount(mainActivity.account);
    }
}
