package com.example.sofrtk.Models.Repository;

import com.example.sofrtk.Network.FoodRemoteDataSource;
import com.example.sofrtk.Network.NetworkCallBack;

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

    public void getRandomMeal(NetworkCallBack networkCallBack){
        foodRemoteDataSource.getRandomMeal(networkCallBack);
    }


    public void getCategories(NetworkCallBack networkCallBack){
        foodRemoteDataSource.getCategories(networkCallBack);
    }

}
