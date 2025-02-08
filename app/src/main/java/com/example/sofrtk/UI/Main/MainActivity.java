package com.example.sofrtk.UI.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.sofrtk.Adapters.CategoryAdapter;
import com.example.sofrtk.Adapters.RandomMealAdapter;
import com.example.sofrtk.Pojos.Category;
import com.example.sofrtk.Pojos.CategoryResponse;
import com.example.sofrtk.Pojos.RandomMeal;
import com.example.sofrtk.Pojos.RandomMealResponse;
import com.example.sofrtk.R;
import com.example.sofrtk.UI.Main.Home.MyCategory;
import com.example.sofrtk.UI.Main.Home.MyRandomMeal;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}