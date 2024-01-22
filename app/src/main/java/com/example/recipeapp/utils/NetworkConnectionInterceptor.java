package com.example.recipeapp.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {
    private Context context;

    private Context applicationContext;

    public NetworkConnectionInterceptor(Context context) {
        this.context = context;
        applicationContext = context.getApplicationContext();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if(!isInternetAvailable()){
            throw new IOException("No internet connection");
        }
        return chain.proceed(chain.request());
    }

    private Boolean isInternetAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE
        );
        return connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
