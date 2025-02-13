package com.example.sofrtk.Views.UI.Main.Search;

import com.example.sofrtk.Models.DTOs.Country;

import java.util.ArrayList;

public interface SearchView {
    void showCountries(ArrayList<Country> countriesList);
    void showCountriesError(String errorMsg);
}
