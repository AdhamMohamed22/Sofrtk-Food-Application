package com.example.sofrtk.Views.UI.Main.Favourite;

import com.example.sofrtk.DB.Model.FavouriteMeal;

import java.util.ArrayList;
import java.util.List;

public interface FavouriteView {
    void showFavouriteMeals(List<FavouriteMeal> favouriteMealsList);
    void showFavouriteMealsError(String errorMsg);
    void deleteFavouriteMeals(FavouriteMeal favouriteMeal);
    void deleteFavouriteMealsError(String errorMsg);
}
