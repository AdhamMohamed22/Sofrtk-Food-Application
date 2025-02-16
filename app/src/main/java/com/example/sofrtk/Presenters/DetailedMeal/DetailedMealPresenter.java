package com.example.sofrtk.Presenters.DetailedMeal;

import com.example.sofrtk.DB.Model.PlanMeal;
import com.example.sofrtk.Models.DTOs.RandomMeal;

public interface DetailedMealPresenter {
    void setMealDetails(String mealId);
    void addToFavourite(RandomMeal meal);
    void addToPlan(RandomMeal meal,String mealDate);
}
