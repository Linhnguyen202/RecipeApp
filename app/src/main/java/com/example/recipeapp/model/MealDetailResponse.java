package com.example.recipeapp.model;

import java.io.Serializable;
import java.util.List;

public class MealDetailResponse implements Serializable {
    private List<MealDetail> meals;

    public MealDetailResponse(List<MealDetail> meals) {
        this.meals = meals;
    }

    public List<MealDetail> getMeals() {
        return meals;
    }

    public void setMeals(List<MealDetail> meals) {
        this.meals = meals;
    }
}
