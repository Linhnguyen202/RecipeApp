package com.example.recipeapp.api;

import com.example.recipeapp.model.CategoryResponse;
import com.example.recipeapp.model.MealDetailResponse;
import com.example.recipeapp.model.MealReponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApi {
    @GET("search.php")
    Observable<MealReponse> getMeal(@Query("s") String type);

    @GET("lookup.php")
    Observable<MealDetailResponse> getDetailMeal(@Query("i") String type);

    @GET("categories.php")
    Observable<CategoryResponse> getCategory();

    @GET("filter.php")
    Observable<MealReponse> getMealCategory(@Query("c") String type);





}