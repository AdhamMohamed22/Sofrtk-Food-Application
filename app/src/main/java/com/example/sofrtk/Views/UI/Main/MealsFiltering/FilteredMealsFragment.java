package com.example.sofrtk.Views.UI.Main.MealsFiltering;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofrtk.Models.DTOs.FilterMeal;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Presenters.MealsFiltering.MealsFilteringImp;
import com.example.sofrtk.R;
import com.example.sofrtk.Views.Adapters.FilterMealsAdapter;
import com.example.sofrtk.Views.Adapters.RandomMealAdapter;

import java.util.ArrayList;

public class FilteredMealsFragment extends Fragment implements MealsFilteringView{
    MealsFilteringImp mealsFiltering;
    RecyclerView filteredMealRecyclerView;
    LinearLayoutManager filterMealLinearLayoutManager;
    FilterMealsAdapter filterMealsAdapter;
    ArrayList<FilterMeal> filterMealsList = new ArrayList<>();


    public FilteredMealsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtered_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealsFiltering = new MealsFilteringImp(this, Repository.getInstance());

        filteredMealRecyclerView = view.findViewById(R.id.filteredMealRecyclerView);
        filterMealLinearLayoutManager = new LinearLayoutManager(getActivity());
        filterMealLinearLayoutManager.setOrientation(filterMealLinearLayoutManager.VERTICAL);
        filteredMealRecyclerView.setLayoutManager(filterMealLinearLayoutManager);
        filterMealsAdapter = new FilterMealsAdapter(getActivity(),filterMealsList);
        filteredMealRecyclerView.setAdapter(filterMealsAdapter);

        String chipType = getArguments().getString("chipType");
        String filterName = getArguments().getString("filterName");

        if(!chipType.isEmpty() && !filterName.isEmpty()){
            if(chipType.equals("Category")){mealsFiltering.setMealsInCategory(filterName);}
            else if(chipType.equals("Country")) {mealsFiltering.setMealsInArea(filterName);}
            else if(chipType.equals("Ingredient")){mealsFiltering.setMealsInIngredient(filterName);}
        }

    }


    @Override
    public void showMealsInCategory(ArrayList<FilterMeal> filterMealsList) {
        filterMealsAdapter.updateData(filterMealsList);
    }

    @Override
    public void showMealsInCategoryError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMealsInArea(ArrayList<FilterMeal> filterMealsList) {
        filterMealsAdapter.updateData(filterMealsList);
    }

    @Override
    public void showMealsInAreaError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMealsInIngredient(ArrayList<FilterMeal> filterMealsList) {
        filterMealsAdapter.updateData(filterMealsList);
    }

    @Override
    public void showMealsInIngredientError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }
}