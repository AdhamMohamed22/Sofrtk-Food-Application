package com.example.sofrtk.UI.Start.Auth.SignUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.google.firebase.auth.GoogleAuthProvider;

import java.lang.reflect.Array;

public class SignUpFragment extends Fragment {
    TextView loginTxt;
    EditText firstNameTextField;
    EditText lastNameTextField;
    EditText emailTextField;
    EditText passwordTextField;
    EditText confirmPasswordTextField;
    Button btnRegister;
    CardView googleCardView;

    boolean isValid = true;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    public SignUpFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getActivity(),gso);

        googleCardView = view.findViewById(R.id.googleCardView);
        googleCardView.setOnClickListener(v -> {
            signUpGoogle();
        });

        loginTxt = view.findViewById(R.id.loginTxt);
        loginTxt.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_loginFragment);
        });

        firstNameTextField = view.findViewById(R.id.firstNameTextField);
        lastNameTextField = view.findViewById(R.id.lastNameTextField);
        emailTextField = view.findViewById(R.id.emailTextField);
        passwordTextField = view.findViewById(R.id.passwordTextField);
        confirmPasswordTextField = view.findViewById(R.id.confirmPasswordTextField);
        btnRegister = view.findViewById(R.id.btnRegister);

        EditText[] editTextFields = {firstNameTextField,lastNameTextField,emailTextField,passwordTextField,confirmPasswordTextField};
        btnRegister.setOnClickListener(v -> {

            isValid = true;

            for(int i=0;i<5;i++){
                validateEmptyEditText(editTextFields[i]);
            }

            validateLengthEditText(firstNameTextField,3);
            validateLengthEditText(lastNameTextField,3);
            validateLengthEditText(passwordTextField,6);
            validateLengthEditText(confirmPasswordTextField,6);
            validateEmailEditText(emailTextField);

            if(passwordTextField.getText().length()>=6 && confirmPasswordTextField.getText().length()>=6){
                if(!passwordTextField.getText().toString().equals(confirmPasswordTextField.getText().toString())){
                    confirmPasswordTextField.setError("Password Must Be The Same");
                    isValid = false;
                }
            }

            if(isValid){
                signup();
            }
        });


    }
    public void validateEmptyEditText(EditText field){
        if(field.getText().toString().trim().isEmpty()){
            field.setError("This Field Cannot Be Empty");
            isValid = false;
        }
    }

    public void validateLengthEditText(EditText field,int length){
        if(field.getText().length()<length){
            field.setError("This Field Cannot Be Less Than " + length + " Characters");
            isValid = false;
        }
    }

    public void validateEmailEditText(EditText field){
        if (!Patterns.EMAIL_ADDRESS.matcher(field.getText().toString().trim()).matches()) {
            field.setError("Invalid Email Format");
            isValid = false;
        }
    }

    private void signup(){
        String email = emailTextField.getText().toString().trim();
        String password = passwordTextField.getText().toString().trim();

        auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getActivity(), "Sign-up successful!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Failed Sign up: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
    }

    private void signUpGoogle(){
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
                signUpWithWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }
    }

    private void signUpWithWithGoogle(String idToken){
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken,null);
        auth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "Sign-up With Google successful!", Toast.LENGTH_SHORT).show();
                        //Log.i("TAG", "onSuccess: " + authResult.getUser().getDisplayName());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed Sign-up With Google: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void logoutWithGoogle(){
        auth.signOut();
        googleSignInClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void logout(){
        auth.signOut();
    }

}