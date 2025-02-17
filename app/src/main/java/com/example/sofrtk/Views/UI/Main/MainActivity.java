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

import com.example.sofrtk.R;
import com.example.sofrtk.Views.UI.Start.Auth.SignUp.SignUpFragment;
import com.example.sofrtk.Views.UI.Start.AuthenticationActivity;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    NavController navController;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private GoogleSignInClient googleSignInClient;
    private RxSharedPreferences rxSharedPreferences;

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
            navController.navigate(R.id.favouriteFragment);
            return true;
        } else if (id == R.id.planFragment) {
            navController.navigate(R.id.planFragment);
            return true;
        } else if (id == R.id.logoutFragment) {
            showLogoutDialog();
            return false;
        }
        return false;
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    logout();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void logout() {
        // Clear user session (e.g., SharedPreferences, FirebaseAuth.signOut(), etc.)
        auth.signOut();
        clearUserPreferences();
        // Navigate to LoginActivity or AuthenticationActivity
        Intent intent = new Intent(this, AuthenticationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
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
    private void clearUserPreferences() {
        rxSharedPreferences.getBoolean("isLoggedIn").delete();
        rxSharedPreferences.getString("userId").delete();
        rxSharedPreferences.getString("email").delete();
    }

}