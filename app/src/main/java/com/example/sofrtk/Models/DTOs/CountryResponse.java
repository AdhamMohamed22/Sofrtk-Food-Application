package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryResponse {
    @SerializedName("meals")
    public ArrayList<Country> countriesList;

    public ArrayList<Country> getCountriesList() {
        return countriesList;
    }
}
