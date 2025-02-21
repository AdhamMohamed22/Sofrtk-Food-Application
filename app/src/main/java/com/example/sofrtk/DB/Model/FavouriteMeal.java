package com.example.sofrtk.DB.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.sofrtk.DB.Converters;
import com.example.sofrtk.Models.DTOs.RandomMeal;


@Entity(tableName = "favourite_meals_table", primaryKeys = {"userId", "mealId"})
@TypeConverters(Converters.class)
public class FavouriteMeal {
    @NonNull
    public String userId;
    @NonNull
    public String mealId;

    public RandomMeal randomMeal;

    public FavouriteMeal() {
    }

    public FavouriteMeal(String userId, String mealId, RandomMeal randomMeal) {
        this.userId = userId;
        this.mealId = mealId;
        this.randomMeal = randomMeal;
    }

    public String getUserId() {
        return userId;
    }

    public String getMealId() {
        return mealId;
    }

    public RandomMeal getMeal() {
        return randomMeal;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public void setMeal(RandomMeal meal) {
        this.randomMeal = meal;
    }

}
