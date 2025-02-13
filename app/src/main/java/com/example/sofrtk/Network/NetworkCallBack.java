package com.example.sofrtk.Network;

import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.RandomMeal;

import java.util.ArrayList;

public interface NetworkCallBack {
     void onRandomMealSuccess(ArrayList<RandomMeal> randomMealList);
     void onRandomMealFailure(String errorMsg);

     void onCategoriesSuccess(ArrayList<Category> categoriesList);
     void onCategoriesFailure(String errorMsg);
}
