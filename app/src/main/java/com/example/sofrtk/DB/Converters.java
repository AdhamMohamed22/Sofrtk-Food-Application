package com.example.sofrtk.DB;

import androidx.room.TypeConverter;

import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;


public class Converters {
    private static final Gson gson = new Gson();

    @TypeConverter
    public static String fromMealModel(RandomMeal randomMeal) {
        return randomMeal == null ? null : gson.toJson(randomMeal);
    }

    @TypeConverter
    public static RandomMeal toMealModel(String mealModelString) {
        if (mealModelString == null) return null;
        Type type = new TypeToken<RandomMeal>() {
        }.getType();
        return gson.fromJson(mealModelString, type);
    }
}
