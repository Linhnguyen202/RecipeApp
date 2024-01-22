package com.example.recipeapp.share;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.recipeapp.model.User;
import com.google.gson.Gson;

public class sharePreferenceUtils {
    public static Boolean isSharedPreferencesExist(Context context, String name, String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }
    public static void saveUser(User user, Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        sharedPreferences.edit().putString("USER_VALUE",userJson).apply();
    }

    public static User getUser( Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String user = sharedPreferences.getString("USER_VALUE",null);
        return gson.fromJson(user, User.class);
    }

    public static void removeUser(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("User",Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("USER_VALUE").apply();
    }
}
