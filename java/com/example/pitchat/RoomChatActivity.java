package com.example.pitchat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RoomChatActivity extends AppCompatActivity {

    private EditText etInputPesan;
    private Button btnKirimPesan;
    private LinearLayout layoutWadahPesan;
    private ScrollView scrollRuangChat;

    private SharedPreferences sharedPreferences;
    private String namaTujuanChat;
    private String currentUser;
    private String nomorKunciChatGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_chat);

        // FIX: Diselaraskan dengan ID yang ada pada activity_room_chat.xml kamu
        etInputPesan = findViewById(R.id.etPesan);
        btnKirimPesan = findViewById(R.id.btnSend);
        layoutWadahPesan = findViewById(R.id.layoutAreaChat);
        scrollRuangChat = findViewById(R.id.chatScrollView);

        // Tombol kembali bawaan XML
        Button btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        sharedPreferences = getSharedPreferences("ChatStorage", Context.MODE_PRIVATE);
        currentUser = sharedPreferences.getString("user", "Saya");

        namaTujuanChat = getIntent().getStringExtra("NAMA_KEY");
        if (namaTujuanChat == null) {
            namaTujuanChat = "Teman Chat";
        }

        if (currentUser.compareTo(namaTujuanChat) < 0) {
            nomorKunciChatGlobal = "chat_" + currentUser + "_" + namaTujuanChat;
        } else {
            nomorKunciChatGlobal = "chat_" + namaTujuanChat + "_" + currentUser;
        }

        buatTombolTeleponDiHeader();
        muatRiwayatChatLokal();

        if (btnKirimPesan != null) {
            btnKirimPesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String teksPesan = etInputPesan.getText().toString().trim();
                    if (!teksPesan.isEmpty()) {
                        simpanDanTampilkanPesan(currentUser, teksPesan);
                        etInputPesan.setText("");
                    }
                }
            });
        }
    }

    private void buatTombolTeleponDiHeader() {
        ViewGroup wadahUtamaLayar = (ViewGroup) findViewById(android.R.id.content);
        if (wadahUtamaLayar != null && wadahUtamaLayar.getChildCount() > 0) {
            View tampilanPalingAtas = wadahUtamaLayar.getChildAt(0);

            // Karena root layout XML kamu adalah RelativeLayout, kita sesuaikan penanganannya di sini
            if (tampilanPalingAtas instanceof ViewGroup) {
                ViewGroup layoutRoot = (ViewGroup) tampilanPalingAtas;

                // Update text nama secara langsung pada TextView yang sudah ada di XML topBar
                TextView tvNamaChat = findViewById(R.id.tvNamaChat);
                if (tvNamaChat != null) {
                    tvNamaChat.setText(namaTujuanChat);
                }

                // Cari layout topBar di XML kamu untuk menyematkan tombol telepon secara pas
                LinearLayout topBar = findViewById(R.id.topBar);
                if (topBar != null) {
                    TextView btnTeleponBintang = new TextView(this);
                    btnTeleponBintang.setText("📞 TELP");
                    btnTeleponBintang.setTextSize(12);
                    btnTeleponBintang.setTextColor(Color.WHITE);
                    btnTeleponBintang.setTypeface(null, Typeface.BOLD);
                    btnTeleponBintang.setPadding(20, 12, 20, 12);

                    GradientDrawable bgTombolTelp = new GradientDrawable();
                    bgTombolTelp.setShape(GradientDrawable.RECTANGLE);
                    bgTombolTelp.setCornerRadius(10);
                    bgTombolTelp.setColor(Color.parseColor("#2ECC71"));
                    btnTeleponBintang.setBackground(bgTombolTelp);

                    LinearLayout.LayoutParams telpParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    telpParams.setMarginStart(16);
                    btnTeleponBintang.setLayoutParams(telpParams);

                    btnTeleponBintang.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intentCall = new Intent(RoomChatActivity.this, CallActivity.class);
                            intentCall.putExtra("NAMA_CALL_KEY", namaTujuanChat);
                            startActivity(intentCall);
                        }
                    });

                    topBar.addView(btnTeleponBintang);
                }
            }
        }
    }

    private void simpanDanTampilkanPesan(String pengirim, String teks) {
        if (layoutWadahPesan == null) return;

        String formatPesanLengkap = pengirim + ": " + teks;

        TextView tvPesan = new TextView(this);
        tvPesan.setText(formatPesanLengkap);
        tvPesan.setTextSize(15);
        tvPesan.setPadding(24, 16, 24, 16);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8);

        if (pengirim.equals(currentUser)) {
            tvPesan.setBackgroundColor(Color.parseColor("#DCF8C6"));
            params.gravity = Gravity.END;
        } else {
            tvPesan.setBackgroundColor(Color.parseColor("#FFFFFF"));
            params.gravity = Gravity.START;
        }

        tvPesan.setLayoutParams(params);
        layoutWadahPesan.addView(tvPesan);
        gulirOtomatisKeBawah();

        ArrayList<String> listChat = ambilDataChatLokal();
        listChat.add(formatPesanLengkap);

        Gson gson = new Gson();
        String jsonBaru = gson.toJson(listChat);
        sharedPreferences.edit().putString(nomorKunciChatGlobal, jsonBaru).apply();
    }

    private ArrayList<String> ambilDataChatLokal() {
        String jsonChat = sharedPreferences.getString(nomorKunciChatGlobal, null);
        if (jsonChat == null) {
            return new ArrayList<>();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>() {}.getType();
            return gson.fromJson(jsonChat, type);
        }
    }

    private void muatRiwayatChatLokal() {
        if (layoutWadahPesan == null) return;
        layoutWadahPesan.removeAllViews();

        ArrayList<String> riwayat = ambilDataChatLokal();
        for (String pesanLama : riwayat) {
            TextView tvPesan = new TextView(this);
            tvPesan.setText(pesanLama);
            tvPesan.setTextSize(15);
            tvPesan.setPadding(24, 16, 24, 16);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 8, 0, 8);

            if (pesanLama.startsWith(currentUser + ":")) {
                tvPesan.setBackgroundColor(Color.parseColor("#DCF8C6"));
                params.gravity = Gravity.END;
            } else {
                tvPesan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                params.gravity = Gravity.START;
            }
            tvPesan.setLayoutParams(params);
            layoutWadahPesan.addView(tvPesan);
        }
        gulirOtomatisKeBawah();
    }

    private void gulirOtomatisKeBawah() {
        if (scrollRuangChat != null) {
            scrollRuangChat.post(() -> scrollRuangChat.fullScroll(View.FOCUS_DOWN));
        }
    }
}