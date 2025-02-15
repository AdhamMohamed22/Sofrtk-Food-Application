package com.example.sofrtk.Network;

import com.example.sofrtk.Models.DTOs.CategoryResponse;
import com.example.sofrtk.Models.DTOs.CountryResponse;
import com.example.sofrtk.Models.DTOs.FilterMealResponse;
import com.example.sofrtk.Models.DTOs.IngredientResponse;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class FoodRemoteDataSource {
    private static final String BaseUrl = "https://www.themealdb.com/api/json/v1/1/";
    public static FoodRemoteDataSource foodRemoteDataSource = null;
    APIDataService apiDataService;

    private FoodRemoteDataSource(){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(BaseUrl)
                .build();

        apiDataService = retrofit.create(APIDataService.class);
    }

    public static FoodRemoteDataSource getInstance(){
        if(foodRemoteDataSource == null){
            foodRemoteDataSource = new FoodRemoteDataSource();
        }
        return foodRemoteDataSource;
    }

    public Single<RandomMealResponse> getRandomMeal(){
        return apiDataService.getRandomMeal();
    }

    public Observable<CategoryResponse> getCategories(){
        return apiDataService.getCategories();
    }
    public Observable<CountryResponse> getCountries(){
        return apiDataService.getCountries("list");
    }
    public Observable<IngredientResponse> getIngredients(){
        return apiDataService.getIngredients("list");
    }
    public Observable<FilterMealResponse> getFilterMealsByCategory(String filterCategory){
        return apiDataService.getFilterMealsByCategory(filterCategory);
    }
    public Observable<FilterMealResponse> getFilterMealsByArea(String filterArea){
        return apiDataService.getFilterMealsByArea(filterArea);
    }
    public Observable<FilterMealResponse> getFilterMealsByIngredient(String filterIngredient){
        return apiDataService.getFilterMealsByIngredient(filterIngredient);
    }
}
