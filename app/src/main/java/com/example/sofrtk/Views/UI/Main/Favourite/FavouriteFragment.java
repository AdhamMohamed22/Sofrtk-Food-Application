package com.example.sofrtk.Views.UI.Main.Favourite;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofrtk.DB.Model.FavouriteMeal;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Presenters.Favourite.FavouritePresenterImp;
import com.example.sofrtk.R;
import com.example.sofrtk.Views.Adapters.FavouriteMealsAdapter;
import com.example.sofrtk.Views.UI.Main.Home.HomeFragmentDirections;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements FavouriteView{
    FavouritePresenterImp favouritePresenter;
    Group group;
    RecyclerView favouritesRecyclerView;
    LinearLayoutManager linearLayoutManager;
    FavouriteMealsAdapter favouriteMealsAdapter;
    List<FavouriteMeal> favouriteMealsList = new ArrayList<>();
    RxSharedPreferences rxSharedPreferences;


    public FavouriteFragment() {
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
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);

        favouritePresenter = new FavouritePresenterImp(this, Repository.getInstance(getActivity()));

        if (rxSharedPreferences.getBoolean("isLoggedIn", false).get()) {
            favouritePresenter.getFavouriteMeals(rxSharedPreferences.getString("userId").get());
        }

        group = view.findViewById(R.id.group);
        favouritesRecyclerView = view.findViewById(R.id.favouritesRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favouritesRecyclerView.setLayoutManager(linearLayoutManager);
        favouriteMealsAdapter = new FavouriteMealsAdapter(getActivity(),favouriteMealsList);
        favouritesRecyclerView.setAdapter(favouriteMealsAdapter);
    }

    @Override
    public void showFavouriteMeals(List<FavouriteMeal> favouriteMealsList) {
        favouriteMealsAdapter.updateData(favouriteMealsList);

        if(favouriteMealsList.isEmpty()){
            favouritesRecyclerView.setVisibility(View.GONE);
            group.setVisibility(View.VISIBLE);
        } else {
            favouritesRecyclerView.setVisibility(View.VISIBLE);
            group.setVisibility(View.GONE);
        }
        favouriteMealsAdapter.setOnItemClickListener(new FavouriteMealsAdapter.OnItemClickListener() {
            @Override
            public void onRemoveClicks(FavouriteMeal favouriteMeal) {
                favouritePresenter.deleteFavouriteMeal(favouriteMeal);
            }

            @Override
            public void onClicks(RandomMeal randomMeal) {
                navigateToDetailedMealFragment(randomMeal.getIdMeal(), randomMeal);
            }
        });
    }

    @Override
    public void showFavouriteMealsError(String errorMsg) {
        Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void deleteFavouriteMeals(FavouriteMeal favouriteMeal) {
        favouriteMealsAdapter.removeItem(favouriteMeal);
        Toast.makeText(getActivity(),"Meal Removed Successfully!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void deleteFavouriteMealsError(String errorMsg) {
        Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_LONG).show();
    }

    @Override
    public Activity getViewActivity() {
        return requireActivity();
    }

    public void navigateToDetailedMealFragment(String id, RandomMeal randomMeal){
        Navigation.findNavController(requireView()).navigate(FavouriteFragmentDirections.actionFavouriteFragmentToDetailedMealFragment(randomMeal.getIdMeal(), randomMeal));
    }
}