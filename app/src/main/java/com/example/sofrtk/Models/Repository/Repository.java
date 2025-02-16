package com.example.sofrtk.Models.Repository;

import android.content.Context;

import com.example.sofrtk.DB.FoodLocalDataSource;
import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.Models.DTOs.CategoryResponse;
import com.example.sofrtk.Models.DTOs.CountryResponse;
import com.example.sofrtk.Models.DTOs.FilterMealResponse;
import com.example.sofrtk.Models.DTOs.IngredientResponse;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;
import com.example.sofrtk.Network.FoodRemoteDataSource;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class Repository {

    public static Repository repository = null;
    FoodRemoteDataSource foodRemoteDataSource;
    FoodLocalDataSource foodLocalDataSource;

    private Repository(Context context){
        foodRemoteDataSource = FoodRemoteDataSource.getInstance();
        foodLocalDataSource = FoodLocalDataSource.getInstance(context);
    }
    public static Repository getInstance(Context context){
        if(repository == null){
            repository = new Repository(context);
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


    public Observable<List<FavouriteMeal>> getFavouriteMeals(String userId){ return foodLocalDataSource.getFavouriteMeals(userId);}
    public Completable insertFavouriteMeal(FavouriteMeal favouriteMeal){ return foodLocalDataSource.insertFavouriteMeal(favouriteMeal);}
    public Completable deleteFavouriteMeal(FavouriteMeal favouriteMeal){ return foodLocalDataSource.deleteFavouriteMeal(favouriteMeal);}
}
