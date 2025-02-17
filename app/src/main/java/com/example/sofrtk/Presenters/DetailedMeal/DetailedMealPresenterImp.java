package com.example.sofrtk.Presenters.DetailedMeal;

import android.content.SharedPreferences;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.DB.Model.PlanMeal;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.DetailedMeal.DetailedMealView;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailedMealPresenterImp implements DetailedMealPresenter {
    DetailedMealView detailedMealView;
    Repository repository;
    RxSharedPreferences rxSharedPreferences;

    public DetailedMealPresenterImp(DetailedMealView detailedMealView, Repository repository) {
        this.detailedMealView = detailedMealView;
        this.repository = repository;
        SharedPreferences sharedPreferences = detailedMealView.getViewActivity().getSharedPreferences("UserPrefs", detailedMealView.getViewActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
    }

    @Override
    public void setMealDetails(String mealId) {
        repository.getMealDetailsById(mealId)
                .subscribeOn(Schedulers.io())
                .map(mealDetails -> mealDetails.randomMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealDetails -> detailedMealView.showMealDetails(mealDetails.get(0)),
                        error -> detailedMealView.showMealDetailsError(error.getMessage())
                );
    }

    @Override
    public void addToFavourite(RandomMeal meal) {
        FavouriteMeal favouriteMeal = new FavouriteMeal(rxSharedPreferences.getString("userId").get(),meal.getIdMeal(),meal);
        repository.insertFavouriteMeal(favouriteMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> detailedMealView.onInsertFavouriteSuccess(),
                        error -> detailedMealView.onInsertFavouriteFail(error.getMessage())
                );
    }

    @Override
    public void addToPlan(RandomMeal meal,String selectedDate) {
        PlanMeal planMeal = new PlanMeal(rxSharedPreferences.getString("userId").get(),meal.getIdMeal(),meal,selectedDate);
        repository.insertPlanMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> detailedMealView.onInsertPlanSuccess(),
                        error -> detailedMealView.onInsertPlanFail(error.getMessage())
                );
    }
}
