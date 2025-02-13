package com.example.sofrtk.Network;

import com.example.sofrtk.Models.DTOs.CategoryResponse;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIDataService {
    @GET("random.php")
    Call<RandomMealResponse> getRandomMeal();
    @GET("categories.php")
    Call<CategoryResponse> getCategories();
}
