package com.example.sofrtk.DB.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.DB.Model.PlanMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface MealDAO {
    @Query("SELECT * From favourite_meals_table WHERE userId = :userId")
    Observable<List<FavouriteMeal>> getFavouriteMeals(String userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertFavouriteMeal(FavouriteMeal favouriteMeal);

    @Delete
    Completable deleteFavouriteMeal(FavouriteMeal favouriteMeal);

    @Query("SELECT * From plan_meals_table WHERE userId = :userId AND mealDate = :mealDate")
    Observable<List<PlanMeal>> getPlanMeals(String userId, String mealDate);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertPlanMeal(PlanMeal planMeal);

    @Delete
    Completable deletePlanMeal(PlanMeal planMeal);
}
