package com.example.sofrtk.Views.UI.Main.DetailedMeal;

import com.example.sofrtk.Models.DTOs.RandomMeal;

import java.util.ArrayList;

public interface DetailedMealView {
    void showMealDetails(ArrayList<RandomMeal> mealList);
    void showMealDetailsError(String errorMsg);
}
