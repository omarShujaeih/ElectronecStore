package com.example.electronecstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.electronecstore.shared.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Switch showPasswordSwitch;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText       = findViewById(R.id.emailEditText);
        passwordEditText    = findViewById(R.id.passwordEditText);
        showPasswordSwitch  = findViewById(R.id.showPasswordSwitch);
        loginButton         = findViewById(R.id.loginButton);

        showPasswordSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

                passwordEditText.setTransformationMethod(null);
            } else {
                passwordEditText.setTransformationMethod(
                        new PasswordTransformationMethod()
                );
            }
            passwordEditText.setSelection(
                    passwordEditText.getText().length()
            );
        });

        loginButton.setOnClickListener(v -> {
            String email    = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this,
                        "Please enter both email and password.",
                        Toast.LENGTH_SHORT).show();
            } else if (!email.contains("@")) {
                Toast.makeText(this,
                        "Please enter a valid email address.",
                        Toast.LENGTH_SHORT).show();
            } else {
                SharedPrefManager sharedPrefManager =
                        new SharedPrefManager(this);
                sharedPrefManager.saveLoginDetails(email, password);

                Toast.makeText(this,
                        "Logged in successfully!",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(this, HomeActivity.class));
                finish();
            }
        });
    }
}
