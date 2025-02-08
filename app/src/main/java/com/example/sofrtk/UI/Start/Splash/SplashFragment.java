package com.example.sofrtk.UI.Start.Splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sofrtk.R;

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

        //logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        logo.setAlpha(0f);
        logo.setScaleX(0.5f);
        logo.setScaleY(0.5f);
        logo.setTranslationY(100f);

        logo.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .translationY(0)
                .setDuration(1200)
                .setStartDelay(500)
                .setInterpolator(new AccelerateDecelerateInterpolator());


        lottieAnimationView.animate()
                .translationY(0)
                .setDuration(1000)
                .setStartDelay(500)
                .alpha(1f)
                .withEndAction(() -> {
                    lottieAnimationView.playAnimation();
                });

        lottieAnimationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_splashFragment_to_signUpFragment);
            }
        });
    }
}