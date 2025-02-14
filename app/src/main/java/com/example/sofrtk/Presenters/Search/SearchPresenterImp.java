package com.example.sofrtk.Presenters.Search;

import com.example.sofrtk.Models.DTOs.Country;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.Search.SearchView;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImp implements SearchPresenter {
    SearchView searchView;
    Repository repository;

    public SearchPresenterImp(SearchView searchView, Repository repository) {
        this.searchView = searchView;
        this.repository = repository;
    }


    @Override
    public void setCountries() {
        repository.getCountries()
                .subscribeOn(Schedulers.io())
                .map(countryResponse -> countryResponse.countriesList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        countries -> searchView.showCountries(countries),
                        error -> searchView.showCountriesError(error.getMessage()));
    }

    @Override
    public void setIngredients() {
        repository.getIngredients()
                .subscribeOn(Schedulers.io())
                .map(ingredientResponse -> ingredientResponse.ingredientsList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        ingredients -> searchView.showIngredients(ingredients),
                        error -> searchView.showIngredientsError(error.getMessage())
                );
    }
    @Override
    public void setCategories() {
        repository.getCategories()
                .subscribeOn(Schedulers.io())
                .map(categoryResponse -> categoryResponse.categoriesList)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categories -> searchView.showCategories(categories),
                        error -> searchView.showCategoriesError(error.getMessage()));
    }
}
