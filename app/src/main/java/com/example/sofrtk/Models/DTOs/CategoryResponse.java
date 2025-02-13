package com.example.sofrtk.Models.DTOs;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryResponse {
    @SerializedName("categories")
    public ArrayList<Category> categoriesList;

    public ArrayList<Category> getCategoriesList() {
        return categoriesList;
    }
}
