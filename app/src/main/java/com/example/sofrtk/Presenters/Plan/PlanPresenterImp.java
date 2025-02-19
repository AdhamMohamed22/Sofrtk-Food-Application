package com.example.sofrtk.Presenters.Plan;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.sofrtk.DB.Model.PlanMeal;
import com.example.sofrtk.Firebase.Firebase;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.Plan.PlanView;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImp implements PlanPresenter {
    PlanView planView;
    Repository repository;
    RxSharedPreferences rxSharedPreferences;

    public PlanPresenterImp(PlanView planView, Repository repository) {
        this.planView = planView;
        this.repository = repository;
        SharedPreferences sharedPreferences = planView.getViewActivity().getSharedPreferences("UserPrefs", planView.getViewActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);
    }


    @Override
    public void getPlanMeals(String userId, String mealDate) {
        repository.getPlanMeals(userId, mealDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        planMeals -> planView.showPlanMeals(planMeals),
                        error -> planView.showPlanMealsError(error.getMessage())
                );
    }

    @Override
    public void deletePlanMeal(PlanMeal planMeal) {
        Firebase.getInstance().deletePlanMeal(planMeal, rxSharedPreferences.getString("userId").get(), planMeal.getMealId(), planMeal.getMealDate())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        repository.deletePlanMeal(planMeal)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> planView.deletePlanMeals(planMeal),
                                        error -> planView.deletePlanMealsError(error.getMessage())
                                );
                    }
                });
    }
}
