package com.example.sofrtk.Presenters.DetailedMeal;

import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.DetailedMeal.DetailedMealView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailedMealPresenterImp implements DetailedMealPresenter {
    DetailedMealView detailedMealView;
    Repository repository;

    public DetailedMealPresenterImp(DetailedMealView detailedMealView, Repository repository) {
        this.detailedMealView = detailedMealView;
        this.repository = repository;
    }

    @Override
    public void setMealDetails(String mealId) {
        repository.getMealDetailsById(mealId)
                .subscribeOn(Schedulers.io())
                .map(mealDetails -> mealDetails.randomMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealDetails -> detailedMealView.showMealDetails(mealDetails),
                        error -> detailedMealView.showMealDetailsError(error.getMessage())
                );
    }
}
