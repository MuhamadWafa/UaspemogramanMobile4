package com.example.pitchat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class CallHistoryActivity extends AppCompatActivity {

    private LinearLayout layoutDaftarHistory;
    private TextView tvHistoryKosong, btnKembaliHistory, btnHapusSemuaHistory;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);

        layoutDaftarHistory = findViewById(R.id.layoutDaftarHistory);
        tvHistoryKosong = findViewById(R.id.tvHistoryKosong);
        btnKembaliHistory = findViewById(R.id.btnKembaliHistory);
        btnHapusSemuaHistory = findViewById(R.id.btnHapusSemuaHistory);

        sharedPreferences = getSharedPreferences("ChatStorage", Context.MODE_PRIVATE);
        gson = new Gson();

        if (btnKembaliHistory != null) {
            btnKembaliHistory.setOnClickListener(v -> finish());
        }

        if (btnHapusSemuaHistory != null) {
            btnHapusSemuaHistory.setOnClickListener(v -> {
                sharedPreferences.edit().remove("riwayat_panggilan_list").apply();
                Toast.makeText(this, "Riwayat dibersihkan!", Toast.LENGTH_SHORT).show();
                tampilkanRiwayat();
            });
        }

        tampilkanRiwayat();
    }

    private void tampilkanRiwayat() {
        if (layoutDaftarHistory == null) return;
        layoutDaftarHistory.removeAllViews();

        // Ambil list riwayat dari SharedPreferences
        String jsonHistory = sharedPreferences.getString("riwayat_panggilan_list", null);
        ArrayList<String> listHistory = new ArrayList<>();

        if (jsonHistory != null) {
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            listHistory = gson.fromJson(jsonHistory, type);
        }

        if (listHistory == null || listHistory.isEmpty()) {
            tvHistoryKosong.setVisibility(View.VISIBLE);
            return;
        }

        tvHistoryKosong.setVisibility(View.GONE);

        // Balik urutan agar panggilan terbaru muncul di paling atas
        Collections.reverse(listHistory);

        float density = getResources().getDisplayMetrics().density;

        for (String item : listHistory) {
            // Format data tersimpan contoh: "Budi Setiawan|03/07/2026 22:45"
            String nama = "Pengguna";
            String waktu = "--:--";

            if (item.contains("|")) {
                String[] split = item.split("\\|");
                if (split.length >= 2) {
                    nama = split[0];
                    waktu = split[1];
                }
            } else {
                nama = item;
            }

            // Desain Row Riwayat Panggilan
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding((int) (16 * density), (int) (16 * density), (int) (16 * density), (int) (16 * density));
            row.setBackgroundColor(Color.WHITE);
            row.setGravity(Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 0, 0, (int) (2 * density));
            row.setLayoutParams(rowParams);

            // Icon Telepon Kecil Hijau/Merah tanda riwayat keluar
            TextView tvIconCall = new TextView(this);
            tvIconCall.setText("📞");
            tvIconCall.setTextSize(18);
            tvIconCall.setGravity(Gravity.CENTER);
            row.addView(tvIconCall);

            // Kontainer Teks (Nama & Waktu)
            LinearLayout textContainer = new LinearLayout(this);
            textContainer.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams textContainerParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            textContainerParams.setMargins((int) (16 * density), 0, 0, 0);
            textContainer.setLayoutParams(textContainerParams);

            TextView tvNama = new TextView(this);
            tvNama.setText(nama);
            tvNama.setTextSize(16);
            tvNama.setTextColor(Color.parseColor("#2C3E50"));
            tvNama.setTypeface(null, Typeface.BOLD);
            textContainer.addView(tvNama);

            TextView tvWaktu = new TextView(this);
            tvWaktu.setText("Panggilan Keluar • " + waktu);
            tvWaktu.setTextSize(12);
            tvWaktu.setTextColor(Color.parseColor("#7F8C8D"));
            tvWaktu.setPadding(0, (int) (4 * density), 0, 0);
            textContainer.addView(tvWaktu);

            row.addView(textContainer);
            layoutDaftarHistory.addView(row);
        }
    }
}