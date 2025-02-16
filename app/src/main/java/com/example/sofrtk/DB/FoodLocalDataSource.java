package com.example.sofrtk.DB;

import android.content.Context;

import com.example.sofrtk.DB.Dao.MealDAO;
import com.example.sofrtk.DB.Model.FavouriteMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

public class FoodLocalDataSource {
    Context context;
    MealDAO mealDAO;

    public static FoodLocalDataSource foodLocalDataSource = null;

    public FoodLocalDataSource(Context context) {
        this.context = context;
        mealDAO = AppDB.getInstance(context).getMealDAO();
    }

    public static FoodLocalDataSource getInstance(Context context){
        if(foodLocalDataSource == null){
            foodLocalDataSource = new FoodLocalDataSource(context);
        }
        return foodLocalDataSource;
    }

    public Observable<List<FavouriteMeal>> getFavouriteMeals(String userId){ return mealDAO.getFavouriteMeals(userId);}
    public Completable insertFavouriteMeal(FavouriteMeal favouriteMeal){ return mealDAO.insertFavouriteMeal(favouriteMeal);}
    public Completable deleteFavouriteMeal(FavouriteMeal favouriteMeal){ return mealDAO.deleteFavouriteMeal(favouriteMeal);}

}
