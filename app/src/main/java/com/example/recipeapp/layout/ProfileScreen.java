package com.example.recipeapp.layout;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.FragmentProfileScreenBinding;
import com.example.recipeapp.model.User;
import com.example.recipeapp.share.sharePreferenceUtils;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileScreen extends Fragment {
    FragmentProfileScreenBinding binding;
    private FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileScreenBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        com.example.recipeapp.model.User user = sharePreferenceUtils.getUser(requireContext());
        auth = FirebaseAuth.getInstance();
        binding.usernameTextView.setText(user.getUsername());
        Glide.with(this).load(user.getUri()).into(binding.profileImage);
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePreferenceUtils.removeUser(requireContext());
                auth.signOut();
                Navigation.findNavController(getView()).navigate(R.id.action_profileScreen_to_splashScreen);
            }
        });
        binding.profleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_profileScreen_to_updateProfileScreen);
            }
        });
    }
}