package com.example.sofrtk.Views.UI.Main.Home;

import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;

public interface HomeView {
    void showRandomMeal(ArrayList<RandomMeal> randomMealList);
    void showRandomMealError(String errorMsg);
    void showCategories(ArrayList<Category> categoriesList);
    void showCategoriesError(String errorMsg);
}
