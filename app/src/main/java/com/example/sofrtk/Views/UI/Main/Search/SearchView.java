package com.example.sofrtk.Views.UI.Main.Search;

import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.Country;
import com.example.sofrtk.Models.DTOs.Ingredient;

import java.util.ArrayList;

public interface SearchView {
    void showCountries(ArrayList<Country> countriesList);
    void showCountriesError(String errorMsg);
    void showIngredients(ArrayList<Ingredient> ingredientsList);
    void showIngredientsError(String errorMsg);
    void showCategories(ArrayList<Category> categoriesList);
    void showCategoriesError(String errorMsg);
}
