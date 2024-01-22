package com.example.recipeapp.model;

import java.io.Serializable;
import java.util.List;

public class MealReponse implements Serializable {
    private List<Meal> meals;

    public MealReponse() {
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public MealReponse(List<Meal> meals) {
        this.meals = meals;
    }
}
