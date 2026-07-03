package com.example.pitchat;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CallActivity extends AppCompatActivity {

    private TextView tvCallName;
    private Chronometer chronometerCall;
    private Button btnEndCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        tvCallName = findViewById(R.id.tvCallName);
        chronometerCall = findViewById(R.id.chronometerCall);
        btnEndCall = findViewById(R.id.btnEndCall);

        // Ambil nama kontak yang dikirim dari halaman chat
        String namaKontak = getIntent().getStringExtra("NAMA_CALL_KEY");
        if (namaKontak != null) {
            tvCallName.setText(namaKontak);
        }

        // Jalankan timer telepon otomatis
        chronometerCall.setBase(SystemClock.elapsedRealtime());
        chronometerCall.start();

        // Aksi ketika tombol tutup telepon ditekan
        btnEndCall.setOnClickListener(v -> {
            chronometerCall.stop();
            Toast.makeText(CallActivity.this, "Panggilan Berakhir", Toast.LENGTH_SHORT).show();
            finish(); // Tutup halaman telepon kembali ke ruang chat
        });
    }
}