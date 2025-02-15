package com.example.sofrtk.Views.UI.Main.DetailedMeal;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sofrtk.Models.Repository.Repository;
import com.example.sofrtk.Presenters.DetailedMeal.DetailedMealPresenterImp;
import com.example.sofrtk.Views.Adapters.IngredientAdapter;
import com.example.sofrtk.Models.DTOs.RandomMeal;
import com.example.sofrtk.R;

import java.util.ArrayList;

public class DetailedMealFragment extends Fragment implements DetailedMealView{
    DetailedMealPresenterImp mealDetailsPresenter;
    ImageView detailedMealImage;
    TextView detailedMealNameTxt;
    TextView detailedMealAreaTxt;
    RecyclerView ingredietsRecyclerView;
    EditText stepsTxtArea;
    WebView videoWebView;
    RandomMeal randomMeal;
    String id;

    public DetailedMealFragment() {
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
        return inflater.inflate(R.layout.fragment_detailed_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mealDetailsPresenter = new DetailedMealPresenterImp(this, Repository.getInstance());

        detailedMealImage = view.findViewById(R.id.detailedMealImage);
        detailedMealNameTxt = view.findViewById(R.id.detailedMealNameTxt);
        detailedMealAreaTxt = view.findViewById(R.id.detailedMealAreaTxt);
        stepsTxtArea = view.findViewById(R.id.stepsTxtArea);

        ingredietsRecyclerView = view.findViewById(R.id.ingredietsRecyclerView);
        ingredietsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        videoWebView = view.findViewById(R.id.videoWebView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            id = getArguments().getString("id");
            randomMeal = getArguments().getParcelable("meal", RandomMeal.class);
        }

        if (randomMeal != null) {
            Glide.with(requireView()).load(randomMeal.getStrMealThumb()).apply(new RequestOptions().override(400, 400)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(detailedMealImage);
            detailedMealNameTxt.setText(randomMeal.getStrMeal());
            detailedMealAreaTxt.setText(randomMeal.getStrArea());
            stepsTxtArea.setText(randomMeal.getStrInstructions());
            IngredientAdapter ingridentAdapter = new IngredientAdapter(getContext(),randomMeal.getNonNullIngredients(),randomMeal.getNonNullMeasures());
            ingredietsRecyclerView.setAdapter(ingridentAdapter);
            loadYouTubeVideo(randomMeal.getStrYoutube());
        } else {
            mealDetailsPresenter.setMealDetails(id);
        }
    }

    private void loadYouTubeVideo(String youtubeUrl) {
        videoWebView.getSettings().setJavaScriptEnabled(true);
        videoWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        videoWebView.setWebChromeClient(new WebChromeClient());

        Uri uri = Uri.parse(youtubeUrl);
        String videoId = uri.getQueryParameter("v");

        if (videoId != null) {
            String iframe = "<iframe width=\"100%\" height=\"100%\" " +
                    "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=0&mute=0\" " +
            "frameborder=\"0\" allowfullscreen></iframe>";

            videoWebView.loadData(iframe, "text/html", "utf-8");
        } else {
            videoWebView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showMealDetails(ArrayList<RandomMeal> mealList) {
        Glide.with(requireView()).load(mealList.get(0).getStrMealThumb()).apply(new RequestOptions().override(400, 400)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(detailedMealImage);
        detailedMealNameTxt.setText(mealList.get(0).getStrMeal());
        detailedMealAreaTxt.setText(mealList.get(0).getStrArea());
        stepsTxtArea.setText(mealList.get(0).getStrInstructions());
        IngredientAdapter ingridentAdapter = new IngredientAdapter(getContext(),mealList.get(0).getNonNullIngredients(),mealList.get(0).getNonNullMeasures());
        ingredietsRecyclerView.setAdapter(ingridentAdapter);
        loadYouTubeVideo(mealList.get(0).getStrYoutube());
    }

    @Override
    public void showMealDetailsError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }
}