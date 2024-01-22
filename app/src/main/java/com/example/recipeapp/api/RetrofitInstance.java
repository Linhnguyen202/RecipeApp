package com.example.recipeapp.api;





import android.content.Context;

import com.example.recipeapp.utils.NetworkConnectionInterceptor;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 public class RetrofitInstance {

    final static String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance(Context context){
        NetworkConnectionInterceptor networkConnectionInterceptor = new NetworkConnectionInterceptor(context);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(networkConnectionInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return retrofit;
    }


    public static MealApi getMealApi(Context context){
        return RetrofitInstance.getRetrofitInstance(context).create(MealApi.class);
    }

}
