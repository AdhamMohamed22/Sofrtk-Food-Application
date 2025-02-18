package com.example.sofrtk.Views.UI.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sofrtk.NetworkUtils.NetworkUtils;
import com.example.sofrtk.R;
import com.example.sofrtk.Views.UI.Start.AuthenticationActivity;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    NavController navController;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;
    RxSharedPreferences rxSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("UserPrefs", this.MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);

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
            if (rxSharedPreferences.getBoolean("isLoggedIn", false).get()){
                navController.navigate(R.id.favouriteFragment);
                return true;
            } else {
                Toast.makeText(this,"Not Available Feature For Guest, You must Register!",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.planFragment) {
            if (rxSharedPreferences.getBoolean("isLoggedIn", false).get()){
                navController.navigate(R.id.planFragment);
                return true;
            } else {
                Toast.makeText(this,"Not Available Feature For Guest, You must Register!",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.logoutFragment) {
            if (rxSharedPreferences.getBoolean("isLoggedIn", false).get()){
                showLogoutDialog();
                return false;
            } else {
                Intent intent = new Intent(this, AuthenticationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
        return false;
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> logout())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void logout() {
        auth.signOut();
        clearUserPreferences();
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void clearUserPreferences() {
        rxSharedPreferences.getBoolean("isLoggedIn").delete();
        rxSharedPreferences.getString("userId").delete();
        rxSharedPreferences.getString("email").delete();
    }

    /*
    private void logoutWithGoogle(){
        googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                auth.signOut();
                clearUserPreferences();
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
     */
}