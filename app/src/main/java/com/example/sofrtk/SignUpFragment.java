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

import java.lang.reflect.Array;

public class SignUpFragment extends Fragment {
    TextView loginTxt;
    EditText firstNameTextField;
    EditText lastNameTextField;
    EditText emailTextField;
    EditText passwordTextField;
    EditText confirmPasswordTextField;
    Button btnRegister;

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
            for(int i=0;i<5;i++){
                validateEmptyEditText(editTextFields[i]);
            }

            if(firstNameTextField.getText().length()<3){
                firstNameTextField.setError("The Name Cannot Be Less Than 3 Characters");
            }

            if(lastNameTextField.getText().length()<3){
                lastNameTextField.setError("The Name Cannot Be Less Than 3 Characters");
            }

            if(passwordTextField.getText().length()<6){
                passwordTextField.setError("The Password Cannot Be Less Than 6 Characters");
            }

            if(confirmPasswordTextField.getText().length()<6){
                confirmPasswordTextField.setError("The Password Cannot Be Less Than 6 Characters");
            }

            if(passwordTextField.getText().length()>=6 && confirmPasswordTextField.getText().length()>=6){
                if(!passwordTextField.getText().toString().equals(confirmPasswordTextField.getText().toString())){
                    confirmPasswordTextField.setError("Password Must Be The Same");
                }
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailTextField.getText().toString().trim()).matches()) {
                emailTextField.setError("Invalid Email Format");
            }
        });


    }
    public void validateEmptyEditText(EditText field){
        if(field.getText().toString().trim().isEmpty()){
            field.setError("This Field Cannot Be Empty");
        }
    }

}