package com.example.sofrtk.Views.UI.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.sofrtk.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_nav_host_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottom_navigation, navController);
        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return clickedFragment(item);
            }
        });
    }

    public boolean clickedFragment(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.homeFragment) {
            navController.navigate(R.id.homeFragment);
            return true;
        } else if (id == R.id.searchFragment) {
            navController.navigate(R.id.searchFragment);
            return true;
        } else if (id == R.id.favouriteFragment) {
            navController.navigate(R.id.favouriteFragment);
            return true;
        } else if (id == R.id.planFragment) {
            navController.navigate(R.id.planFragment);
            return true;
        }
        return false;
    }
}