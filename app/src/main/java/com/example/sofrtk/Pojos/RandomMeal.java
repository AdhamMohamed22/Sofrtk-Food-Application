package com.example.sofrtk.Pojos;

import com.google.gson.annotations.SerializedName;

public class RandomMeal {
    @SerializedName("strMealThumb")
    String mealImage;
    @SerializedName("strMeal")
    String mealTitle;
    @SerializedName("strCategory")
    String mealCategory;
    @SerializedName("strArea")
    String mealArea;

    public RandomMeal(String mealImage, String mealTitle, String mealCategory, String mealArea) {
        this.mealImage = mealImage;
        this.mealTitle = mealTitle;
        this.mealCategory = mealCategory;
        this.mealArea = mealArea;
    }

    public String getMealImage() {
        return mealImage;
    }

    public String getMealTitle() {
        return mealTitle;
    }

    public String getMealCategory() {
        return mealCategory;
    }

    public String getMealArea() {
        return mealArea;
    }

    public void setMealImage(String image) {
        this.mealImage = image;
    }

    public void setMealTitle(String mealTitle) {
        this.mealTitle = mealTitle;
    }

    public void setMealCategory(String mealCategory) {
        this.mealCategory = mealCategory;
    }

    public void setMealArea(String mealArea) {
        this.mealArea = mealArea;
    }
}
