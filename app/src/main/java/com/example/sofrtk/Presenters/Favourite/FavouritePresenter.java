package com.example.sofrtk.Presenters.Favourite;

import com.example.sofrtk.DB.Model.FavouriteMeal;

public interface FavouritePresenter {
    void getFavouriteMeals(String userId);

    void deleteFavouriteMeal(FavouriteMeal favouriteMeal);

}
