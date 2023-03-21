package com.androidexam.stayfinder.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.base.fragment.Inflate;
import com.androidexam.stayfinder.databinding.LoginClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginFragment extends BaseFragment<LoginClass> {

//    GoogleSignInOptions gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//    GoogleSignInClient gsc = GoogleSignIn.getClient(getActivity().getApplicationContext(), gso);
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
        dataBinding.imgGoogle.setOnClickListener(v -> signIn());
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
                        task.getResult(ApiException.class);
                        Log.d("CheckLogin","OK");
                        dataBinding.editPhoneNumber.setText("OK");
                    }
                    catch (ApiException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        dataBinding.editPhoneNumber.setText("Error");
                    }
                }
            });
}
