package com.example.pitchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText etLoginUsername = findViewById(R.id.etLoginUsername);
        EditText etLoginPassword = findViewById(R.id.etLoginPassword);
        Button btnLoginSubmit = findViewById(R.id.btnLoginSubmit);
        TextView tvToRegister = findViewById(R.id.tvToRegister);
        SharedPreferences sharedPreferences = getSharedPreferences("ChatStorage", Context.MODE_PRIVATE);

        btnLoginSubmit.setOnClickListener(v -> {
            String user = etLoginUsername.getText().toString().trim();
            String pass = etLoginPassword.getText().toString().trim();

            if (pass.equals(sharedPreferences.getString("credentials_" + user, ""))) {
                sharedPreferences.edit().putString("logged_in_user", user).apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
            }
        });

        tvToRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}