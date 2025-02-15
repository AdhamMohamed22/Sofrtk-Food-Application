package com.example.sofrtk.Presenters.FilteredMeals;

import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.FilteredMeals.FilteredMealsView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilteredMealsPresenterImp implements FilteredMealsPresenter {
    FilteredMealsView filteredMealsView;
    Repository repository;

    public FilteredMealsPresenterImp(FilteredMealsView filteredMealsView, Repository repository) {
        this.filteredMealsView = filteredMealsView;
        this.repository = repository;
    }

    @Override
    public void setMealsInCategory(String filterCategory) {
        repository.getFilterMealsByCategory(filterCategory)
                .subscribeOn(Schedulers.io())
                .map(filterMealResponse -> filterMealResponse.filterMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filterMeals -> filteredMealsView.showMealsInCategory(filterMeals),
                        error -> filteredMealsView.showMealsInCategoryError(error.getMessage())
                );
    }

    @Override
    public void setMealsInArea(String filterArea) {
        repository.getFilterMealsByArea(filterArea)
                .subscribeOn(Schedulers.io())
                .map(filterMealResponse -> filterMealResponse.filterMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filterMeals -> filteredMealsView.showMealsInArea(filterMeals),
                        error -> filteredMealsView.showMealsInAreaError(error.getMessage())
                );
    }

    @Override
    public void setMealsInIngredient(String filterIngredient) {
        repository.getFilterMealsByIngredient(filterIngredient)
                .subscribeOn(Schedulers.io())
                .map(filterMealResponse -> filterMealResponse.filterMealsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        filterMeals -> filteredMealsView.showMealsInIngredient(filterMeals),
                        error -> filteredMealsView.showMealsInIngredientError(error.getMessage())
                );
    }
}
