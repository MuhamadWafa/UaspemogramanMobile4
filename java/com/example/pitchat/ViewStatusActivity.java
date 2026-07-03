package com.example.pitchat;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class ViewStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_status);

        TextView tvNamaPemilikStatus = findViewById(R.id.tvNamaPemilikStatus);
        TextView btnTutupStatus = findViewById(R.id.btnTutupStatus);
        LinearLayout layoutStatusTeks = findViewById(R.id.layoutStatusTeks);
        TextView tvKontenStatusTeks = findViewById(R.id.tvKontenStatusTeks);
        ImageView ivStatusGambar = findViewById(R.id.ivStatusGambar);
        VideoView vvStatusVideo = findViewById(R.id.vvStatusVideo);

        String nama = getIntent().getStringExtra("STATUS_NAMA_KEY");
        String konten = getIntent().getStringExtra("STATUS_KONTEN_KEY");

        if (nama != null) tvNamaPemilikStatus.setText(nama);

        // SEMBANYIKAN SEMUA KOMPONEN DULU
        layoutStatusTeks.setVisibility(View.GONE);
        ivStatusGambar.setVisibility(View.GONE);
        vvStatusVideo.setVisibility(View.GONE);

        if (konten != null) {
            if (konten.startsWith("content://") || konten.startsWith("file://")) {
                Uri mediaUri = Uri.parse(konten);
                String type = getContentResolver().getType(mediaUri);

                if (type != null && type.startsWith("video/")) {
                    // JIKA MEDIA ADALAH VIDEO
                    vvStatusVideo.setVisibility(View.VISIBLE);
                    vvStatusVideo.setVideoURI(mediaUri);
                    vvStatusVideo.setOnPreparedListener(mp -> {
                        mp.setLooping(true); // Putar terus-menerus
                        vvStatusVideo.start();
                    });
                } else {
                    // JIKA MEDIA ADALAH FOTO
                    ivStatusGambar.setVisibility(View.VISIBLE);
                    ivStatusGambar.setImageURI(mediaUri);
                }
            } else {
                // JIKA HANYA TEXT KETIKAN BIASA
                layoutStatusTeks.setVisibility(View.VISIBLE);
                tvKontenStatusTeks.setText(konten);

                String[] warnaPastel = {"#9B59B6", "#1ABC9C", "#E67E22", "#2C3E50", "#E74C3C", "#27AE60"};
                int acak = new Random().nextInt(warnaPastel.length);
                layoutStatusTeks.setBackgroundColor(Color.parseColor(warnaPastel[acak]));
            }
        }

        if (btnTutupStatus != null) {
            btnTutupStatus.setOnClickListener(v -> finish());
        }
    }
}