package com.example.sofrtk.Views.UI.Main.MealsFiltering;

import com.example.sofrtk.Models.DTOs.FilterMeal;

import java.util.ArrayList;

public interface MealsFilteringView {
    void showMealsInCategory(ArrayList<FilterMeal> filterMealsList);
    void showMealsInCategoryError(String errorMsg);
    void showMealsInArea(ArrayList<FilterMeal> filterMealsList);
    void showMealsInAreaError(String errorMsg);
    void showMealsInIngredient(ArrayList<FilterMeal> filterMealsList);
    void showMealsInIngredientError(String errorMsg);
}
