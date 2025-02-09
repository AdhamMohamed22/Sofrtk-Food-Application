package com.example.sofrtk.Pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RandomMealResponse {
    @SerializedName("meals")
    public ArrayList<RandomMeal> randomMealsList;
}
