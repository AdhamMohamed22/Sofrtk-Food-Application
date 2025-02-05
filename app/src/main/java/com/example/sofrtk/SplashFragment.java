package com.example.sofrtk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class SplashFragment extends Fragment {
    ImageView logo;
    LottieAnimationView lottieAnimationView;

    public SplashFragment() {
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
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        logo = view.findViewById(R.id.sofrtkLogo);
        lottieAnimationView = view.findViewById(R.id.lottie);

        logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        //lottieAnimationView.animate().translationY(1400).setDuration(6000).setStartDelay(4000);
        lottieAnimationView.animate()
                .translationY(0)
                .setDuration(1000)
                .setStartDelay(500)
                .alpha(1f)
                .withEndAction(() -> {
                    lottieAnimationView.playAnimation();
                });
    }
}