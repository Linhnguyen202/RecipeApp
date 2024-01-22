package com.example.recipeapp.layout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.activity.RegisterScreen_2;
import com.example.recipeapp.databinding.FragmentUpdateProfileScreenBinding;
import com.example.recipeapp.model.User;
import com.example.recipeapp.share.sharePreferenceUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class UpdateProfileScreen extends Fragment {

   FragmentUpdateProfileScreenBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference reference;

    private Uri imageProfile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUpdateProfileScreenBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("User");
        imageProfile = auth.getCurrentUser().getPhotoUrl();
        addData();
        addEvents();
    }

    private void addData() {
        Glide.with(this).load(auth.getCurrentUser().getPhotoUrl()).into(binding.imageProfile);
        binding.usernameEditText.setText(auth.getCurrentUser().getDisplayName());
    }

    private void addEvents() {
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
        binding.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(UpdateProfileScreen.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageProfile = data.getData();
        binding.imageProfile.setImageURI(imageProfile);

    }
    private void updateUser() {
        String username = binding.usernameEditText.getText().toString();
        UserProfileChangeRequest profileUser = new UserProfileChangeRequest.Builder()
                .setDisplayName(username.toString())// tên// ảnh
                .setPhotoUri(imageProfile)
                .build();
        auth.getCurrentUser().updateProfile(profileUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    User user = new User(auth.getCurrentUser().getUid(),auth.getCurrentUser().getDisplayName(),auth.getCurrentUser().getEmail(),auth.getCurrentUser().getPhotoUrl().toString());
                    saveUsertoDb(user);
                }

            }
        });
    }

    private void saveUsertoDb(User user) {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put(user.getId(),user);
        reference.updateChildren(updateData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Snackbar.make(UpdateProfileScreen.this.getView(),"Update user Successfully",
                            Snackbar.LENGTH_SHORT).show();
                    sharePreferenceUtils.saveUser(user,requireContext());
                }
            }
        });
    }
}