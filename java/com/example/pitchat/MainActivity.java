package com.example.pitchat;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.pitchat.R;

public class MainActivity extends AppCompatActivity {

    private LinearLayout navChat, navProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navChat = findViewById(R.id.nav_chat);
        navProfile = findViewById(R.id.nav_profile);

        // Menampilkan Menu Chat pertama kali aplikasi dibuka
        loadFragment(new ChatFragment());

        if (navChat != null) {
            navChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new ChatFragment());
                }
            });
        }

        if (navProfile != null) {
            navProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadFragment(new ProfileFragment());
                }
            });
        }
    }

    private void loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}