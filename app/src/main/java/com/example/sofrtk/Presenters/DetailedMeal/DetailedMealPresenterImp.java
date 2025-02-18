package com.example.sofrtk.Presenters.DetailedMeal;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.DB.Model.PlanMeal;
import com.example.sofrtk.Firebase.Firebase;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.DetailedMeal.DetailedMealView;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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
        Firebase.getInstance().insertFavouriteMeal(favouriteMeal,rxSharedPreferences.getString("userId").get(),meal.getIdMeal())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    repository.insertFavouriteMeal(favouriteMeal)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    () -> detailedMealView.onInsertFavouriteSuccess(),
                                                    error -> detailedMealView.onInsertFavouriteFail(error.getMessage())
                                            );
                                } else {
                                    detailedMealView.showMealDetailsError(task.getException().toString());
                                }
                            }
                        });
    }

    @Override
    public void addToPlan(RandomMeal meal,String selectedDate) {
        PlanMeal planMeal = new PlanMeal(rxSharedPreferences.getString("userId").get(),meal.getIdMeal(),meal,selectedDate);
        Firebase.getInstance().insertPlanMeal(planMeal,rxSharedPreferences.getString("userId").get(),meal.getIdMeal(),selectedDate)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                repository.insertPlanMeal(planMeal)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(
                                                () -> detailedMealView.onInsertPlanSuccess(),
                                                error -> detailedMealView.onInsertPlanFail(error.getMessage())
                                        );
                            }
                        });
    }
}
