package com.example.recipeapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.example.recipeapp.databinding.ActivityRegisterScreen2Binding;
import com.example.recipeapp.model.User;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URI;

public class RegisterScreen_2 extends AppCompatActivity {
    ActivityRegisterScreen2Binding binding;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference reference;

    private Uri imageProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreen2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("User");
        addEvents();

    }

    private void addEvents() {
        binding.usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.userContainer.setError("");
                    binding.userContainer.setErrorEnabled(false);
                }
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(RegisterScreen_2.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

    }
    // tra du lieu anh

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageProfile = data.getData();
        binding.imageProfile.setImageURI(imageProfile);

    }
    private Boolean validateUsername(){
        String username = binding.usernameEditText.getText().toString();
        String error = "";
        if(username.isEmpty()){
            error = "Username is required";
        }
        if(!error.isEmpty()){
            binding.userContainer.setError(error);
            binding.userContainer.setErrorEnabled(true);

        }
        return !error.isEmpty();

    }
    private void onSubmit() {
        if(!validateUsername()){
            Intent intent = getIntent();
            String username = binding.usernameEditText.getText().toString();
            String email = intent.getExtras().getString("email");
            String pass = intent.getExtras().getString("password");
            UserProfileChangeRequest profileUser = new UserProfileChangeRequest.Builder()
                    .setDisplayName(username.toString())// tên// ảnh
                    .setPhotoUri(imageProfile)
                    .build();
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        auth.getCurrentUser().updateProfile(profileUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                saveUserToDB();
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterScreen_2.this,"Authentication is Failed",Toast.LENGTH_LONG).show();
                    }

                }

            });
        }

    }

    private void saveUserToDB() {
        User user = new User(auth.getCurrentUser().getUid(),auth.getCurrentUser().getDisplayName(),auth.getCurrentUser().getEmail(),auth.getCurrentUser().getPhotoUrl().toString());
        reference.child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterScreen_2.this,"Successful",Toast.LENGTH_LONG).show();
                auth.signOut();
                startActivity(new Intent(RegisterScreen_2.this, LoginScreen.class));
            }
        });
    }
}