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
import com.example.recipeapp.databinding.SearchCardBinding;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.my_interface.OnClickItemMeals;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Meal> listMeal;
    private OnClickItemMeals onClickItemMeals;

    public SearchAdapter(OnClickItemMeals onClickItemMeals) {
        this.onClickItemMeals = onClickItemMeals;
    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        SearchCardBinding binding;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            binding = SearchCardBinding.bind(itemView);
        }

        public void setData(Meal meal){
            binding.mealTitle.setText(meal.getStrMeal());
            binding.cateTitle.setText(meal.getStrCategory());
            binding.locateTitle.setText(meal.getStrArea());
            Glide.with(itemView).load(meal.getStrMealThumb()).into(binding.imgMeal);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemMeals.onClickItemMeal(meal);
                }
            });
        }


    }
    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_card, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {
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
