package com.example.sofrtk.Presenters.Plan;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.DB.Model.PlanMeal;

public interface PlanPresenter {
    void getPlanMeals(String userId, String selectedDate);

    void deletePlanMeal(PlanMeal planMeal);
}
