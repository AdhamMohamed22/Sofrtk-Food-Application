package com.example.sofrtk.Views.UI.Main.Search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.Country;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Presenters.Search.SearchPresenterImp;
import com.example.sofrtk.R;
import com.example.sofrtk.Views.Adapters.CategoryAdapter;
import com.example.sofrtk.Views.Adapters.CountryAdapter;
import com.jackandphantom.carouselrecyclerview.CarouselRecyclerview;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements com.example.sofrtk.Views.UI.Main.Search.SearchView {
    SearchPresenterImp searchPresenter;
    CarouselRecyclerview countryRecyclerView;
    CountryAdapter countryAdapter;
    ArrayList<Country> countriesList = new ArrayList<>();
    SearchView searchView;


    public SearchFragment() {
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
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.searchView);
        EditText searchEditText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.WHITE);

        searchPresenter = new SearchPresenterImp(this, Repository.getInstance());
        searchPresenter.setCountries();

        countryRecyclerView = view.findViewById(R.id.countryRecyclerView);
        countryAdapter = new CountryAdapter(getActivity(),countriesList);
        countryRecyclerView.setAdapter(countryAdapter);
        countryRecyclerView.setAlpha(true);
        countryRecyclerView.setInfinite(false);

        /*
        countryAdapter.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onClicks(int id) {
                navigateToDetailedMealFragment(id,null);
            }
        });
        */
    }

    @Override
    public void showCountries(ArrayList<Country> countriesList) {
        countryAdapter.updateData(countriesList);
    }

    @Override
    public void showCountriesError(String errorMsg) {
        Toast.makeText(getActivity(),errorMsg,Toast.LENGTH_LONG);
    }

    public static int getFlagResourceByName(Context context, String countryName) {
        String formattedName = countryName.toLowerCase().replace(" ", "_");
        int resourceId = context.getResources().getIdentifier(formattedName, "drawable", context.getPackageName());
        return resourceId != 0 ? resourceId : R.drawable.ic_launcher_background;
    }
}