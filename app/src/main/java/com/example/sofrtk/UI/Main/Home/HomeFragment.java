package com.example.sofrtk.UI.Main.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sofrtk.Adapters.CategoryAdapter;
import com.example.sofrtk.Adapters.RandomMealAdapter;
import com.example.sofrtk.Pojos.Category;
import com.example.sofrtk.Pojos.CategoryResponse;
import com.example.sofrtk.Pojos.RandomMeal;
import com.example.sofrtk.Pojos.RandomMealResponse;
import com.example.sofrtk.R;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    RecyclerView randomMealRecyclerView;
    LinearLayoutManager mealLinearLayoutManager;
    ArrayList<RandomMeal> randomMealsList = new ArrayList<>();
    private static final String BaseUrl = "https://www.themealdb.com/api/json/v1/1/";

    CarouselRecyclerview categoryRecyclerView;
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


        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl)
                .build();

        MyRandomMeal myRandomMeal = retrofit.create(MyRandomMeal.class);

        myRandomMeal.getRandomMeal().enqueue(new Callback<RandomMealResponse>() {
            @Override
            public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                randomMealsList = response.body().randomMealsList;
                RandomMealAdapter randomMealAdapter = new RandomMealAdapter(getActivity(),randomMealsList);
                randomMealRecyclerView.setAdapter(randomMealAdapter);
                randomMealAdapter.notifyDataSetChanged();
                Log.i("TAG", "onResponse: " + response.body());
                randomMealAdapter.setOnItemClickListener(randomMeal -> {
                    int id = Integer.parseInt(randomMeal.getIdMeal());
                    navigateToDetailed(id,randomMeal);
                });
            }

            @Override
            public void onFailure(Call<RandomMealResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: Failed Fetching Data" + t.getMessage());
            }
        });

        randomMealRecyclerView = view.findViewById(R.id.randomMealRecyclerView);
        mealLinearLayoutManager = new LinearLayoutManager(getActivity());
        mealLinearLayoutManager.setOrientation(mealLinearLayoutManager.HORIZONTAL);
        randomMealRecyclerView.setLayoutManager(mealLinearLayoutManager);


        Retrofit retrofit2 = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BaseUrl)
                .build();

        MyCategory myCategory = retrofit2.create(MyCategory.class);
        myCategory.getCategories().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                categoriesList = response.body().categoriesList;
                CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(),categoriesList);
                categoryRecyclerView.setAdapter(categoryAdapter);
                categoryRecyclerView.setAlpha(true);
                categoryRecyclerView.setInfinite(false);
                categoryAdapter.notifyDataSetChanged();
                Log.i("TAG", "onResponse: " + response.body());
                categoryAdapter.setOnItemClickListener(id -> {
                    navigateToDetailed(id,null);
                });
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.i("TAG", "onFailure: Failed Fetching Data" + t.getMessage());
            }
        });

        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);

    }

    public void navigateToDetailed(int id,RandomMeal randomMeal){
        Navigation.findNavController(requireView()).navigate(HomeFragmentDirections.actionHomeFragmentToDetailedMealFragment(id,randomMeal));
    }
}