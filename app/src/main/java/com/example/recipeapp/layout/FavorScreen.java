package com.example.recipeapp.layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipeapp.R;
import com.example.recipeapp.activity.DetailScreen;
import com.example.recipeapp.adapter.FavorMealAdapter;
import com.example.recipeapp.databinding.FragmentFavorScreenBinding;
import com.example.recipeapp.model.MealDetail;
import com.example.recipeapp.model.User;
import com.example.recipeapp.my_interface.OnClickItemMealFavor;
import com.example.recipeapp.share.sharePreferenceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FavorScreen extends Fragment {
    private FragmentFavorScreenBinding binding;
    FirebaseDatabase db;
    DatabaseReference reference;

    FavorMealAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavorScreenBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Favorite");
        adapter = new FavorMealAdapter(new OnClickItemMealFavor() {
            @Override
            public void onClickItemMeal(MealDetail meal) {
                String idMeal = meal.getIdMeal();
                Bundle bundle = new Bundle();
                bundle.putString("Id",idMeal);
                Intent intent = new Intent(requireContext(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onClickDislikeDFavor(MealDetail meal) {
                User user = sharePreferenceUtils.getUser(requireContext());
                reference.child(user.getId().toString()).child(meal.getIdMeal().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       getData();
                    }
                });
            }
        });
        binding.rvLiked.setAdapter(adapter);
        getData();
    }

    private void checkFavorMeal(){

    }
    private void getData() {
        ArrayList<MealDetail> mealDetailArrayList = new ArrayList<>();
        User user = sharePreferenceUtils.getUser(requireContext());
        reference.child(user.getId().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    for (DataSnapshot item: snapshot.getChildren()) {
                        mealDetailArrayList.add(item.getValue(MealDetail.class));

                    }
                    adapter.setData(mealDetailArrayList);
                }
                else{
                    adapter.setData(mealDetailArrayList);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}