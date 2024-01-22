package com.example.recipeapp.viewModel;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipeapp.application.MyApplication;
import com.example.recipeapp.model.CategoryResponse;
import com.example.recipeapp.model.Meal;
import com.example.recipeapp.model.MealDetailResponse;
import com.example.recipeapp.model.MealReponse;
import com.example.recipeapp.repository.MealRepository;

import java.io.Closeable;
import java.io.Serializable;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealViewModel extends ViewModel {
    private MyApplication application;
    private MealRepository mealRepository;

    private Disposable mdisposable;
    public MutableLiveData<MealReponse> mealList = new MutableLiveData<>();

    public MutableLiveData<CategoryResponse> categoryList = new MutableLiveData<>();

    public MutableLiveData<MealReponse> searchingList = new MutableLiveData<>();


    public MutableLiveData<MealDetailResponse> mealDetail = new MutableLiveData<>();

    public MutableLiveData<MealReponse> mealCate = new MutableLiveData<>();
    public MealViewModel(MyApplication application, MealRepository mealRepository) {
        this.application = application;
        this.mealRepository = mealRepository;
    }

    @SuppressLint("CheckResult")
    public void getMealData(String type){
        Observable.merge(mealRepository.getMeal(type),mealRepository.getCategory())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Serializable>() {
                                    @Override
                                    public void onSubscribe(@NonNull Disposable d) {

                                    }

                                    @Override
                                    public void onNext(@NonNull Serializable o) {
                                        if(o.getClass().isAssignableFrom(CategoryResponse.class)){
                                            categoryList.postValue((CategoryResponse) o);
                                        }
                                        if(o.getClass().isAssignableFrom(MealReponse.class)){
                                            mealList.postValue((MealReponse) o);
                                        }
                                    }

                                    @Override
                                    public void onError(@NonNull Throwable e) {
                                        Log.d("next", "error " + e.toString());
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

    }

    public void getSearching(String query){
                mealRepository.getMeal(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealReponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mdisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull MealReponse mealReponse) {
                        searchingList.postValue(mealReponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("next", "error " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        mdisposable.dispose();
                    }
                });
    }


    public void getDetailMeal(String id){
        mealRepository.getMealDetail(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealDetailResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealDetailResponse mealDetailResponse) {
                        mealDetail.postValue(mealDetailResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getMealCategory(String type){
        mealRepository.getMealCategory(type).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MealReponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MealReponse mealReponse) {
                        mealCate.postValue(mealReponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
