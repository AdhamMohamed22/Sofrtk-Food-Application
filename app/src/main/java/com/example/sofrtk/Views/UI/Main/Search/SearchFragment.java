package com.example.sofrtk.Views.UI.Main.Search;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sofrtk.Models.DTOs.Category;
import com.example.sofrtk.Models.DTOs.Country;
import com.example.sofrtk.Models.DTOs.Ingredient;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Presenters.Search.SearchPresenterImp;
import com.example.sofrtk.R;
import com.example.sofrtk.Views.Adapters.CountryAdapter;
import com.example.sofrtk.Views.Adapters.MainIngredientAdapter;
import com.example.sofrtk.Views.Adapters.SearchCategoryAdapter;
import com.example.sofrtk.Views.UI.Main.Home.HomeFragmentDirections;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment implements com.example.sofrtk.Views.UI.Main.Search.SearchView {
    SearchPresenterImp searchPresenter;
    RecyclerView searchRecyclerView;
    CountryAdapter countryAdapter;
    MainIngredientAdapter mainIngredientAdapter;
    SearchCategoryAdapter categoryAdapter;
    GridLayoutManager searchGridLayoutManager;

    ArrayList<Category> categoriesList = new ArrayList<>();
    ArrayList<Country> countriesList = new ArrayList<>();
    ArrayList<Ingredient> ingredientsList = new ArrayList<>();
    ArrayList<Country> countriesListToFilter;
    ArrayList<Category> categoriesListToFilter;
    ArrayList<Ingredient> ingredientsListToFilter;

    ChipGroup chipGroup;
    Chip categoryChip;
    Chip countryChip;
    Chip ingredientChip;
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

        chipGroup = view.findViewById(R.id.chipGroup);
        categoryChip = view.findViewById(R.id.categoryChip);
        countryChip = view.findViewById(R.id.countryChip);
        ingredientChip = view.findViewById(R.id.ingredientChip);

        searchPresenter = new SearchPresenterImp(this, Repository.getInstance());

        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        searchGridLayoutManager = new GridLayoutManager(getActivity(),3);
        searchRecyclerView.setLayoutManager(searchGridLayoutManager);

        categoryAdapter = new SearchCategoryAdapter(getActivity(), categoriesList);
        countryAdapter = new CountryAdapter(getActivity(), countriesList);
        mainIngredientAdapter = new MainIngredientAdapter(getActivity(), ingredientsList);

        setupFilterChips();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(countryChip.isChecked()){
                    filterSelectedCountryChip(newText);
                }else if(categoryChip.isChecked()){
                    filterSelectedCategoryChip(newText);
                }else if(ingredientChip.isChecked()){
                    filterSelectedIngredientChip(newText);
                }
                return true;
            }
        });
    }

    @Override
    public void showCountries(ArrayList<Country> countriesList) {
        countryAdapter.updateData(countriesList);
        countriesListToFilter = countriesList;
        countryAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
            @Override
            public void onClicks(String filterName) {
                navigateToFilteredMealsFragment(countryChip.getText().toString(),filterName);
            }
        });
    }

    @Override
    public void showCountriesError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIngredients(ArrayList<Ingredient> ingredientsList) {
        mainIngredientAdapter.updateData(ingredientsList);
        ingredientsListToFilter = ingredientsList;
        mainIngredientAdapter.setOnItemClickListener(new MainIngredientAdapter.OnItemClickListener() {
            @Override
            public void onClicks(String filterName) {
                navigateToFilteredMealsFragment(ingredientChip.getText().toString(),filterName);
            }
        });
    }

    @Override
    public void showIngredientsError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCategories(ArrayList<Category> categoriesList) {
        categoryAdapter.updateData(categoriesList);
        categoriesListToFilter = categoriesList;
        categoryAdapter.setOnItemClickListener(new SearchCategoryAdapter.OnItemClickListener() {
            @Override
            public void onClicks(String filterName) {
                navigateToFilteredMealsFragment(categoryChip.getText().toString(),filterName);
            }
        });
    }

    @Override
    public void showCategoriesError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    public static int getFlagResourceByName(Context context, String countryName) {
        String formattedName = countryName.toLowerCase().replace(" ", "_");
        int resourceId = context.getResources().getIdentifier(formattedName, "drawable", context.getPackageName());
        return resourceId != 0 ? resourceId : R.drawable.ic_launcher_background;
    }

    public void setupFilterChips(){
        for(int i = 0;i< chipGroup.getChildCount();i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (chip.getText().equals("Category")) {
                            searchRecyclerView.setAdapter(categoryAdapter);
                            searchPresenter.setCategories();
                        } else if (chip.getText().equals("Country")) {
                            searchRecyclerView.setAdapter(countryAdapter);
                            searchPresenter.setCountries();
                        } else if (chip.getText().equals("Ingredient")) {
                            searchRecyclerView.setAdapter(mainIngredientAdapter);
                            searchPresenter.setIngredients();
                        }
                    }
                }
            });
        }
    }

    public void filterSelectedCountryChip(String text) {
        if (countriesListToFilter == null || countriesListToFilter.isEmpty()) {
            return;
        }

        List<Country> filteredCountries;

        if (text == null || text.isEmpty()) {
            filteredCountries = new ArrayList<>(countriesListToFilter);
        } else {
            filteredCountries = countriesListToFilter.stream()
                    .filter(country -> country.getCountryName().toLowerCase().startsWith(text.toLowerCase()))
                    .collect(Collectors.toList());
        }
        countryAdapter.updateData(new ArrayList<>(filteredCountries));
    }

    public void filterSelectedCategoryChip(String text) {
        if (categoriesListToFilter == null || categoriesListToFilter.isEmpty()) {
            return;
        }

        List<Category> filteredCategories;

        if (text == null || text.isEmpty()) {
            filteredCategories = new ArrayList<>(categoriesListToFilter);
        } else {
            filteredCategories = categoriesListToFilter.stream()
                    .filter(category -> category.getCategoryTitle().toLowerCase().startsWith(text.toLowerCase()))
                    .collect(Collectors.toList());
        }
        categoryAdapter.updateData(new ArrayList<>(filteredCategories));
    }

    public void filterSelectedIngredientChip(String text) {
        if (ingredientsListToFilter == null || ingredientsListToFilter.isEmpty()) {
            return;
        }

        List<Ingredient> filteredIngredients;

        if (text == null || text.isEmpty()) {
            filteredIngredients = new ArrayList<>(ingredientsListToFilter);
        } else {
            filteredIngredients = ingredientsListToFilter.stream()
                    .filter(ingredient -> ingredient.getIngredientName().toLowerCase().startsWith(text.toLowerCase()))
                    .collect(Collectors.toList());
        }
        mainIngredientAdapter.updateData(new ArrayList<>(filteredIngredients));
    }

    public void navigateToFilteredMealsFragment(String chipType,String filterName){
        Navigation.findNavController(requireView()).navigate(SearchFragmentDirections.actionSearchFragmentToFilteredMealsFragment(chipType,filterName));
    }

}