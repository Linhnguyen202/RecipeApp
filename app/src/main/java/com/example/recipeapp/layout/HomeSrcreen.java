package com.example.recipeapp.layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.recipeapp.R;
import com.example.recipeapp.activity.DetailScreen;
import com.example.recipeapp.activity.DetailScreenArgs;
import com.example.recipeapp.adapter.CategoryAdapter;
import com.example.recipeapp.adapter.MealApdater;
import com.example.recipeapp.application.MyApplication;
import com.example.recipeapp.databinding.FragmentHomeSrcreenBinding;
import com.example.recipeapp.model.Category;
import com.example.recipeapp.model.CategoryResponse;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.MealReponse;
import com.example.recipeapp.my_interface.OnClickIItemCategory;
import com.example.recipeapp.my_interface.OnClickItemMeals;
import com.example.recipeapp.repository.MealRepository;
import com.example.recipeapp.utils.NetworkConnection;
import com.example.recipeapp.viewModel.MealViewModel;
import com.example.recipeapp.viewModel.MealViewModelProviderFactory;

import java.util.ArrayList;

public class HomeSrcreen extends Fragment {
    private FragmentHomeSrcreenBinding binding;

    private MealViewModel mealViewModel;
    private MealViewModelProviderFactory mealViewModelProviderFactory;

    private MealRepository mealRepository;

    private MealApdater mealApdater;

    private CategoryAdapter categoryAdapter;
    private NetworkConnection networkConnection;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeSrcreenBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addSlider();
        setUp();

        networkConnection = new NetworkConnection(requireContext());
        networkConnection.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    getData();
                }

            }
        });
        observerData();


    }

    private void addSlider() {
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel("https://i.pinimg.com/564x/34/f4/8f/34f48f5c56c938642b80b0555e5adf82.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://i.pinimg.com/564x/1e/11/5e/1e115eb17da8de8eb278df6548da461f.jpg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://i.pinimg.com/564x/5f/ef/45/5fef45d01c4896e0ecc1bf83ff4d8823.jpg", ScaleTypes.CENTER_CROP));

        binding.imgRandomMeal.setImageList(imageList);
        binding.imgRandomMeal.startSliding(2000);
    }

    private void setUp() {
        mealRepository = new MealRepository(requireContext());
        mealViewModelProviderFactory = new MealViewModelProviderFactory(new MyApplication(),mealRepository);
        mealViewModel = new ViewModelProvider(this,
                mealViewModelProviderFactory).get(MealViewModel.class);
        mealApdater = new MealApdater(new OnClickItemMeals() {
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
        categoryAdapter = new CategoryAdapter(new OnClickIItemCategory() {
            @Override
            public void onClickItemCategory(Category category) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Category",category);
                Navigation.findNavController(getView()).navigate(R.id.action_homeSrcreen_to_mealCategoryScreen,bundle);
            }
        });
        binding.recViewMealsPopular.setAdapter(mealApdater);
        binding.cvCategory.setAdapter(categoryAdapter);
    }

    private void observerData() {
        mealViewModel.mealList.observe(getViewLifecycleOwner(), new Observer<MealReponse>() {
            @Override
            public void onChanged(MealReponse mealReponse) {
                mealApdater.setData(mealReponse.getMeals());
            }
        });
        mealViewModel.categoryList.observe(getViewLifecycleOwner(), new Observer<CategoryResponse>() {
            @Override
            public void onChanged(CategoryResponse categoryResponse) {
                categoryAdapter.setData(categoryResponse.getCategories());
            }
        });
    }

    private void getData() {
        mealViewModel.getMealData("beef");
    }
}