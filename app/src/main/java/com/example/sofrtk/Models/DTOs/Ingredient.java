package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("strIngredient")
    String ingredientName;
    @SerializedName("strDescription")
    String ingredientDescription;
    @SerializedName("idIngredient")
    String ingredientId;

    public Ingredient(String ingridentName, String ingridentDescription, String ingridentId) {
        this.ingredientName = ingridentName;
        this.ingredientDescription = ingridentDescription;
        this.ingredientId = ingridentId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getIngredientDescription() {
        return ingredientDescription;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setIngredientDescription(String ingredientDescription) {
        this.ingredientDescription = ingredientDescription;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }
}
