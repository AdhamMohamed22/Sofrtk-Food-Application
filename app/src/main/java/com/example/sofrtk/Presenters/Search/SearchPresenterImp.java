package com.example.sofrtk.Presenters.Search;

import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Views.UI.Main.Search.SearchView;

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
}
