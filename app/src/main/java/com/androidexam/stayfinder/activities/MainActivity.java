package com.androidexam.stayfinder.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.androidexam.stayfinder.R;
import com.androidexam.stayfinder.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
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
                    navController.navigate(R.id.adminHomeFragment);
                    break;
                case R.id.chat:
                    navController.navigate(R.id.listChatFragment);
                    break;
            }
            return true;
        });
    }
}