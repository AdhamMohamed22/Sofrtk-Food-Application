package com.example.sofrtk.Views.UI.Main.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sofrtk.Models.DTOs.RandomMealResponse;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Presenters.Home.HomePresenterImp;
import com.example.sofrtk.Views.Adapters.CategoryAdapter;
import com.example.sofrtk.Views.Adapters.RandomMealAdapter;
import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.R;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment implements HomeView {
    HomePresenterImp homePresenter;
    RecyclerView randomMealRecyclerView;
    LinearLayoutManager mealLinearLayoutManager;
    RandomMealAdapter randomMealAdapter;
    ArrayList<RandomMeal> randomMealsList = new ArrayList<>();
    CarouselRecyclerview categoryRecyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categoriesList = new ArrayList<>();

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homePresenter = new HomePresenterImp(this, Repository.getInstance());
        homePresenter.setRandomMeal();
        homePresenter.setCategories();

        randomMealRecyclerView = view.findViewById(R.id.randomMealRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);

        mealLinearLayoutManager = new LinearLayoutManager(getActivity());
        mealLinearLayoutManager.setOrientation(mealLinearLayoutManager.HORIZONTAL);
        randomMealRecyclerView.setLayoutManager(mealLinearLayoutManager);
        randomMealAdapter = new RandomMealAdapter(getActivity(),randomMealsList);
        randomMealRecyclerView.setAdapter(randomMealAdapter);
        randomMealAdapter.setOnItemClickListener(new RandomMealAdapter.OnItemClickListener() {
            @Override
            public void onClicks(RandomMeal randomMeal) {
                navigateToDetailedMealFragment(Integer.parseInt(randomMeal.getIdMeal()),randomMeal);
            }
        });

        categoryAdapter = new CategoryAdapter(getActivity(),categoriesList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setAlpha(true);
        categoryRecyclerView.setInfinite(false);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onClicks(int id) {
                navigateToDetailedMealFragment(id,null);
            }
        });

    }

    @Override
    public void showRandomMeal(ArrayList<RandomMeal> randomMealList) {
        randomMealAdapter.updateData(randomMealList);
    }

    @Override
    public void showRandomMealError(String errorMsg) {
        Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_LONG);
    }

    @Override
    public void showCategories(ArrayList<Category> categoriesList) {
        categoryAdapter.updateData(categoriesList);
    }

    @Override
    public void showCategoriesError(String errorMsg) {
        Toast.makeText(getActivity(),errorMsg.toString(),Toast.LENGTH_LONG);
    }

    public void navigateToDetailedMealFragment(int id, RandomMeal randomMeal){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToDetailedMealFragment(id,randomMeal));
    }
}