package com.example.sofrtk.Presenters.Favourite;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.Firebase.Firebase;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.Favourite.FavouriteView;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenterImp implements FavouritePresenter {
    FavouriteView favouriteView;
    Repository repository;
    RxSharedPreferences rxSharedPreferences;

    public FavouritePresenterImp(FavouriteView favouriteView, Repository repository) {
        this.favouriteView = favouriteView;
        this.repository = repository;
        SharedPreferences sharedPreferences = favouriteView.getViewActivity().getSharedPreferences("UserPrefs", favouriteView.getViewActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
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
        Firebase.getInstance().deleteFavouriteMeal(favouriteMeal, rxSharedPreferences.getString("userId").get(), favouriteMeal.getMealId())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        repository.deleteFavouriteMeal(favouriteMeal)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> favouriteView.deleteFavouriteMeals(favouriteMeal),
                                        error -> favouriteView.deleteFavouriteMealsError(error.getMessage())
                                );
                    }
                });
    }
}
