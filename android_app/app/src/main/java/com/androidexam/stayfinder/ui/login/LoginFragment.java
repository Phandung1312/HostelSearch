package com.androidexam.stayfinder.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.activities.MainActivity;
import com.androidexam.stayfinder.base.dialogs.NotifyDialog;
import com.androidexam.stayfinder.base.fragment.BaseFragment;
import com.androidexam.stayfinder.common.ImageConvertResult;
import com.androidexam.stayfinder.common.Utils;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.databinding.LoginClass;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.paperdb.Paper;


@AndroidEntryPoint
public class LoginFragment extends BaseFragment<LoginClass> {
    BottomNavigationView bottomNavigationView;
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
                   loginViewModel.login(account.getAccountName(), account.getPassword());
                   loginViewModel.loadData().observe(getViewLifecycleOwner(),resAccount ->{
                       auth.signInWithEmailAndPassword(account.getAccountName(),
                               account.getPassword()).addOnCompleteListener(task -> {
                                   if(task.isSuccessful()){
                                       showHome(resAccount);
                                   }
                                   else{
                                       Log.d("Error","Login error");
                                   }
                       });
                   });
                }
            }
        }
    }
    @Override
    public void initView() {
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation_view);
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
                            loginFirebase(googleSignInAccount);
                        }
                    }
                    catch (ApiException e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                    }
                }
            });
    private void loginFirebase(GoogleSignInAccount googleSignInAccount){
        loginViewModel.firebaseSignInWithGoogle(googleSignInAccount);
        loginViewModel.isNewAccount().observe(getViewLifecycleOwner(), isNewAccount ->{
            Log.d("NewAccount",isNewAccount.toString());
            if(isNewAccount){
                Account account = new Account();
                account.setAccountName(googleSignInAccount.getEmail());
                account.setPassword(googleSignInAccount.getId());
                account.setUserName(googleSignInAccount.getDisplayName());
                loginViewModel.signUp(account);
            }
            else{
                loginViewModel.login(googleSignInAccount.getEmail(), googleSignInAccount.getId());
            }
            loginViewModel.loadData().observe(getViewLifecycleOwner(), resAccount ->{
                Paper.book().write("email",resAccount.getAccountName());
                Paper.book().write("password", resAccount.getPassword());
                Paper.book().write("isLogin",true);
                showHome(resAccount);
            });
        });
    }
    private void showHome(Account account){
        mainActivity.account = account;
        bottomNavigationView.setVisibility(View.VISIBLE);
        if(mainActivity.account.getPosition().getId() == 1){
            Navigation.findNavController(dataBinding.getRoot()).navigate(R.id.clientHomeFragment);
        }
        else{
            Navigation.findNavController(dataBinding.getRoot()).navigate(R.id.adminHomeFragment);
        }
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
