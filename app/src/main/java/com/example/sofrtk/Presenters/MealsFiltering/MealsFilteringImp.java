package com.example.sofrtk.Presenters.MealsFiltering;

import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.MealsFiltering.MealsFilteringView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsFilteringImp implements MealsFiltering{
    MealsFilteringView mealsFilteringView;
    Repository repository;

    public MealsFilteringImp(MealsFilteringView mealsFilteringView, Repository repository) {
        this.mealsFilteringView = mealsFilteringView;
        this.repository = repository;
    }

    @Override
    public void setMealsInCategory(String filterCategory) {
        repository.getFilterMealsByCategory(filterCategory)
                .subscribeOn(Schedulers.io())
                .map(filterMealResponse -> filterMealResponse.filterMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filterMeals -> mealsFilteringView.showMealsInCategory(filterMeals),
                        error -> mealsFilteringView.showMealsInCategoryError(error.getMessage())
                );
    }

    @Override
    public void setMealsInArea(String filterArea) {
        repository.getFilterMealsByArea(filterArea)
                .subscribeOn(Schedulers.io())
                .map(filterMealResponse -> filterMealResponse.filterMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filterMeals -> mealsFilteringView.showMealsInArea(filterMeals),
                        error -> mealsFilteringView.showMealsInAreaError(error.getMessage())
                );
    }

    @Override
    public void setMealsInIngredient(String filterIngredient) {
        repository.getFilterMealsByIngredient(filterIngredient)
                .subscribeOn(Schedulers.io())
                .map(filterMealResponse -> filterMealResponse.filterMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filterMeals -> mealsFilteringView.showMealsInIngredient(filterMeals),
                        error -> mealsFilteringView.showMealsInIngredientError(error.getMessage())
                );
    }
}
