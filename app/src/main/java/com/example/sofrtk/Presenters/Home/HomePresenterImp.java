package com.example.sofrtk.Presenters.Home;

import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.Home.HomeView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImp implements HomePresenter {
    HomeView homeView;
    Repository repository;

    public HomePresenterImp(HomeView homeView, Repository repository) {
        this.homeView = homeView;
        this.repository = repository;
    }

    @Override
    public void setRandomMeal(){
        repository.getRandomMeal()
                .subscribeOn(Schedulers.io())
                .map(randomMealResponse -> randomMealResponse.randomMealsList)
                .repeat(3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        randomMeal -> homeView.showRandomMeal(randomMeal),
                        error -> {homeView.showRandomMealError(error.getMessage());});
    }

    @Override
    public void setCategories() {
        repository.getCategories()
                .subscribeOn(Schedulers.io())
                .map(categoryResponse -> categoryResponse.categoriesList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> homeView.showCategories(categories),
                        error -> homeView.showCategoriesError(error.getMessage()));
    }
}
