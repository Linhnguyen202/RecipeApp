package com.example.recipeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.recipeapp.R;
import com.example.recipeapp.databinding.ActivityRegisterScreenBinding;
import com.example.recipeapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class RegisterScreen extends AppCompatActivity {
    private ActivityRegisterScreenBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("User");
        addEvents();
    }

    private void addEvents() {
        binding.btnRegister.setOnClickListener(v -> {
            onSubmit();
        });
        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        binding.emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.emailContainer.setError("");
                    binding.emailContainer.setErrorEnabled(false);
                }
            }
        });

        binding.passEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.passContainer.setError("");
                    binding.passContainer.setErrorEnabled(false);
                }
            }
        });


        binding.confirmPassEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    binding.confirmPassContainer.setError("");
                    binding.confirmPassContainer.setErrorEnabled(false);
                }
            }
        });
        
    }

    private Boolean validateEmail(){
        String email = binding.emailEditText.getText().toString();
        String error = "";
        if(email.isEmpty()){
            error = "Email is required";
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            error = "Email is not valid";
        }
        if(!error.isEmpty()){
            binding.emailContainer.setError(error);
            binding.emailContainer.setErrorEnabled(true);

        }
        return !error.isEmpty();

    }

    private Boolean validatePass(){
        String pass = binding.passEditText.getText().toString();
        String error = "";
        if(pass.isEmpty()){
            error = "Password is required";
        }
        else if(pass.length() < 6){
            error = "Password at least 6 characters";
        }
        if(!error.isEmpty()){
            binding.passContainer.setError(error);
            binding.passContainer.setErrorEnabled(true);
        }
        return !error.isEmpty();
    }

    private Boolean validateConfirmPass(){
        String pass = binding.passEditText.getText().toString();
        String confirmPass = binding.confirmPassEditText.getText().toString();
        String error = "";
        if(confirmPass.isEmpty()){
            error = "Confirm Password is required";
        }
        else if(!pass.equals(confirmPass)){
            error = "Confirm Password is not same Password";
        }
        if(!error.isEmpty()){
            binding.confirmPassContainer.setError(error);
            binding.confirmPassContainer.setErrorEnabled(true);
        }
        return !error.isEmpty();
    }
    private Boolean validate(){
        Boolean validateValue = false;
        if(validateEmail()) validateValue = true;
        if(validatePass()) validateValue = true;
        if(validateConfirmPass()) validateValue = true;
        return validateValue;
    }
    private void onSubmit() {
        if(!validate()){
            String email = binding.emailEditText.getText().toString();
            String pass = binding.passEditText.getText().toString();
            Intent intent = new Intent(this, RegisterScreen_2.class);
            Bundle bundle = new Bundle();
            bundle.putString("email",email);
            bundle.putString("password",pass);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void saveUserToDB() {
        User user = new User(auth.getCurrentUser().getUid(),auth.getCurrentUser().getDisplayName(),auth.getCurrentUser().getEmail(),auth.getCurrentUser().getPhotoUrl().toString());
        reference.child(user.getId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterScreen.this,"Successful",Toast.LENGTH_LONG).show();
                auth.signOut();
                startActivity(new Intent(RegisterScreen.this, LoginScreen.class));
            }
        });
    }
}