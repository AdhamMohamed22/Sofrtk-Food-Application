package com.example.sofrtk.Network;

import com.example.sofrtk.Models.DTOs.CategoryResponse;
import com.example.sofrtk.Models.DTOs.CountryResponse;
import com.example.sofrtk.Models.DTOs.FilterMealResponse;
import com.example.sofrtk.Models.DTOs.IngredientResponse;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIDataService {
    @GET("random.php")
    Single<RandomMealResponse> getRandomMeal();
    @GET("categories.php")
    Observable<CategoryResponse> getCategories();
    @GET("list.php")
    Observable<CountryResponse> getCountries(@Query("a") String country);
    @GET("list.php")
    Observable<IngredientResponse> getIngredients(@Query("i") String ingredient);
    @GET("filter.php")
    Observable<FilterMealResponse> getFilterMealsByCategory(@Query("c") String category);
    @GET("filter.php")
    Observable<FilterMealResponse> getFilterMealsByArea(@Query("a") String area);
    @GET("filter.php")
    Observable<FilterMealResponse> getFilterMealsByIngredient(@Query("i") String ingredient);
}
