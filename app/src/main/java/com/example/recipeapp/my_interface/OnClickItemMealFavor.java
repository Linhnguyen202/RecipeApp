package com.example.recipeapp.my_interface;

import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.MealDetail;

public interface OnClickItemMealFavor {
    void onClickItemMeal(MealDetail meal);

    void onClickDislikeDFavor(MealDetail meal);
}
