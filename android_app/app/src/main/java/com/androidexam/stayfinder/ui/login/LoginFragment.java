package com.androidexam.stayfinder.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.activities.MainActivity;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.paperdb.Paper;


@AndroidEntryPoint
public class LoginFragment extends BaseFragment<LoginClass> {
    @Inject
    GoogleSignInClient gsc;
    @Inject
    FirebaseAuth auth;
    LoginViewModel loginViewModel;
    public LoginFragment() {
        super(LoginClass::inflate);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (Paper.book().read("email") != null && Paper.book().read("password") != null){
            if(Paper.book().read("isLogin") != null){
                boolean flag = Paper.book().read("isLogin");
                if(flag){
                   loginViewModel.isNew.setValue(true);
                   Account account = new Account();
                   account.setAccountName(Paper.book().read("email"));
                   account.setPassword(Paper.book().read("password"));
                   loginViewModel.setData(account);
                   loginViewModel.loadData().observe(getViewLifecycleOwner(),resAccount ->{
                   });
                }
            }
        }
    }

    @Override
    public void initView() {
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setVisibility(View.GONE);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
    @Override
    public void initListeners() {
        dataBinding.btnSignInGoogle.setOnClickListener(v -> signIn());
        dataBinding.btnSignInFacebook.setOnClickListener(v -> showNotify());
    }

    @Override
    public void initData() {
        Paper.init(getContext());
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
                                Log.d("Check",account.getPassword());
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
                Paper.book().write("email",account.getAccountName());
                Paper.book().write("password", account.getPassword());
                Paper.book().write("isLogin",true);
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
