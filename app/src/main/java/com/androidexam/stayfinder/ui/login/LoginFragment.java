package com.androidexam.stayfinder.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.androidexam.stayfinder.base.dialogs.NotifyDialog;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.common.ImageConvertResult;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.databinding.LoginClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class LoginFragment extends BaseFragment<LoginClass> {
    @Inject
    GoogleSignInClient gsc;
    LoginViewModel loginViewModel;
    public LoginFragment() {
        super(LoginClass::inflate);
    }
    @Override
    public void initView() {

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
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
                        GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                        if(googleSignInAccount != null){
                            login(googleSignInAccount);
                        }
                    }
                    catch (ApiException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
            });
    private void login(GoogleSignInAccount googleSignInAccount){
        loginViewModel.firebaseSignInWithGoogle(googleSignInAccount.getEmail(),
                googleSignInAccount.getId());
        loginViewModel.isNewAccount().observe(getViewLifecycleOwner(), isNewAccount ->{
            Account account = new Account();
            account.setAccountName(googleSignInAccount.getEmail());
            account.setPassword(googleSignInAccount.getId());
            account.setUserName(googleSignInAccount.getDisplayName());
            if(googleSignInAccount.getPhotoUrl() != null){
                loginViewModel.convertUrlToByteArr(getContext(), googleSignInAccount.getPhotoUrl().toString()
                        , new ImageConvertResult<byte[]>() {
                            @Override
                            public void onSuccess(byte[] result) {
                                Log.d("Check","HERE");
                                account.setAvatar(result);
                                loginViewModel.setData(account);
                            }
                            @Override
                            public void onError() {
                                Toast.makeText(getContext(), "Xảy ra lỗi xử lí ảnh đăng nhập!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else{
                loginViewModel.setData(account);
            }
            loginViewModel.loadData().observe(getViewLifecycleOwner(), resAccount ->{

            });
        });

    }
    private void showNotify(){
        String title = "Thông báo";
        String message = "Vì lí do bảo mật nên chức năng này tạm thời không còn được sử dụng.";
        String textButton = "OK";
        NotifyDialog notifyDialog = new NotifyDialog(requireContext(),
                title, message, textButton);
        notifyDialog.show();
    }
}
