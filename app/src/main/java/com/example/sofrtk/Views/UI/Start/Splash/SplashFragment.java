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

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SplashFragment extends Fragment {
    private ImageView logo;
    private LottieAnimationView lottieAnimationView;
    private RxSharedPreferences rxSharedPreferences;
    private CompositeDisposable compositeDisposable = new CompositeDisposable(); // For managing Rx subscriptions

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // Dispose subscriptions to avoid memory leaks
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);

        logo = view.findViewById(R.id.sofrtkLogo);
        lottieAnimationView = view.findViewById(R.id.lottie);

        animateLogo();
        playLottieAnimation(view);
    }

    private void animateLogo() {
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
    }

    private void playLottieAnimation(View view) {
        lottieAnimationView.animate()
                .translationY(0)
                .setDuration(1000)
                .setStartDelay(500)
                .alpha(1f)
                .withEndAction(() -> lottieAnimationView.playAnimation());

        lottieAnimationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                checkUserSession(view);
            }
        });
    }

    private void checkUserSession(View view) {
        Disposable disposable = rxSharedPreferences.getBoolean("isLoggedIn", false)
                .asObservable()
                .subscribe(
                        isLoggedIn -> {
                            if (isLoggedIn) {
                                navigateToMainActivity();
                            } else {
                                navigateToSignUpFragment(view);
                            }
                        },
                        throwable -> {
                            Log.e("SplashFragment", "Error checking login status", throwable);
                            navigateToSignUpFragment(view); // Navigate to sign up on error
                        }
                );

        compositeDisposable.add(disposable); // Add disposable to manage lifecycle
    }

    private void navigateToMainActivity() {
        if (isAdded() && getActivity() != null) {
            Disposable disposable = rxSharedPreferences.getString("email").asObservable()
                    .subscribe(email -> {
                        Toast.makeText(getActivity(), getString(R.string.welcome_back) + email, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }, throwable -> Log.e("SplashFragment", "Error getting email", throwable));

            compositeDisposable.add(disposable);
        }
    }

    private void navigateToSignUpFragment(View view) {
        if (isAdded() && getActivity() != null) {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_splashFragment_to_signUpFragment);
        }
    }
}
