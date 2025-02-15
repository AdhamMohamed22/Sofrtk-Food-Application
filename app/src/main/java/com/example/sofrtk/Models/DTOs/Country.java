package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("strArea")
    String countryName;

    public Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
