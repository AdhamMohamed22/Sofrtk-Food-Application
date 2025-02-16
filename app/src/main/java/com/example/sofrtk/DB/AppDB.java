package com.example.sofrtk.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.sofrtk.DB.Dao.MealDAO;
import com.example.sofrtk.DB.Model.FavouriteMeal;


@Database(entities = {FavouriteMeal.class},version = 1)
@TypeConverters(Converters.class)
public abstract class AppDB extends RoomDatabase {
    private static AppDB instance = null;
    public abstract MealDAO getMealDAO();
    public static synchronized AppDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDB.class,"MealsDB").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
