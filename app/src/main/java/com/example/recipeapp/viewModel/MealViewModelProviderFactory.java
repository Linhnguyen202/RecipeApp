package com.example.recipeapp.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipeapp.application.MyApplication;
import com.example.recipeapp.repository.MealRepository;

public class MealViewModelProviderFactory implements ViewModelProvider.Factory {
    private MyApplication application;
    private MealRepository mealRepository;
    public MealViewModelProviderFactory(MyApplication application, MealRepository mealRepository){
        this.application = application;
        this.mealRepository = mealRepository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MealViewModel(application, mealRepository);

    }
}
