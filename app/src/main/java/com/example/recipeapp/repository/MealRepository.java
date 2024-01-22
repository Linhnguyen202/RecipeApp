package com.example.recipeapp.repository;

import android.content.Context;

import com.example.recipeapp.api.MealApi;
import com.example.recipeapp.api.RetrofitInstance;
import com.example.recipeapp.model.CategoryResponse;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.MealDetailResponse;
import com.example.recipeapp.model.MealReponse;
import com.google.android.gms.common.internal.service.Common;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MealRepository {
    private MealApi mealApi;

    private Context context;
    public MealRepository(Context context){
        this.context = context;
        mealApi = RetrofitInstance.getMealApi(context);
    }
    public Observable<MealReponse> getMeal(String type){
       return mealApi.getMeal(type);
    }

    public Observable<MealReponse> getMealCategory(String type){
        return mealApi.getMealCategory(type);
    }

    public Observable<CategoryResponse> getCategory(){
        return mealApi.getCategory();
    }

    public Observable<MealDetailResponse> getMealDetail(String id){
        return mealApi.getDetailMeal(id);
    }


}
