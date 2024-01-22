package com.example.recipeapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.MealCardBinding;
import com.example.recipeapp.databinding.MealCategoryCardBinding;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.my_interface.OnClickItemMeals;

import java.util.List;

public class MealCategoryAdapter extends RecyclerView.Adapter<MealCategoryAdapter.MealCategoryViewHolder> {
    private List<Meal> listMeal;

    private OnClickItemMeals onClickItemMeals;

    public MealCategoryAdapter(OnClickItemMeals onClickItemMeals) {
        this.onClickItemMeals = onClickItemMeals;
    }

    class MealCategoryViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        MealCategoryCardBinding binding;
        public MealCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            binding = MealCategoryCardBinding.bind(itemView);
        }

        public void setData(Meal meal){
            binding.tvMealName.setText(meal.getStrMeal());
            Glide.with(itemView).load(meal.getStrMealThumb()).into(binding.imgMeal);
            binding.cardFood.setOnClickListener(view -> {
                onClickItemMeals.onClickItemMeal(meal);
            });
        }


    }
    @NonNull
    @Override
    public MealCategoryAdapter.MealCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_category_card, parent, false);
        return new MealCategoryAdapter.MealCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealCategoryAdapter.MealCategoryViewHolder holder, int position) {
        holder.setData(listMeal.get(position));
    }

    @Override
    public int getItemCount() {
        return (listMeal != null) ? listMeal.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Meal> list){
        this.listMeal = list;
        notifyDataSetChanged();
    }

}
