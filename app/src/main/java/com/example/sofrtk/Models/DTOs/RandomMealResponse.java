package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RandomMealResponse {
    @SerializedName("meals")
    public ArrayList<RandomMeal> randomMealsList;

    public ArrayList<RandomMeal> getRandomMealList() {
        return randomMealsList;
    }
}
