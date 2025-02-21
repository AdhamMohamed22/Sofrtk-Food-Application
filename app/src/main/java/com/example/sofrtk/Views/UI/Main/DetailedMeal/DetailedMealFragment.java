package com.example.sofrtk.Views.UI.Main.DetailedMeal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.util.Log;
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
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

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
    CardView favouriteCardView;
    ImageView favourite;
    CardView bookmarkCardView;
    ImageView bookmark;
    RxSharedPreferences rxSharedPreferences;

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

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);

        mealDetailsPresenter = new DetailedMealPresenterImp(this, Repository.getInstance(getActivity()));

        favouriteCardView = view.findViewById(R.id.favouriteCardView);
        favourite = view.findViewById(R.id.favourite);
        bookmarkCardView = view.findViewById(R.id.bookmarkCardView);
        bookmark = view.findViewById(R.id.bookmark);

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
            showMealDetails(randomMeal);
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
    public void showMealDetails(RandomMeal meal) {
        Glide.with(requireView()).load(meal.getStrMealThumb()).apply(new RequestOptions().override(400, 400)).into(detailedMealImage);
        detailedMealNameTxt.setText(meal.getStrMeal());
        detailedMealAreaTxt.setText(meal.getStrArea());
        stepsTxtArea.setText(meal.getStrInstructions());
        IngredientAdapter ingridentAdapter = new IngredientAdapter(getContext(),meal.getNonNullIngredients(),meal.getNonNullMeasures());
        ingredietsRecyclerView.setAdapter(ingridentAdapter);
        loadYouTubeVideo(meal.getStrYoutube());

        favouriteCardView.setOnClickListener(v -> {
            if (rxSharedPreferences.getBoolean("isLoggedIn", false).get()) {
                mealDetailsPresenter.addToFavourite(meal);
            } else {
                Toast.makeText(getActivity(),"Not Available Feature For Guest,           You must Register!",Toast.LENGTH_LONG).show();
            }
        });

        bookmarkCardView.setOnClickListener(v -> {
            if (rxSharedPreferences.getBoolean("isLoggedIn", false).get()) {
                handleDatePicker(v,meal);
            } else {
                Toast.makeText(getActivity(),"Not Available Feature For Guest,           You must Register!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void showMealDetailsError(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInsertFavouriteSuccess() {
        Toast.makeText(getActivity(), "Meal Added To Favourites Successfully!", Toast.LENGTH_LONG).show();
        favourite.setImageResource(R.drawable.favourite_icon);
    }

    @Override
    public void onInsertFavouriteFail(String errorMsg) {
        Toast.makeText(getActivity(), "Failed Adding Meal To Favourites!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInsertPlanSuccess() {
        Toast.makeText(getActivity(), "Meal Added To Plan Successfully!", Toast.LENGTH_LONG).show();
        bookmark.setImageResource(R.drawable.bookmark);
    }

    @Override
    public void onInsertPlanFail(String errorMsg) {
        Toast.makeText(getActivity(), "Failed Adding Meal To Plan!", Toast.LENGTH_LONG).show();
        Log.e("TAG", "onInsertPlanFail: " + errorMsg);
    }

    @Override
    public Activity getViewActivity() {
        return requireActivity();
    }

    public void handleDatePicker(View v,RandomMeal meal){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long today = calendar.getTimeInMillis();

        calendar.add(Calendar.DAY_OF_MONTH, 7);
        long maxDate = calendar.getTimeInMillis();

        CalendarConstraints.DateValidator validator = new CalendarConstraints.DateValidator() {
            @Override
            public int describeContents() {return 0;}
            @Override
            public void writeToParcel(@NonNull Parcel dest, int flags) {}
            @Override
            public boolean isValid(long date) {return date >= today && date <= maxDate;}
        };

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setStart(today)
                .setEnd(maxDate)
                .setValidator(validator)
                .build();

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a Date")
                .setSelection(today)
                .setCalendarConstraints(constraints)
                .build();

        datePicker.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), "DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM dd", Locale.getDefault());
            String selectedDate = sdf.format(selection);
            mealDetailsPresenter.addToPlan(meal,selectedDate);
            Log.i("TAG", "onClicks: " + rxSharedPreferences.getString("userId").get() + selectedDate);
        });
    }
}