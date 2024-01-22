package com.example.recipeapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.LikedItemBinding;
import com.example.recipeapp.databinding.MealCardBinding;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.MealDetail;
import com.example.recipeapp.my_interface.OnClickItemMealFavor;
import com.example.recipeapp.my_interface.OnClickItemMeals;

import java.util.List;

public class FavorMealAdapter extends RecyclerView.Adapter<FavorMealAdapter.FavorMealViewHolder> {
    private List<MealDetail> listMeal;

    private OnClickItemMealFavor onClickItemMeals;

    public FavorMealAdapter(OnClickItemMealFavor onClickItemMeals) {
        this.onClickItemMeals = onClickItemMeals;
    }

    class FavorMealViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        LikedItemBinding binding;
        public FavorMealViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            binding = LikedItemBinding.bind(itemView);
        }

        public void setData(MealDetail meal){
            binding.tvMealName.setText(meal.getStrMeal());
            binding.tvCategoryInfo.setText(meal.getStrCategory());
            binding.tvAreaInfo.setText(meal.getStrArea());
            Glide.with(itemView).load(meal.getStrMealThumb()).into(binding.imgMeal);
            binding.cardFood.setOnClickListener(view -> {
                onClickItemMeals.onClickItemMeal(meal);
            });
            binding.likeButton.setChecked(true);
            binding.likeButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!isChecked){
                        onClickItemMeals.onClickDislikeDFavor(meal);
                    }
                }
            });
        }


    }
    @NonNull
    @Override
    public FavorMealAdapter.FavorMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liked_item, parent, false);
        return new FavorMealAdapter.FavorMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavorMealAdapter.FavorMealViewHolder holder, int position) {
        holder.setData(listMeal.get(position));
    }

    @Override
    public int getItemCount() {
        return (listMeal != null) ? listMeal.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MealDetail> list){
        this.listMeal = list;
        notifyDataSetChanged();
    }


}