package com.example.acsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.acsi.databinding.ActivityLoginBinding;
import com.example.acsi.databinding.ActivityMainBinding;

public class Login extends AppCompatActivity {
    ActivityMainBinding bind;
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);
        binding.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();
                if (email.equals("") || password.equals("")) {
                    Toast.makeText(Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValid = databaseHelper.isValidEmail(email);

                    if (!isValid) {
                        Toast.makeText(Login.this, "Not an email format", Toast.LENGTH_SHORT).show();
                    } else {
                        Boolean chekCredentials = databaseHelper.checkEmailPassword(email, password);
                        if (chekCredentials == true) {

                                Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);

                                finish();

                        } else {
                            Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
        binding.loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Signup.class);
                startActivity(intent);
                finish();
            }
        });
    }
}