package com.example.sofrtk.Views.UI.Start.Splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.sofrtk.R;
import com.example.sofrtk.Views.UI.Main.MainActivity;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

public class SplashFragment extends Fragment {
    ImageView logo;
    LottieAnimationView lottieAnimationView;
    private RxSharedPreferences rxSharedPreferences;

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

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);

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
                checkUserSession();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_splashFragment_to_signUpFragment);
            }
        });
    }

    private void checkUserSession() {
            rxSharedPreferences.getBoolean("isLoggedIn", false)
                    .asObservable()
                    .subscribe(
                            isLoggedIn -> {
                                if (isLoggedIn) {
                                    rxSharedPreferences.getString("email").asObservable()
                                            .subscribe(email -> {
                                                // Check if the fragment is still attached before showing the toast
                                                if (isAdded() && getActivity() != null) {
                                                    Toast.makeText(getActivity(), "Welcome back: " + email, Toast.LENGTH_SHORT).show();
                                                }
                                                // Navigate to MainActivity if logged in
                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear the back stack
                                                startActivity(intent);
                                                getActivity().finish();
                                            }, throwable -> {
                                                // Handle error while fetching email
                                                Log.e("SplashFragment", "Error getting email", throwable);
                                            });
                                }
                            },
                            throwable -> {
                                // Handle error while checking login status
                                Log.e("SplashFragment", "Error checking login status", throwable);
                            }
                    );
        }

}