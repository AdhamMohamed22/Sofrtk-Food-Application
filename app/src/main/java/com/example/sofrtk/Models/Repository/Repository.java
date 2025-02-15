package com.example.sofrtk.Models.Repository;

import com.example.sofrtk.Models.DTOs.CategoryResponse;
import com.example.sofrtk.Models.DTOs.CountryResponse;
import com.example.sofrtk.Models.DTOs.FilterMealResponse;
import com.example.sofrtk.Models.DTOs.IngredientResponse;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;
import com.example.sofrtk.Network.FoodRemoteDataSource;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repository {
    public static Repository repository = null;
    FoodRemoteDataSource foodRemoteDataSource;

    private Repository(){
        foodRemoteDataSource = FoodRemoteDataSource.getInstance();
    }
    public static Repository getInstance(){
        if(repository == null){
            repository = new Repository();
        }
        return repository;
    }

    public Single<RandomMealResponse> getRandomMeal(){
        return foodRemoteDataSource.getRandomMeal();
    }

    public Observable<CategoryResponse> getCategories(){
        return foodRemoteDataSource.getCategories();
    }

    public Observable<CountryResponse> getCountries(){
        return foodRemoteDataSource.getCountries();
    }
    public Observable<IngredientResponse> getIngredients(){
        return foodRemoteDataSource.getIngredients();
    }
    public Observable<FilterMealResponse> getFilterMealsByCategory(String filterCategory){
        return foodRemoteDataSource.getFilterMealsByCategory(filterCategory);
    }
    public Observable<FilterMealResponse> getFilterMealsByArea(String filterArea){
        return foodRemoteDataSource.getFilterMealsByArea(filterArea);
    }
    public Observable<FilterMealResponse> getFilterMealsByIngredient(String filterIngredient){
        return foodRemoteDataSource.getFilterMealsByIngredient(filterIngredient);
    }
    public Observable<RandomMealResponse> getMealDetailsById(String mealId){
        return foodRemoteDataSource.getMealDetailsById(mealId);
    }
}
