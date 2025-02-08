package com.example.sofrtk.UI.Main.Home;

import com.example.sofrtk.Pojos.RandomMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyRandomMeal {
    @GET("random.php")
    Call<RandomMealResponse> getRandomMeal();
}
