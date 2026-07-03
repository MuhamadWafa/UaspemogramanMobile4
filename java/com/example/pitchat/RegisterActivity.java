package com.example.pitchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegUsername, etRegPassword;
    private Button btnRegisterSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etRegUsername.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 1. Simpan session user saat ini untuk login otomatis
                SharedPreferences sharedPreferences = getSharedPreferences("ChatStorage", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user", username);
                editor.putString("pass", password);

                // 2. LOGIKA UTAMA: Tambahkan akun baru ke daftar beranda chat global
                Gson gson = new Gson();
                String jsonDaftar = sharedPreferences.getString("global_user_list", null);
                ArrayList<String> listUser;

                if (jsonDaftar == null) {
                    listUser = new ArrayList<>();
                } else {
                    Type type = new TypeToken<ArrayList<String>>() {}.getType();
                    listUser = gson.fromJson(jsonDaftar, type);
                }

                // Cegah duplikasi nama di beranda chat
                if (!listUser.contains(username)) {
                    listUser.add(username);
                }

                // Simpan list yang sudah diperbarui kembali ke storage
                String jsonBaru = gson.toJson(listUser);
                editor.putString("global_user_list", jsonBaru);
                editor.apply();

                Toast.makeText(RegisterActivity.this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show();

                // Langsung masuk ke halaman utama setelah daftar
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}