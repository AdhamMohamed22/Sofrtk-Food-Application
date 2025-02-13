package com.example.sofrtk.Network;

import com.example.sofrtk.Models.DTOs.CategoryResponse;
import com.example.sofrtk.Models.DTOs.RandomMealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodRemoteDataSource {
    private static final String BaseUrl = "https://www.themealdb.com/api/json/v1/1/";
    public static FoodRemoteDataSource foodRemoteDataSource = null;
    APIDataService apiDataService;

    private FoodRemoteDataSource(){
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
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

    public void getRandomMeal(NetworkCallBack networkCallBack){
        apiDataService.getRandomMeal().enqueue(new Callback<RandomMealResponse>() {
            @Override
            public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    networkCallBack.onRandomMealSuccess(response.body().getRandomMealList());
                }
            }

            @Override
            public void onFailure(Call<RandomMealResponse> call, Throwable t) {
                networkCallBack.onRandomMealFailure(t.getMessage().toString());
            }
        });
    }

    public void getCategories(NetworkCallBack networkCallBack){
        apiDataService.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                networkCallBack.onCategoriesSuccess(response.body().categoriesList);
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                networkCallBack.onCategoriesFailure(t.getMessage().toString());
            }
        });
    }
}
