package com.example.sofrtk.Views.UI.Main.DetailedMeal;

import android.app.Activity;

import com.example.sofrtk.Models.DTOs.RandomMeal;

import java.util.ArrayList;

public interface DetailedMealView {
    void showMealDetails(RandomMeal meal);
    void showMealDetailsError(String errorMsg);
    void onInsertFavouriteSuccess();
    void onInsertFavouriteFail(String errorMsg);
    void onInsertPlanSuccess();
    void onInsertPlanFail(String errorMsg);
    Activity getViewActivity();
}
