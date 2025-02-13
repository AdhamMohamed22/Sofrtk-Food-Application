package com.example.sofrtk.Network;

import com.example.sofrtk.Models.DTOs.CategoryResponse;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIDataService {
    @GET("random.php")
    Single<RandomMealResponse> getRandomMeal();
    @GET("categories.php")
    Observable<CategoryResponse> getCategories();
}
