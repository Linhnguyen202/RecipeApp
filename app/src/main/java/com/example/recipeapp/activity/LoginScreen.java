package com.example.recipeapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.recipeapp.databinding.ActivityLoginScreenBinding;
import com.example.recipeapp.model.User;
import com.example.recipeapp.share.sharePreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class LoginScreen extends AppCompatActivity {
    private ActivityLoginScreenBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        addEvents();
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
    private Boolean validate(){
        Boolean validateValue = false;
        if(validateEmail()) validateValue = true;
        if(validatePass()) validateValue = true;
        return validateValue;
    }
    private void addEvents() {
        binding.createAccTitle.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterScreen.class));
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

        binding.btnLogin.setOnClickListener(v -> {
            if(!validate()){
                onSubmit();
            }



        });
    }

    private void onSubmit() {
        String email = binding.emailEditText.getText().toString();
        String password = binding.passEditText.getText().toString();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(auth.getCurrentUser().getUid(),auth.getCurrentUser().getDisplayName(),auth.getCurrentUser().getEmail(),auth.getCurrentUser().getPhotoUrl().toString());
                        sharePreferenceUtils.saveUser(user,LoginScreen.this);
                        startActivity(new Intent(LoginScreen.this, MainScreen.class));
                    }
                    else{
                        try {
                            throw task.getException();
                        } catch(FirebaseAuthException e) {
                            Toast.makeText(LoginScreen.this,"Authentication is Failed", Toast.LENGTH_LONG).show();
                        }
                        catch (FirebaseNetworkException e){
                            Toast.makeText(LoginScreen.this,"Internet disconnected", Toast.LENGTH_LONG).show();
                        }catch (Exception e) {
                            throw new RuntimeException(e);
                        }

                    }

                }
            });




    }
}