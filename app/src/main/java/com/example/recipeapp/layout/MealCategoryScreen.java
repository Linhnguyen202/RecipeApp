package com.example.recipeapp.layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.activity.DetailScreen;
import com.example.recipeapp.adapter.MealApdater;
import com.example.recipeapp.adapter.MealCategoryAdapter;
import com.example.recipeapp.application.MyApplication;
import com.example.recipeapp.databinding.FragmentMealCategoryScreenBinding;
import com.example.recipeapp.model.Category;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.MealReponse;
import com.example.recipeapp.my_interface.OnClickItemMeals;
import com.example.recipeapp.repository.MealRepository;
import com.example.recipeapp.viewModel.MealViewModel;
import com.example.recipeapp.viewModel.MealViewModelProviderFactory;

import java.util.ArrayList;
import java.util.Objects;

public class MealCategoryScreen extends Fragment {
    FragmentMealCategoryScreenBinding binding;
    private MealViewModel mealViewModel;
    private MealViewModelProviderFactory mealViewModelProviderFactory;

    private MealRepository mealRepository;

    private MealCategoryAdapter mealCategoryAdapter;
    private  Category data;
    private ArrayList<Meal> mealList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMealCategoryScreenBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        data = (Category) getArguments().getSerializable("Category");
        setUp();
        getData();
        observerData();
        addData();
        addEvents();

    }

    private void observerData() {
        mealViewModel.mealCate.observe(getViewLifecycleOwner(), new Observer<MealReponse>() {
            @Override
            public void onChanged(MealReponse mealReponse) {
                mealCategoryAdapter.setData(mealReponse.getMeals());
                mealList.addAll(mealReponse.getMeals());
            }
        });
    }

    private void getData() {
        mealViewModel.getMealCategory(data.getStrCategory());
    }

    private void setUp() {
        mealRepository = new MealRepository(requireContext());
        mealViewModelProviderFactory = new MealViewModelProviderFactory(new MyApplication(),mealRepository);
        mealViewModel = new ViewModelProvider(this,
                mealViewModelProviderFactory).get(MealViewModel.class);
        mealCategoryAdapter = new MealCategoryAdapter(new OnClickItemMeals() {
            @Override
            public void onClickItemMeal(Meal meal) {
                String idMeal = meal.getIdMeal();
                Bundle bundle = new Bundle();
                bundle.putString("Id",idMeal);
                Intent intent = new Intent(requireContext(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        });
        binding.mealRecyclerview.setAdapter(mealCategoryAdapter);
    }
    private void addData() {
        binding.toolbar.title.setText(data.getStrCategory());
        binding.tvDescCategories.setText(data.getStrCategoryDescription());
        Glide.with(this).load(data.getStrCategoryThumb()).into(binding.imgCategories);
        Glide.with(this).load(data.getStrCategoryThumb()).into(binding.imgCategoriesBg);
    }
    private void addEvents() {
        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });
        binding.searchMeal.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterSearchMeal(newText);
                return true;

            }
        });
    }

    private void filterSearchMeal(String query){
        if(query!=null){
            ArrayList<Meal> filterList = new ArrayList<>();
            for(Meal meal : mealList){
                if(meal.getStrMeal().contains(query)){
                    filterList.add(meal);
                }
            }
            mealCategoryAdapter.setData(filterList);
        }

    }

}