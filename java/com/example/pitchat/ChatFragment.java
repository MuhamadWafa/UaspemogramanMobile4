package com.example.pitchat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private EditText etSearchChat;
    private LinearLayout layoutDaftarKontak;
    private LinearLayout layoutDaftarStatus;
    private Button btnTambahStatus;
    private SharedPreferences sharedPreferences;

    private Uri mediaUriTerpilih = null;
    private TextView tvStatusMediaTerpilih = null;

    // Launcher untuk mengambil foto/video dari galeri
    private final ActivityResultLauncher<String> pencariGaleriLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null && getContext() != null) {
                    mediaUriTerpilih = uri;

                    try {
                        getContext().getContentResolver().takePersistableUriPermission(
                                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (tvStatusMediaTerpilih != null) {
                        tvStatusMediaTerpilih.setText("Media Siap Dibagikan! ✅");
                        tvStatusMediaTerpilih.setTextColor(Color.parseColor("#2ECC71"));
                    }
                }
            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        etSearchChat = view.findViewById(R.id.etSearchChat);
        layoutDaftarKontak = view.findViewById(R.id.layoutDaftarKontak);
        layoutDaftarStatus = view.findViewById(R.id.layoutDaftarStatus);
        btnTambahStatus = view.findViewById(R.id.btnTambahStatus);

        if (getActivity() != null) {
            sharedPreferences = getActivity().getSharedPreferences("ChatStorage", Context.MODE_PRIVATE);
        }

        if (btnTambahStatus != null) {
            btnTambahStatus.setOnClickListener(v -> tampilkanDialogInputStatus());
        }

        // Tombol Chatbot Otomatis Dinamis di atas daftar kontak
        if (getContext() != null && layoutDaftarKontak != null) {
            TextView btnKeChatbot = new TextView(getContext());
            btnKeChatbot.setText("🤖 TANYA AI ASISTEN MATERI (CHATBOT) →");
            btnKeChatbot.setTextSize(14);
            btnKeChatbot.setTextColor(Color.WHITE);
            btnKeChatbot.setTypeface(null, Typeface.BOLD);
            btnKeChatbot.setGravity(Gravity.CENTER);
            btnKeChatbot.setPadding(32, 32, 32, 32);

            GradientDrawable bgBot = new GradientDrawable();
            bgBot.setShape(GradientDrawable.RECTANGLE);
            bgBot.setCornerRadius(15);
            bgBot.setColor(Color.parseColor("#0088CC"));
            btnKeChatbot.setBackground(bgBot);

            LinearLayout.LayoutParams botParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            botParams.setMargins(32, 16, 32, 32);
            btnKeChatbot.setLayoutParams(botParams);

            btnKeChatbot.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ChatBotActivity.class);
                startActivity(intent);
            });

            layoutDaftarKontak.addView(btnKeChatbot, 0);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<String> daftarNama = dapatkanDaftarNamaSaja();
        tampilkanStatusDinamis(daftarNama);
        tampilkanKontakDinamis(daftarNama);
    }

    private void tampilkanDialogInputStatus() {
        if (getContext() == null) return;

        mediaUriTerpilih = null; // reset pilihan media

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Atur Status Kamu 🎬");

        LinearLayout dialogLayout = new LinearLayout(getContext());
        dialogLayout.setOrientation(LinearLayout.VERTICAL);
        dialogLayout.setPadding(40, 20, 40, 20);

        final EditText inputTeksStatus = new EditText(getContext());
        inputTeksStatus.setHint("Tulis caption/ketikan status...");
        dialogLayout.addView(inputTeksStatus);

        // Tombol pilih foto/video
        Button btnPilihMedia = new Button(getContext());
        btnPilihMedia.setText("📁 Pilih Foto / Video dari Galeri");
        btnPilihMedia.setBackgroundColor(Color.parseColor("#34495E"));
        btnPilihMedia.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.setMargins(0, 20, 0, 10);
        btnPilihMedia.setLayoutParams(btnParams);
        dialogLayout.addView(btnPilihMedia);

        // Text Penanda status file media
        tvStatusMediaTerpilih = new TextView(getContext());
        tvStatusMediaTerpilih.setText("Atau bagikan teks saja tanpa media.");
        tvStatusMediaTerpilih.setTextSize(12);
        tvStatusMediaTerpilih.setTextColor(Color.GRAY);
        tvStatusMediaTerpilih.setPadding(0, 0, 0, 20);
        dialogLayout.addView(tvStatusMediaTerpilih);

        // --- FITUR TOMBOL HAPUS STATUS ---
        String currentUser = sharedPreferences != null ? sharedPreferences.getString("user", "Saya") : "Saya";
        String statusSaatIni = sharedPreferences != null ?
                sharedPreferences.getString("status_saya_" + currentUser, "") : "";

        // Jika user punya status aktif (bukan kosong/bukan bawaan), munculkan tombol hapus
        if (!statusSaatIni.isEmpty() && !statusSaatIni.equals("Ketuk untuk membuat status baru.")) {
            Button btnHapusStatus = new Button(getContext());
            btnHapusStatus.setText("🗑️ Hapus Status Saat Ini");
            btnHapusStatus.setBackgroundColor(Color.parseColor("#E74C3C"));
            btnHapusStatus.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams hapusParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            hapusParams.setMargins(0, 10, 0, 10);
            btnHapusStatus.setLayoutParams(hapusParams);

            btnHapusStatus.setOnClickListener(v -> {
                if (sharedPreferences != null) {
                    // Kembalikan ke teks kosong / bawaan awal
                    sharedPreferences.edit().putString("status_saya_" + currentUser, "Ketuk untuk membuat status baru.").apply();
                    Toast.makeText(getContext(), "Status berhasil dihapus!", Toast.LENGTH_SHORT).show();

                    // Refresh tampilan ring status secara real-time
                    ArrayList<String> daftarNama = dapatkanDaftarNamaSaja();
                    tampilkanStatusDinamis(daftarNama);
                }
                // Tutup dialog secara otomatis setelah menghapus
                builder.create().dismiss();
                tampilkanStatusDinamis(dapatkanDaftarNamaSaja());
            });
            dialogLayout.addView(btnHapusStatus);
        }
        // ---------------------------------

        builder.setView(dialogLayout);

        btnPilihMedia.setOnClickListener(v -> pencariGaleriLauncher.launch("*/*"));

        builder.setPositiveButton("Bagikan", (dialog, which) -> {
            String dataStatusAkhir = "";

            if (mediaUriTerpilih != null) {
                dataStatusAkhir = mediaUriTerpilih.toString();
            } else {
                dataStatusAkhir = inputTeksStatus.getText().toString().trim();
            }

            if (!dataStatusAkhir.isEmpty() && sharedPreferences != null) {
                sharedPreferences.edit().putString("status_saya_" + currentUser, dataStatusAkhir).apply();
                Toast.makeText(getContext(), "Status berhasil diperbarui!", Toast.LENGTH_SHORT).show();

                ArrayList<String> daftarNama = dapatkanDaftarNamaSaja();
                tampilkanStatusDinamis(daftarNama);
            }
        });

        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private ArrayList<String> dapatkanDaftarNamaSaja() {
        ArrayList<String> daftarNama = new ArrayList<>();
        daftarNama.add("Saiful Udin");
        daftarNama.add("Budi Setiawan");
        daftarNama.add("Andi Wijaya");
        daftarNama.add("upit");

        if (sharedPreferences != null) {
            String jsonDaftar = sharedPreferences.getString("global_user_list", null);
            if (jsonDaftar != null) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> userDariRegister = gson.fromJson(jsonDaftar, type);
                if (userDariRegister != null) {
                    for (String namaBaru : userDariRegister) {
                        if (!daftarNama.contains(namaBaru)) {
                            daftarNama.add(namaBaru);
                        }
                    }
                }
            }
        }
        return daftarNama;
    }

    private void tampilkanStatusDinamis(ArrayList<String> daftarNama) {
        if (layoutDaftarStatus == null) return;
        layoutDaftarStatus.removeAllViews();

        String currentUser = sharedPreferences != null ? sharedPreferences.getString("user", "Saya") : "Saya";
        String statusSayaSaves = sharedPreferences != null ?
                sharedPreferences.getString("status_saya_" + currentUser, "Ketuk untuk membuat status baru.") : "Ketuk untuk membuat status baru.";

        String[] warnaAvatar = {"#E57373", "#4FC3F7", "#81C784", "#BA68C8", "#FFB74D"};
        String[] listStoryTiruan = {
                "Lagi belajar Android Studio nih! 💻",
                "Info takjil gratis dong cuy.. 🥥",
                "Hari ini cuacanya cerah banget ya ☀️",
                "Otw ngopi bareng anak-anak ☕",
                "Tetap semangat coding semuanya! 🙌"
        };

        // 1. Status Saya
        buatBulatanStatus("Status Saya", "#95A5A6", statusSayaSaves, true);

        // 2. Status Kontak Lain
        for (int i = 0; i < daftarNama.size(); i++) {
            final String nama = daftarNama.get(i);
            if (nama.equalsIgnoreCase(currentUser)) continue;

            String warnaPilihan = warnaAvatar[i % warnaAvatar.length];
            String isiStory = listStoryTiruan[i % listStoryTiruan.length];

            buatBulatanStatus(nama, warnaPilihan, isiStory, false);
        }
    }

    private void buatBulatanStatus(String nama, String warnaHex, String kontenStory, boolean isMe) {
        LinearLayout wadahBulatan = new LinearLayout(getContext());
        wadahBulatan.setOrientation(LinearLayout.VERTICAL);
        wadahBulatan.setGravity(Gravity.CENTER_HORIZONTAL);
        wadahBulatan.setPadding(12, 0, 12, 0);

        String inisial = nama.substring(0, 1).toUpperCase();

        TextView tvAvatarStatus = new TextView(getContext());
        tvAvatarStatus.setText(inisial);
        tvAvatarStatus.setTextSize(16);
        tvAvatarStatus.setTextColor(Color.WHITE);
        tvAvatarStatus.setGravity(Gravity.CENTER);

        GradientDrawable ringStory = new GradientDrawable();
        ringStory.setShape(GradientDrawable.OVAL);
        ringStory.setColor(Color.parseColor(warnaHex));

        // Ring border otomatis menjadi abu-abu jika status kosong/belum dibuat
        boolean statusKosong = kontenStory.equals("Ketuk untuk membuat status baru.");
        ringStory.setStroke(6, Color.parseColor(isMe && statusKosong ? "#BDC3C7" : (isMe ? "#2ECC71" : "#2ECC71")));

        int ukuranPixel = (int) (56 * getResources().getDisplayMetrics().density);
        LinearLayout.LayoutParams avatarParams = new LinearLayout.LayoutParams(ukuranPixel, ukuranPixel);
        tvAvatarStatus.setLayoutParams(avatarParams);
        tvAvatarStatus.setBackground(ringStory);
        wadahBulatan.addView(tvAvatarStatus);

        TextView tvNamaStatus = new TextView(getContext());
        String namaPanggilan = nama.split(" ")[0];
        tvNamaStatus.setText(namaPanggilan);
        tvNamaStatus.setTextSize(11);
        tvNamaStatus.setTextColor(Color.parseColor("#2C3E50"));
        tvNamaStatus.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams namaParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        namaParams.setMargins(0, 8, 0, 0);
        tvNamaStatus.setLayoutParams(namaParams);
        wadahBulatan.addView(tvNamaStatus);

        wadahBulatan.setOnClickListener(v -> {
            if (isMe && kontenStory.equals("Ketuk untuk membuat status baru.")) {
                tampilkanDialogInputStatus();
            } else {
                if (!isMe) {
                    ringStory.setStroke(6, Color.parseColor("#BDC3C7"));
                    tvAvatarStatus.setBackground(ringStory);
                }

                Intent intentStatus = new Intent(getContext(), ViewStatusActivity.class);
                intentStatus.putExtra("STATUS_NAMA_KEY", nama);
                intentStatus.putExtra("STATUS_KONTEN_KEY", kontenStory);
                startActivity(intentStatus);
            }
        });

        layoutDaftarStatus.addView(wadahBulatan);
    }

    private void tampilkanKontakDinamis(ArrayList<String> daftarNama) {
        if (layoutDaftarKontak == null) return;

        int childCount = layoutDaftarKontak.getChildCount();
        for (int i = childCount - 1; i >= 1; i--) {
            View child = layoutDaftarKontak.getChildAt(i);
            if (child instanceof LinearLayout) {
                layoutDaftarKontak.removeViewAt(i);
            }
        }

        String currentUser = sharedPreferences != null ? sharedPreferences.getString("user", "") : "";
        String[] warnaAvatar = {"#E57373", "#4FC3F7", "#81C784", "#BA68C8", "#FFB74D"};

        for (int i = 0; i < daftarNama.size(); i++) {
            final String nama = daftarNama.get(i);
            if (nama.equalsIgnoreCase(currentUser)) continue;

            String warnaPilihan = warnaAvatar[i % warnaAvatar.length];

            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setPadding(32, 28, 32, 28);
            row.setBackgroundColor(Color.WHITE);
            row.setGravity(Gravity.CENTER_VERTICAL);

            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            rowParams.setMargins(0, 0, 0, 2);
            row.setLayoutParams(rowParams);

            String inisialHuruf = nama.trim().substring(0, 1).toUpperCase();

            TextView tvAvatar = new TextView(getContext());
            tvAvatar.setText(inisialHuruf);
            tvAvatar.setTextSize(18);
            tvAvatar.setTextColor(Color.WHITE);
            tvAvatar.setGravity(Gravity.CENTER);

            GradientDrawable shape = new GradientDrawable();
            shape.setShape(GradientDrawable.OVAL);
            shape.setColor(Color.parseColor(warnaPilihan));
            tvAvatar.setBackground(shape);

            int ukuranPixel = (int) (48 * getResources().getDisplayMetrics().density);
            LinearLayout.LayoutParams avatarParams = new LinearLayout.LayoutParams(ukuranPixel, ukuranPixel);
            tvAvatar.setLayoutParams(avatarParams);
            row.addView(tvAvatar);

            TextView tvNama = new TextView(getContext());
            tvNama.setText(nama);
            tvNama.setTextSize(16);
            tvNama.setTextColor(Color.parseColor("#1A1A1A"));
            tvNama.setTypeface(null, Typeface.BOLD);

            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            textParams.setMargins(32, 0, 0, 0);
            tvNama.setLayoutParams(textParams);
            row.addView(tvNama);

            row.setOnClickListener(v -> {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), RoomChatActivity.class);
                    intent.putExtra("NAMA_KEY", nama);
                    startActivity(intent);
                }
            });

            layoutDaftarKontak.addView(row);
        }
    }
}