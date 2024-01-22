package com.example.recipeapp.activity;

import static com.example.recipeapp.share.sharePreferenceUtils.isSharedPreferencesExist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.recipeapp.R;
import com.example.recipeapp.databinding.ActivitySplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addEvents();
    }

    private void addEvents() {
        binding.btnGetStarted.setOnClickListener(view -> {
            if(isSharedPreferencesExist(this,"User","USER_VALUE")){
                Intent intent = new Intent(this, MainScreen.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(this, LoginScreen.class);
                startActivity(intent);
            }
            finish();

        });
    }
}