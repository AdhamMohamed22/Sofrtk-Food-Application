package com.example.sofrtk.Presenters.Favourite;

import android.util.Log;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.Favourite.FavouriteView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenterImp implements FavouritePresenter{
    FavouriteView favouriteView;
    Repository repository;

    public FavouritePresenterImp(FavouriteView favouriteView, Repository repository) {
        this.favouriteView = favouriteView;
        this.repository = repository;
    }

    @Override
    public void getFavouriteMeals(String userId) {
        repository.getFavouriteMeals(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        favouriteMeals -> favouriteView.showFavouriteMeals(favouriteMeals),
                        error -> favouriteView.showFavouriteMealsError(error.getMessage())
                );
    }

    @Override
    public void deleteFavouriteMeal(FavouriteMeal favouriteMeal) {
        repository.deleteFavouriteMeal(favouriteMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> favouriteView.deleteFavouriteMeals(favouriteMeal),
                        error -> favouriteView.deleteFavouriteMealsError(error.getMessage())
                );
    }
}
