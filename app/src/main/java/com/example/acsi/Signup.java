package com.example.acsi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.acsi.databinding.ActivitySignupBinding;

public class Signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirmpassword.getText().toString();

                if (email.equals("") || password.equals("") || confirmPassword.equals("")) {
                    Toast.makeText(Signup.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValid = databaseHelper.isValidEmail(email);

                    if (!isValid) {
                        Toast.makeText(Signup.this, "Not an email format", Toast.LENGTH_SHORT).show();
                    } else {


                        if (password.equals(confirmPassword)) {
                            boolean checkUserEmail = databaseHelper.checkEmail(email);
                            if (!checkUserEmail) {
                                Boolean insert = databaseHelper.insertData(email, password);
                                if (insert) {
                                    Toast.makeText(Signup.this, "SignUp successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(Signup.this, "Signup failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Signup.this, "User Already exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Signup.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });

        binding.signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}