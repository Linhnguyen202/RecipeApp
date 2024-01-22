package com.example.recipeapp.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recipeapp.R;
import com.example.recipeapp.application.MyApplication;
import com.example.recipeapp.databinding.ActivityDetailScreenBinding;
import com.example.recipeapp.model.MealDetail;
import com.example.recipeapp.model.MealDetailResponse;
import com.example.recipeapp.model.User;
import com.example.recipeapp.repository.MealRepository;
import com.example.recipeapp.share.sharePreferenceUtils;
import com.example.recipeapp.viewModel.MealViewModel;
import com.example.recipeapp.viewModel.MealViewModelProviderFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

public class DetailScreen extends AppCompatActivity {
    ActivityDetailScreenBinding binding;
    private Boolean isExpanded = false;
    private Menu menu;

    public MealViewModel mealViewModel;
    private MealViewModelProviderFactory mealViewModelProviderFactory;

    private MealRepository mealRepository;

    FirebaseDatabase db;
    DatabaseReference reference;
    private String ID;

    private MealDetail mealDetail;
    private Boolean liked = false;

    private Boolean isFullScreen = false;

    private YouTubePlayer youTubePlayer;
    OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            if(isFullScreen){
                youTubePlayer.toggleFullscreen();
            }
            else{
                finish();
            }
        }
    };
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
        ID =  getIntent().getExtras().get("Id").toString();
        db = FirebaseDatabase.getInstance();
        reference = db.getReference("Favorite");
        mealRepository = new MealRepository(this);
        mealViewModelProviderFactory = new MealViewModelProviderFactory(new MyApplication(),mealRepository);
        mealViewModel = new ViewModelProvider(this,
                mealViewModelProviderFactory).get(MealViewModel.class);
        initToolbar();

        getData(ID);
        observerData();



    }



    private void observerData() {
        mealViewModel.mealDetail.observe(this, new Observer<MealDetailResponse>() {
            @Override
            public void onChanged(MealDetailResponse mealDetailResponse) {
                mealDetail = mealDetailResponse.getMeals().get(0);
                String ingredient = mealDetailResponse.getMeals().get(0).getStrMeasure1() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient1()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure2() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient2()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure3() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient3()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure4() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient4()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure5() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient5()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure6() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient6()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure7() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient7()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure8() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient8()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure9() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient9()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure10() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient10()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure11() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient11()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure12() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient12()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure13() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient13()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure14() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient14()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure15() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient15()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure16() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient16()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure17() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient17()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure18() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient18()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure19() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient19()+"\n"
                        + mealDetailResponse.getMeals().get(0).getStrMeasure20() +"  "+mealDetailResponse.getMeals().get(0).getStrIngredient20()+"\n";
                binding.progressBar.setVisibility(View.GONE);
                binding.collapsingToolbar.setTitle(mealDetailResponse.getMeals().get(0).getStrMeal().toString());
                binding.tvAreaInfo.setText("Area " + mealDetailResponse.getMeals().get(0).getStrArea().toString());
                binding.tvCategoryInfo.setText("Category " + mealDetailResponse.getMeals().get(0).getStrCategory().toString());
                binding.tvIngredient.setText(ingredient);
                binding.tvIngredient.setLineSpacing(1,2);
                binding.tvInstructions.setText(mealDetailResponse.getMeals().get(0).getStrInstructions().toString());
                binding.tvInstructions.setLineSpacing(1,(float) 1.5);
                Glide.with(DetailScreen.this).load(mealDetailResponse.getMeals().get(0).getStrMealThumb().toString()).into(binding.imgMealDetail);
               String[] videoLink = mealDetailResponse.getMeals().get(0).getStrYoutube().split("=");

               initYoutubeVideo(videoLink[1]);

               checkFavorMeal();
            }
        });
    }

    private void initYoutubeVideo(String videoLink) {
        getLifecycle().addObserver(binding.videoFrame);
        binding.videoFrame.addFullscreenListener(new FullscreenListener() {
            @Override
            public void onEnterFullscreen(@NonNull View view, @NonNull Function0<Unit> function0) {
                isFullScreen = true;
                binding.fullScreenContainer.setVisibility(View.VISIBLE);
                binding.fullScreenContainer.addView(view);

                if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                }
            }

            @Override
            public void onExitFullscreen() {
                isFullScreen = false;
                binding.fullScreenContainer.setVisibility(View.GONE);
                binding.fullScreenContainer.removeAllViews();

                if(getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }
        });

        AbstractYouTubePlayerListener abstractYouTubePlayerListener = new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                DetailScreen.this.youTubePlayer = youTubePlayer;
                youTubePlayer.loadVideo(videoLink,0);
            }
        };

        IFramePlayerOptions iFramePlayerOptions = new IFramePlayerOptions.Builder()
                .controls(1)
                .fullscreen(1).build();
        binding.videoFrame.setEnableAutomaticInitialization(false);
        binding.videoFrame.initialize(abstractYouTubePlayerListener,iFramePlayerOptions);


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(!isFullScreen){
                youTubePlayer.toggleFullscreen();
            }

        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            if(isFullScreen){
                youTubePlayer.toggleFullscreen();
            }

        }
    }

    private void getData(String id) {
        binding.progressBar.setVisibility(View.VISIBLE);
        mealViewModel.getDetailMeal(id);
    }



    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Title");
        }
        int primary = ContextCompat.getColor(this, R.color.primary);
        binding.collapsingToolbar.setContentScrimColor(primary);
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CustomToolbarTheme);
        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 200){

                    isExpanded = false;
                }
                else{
                    isExpanded = true;
                }
                invalidateOptionsMenu();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_detail,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(liked){
            menu.getItem(0).setIcon(R.drawable.ic_saved).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            this.menu = menu;
            return true;
        }
        else{
            menu.getItem(0).setIcon(R.drawable.ic_baseline_save24).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            this.menu = menu;
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.likeButton){
            if(liked){
                removeFavor();
            }
            else{
                saveFavor();
            }

            invalidateOptionsMenu();
            return true;

        }
        else if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void saveFavor() {
        User user = sharePreferenceUtils.getUser(this);
        reference.child(user.getId().toString()).child(mealDetail.getIdMeal()).setValue(mealDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    liked = true;
                }

            }
        });
    }
    private void removeFavor(){
        User user = sharePreferenceUtils.getUser(this);
        reference.child(user.getId().toString()).child(mealDetail.getIdMeal().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                liked = false;
            }
        });
    }
    private void checkFavorMeal(){
        User user = sharePreferenceUtils.getUser(this);
            reference.child(user.getId().toString()).child(mealDetail.getIdMeal()).orderByChild("idMeal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                   liked = true;
                    invalidateOptionsMenu();
                }
                else{
                    liked = false;
                    invalidateOptionsMenu();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}