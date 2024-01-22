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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.R;
import com.example.recipeapp.activity.DetailScreen;
import com.example.recipeapp.adapter.SearchAdapter;
import com.example.recipeapp.application.MyApplication;
import com.example.recipeapp.databinding.FragmentSearchScreenBinding;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.MealReponse;
import com.example.recipeapp.my_interface.OnClickItemMeals;
import com.example.recipeapp.repository.MealRepository;
import com.example.recipeapp.viewModel.MealViewModel;
import com.example.recipeapp.viewModel.MealViewModelProviderFactory;


public class SearchScreen extends Fragment {

    private FragmentSearchScreenBinding binding;
    private MealViewModel mealViewModel;
    private MealViewModelProviderFactory mealViewModelProviderFactory;

    private MealRepository mealRepository;

    private SearchAdapter searchAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchScreenBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealRepository = new MealRepository(requireContext());
        mealViewModelProviderFactory = new MealViewModelProviderFactory(new MyApplication(),mealRepository);
        mealViewModel = new ViewModelProvider(this,
                mealViewModelProviderFactory).get(MealViewModel.class);
        searchAdapter = new SearchAdapter(new OnClickItemMeals() {
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
        binding.searchList.setAdapter(searchAdapter);
        addEvents();
        obseverData();
    }

    private void obseverData() {
        mealViewModel.searchingList.observe(getViewLifecycleOwner(), new Observer<MealReponse>() {
            @Override
            public void onChanged(MealReponse mealReponse) {
                searchAdapter.setData(mealReponse.getMeals());
            }
        });
    }

    private void addEvents() {
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListCate(newText);
                return true;
            }
        });
    }
    private void filterListCate(String query) {
        mealViewModel.getSearching(query);
    }
}