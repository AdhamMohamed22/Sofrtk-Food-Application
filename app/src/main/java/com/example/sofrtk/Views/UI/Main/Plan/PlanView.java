package com.example.sofrtk.Views.UI.Main.Plan;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.DB.Model.PlanMeal;

import java.util.List;

public interface PlanView {
    void showPlanMeals(List<PlanMeal> planMealList);
    void showPlanMealsError(String errorMsg);
    void deletePlanMeals(PlanMeal planMeal);
    void deletePlanMealsError(String errorMsg);

}
