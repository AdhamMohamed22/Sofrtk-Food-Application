package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FilterMealResponse {
    @SerializedName("meals")
    public ArrayList<FilterMeal> filterMealsList;

    public ArrayList<FilterMeal> getFilterMealsList() {
        return filterMealsList;
    }
}
