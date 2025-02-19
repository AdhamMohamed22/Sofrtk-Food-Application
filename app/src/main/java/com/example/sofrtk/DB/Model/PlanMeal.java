package com.example.sofrtk.DB.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.sofrtk.DB.Converters;
import com.example.sofrtk.Models.DTOs.RandomMeal;

@Entity(tableName = "plan_meals_table", primaryKeys = {"userId", "mealId"})
@TypeConverters(Converters.class)
public class PlanMeal {
    @NonNull
    public String userId;
    @NonNull
    public String mealId;

    public RandomMeal meal;
    public String mealDate;

    public PlanMeal() {
    }

    public PlanMeal(@NonNull String userId, @NonNull String mealId, RandomMeal meal, String mealDate) {
        this.userId = userId;
        this.mealId = mealId;
        this.meal = meal;
        this.mealDate = mealDate;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getMealId() {
        return mealId;
    }

    public RandomMeal getMeal() {
        return meal;
    }

    public String getMealDate() {
        return mealDate;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public void setMealId(@NonNull String mealId) {
        this.mealId = mealId;
    }

    public void setMeal(RandomMeal meal) {
        this.meal = meal;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }
}
