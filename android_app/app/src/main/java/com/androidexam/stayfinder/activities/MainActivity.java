package com.androidexam.stayfinder.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.data.models.Account;
import com.androidexam.stayfinder.databinding.ActivityMainBinding;
import com.androidexam.stayfinder.ui.login.LoginFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    public Account account = new Account();
    public ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setNavController();
        setListeners();
        setContentView(binding.getRoot());
    }
    private void setNavController(){
        try {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
            navController = navHostFragment.getNavController();

        }
        catch (Exception  e){
            if(e.getMessage() != null){
                Log.d("Exception in mainactivity","Line 29");
            }
        }
    }
    private void setListeners(){
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    if(account.getPosition().getId() == 1) navController.navigate(R.id.clientHomeFragment);
                    else navController.navigate(R.id.adminHomeFragment);
                    break;
                case R.id.chat:
                    navController.navigate(R.id.listChatFragment);
                    break;
                case R.id.favourite:
                    navController.navigate(R.id.favouriteFragment);
                    break;
                case R.id.profile:
                    if(account.getPosition().getId() != 1){
                        navController.navigate(R.id.settingFragment);
                    }
                    else{
                        navController.navigate(R.id.profileFragment);
                    }
                    break;
            }
            return true;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}