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

public class LoginFragment extends Fragment {
    TextView RegisterTxt;
    EditText emailTextField;
    EditText passwordTextField;
    Button btnLogin;

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

        RegisterTxt = view.findViewById(R.id.RegisterTxt);
        RegisterTxt.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        emailTextField = view.findViewById(R.id.emailTextField);
        passwordTextField = view.findViewById(R.id.passwordTextField);
        btnLogin = view.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            if (!Patterns.EMAIL_ADDRESS.matcher(emailTextField.getText().toString().trim()).matches()) {
                emailTextField.setError("Invalid Email Format");
            }

            if(passwordTextField.getText().length()<6){
                passwordTextField.setError("The Password Cannot Be Less Than 6 Characters");
            }
        });
    }
}