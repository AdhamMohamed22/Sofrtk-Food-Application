package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

public class FilterMeal {
    @SerializedName("strMealThumb")
    String filterMealImage;
    @SerializedName("strMeal")
    String filterMealName;
    @SerializedName("idMeal")
    String filterMealId;

    public FilterMeal(String filterMealImage, String filterMealName, String filterMealId) {
        this.filterMealImage = filterMealImage;
        this.filterMealName = filterMealName;
        this.filterMealId = filterMealId;
    }

    public String getFilterMealImage() {
        return filterMealImage;
    }

    public String getFilterMealName() {
        return filterMealName;
    }

    public String getFilterMealId() {
        return filterMealId;
    }

    public void setFilterMealImage(String filterMealImage) {
        this.filterMealImage = filterMealImage;
    }

    public void setFilterMealName(String filterMealName) {
        this.filterMealName = filterMealName;
    }

    public void setFilterMealId(String filterMealId) {
        this.filterMealId = filterMealId;
    }
}
