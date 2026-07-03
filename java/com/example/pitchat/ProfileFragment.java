package com.example.pitchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private TextView tvNamaProfil, tvInisialProfil, tvBioProfil;
    private EditText etNamaProfil, etBioProfil;
    private Button btnEditSimpan, btnBukaHistory, btnLogout;
    private SharedPreferences sharedPreferences;

    private boolean isEditMode = false;
    private String currentUsername = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Context context = getContext();
        if (context == null) return null;

        // Layout Utama
        LinearLayout rootLayout = new LinearLayout(context);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.parseColor("#F5F7FA"));
        rootLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        rootLayout.setPadding(48, 64, 48, 48);

        // 1. Avatar Lingkaran Besar
        tvInisialProfil = new TextView(context);
        tvInisialProfil.setTextSize(36);
        tvInisialProfil.setTextColor(Color.WHITE);
        tvInisialProfil.setGravity(Gravity.CENTER);

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(Color.parseColor("#1E88E5"));
        tvInisialProfil.setBackground(shape);

        int ukuranPixel = (int) (100 * resourcesDensity());
        LinearLayout.LayoutParams avatarParams = new LinearLayout.LayoutParams(ukuranPixel, ukuranPixel);
        avatarParams.setMargins(0, 0, 0, 40);
        tvInisialProfil.setLayoutParams(avatarParams);
        rootLayout.addView(tvInisialProfil);

        // Container Kotak Informasi Akun
        LinearLayout infoContainer = new LinearLayout(context);
        infoContainer.setOrientation(LinearLayout.VERTICAL);
        infoContainer.setPadding(40, 32, 40, 32);

        GradientDrawable bgContainer = new GradientDrawable();
        bgContainer.setColor(Color.WHITE);
        bgContainer.setCornerRadius(20);
        infoContainer.setBackground(bgContainer);

        LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        infoContainer.setLayoutParams(containerParams);

        // 2. KOMPONEN NAMA
        TextView labelNama = new TextView(context);
        labelNama.setText("Nama Pengguna");
        labelNama.setTextColor(Color.parseColor("#7F8C8D"));
        labelNama.setTextSize(12);
        labelNama.setTypeface(null, Typeface.BOLD);
        infoContainer.addView(labelNama);

        // Teks Tampilan Nama
        tvNamaProfil = new TextView(context);
        tvNamaProfil.setTextSize(18);
        tvNamaProfil.setTextColor(Color.parseColor("#2C3E50"));
        tvNamaProfil.setTypeface(null, Typeface.BOLD);
        tvNamaProfil.setPadding(0, 8, 0, 24);
        infoContainer.addView(tvNamaProfil);

        // Kolom Edit Nama
        etNamaProfil = new EditText(context);
        etNamaProfil.setTextSize(16);
        etNamaProfil.setSingleLine(true);
        etNamaProfil.setVisibility(View.GONE);
        infoContainer.addView(etNamaProfil);

        // Garis Pembatas
        View divider = new View(context);
        divider.setBackgroundColor(Color.parseColor("#ECF0F1"));
        LinearLayout.LayoutParams divParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        divParams.setMargins(0, 8, 0, 16);
        divider.setLayoutParams(divParams);
        infoContainer.addView(divider);

        // 3. KOMPONEN BIO (STATUS)
        TextView labelBio = new TextView(context);
        labelBio.setText("Info / Bio Status");
        labelBio.setTextColor(Color.parseColor("#7F8C8D"));
        labelBio.setTextSize(12);
        labelBio.setTypeface(null, Typeface.BOLD);
        infoContainer.addView(labelBio);

        // Teks Tampilan Bio
        tvBioProfil = new TextView(context);
        tvBioProfil.setTextSize(15);
        tvBioProfil.setTextColor(Color.parseColor("#34495E"));
        tvBioProfil.setPadding(0, 8, 0, 16);
        infoContainer.addView(tvBioProfil);

        // Kolom Edit Bio
        etBioProfil = new EditText(context);
        etBioProfil.setTextSize(15);
        etBioProfil.setVisibility(View.GONE);
        infoContainer.addView(etBioProfil);

        // 4. TOMBOL EDIT DAN SIMPAN PROFIL
        btnEditSimpan = new Button(context);
        btnEditSimpan.setText("EDIT PROFIL 📝");
        btnEditSimpan.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#2ECC71")));
        btnEditSimpan.setTextColor(Color.WHITE);
        btnEditSimpan.setTextSize(12);
        btnEditSimpan.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams btnEditParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnEditParams.setMargins(0, 16, 0, 0);
        btnEditSimpan.setLayoutParams(btnEditParams);
        infoContainer.addView(btnEditSimpan);

        rootLayout.addView(infoContainer);

        // Spacer pendorong menu bawah
        View spacer = new View(context);
        LinearLayout.LayoutParams spacerParams = new LinearLayout.LayoutParams(1, 0, 1f);
        spacer.setLayoutParams(spacerParams);
        rootLayout.addView(spacer);

        // 5. TOMBOL MENU RIWAYAT PANGGILAN
        btnBukaHistory = new Button(context);
        btnBukaHistory.setText("RIWAYAT PANGGULAN TELEPOL 📞");
        btnBukaHistory.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#F39C12")));
        btnBukaHistory.setTextColor(Color.WHITE);
        btnBukaHistory.setTextSize(14);
        btnBukaHistory.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams btnHistoryParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnHistoryParams.setMargins(0, 16, 0, 16);
        btnBukaHistory.setLayoutParams(btnHistoryParams);
        rootLayout.addView(btnBukaHistory);

        // 6. TOMBOL LOGOUT
        btnLogout = new Button(context);
        btnLogout.setText("KELUAR AKUN (LOGOUT)");
        btnLogout.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#E74C3C")));
        btnLogout.setTextColor(Color.WHITE);
        btnLogout.setTextSize(14);
        btnLogout.setTypeface(null, Typeface.BOLD);

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnLogout.setLayoutParams(btnParams);
        rootLayout.addView(btnLogout);

        // Mengambil Data Awal dari SharedPreferences
        sharedPreferences = context.getSharedPreferences("ChatStorage", Context.MODE_PRIVATE);
        currentUsername = sharedPreferences.getString("user", "Pengguna");
        String bioUser = sharedPreferences.getString("status_saya_" + currentUsername, "Ada deh~");

        // Terapkan data ke teks view
        tvNamaProfil.setText(currentUsername);
        tvBioProfil.setText(bioUser);
        if (!currentUsername.isEmpty()) {
            tvInisialProfil.setText(currentUsername.substring(0, 1).toUpperCase());
        }

        // AKSI EDIT / SIMPAN DATA PROFIL
        btnEditSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditMode) {
                    isEditMode = true;
                    btnEditSimpan.setText("SIMPAN PERUBAHAN ✅");
                    btnEditSimpan.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#3498DB")));

                    etNamaProfil.setText(tvNamaProfil.getText().toString());
                    etBioProfil.setText(tvBioProfil.getText().toString());

                    tvNamaProfil.setVisibility(View.GONE);
                    tvBioProfil.setVisibility(View.GONE);
                    etNamaProfil.setVisibility(View.VISIBLE);
                    etBioProfil.setVisibility(View.VISIBLE);
                } else {
                    String namaBaru = etNamaProfil.getText().toString().trim();
                    String bioBaru = etBioProfil.getText().toString().trim();

                    if (namaBaru.isEmpty()) {
                        Toast.makeText(context, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    perbaruiDaftarUserGlobal(currentUsername, namaBaru);

                    sharedPreferences.edit().putString("user", namaBaru).apply();
                    sharedPreferences.edit().putString("status_saya_" + namaBaru, bioBaru).apply();

                    if (!namaBaru.equalsIgnoreCase(currentUsername)) {
                        sharedPreferences.edit().remove("status_saya_" + currentUsername).apply();
                    }

                    currentUsername = namaBaru;

                    tvNamaProfil.setText(namaBaru);
                    tvBioProfil.setText(bioBaru);
                    tvInisialProfil.setText(namaBaru.substring(0, 1).toUpperCase());

                    isEditMode = false;
                    btnEditSimpan.setText("EDIT PROFIL 📝");
                    btnEditSimpan.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#2ECC71")));

                    etNamaProfil.setVisibility(View.GONE);
                    etBioProfil.setVisibility(View.GONE);
                    tvNamaProfil.setVisibility(View.VISIBLE);
                    tvBioProfil.setVisibility(View.VISIBLE);

                    Toast.makeText(context, "Profil Berhasil Diperbarui!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // AKSI BUKA RIWAYAT PANGGILAN
        btnBukaHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentHistory = new Intent(getActivity(), Class.forName("com.example.pitchat.CallHistoryActivity"));
                    startActivity(intentHistory);
                } catch (ClassNotFoundException e) {
                    Toast.makeText(context, "CallHistoryActivity belum dibuat atau terdaftar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // AKSI LOGOUT
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    sharedPreferences.edit().remove("user").apply();
                    try {
                        Intent intentKeLogin = new Intent(getActivity(), Class.forName("com.example.pitchat.LoginActivity"));
                        intentKeLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intentKeLogin);
                    } catch (ClassNotFoundException e) {
                        getActivity().finish();
                    }
                }
            }
        });

        return rootLayout;
    }

    private void perbaruiDaftarUserGlobal(String namaLama, String namaBaru) {
        if (sharedPreferences == null || namaLama.equalsIgnoreCase(namaBaru)) return;

        String jsonDaftar = sharedPreferences.getString("global_user_list", null);
        if (jsonDaftar != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            ArrayList<String> userList = gson.fromJson(jsonDaftar, type);

            if (userList != null) {
                int index = userList.indexOf(namaLama);
                if (index != -1) {
                    userList.set(index, namaBaru);
                } else if (!userList.contains(namaBaru)) {
                    userList.add(namaBaru);
                }
                String jsonBaru = gson.toJson(userList);
                sharedPreferences.edit().putString("global_user_list", jsonBaru).apply();
            }
        }
    }

    private float resourcesDensity() {
        if (getResources() != null && getResources().getDisplayMetrics() != null) {
            return getResources().getDisplayMetrics().density;
        }
        return 2.0f;
    }
}