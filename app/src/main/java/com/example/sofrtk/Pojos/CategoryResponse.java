package com.example.sofrtk.Pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryResponse {
    @SerializedName("categories")
    public ArrayList<Category> categoriesList;
}
