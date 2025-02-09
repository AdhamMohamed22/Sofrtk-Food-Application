package com.example.sofrtk.UI.Main.MealDetails;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sofrtk.Adapters.IngridentAdapter;
import com.example.sofrtk.Pojos.RandomMeal;
import com.example.sofrtk.R;

public class DetailedMealFragment extends Fragment {
    ImageView detailedMealImage;
    TextView detailedMealNameTxt;
    TextView detailedMealAreaTxt;
    RecyclerView ingredietsRecyclerView;
    EditText stepsTxtArea;
    WebView videoWebView;
    int id;
    RandomMeal randomMeal;

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

        detailedMealImage = view.findViewById(R.id.detailedMealImage);
        detailedMealNameTxt = view.findViewById(R.id.detailedMealNameTxt);
        detailedMealAreaTxt = view.findViewById(R.id.detailedMealAreaTxt);
        stepsTxtArea = view.findViewById(R.id.stepsTxtArea);

        ingredietsRecyclerView = view.findViewById(R.id.ingredietsRecyclerView);
        ingredietsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        videoWebView = view.findViewById(R.id.videoWebView);

        id = getArguments().getInt("id");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            randomMeal = getArguments().getParcelable("meal", RandomMeal.class);
        }

        if (randomMeal != null) {
            //Log.i("X", "Object onViewCreated: " + randomMeal.getStrMeal());
            Glide.with(requireView()).load(randomMeal.getStrMealThumb()).apply(new RequestOptions().override(400, 400)).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground).into(detailedMealImage);
            detailedMealNameTxt.setText(randomMeal.getStrMeal());
            detailedMealAreaTxt.setText(randomMeal.getStrArea());
            stepsTxtArea.setText(randomMeal.getStrInstructions());

            IngridentAdapter ingridentAdapter = new IngridentAdapter(getContext(),randomMeal.getNonNullIngredients(),randomMeal.getNonNullMeasures());
            ingredietsRecyclerView.setAdapter(ingridentAdapter);

            loadYouTubeVideo(randomMeal.getStrYoutube());
        } else {
            Log.i("X", "id onViewCreated: " + id);
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
}