package com.example.sofrtk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    TextView RegisterTxt;
    EditText emailTextField;
    EditText passwordTextField;
    Button btnLogin;

    boolean isValid = true;
    FirebaseAuth auth;

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

        auth = FirebaseAuth.getInstance();

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
                        Toast.makeText(requireContext(), "log-in successful!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failed log-in: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}