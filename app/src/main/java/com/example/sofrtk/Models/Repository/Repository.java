package com.example.sofrtk.Models.Repository;

import com.example.sofrtk.Models.DTOs.CategoryResponse;
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

}
