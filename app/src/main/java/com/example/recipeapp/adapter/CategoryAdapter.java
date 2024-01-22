package com.example.recipeapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.databinding.CategoryCardBinding;
import com.example.recipeapp.databinding.MealCardBinding;
import com.example.recipeapp.model.Category;
import com.example.recipeapp.model.CategoryResponse;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.my_interface.OnClickIItemCategory;

import java.util.List;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.CateViewHolder> {
    private List<Category> listMeal;

    private OnClickIItemCategory onClickIItemCategory;

    public CategoryAdapter(OnClickIItemCategory onClickIItemCategory) {
        this.onClickIItemCategory = onClickIItemCategory;
    }

    class CateViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        CategoryCardBinding binding;
        public CateViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            binding = CategoryCardBinding.bind(itemView);
        }

        public void setData(Category category){
            binding.tvCategoryName.setText(category.getStrCategory());
            Glide.with(itemView).load(category.getStrCategoryThumb()).into(binding.imgMeal);
            binding.cvCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickIItemCategory.onClickItemCategory(category);
                }
            });
        }

    }


    @NonNull
    @Override
    public CateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CateViewHolder holder, int position) {
        holder.setData(listMeal.get(position));
    }

    @Override
    public int getItemCount() {
        return (listMeal != null) ? listMeal.size() : 0;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Category> list){
        this.listMeal = list;
        notifyDataSetChanged();
    }


}

