package com.example.sofrtk.Presenters.Home;

import android.util.Log;

import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Network.NetworkCallBack;
import com.example.sofrtk.Views.UI.Main.Home.HomeView;

import java.util.ArrayList;

public class HomePresenterImp implements HomePresenter, NetworkCallBack {
    HomeView homeView;
    Repository repository;

    public HomePresenterImp(HomeView homeView, Repository repository) {
        this.homeView = homeView;
        this.repository = repository;
    }

    @Override
    public void getRandomMeal() {
        repository.getRandomMeal(this);
    }

    @Override
    public void getCategories() { repository.getCategories(this); }

    @Override
    public void onRandomMealSuccess(ArrayList<RandomMeal> randomMealList) {
        homeView.showRandomMeal(randomMealList);
    }

    @Override
    public void onRandomMealFailure(String errorMsg) {
        homeView.showRandomMealError(errorMsg);
    }

    @Override
    public void onCategoriesSuccess(ArrayList<Category> categoriesList) {
        homeView.showCategories(categoriesList);
    }

    @Override
    public void onCategoriesFailure(String errorMsg) {
        homeView.showCategoriesError(errorMsg);
    }
}
