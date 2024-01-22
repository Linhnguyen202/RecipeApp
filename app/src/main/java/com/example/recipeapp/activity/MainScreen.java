package com.example.recipeapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.example.recipeapp.databinding.ActivityMainScreenBinding;
import com.example.recipeapp.utils.NetworkConnection;

public class MainScreen extends AppCompatActivity {
   private ActivityMainScreenBinding binding;

    private NetworkConnection networkConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.frag_host);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        networkConnection = new NetworkConnection(this);
        networkConnection.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean){
                    binding.internetTitle.setVisibility(View.VISIBLE);
                }
                else{
                    binding.internetTitle.setVisibility(View.GONE);
                }
            }
        });
    }
}