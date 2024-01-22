package com.example.recipeapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class NetworkConnection extends LiveData<Boolean> {
    private Context context;
    private ConnectivityManager connectivityManager;



    public NetworkConnection(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    NetworkRequest networkRequest = new NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        .build();

    ConnectivityManager.NetworkCallback callback = new ConnectivityManager.NetworkCallback(){
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            postValue(true);
        }

        @Override
        public void onUnavailable() {
            super.onUnavailable();
            postValue(false);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            postValue(false);
        }
    };

    @Override
    protected void onActive() {
        super.onActive();
        Network network = connectivityManager.getActiveNetwork();
        if(network == null){
            postValue(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            connectivityManager.registerDefaultNetworkCallback(callback);
        }else{
            connectivityManager.registerNetworkCallback(networkRequest,callback);
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        connectivityManager.unregisterNetworkCallback(callback);
    }
}
