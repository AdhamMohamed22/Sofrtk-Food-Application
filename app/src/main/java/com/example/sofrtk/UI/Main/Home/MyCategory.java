package com.example.sofrtk.UI.Main.Home;

import com.example.sofrtk.Pojos.Category;
import com.example.sofrtk.Pojos.CategoryResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyCategory {
    @GET("categories.php")
    Call<CategoryResponse> getCategories();
}
