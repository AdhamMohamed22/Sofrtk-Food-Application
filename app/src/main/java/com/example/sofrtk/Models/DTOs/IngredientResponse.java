package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IngredientResponse {
    @SerializedName("meals")
    public ArrayList<Ingredient> ingredientsList;
}
