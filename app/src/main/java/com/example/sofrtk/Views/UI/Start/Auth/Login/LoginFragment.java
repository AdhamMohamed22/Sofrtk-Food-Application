package com.example.sofrtk.Views.UI.Start.Auth.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sofrtk.R;
import com.example.sofrtk.Views.UI.Main.MainActivity;
import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginFragment extends Fragment {
    TextView RegisterTxt;
    EditText emailTextField;
    EditText passwordTextField;
    Button btnLogin;
    CardView googleCardView;

    boolean isValid = true;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private RxSharedPreferences rxSharedPreferences;

    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", getActivity().MODE_PRIVATE);
        rxSharedPreferences = RxSharedPreferences.create(sharedPreferences);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(),gso);

        googleCardView = view.findViewById(R.id.cardView);
        googleCardView.setOnClickListener(v -> {
            loginGoogle();
        });

        RegisterTxt = view.findViewById(R.id.RegisterTxt);
        RegisterTxt.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        emailTextField = view.findViewById(R.id.emailTextField);
        passwordTextField = view.findViewById(R.id.passwordTextField);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {

            isValid = true;

            validateEmailEditText(emailTextField);
            validateLengthEditText(passwordTextField,6);

            if(isValid) {
                login();
            }
        });
    }

    public void validateEmailEditText(EditText field){
        if (!Patterns.EMAIL_ADDRESS.matcher(field.getText().toString().trim()).matches()) {
            field.setError("Invalid Email Format");
            isValid = false;
        }
    }

    public void validateLengthEditText(EditText field,int length){
        if(field.getText().length()<length){
            field.setError("This Field Cannot Be Less Than " + length + " Characters");
            isValid = false;
        }
    }

    private void login(){
        String email = emailTextField.getText().toString().trim();
        String password = passwordTextField.getText().toString().trim();

        auth.signInWithEmailAndPassword(email,password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "Logged-in successfully!", Toast.LENGTH_SHORT).show();
                        String userId = authResult.getUser().getUid();
                        saveUserToPreferences(userId, email);
                        navigateToMaiNActivity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed log-in: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void loginGoogle(){
        Intent loginIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(loginIntent,123);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }
    }

    private void loginWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "Logged-in With Google successfully!", Toast.LENGTH_SHORT).show();
                        String userId = authResult.getUser().getUid();
                        String email = authResult.getUser().getEmail();
                        saveUserToPreferences(userId, email);
                        navigateToMaiNActivity();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed log-in With Google: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void saveUserToPreferences(String userId, String email) {
        rxSharedPreferences.getString("userId").set(userId);
        rxSharedPreferences.getString("email").set(email);
        rxSharedPreferences.getBoolean("isLoggedIn").set(true);
    }

    public void navigateToMaiNActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void logoutWithGoogle() {
        auth.signOut();
        googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                RxSharedPreferences rxSharedPreferences = RxSharedPreferences.create(
                        PreferenceManager.getDefaultSharedPreferences(requireContext())
                );

                Preference<Boolean> isLoggedIn = rxSharedPreferences.getBoolean("isLoggedIn", false);
                Preference<String> emailPref = rxSharedPreferences.getString("email", "");

                isLoggedIn.set(false);
                emailPref.delete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {}
        });
    }

}