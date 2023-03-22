package com.androidexam.stayfinder.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.androidexam.stayfinder.base.dialogs.NotifyDialog;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.databinding.LoginClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginFragment extends BaseFragment<LoginClass> {
    @Inject
    GoogleSignInClient gsc;
    public LoginFragment() {
        super(LoginClass::inflate);
    }
    @Override
    public void initView() {
    }

    @Override
    public void initListeners() {
        dataBinding.btnSignInGoogle.setOnClickListener(v -> signIn());
        dataBinding.btnSignInFacebook.setOnClickListener(v -> showNotify());
    }

    @Override
    public void initData() {

    }
    private void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        mStartForResult.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        Log.d("INFO",task.getResult(ApiException.class).getEmail());

                    }
                    catch (ApiException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
            });
    private void showNotify(){
        String title = "Thông báo";
        String message = "Vì lí do bảo mật nên chức năng này tạm thời không được sử dụng.";
        String textButton = "OK";
        NotifyDialog notifyDialog = new NotifyDialog(requireContext(),
                title, message, textButton);
        notifyDialog.show();
    }
}
