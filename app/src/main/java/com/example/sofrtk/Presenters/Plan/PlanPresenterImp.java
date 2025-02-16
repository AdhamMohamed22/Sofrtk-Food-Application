package com.example.sofrtk.Presenters.Plan;

import com.example.sofrtk.DB.Model.PlanMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.Plan.PlanView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanPresenterImp implements PlanPresenter{
    PlanView planView;
    Repository repository;

    public PlanPresenterImp(PlanView planView, Repository repository) {
        this.planView = planView;
        this.repository = repository;
    }


    @Override
    public void getPlanMeals(String userId,String mealDate) {
        repository.getPlanMeals(userId,mealDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        planMeals -> planView.showPlanMeals(planMeals),
                        error -> planView.showPlanMealsError(error.getMessage())
                );
    }

    @Override
    public void deletePlanMeal(PlanMeal planMeal) {
        repository.deletePlanMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> planView.deletePlanMeals(planMeal),
                        error -> planView.deletePlanMealsError(error.getMessage())
                );
    }
}
