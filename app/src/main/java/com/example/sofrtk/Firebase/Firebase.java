package com.example.sofrtk.Firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.DB.Model.PlanMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Firebase {
    public static Firebase firebase = null;
    DatabaseReference databaseReference;
    Repository repository;

    private Firebase(){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        firebase = this;
    }
    public static Firebase getInstance(){
        if(firebase == null){
            firebase = new Firebase();
        }
        return firebase;
    }

    public Task<Void> insertFavouriteMeal(FavouriteMeal favouriteMeal,String userId,String mealId){
        return databaseReference.child( "Favourite").child(userId + "-" + mealId).setValue(favouriteMeal);
    }
    public Task<Void> deleteFavouriteMeal(FavouriteMeal favouriteMeal,String userId,String mealId){
        return databaseReference.child( "Favourite").child(userId + "-" + mealId).removeValue();
    }

    public Task<Void> insertPlanMeal(PlanMeal planMeal, String userId, String mealId,String mealDate){
        planMeal.setMealDate(mealDate);
        return databaseReference.child( "Plan").child(userId + "-" + mealId + "-" + mealDate).setValue(planMeal);
    }
    public Task<Void> deletePlanMeal(PlanMeal planMeal, String userId, String mealId,String mealDate){
        return databaseReference.child( "Plan").child(userId + "-" + mealId + "-" + mealDate).removeValue();
    }

    public void updateFavouriteMeals(String userId, Context context){
        repository = Repository.getInstance(context);
        List<FavouriteMeal> favouriteMealList = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.getKey().equals("Favourite")){
                        for(DataSnapshot meal : snap.getChildren()){
                            favouriteMealList.add(meal.getValue(FavouriteMeal.class));
                            Log.i("TAG", "onDataChange: " + favouriteMealList.size());
                        }
                    }
                }
                if(!favouriteMealList.isEmpty()){
                    Observable.fromIterable(favouriteMealList)
                            .subscribeOn(Schedulers.io())
                            .flatMap(favouriteMeal -> repository.insertFavouriteMeal(favouriteMeal)
                                    .subscribeOn(Schedulers.io())
                                    .toObservable()
                            )
                            .subscribe(
                                    o -> Log.i("TAG", "Favourite Meals Added Successfully!" ),
                                    throwable ->  Log.i("TAG", throwable.getMessage() ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }

    public void updatePlanMeals(String userId, Context context){
        repository = Repository.getInstance(context);
        List<PlanMeal> planMealList = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    if(snap.getKey().equals("Plan")){
                        for(DataSnapshot meal : snap.getChildren()){
                            planMealList.add(meal.getValue(PlanMeal.class));
                            Log.i("TAG", "onDataChange: " + planMealList.size());
                        }
                    }
                }
                if(!planMealList.isEmpty()){
                    Observable.fromIterable(planMealList)
                            .subscribeOn(Schedulers.io())
                            .flatMap(planMeal -> {
                                return repository.insertPlanMeal(planMeal)
                                    .subscribeOn(Schedulers.io())
                                    .toObservable();
                            }
                            )
                            .subscribe(
                                    o -> Log.i("TAG", "Plan Meals Added Successfully!" ),
                                    throwable ->  Log.i("TAG", throwable.getMessage() ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("TAG", "onCancelled: " + error.getMessage());
            }
        });
    }

}
