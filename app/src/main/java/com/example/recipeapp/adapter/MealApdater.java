package com.example.recipeapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.MealCardBinding;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.my_interface.OnClickItemMeals;
import com.example.recipeapp.viewModel.MealViewModel;

import java.util.List;

public class MealApdater extends RecyclerView.Adapter<MealApdater.MealViewHolder> {
    private List<Meal> listMeal;

    private OnClickItemMeals onClickItemMeals;

    public MealApdater(OnClickItemMeals onClickItemMeals) {
        this.onClickItemMeals = onClickItemMeals;
    }

    class MealViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        MealCardBinding binding;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            binding = MealCardBinding.bind(itemView);
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
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
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
