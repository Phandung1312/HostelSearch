package com.androidexam.stayfinder.ui.setting;

import android.app.AlertDialog;
import android.app.NativeActivity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
import com.androidexam.stayfinder.ui.profile.ProfileFragment;
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
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.profileFragment, ProfileFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null).commit();

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
                        if(binding.edtName.getText().toString().trim().length() > 0){
                            settingViewModel.UpdateUsername(mainActivity.account.getAccountName(), String.valueOf(binding.edtName.getText())).observe(getViewLifecycleOwner(), account -> {
                                if(account != null){
                                    Toast.makeText(view.getContext(), "Cập nhật họ tên thành công!", Toast.LENGTH_SHORT).show();
                                    mainActivity.account = account;
                                    dataBinding.setAccount(account);
                                    dialog.dismiss();
                                }
                            });
                        }
                        else{
                            Toast.makeText(view.getContext(), "Họ tên không thể để trống!", Toast.LENGTH_SHORT).show();
                        }
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
                        System.out.println(mainActivity.account.getAccountName());
                        settingViewModel.UpdateGender(mainActivity.account.getAccountName(), binding.rbMale.isChecked()).observe(getViewLifecycleOwner(), account -> {
                            if(account != null){
                                Toast.makeText(view.getContext(), "Cập nhật giới tính thành công!", Toast.LENGTH_SHORT).show();
                                mainActivity.account = account;
//                                dataBinding.tvGender.setText(binding.rbMale.isChecked() ? "Nam" : "Nữ");
//                                mainActivity.account.setGender(binding.rbMale.isChecked());
                                dataBinding.setAccount(account);
                                dialog.dismiss();
                            }
                        });
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
                        if(checkLogicPhone(binding.edtPhone.getText().toString())){
                            settingViewModel.UpdatePhoneNumber(mainActivity.account.getAccountName(), binding.edtPhone.getText().toString()).observe(getViewLifecycleOwner(), account -> {
                                if(account != null){
                                    Toast.makeText(view.getContext(), "Cập nhật số điện thoại thành công!", Toast.LENGTH_SHORT).show();
                                    mainActivity.account = account;
                                    dataBinding.setAccount(account);
                                    dialog.dismiss();
                                }
                            });

                        }
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

    public boolean checkLogicPhone(String phone){
//        if(phone.trim() == ""){
//            Toast.makeText(getContext(), "Số điện thoại không thể để trống!", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if(phone.trim().length() == 0){
            phone = "";
            return true;
        }

        if(phone.length() != 10){
            Toast.makeText(getContext(), "Số điện thoại phải có 10 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }

        try{
            Long.parseLong(phone);
        }catch (NumberFormatException e){
            Toast.makeText(getContext(), "Số điện thoại phải là một số!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    @Override
    public void initData() {
        dataBinding.setAccount(mainActivity.account);
    }
}
