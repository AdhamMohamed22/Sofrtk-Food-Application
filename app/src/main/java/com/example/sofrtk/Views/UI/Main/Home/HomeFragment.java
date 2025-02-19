package com.example.sofrtk.Views.UI.Main.Home;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sofrtk.Firebase.Firebase;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.NetworkUtils.NetworkConnection;
import com.example.sofrtk.NetworkUtils.NetworkUtils;
import com.example.sofrtk.Presenters.Home.HomePresenterImp;
import com.example.sofrtk.Views.Adapters.CategoryAdapter;
import com.example.sofrtk.Views.Adapters.RandomMealAdapter;
import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.R;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeView , NetworkConnection {
    HomePresenterImp homePresenter;
    RecyclerView randomMealRecyclerView;
    LinearLayoutManager mealLinearLayoutManager;
    RandomMealAdapter randomMealAdapter;
    ArrayList<RandomMeal> randomMealsList = new ArrayList<>();
    CarouselRecyclerview categoryRecyclerView;
    CategoryAdapter categoryAdapter;
    ArrayList<Category> categoriesList = new ArrayList<>();
    TextView userName;
    RxSharedPreferences rxSharedPreferences;
    Group mainGroup;
    LottieAnimationView networkFailedLottie;

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

        mainGroup = view.findViewById(R.id.mainGroup);
        networkFailedLottie = view.findViewById(R.id.networkFailedLottie);

        homePresenter = new HomePresenterImp(this, Repository.getInstance(getActivity()));

        if(NetworkUtils.isNetworkAvailable(requireActivity())){
            onNetworkConnected();
        } else {
            onNetworkDisconnected();
        }
        NetworkUtils.registerNetworkCallback(requireActivity(),this);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);

        userName = view.findViewById(R.id.userName);

        if (rxSharedPreferences.getBoolean("isLoggedIn", false).get()) {
            userName.setText(getString(R.string.welcome) + rxSharedPreferences.getString("email").get());
        } else {
            userName.setText(R.string.welcome_guest);
        }

        Firebase.getInstance().updateFavouriteMeals(rxSharedPreferences.getString("userId").get(),requireContext());
        Firebase.getInstance().updatePlanMeals(rxSharedPreferences.getString("userId").get(),requireContext());

        randomMealRecyclerView = view.findViewById(R.id.randomMealRecyclerView);
        categoryRecyclerView = view.findViewById(R.id.searchRecyclerView);

        mealLinearLayoutManager = new LinearLayoutManager(getActivity());
        mealLinearLayoutManager.setOrientation(mealLinearLayoutManager.HORIZONTAL);
        randomMealRecyclerView.setLayoutManager(mealLinearLayoutManager);
        randomMealAdapter = new RandomMealAdapter(getActivity(),randomMealsList);
        randomMealRecyclerView.setAdapter(randomMealAdapter);
        randomMealAdapter.setOnItemClickListener(new RandomMealAdapter.OnItemClickListener() {
            @Override
            public void onClicks(RandomMeal randomMeal) {
                navigateToDetailedMealFragment(randomMeal.getIdMeal(),randomMeal);
            }
        });

        categoryAdapter = new CategoryAdapter(getActivity(),categoriesList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryRecyclerView.setAlpha(true);
        categoryRecyclerView.setInfinite(false);
        categoryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onClicks(String id) {
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

    public void navigateToDetailedMealFragment(String id, RandomMeal randomMeal){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToDetailedMealFragment(id,randomMeal));
    }

    @Override
    public void onNetworkConnected() {
        mainGroup.setVisibility(View.VISIBLE);
        networkFailedLottie.setVisibility(View.GONE);
        homePresenter.setRandomMeal();
        homePresenter.setCategories();
    }

    @Override
    public void onNetworkDisconnected() {
        mainGroup.setVisibility(View.GONE);
        networkFailedLottie.setVisibility(View.VISIBLE);
    }
}