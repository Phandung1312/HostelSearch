package com.androidexam.stayfinder.ui.setting;

import android.app.AlertDialog;
import android.app.NativeActivity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.activities.MainActivity;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.SettingClass;
import com.androidexam.stayfinder.databinding.UpdateGenderBinding;
import com.androidexam.stayfinder.databinding.UpdateNameBinding;
import com.androidexam.stayfinder.databinding.UpdatePhoneBinding;
import com.androidexam.stayfinder.ui.login.LoginFragment;
import com.androidexam.stayfinder.ui.profile.ProfileFragmentDirections;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.paperdb.Paper;

@AndroidEntryPoint
public class SettingFragment extends BaseFragment<SettingClass> {
    SettingViewModel settingViewModel;

    @Inject
    GoogleSignInClient gsc;

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
                UpdateNameBinding binding = UpdateNameBinding.inflate(getLayoutInflater());

                mDialog.setView(binding.getRoot());

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);

                binding.edtName.setText(dataBinding.tvName.getText());
                binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        settingViewModel.UpdateUsername(mainActivity.account.getAccountName(), String.valueOf(binding.edtName.getText()));
                        Toast.makeText(view.getContext(), "Cập nhật họ tên thành công!", Toast.LENGTH_SHORT).show();
                        dataBinding.tvName.setText(binding.edtName.getText());
                        dialog.dismiss();
                    }
                });
                binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        System.out.println("exit dialog");
                    }
                });

                dialog.show();
            }
        });
        dataBinding.tvGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                UpdateGenderBinding binding = UpdateGenderBinding.inflate(getLayoutInflater());

                mDialog.setView(binding.getRoot());

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);

                if(dataBinding.tvGender.getText() == "Nam")
                    binding.rbMale.setChecked(true);
                else
                    binding.rbFemale.setChecked(true);

                binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(view.getContext(), "Cập nhật giới tính thành công!", Toast.LENGTH_SHORT).show();
                        dataBinding.tvGender.setText(binding.rbMale.isChecked() ? "Nam" : "Nữ");
                        dialog.dismiss();
                    }
                });
                binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        dataBinding.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mDialog = new AlertDialog.Builder(view.getContext());
                UpdatePhoneBinding binding = UpdatePhoneBinding.inflate(getLayoutInflater());

                mDialog.setView(binding.getRoot());

                AlertDialog dialog = mDialog.create();
                dialog.setCancelable(true);

                binding.edtPhone.setText(dataBinding.tvPhone.getText());
                binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(view.getContext(), "Cập nhật số điện thoại thành công!", Toast.LENGTH_SHORT).show();
                        dataBinding.tvPhone.setText(binding.edtPhone.getText());
                        dialog.dismiss();
                    }
                });
                binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        dataBinding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gsc.signOut().addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Paper.init(getContext());
                        Paper.book().write("email", "");
                        Paper.book().write("password", "");
                        Paper.book().write("isLogin", false);
//                        getActivity().finishAffinity();

//                        getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        Navigation.findNavController(dataBinding.getRoot()) .popBackStack(Navigation.findNavController(dataBinding.getRoot()).getGraph()
                                .getStartDestinationId(), false);

                        Navigation.findNavController(dataBinding.getRoot()).navigate(R.id.loginFragment);

                        Toast.makeText(view.getContext(), "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("DEBUG", "Error log out in settingfragment!");
                    }
                });

            }
        });
    }

    @Override
    public void initData() {
        dataBinding.setAccount(mainActivity.account);
    }
}
