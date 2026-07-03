package com.example.pitchat;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ChatBotActivity extends AppCompatActivity {

    private EditText etPesanBot;
    private Button btnSendBot, btnBackBot;
    private LinearLayout layoutAreaChatBot;
    private ScrollView botScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        etPesanBot = findViewById(R.id.etPesanBot);
        btnSendBot = findViewById(R.id.btnSendBot);
        btnBackBot = findViewById(R.id.btnBackBot);
        layoutAreaChatBot = findViewById(R.id.layoutAreaChatBot);
        botScrollView = findViewById(R.id.botScrollView);

        // Sambutan ramah untuk masyarakat umum
        tampilkanBubble(true, "Halo! Selamat datang di Asisten AI Masyarakat Pitchat. 🌾🏥🍳\n\nAku siap membantu menjawab pertanyaan sehari-hari seputar:\n• Kesehatan & Obat Herbal\n• Resep & Tips Memasak\n• Tips Keuangan & Usaha\n• Edukasi Umum & Layanan Publik\n\nSilakan tanyakan apa saja!");

        btnBackBot.setOnClickListener(v -> finish());

        btnSendBot.setOnClickListener(v -> {
            String pertanyaan = etPesanBot.getText().toString().trim();
            if (!pertanyaan.isEmpty()) {
                tampilkanBubble(false, pertanyaan);
                etPesanBot.setText("");
                gulirKeBawah();

                new Handler().postDelayed(() -> {
                    String jawabanAI = analisisKonteksMasyarakat(pertanyaan);
                    tampilkanBubble(true, jawabanAI);
                    gulirKeBawah();
                }, 800);
            }
        });
    }

    /**
     * Otak Pintar AI untuk Kebutuhan Masyarakat Umum
     */
    private String analisisKonteksMasyarakat(String query) {
        String q = query.toLowerCase().trim();

        // 1. KATEGORI KESEHATAN & PERTOLONGAN PERTAMA
        if (cekKecocokan(q, "sakit", "obat", "batuk", "demam", "pusing", "herbal", "flu", "luka", "sehat", "medis")) {
            return "🏥 **Tips Kesehatan & Pertolongan Pertama**:\n\n" +
                    "• **Demam/Pusing**: Istirahat cukup, minum air putih minimal 2 liter/hari. Bisa redakan dengan kompres air hangat atau minum parasetamol.\n" +
                    "• **Batuk/Flu**: Minum air hangat dicampur perasan jeruk nipis dan madu sebagai herbal alami.\n" +
                    "• **Luka Bakar Ringan**: Basuh dengan air mengalir (bukan es/odol!) selama 10 menit untuk mencegah melepuh.\n\n*Catatan: Jika gejala berlanjut lebih dari 3 hari, segera hubungi puskesmas terdekat ya!*";
        }

        // 2. KATEGORI KULINER, MASAK, & BAHAN MAKANAN
        if (cekKecocokan(q, "masak", "resep", "makan", "bumbu", "dapur", "goreng", "rebus", "sayur", "ayam", "nasi")) {
            return "🍳 **Tips Dapur & Resep Praktis**:\n\n" +
                    "• **Bumbu Dasar Putih**: Cocok untuk tumisan/sup. Haluskan bawang merah, bawang putih, kemiri, dan sedikit garam.\n" +
                    "• **Tips Sayuran Hijau**: Agar sayur (seperti kangkung/bayam) tetap hijau segar saat ditumis, masukkan sayur saat wajan sudah benar-benar panas dan masak dengan cepat.\n" +
                    "• **Tips Mengawetkan**: Simpan cabai di wadah tertutup yang dialasi tisu kering, lalu selipkan satu siung bawang putih kupas agar tidak cepat busuk.";
        }

        // 3. KATEGORI KEUANGAN MANDIRI & IDE USAHA RUMAHAN
        if (cekKecocokan(q, "uang", "usaha", "modal", "bisnis", "kerja", "hemat", "tabungan", "dagang", "jual")) {
            return "💰 **Tips Keuangan & Ide Usaha Mandiri**:\n\n" +
                    "• **Rumus Hemat 50-30-20**: Alokasikan 50% pendapatan untuk kebutuhan pokok, 30% untuk keinginan, dan 20% wajib ditabung/investasi.\n" +
                    "• **Ide Usaha Modal Kecil**: \n" +
                    "  1. Katering sarapan pagi / camilan sehat titip di warung.\n" +
                    "  2. Jasa agen pembayaran digital (pulsa, listrik, transfer bank).\n" +
                    "  3. Jasa cuci/laundry pakaian kiloan rumahan.";
        }

        // 4. KATEGORI UMUM, INFORMASI SOSIAL & LAYANAN PUBLIK
        if (cekKecocokan(q, "ktp", "surat", "nikah", "sim", "bpjs", "desa", "rt", "rw", "paspor", "warga")) {
            return "📄 **Panduan Layanan Publik & Administrasi Warga**:\n\n" +
                    "• **Urus KTP/KK Hilang**: Minta surat pengantar dari RT/RW, bawa surat kehilangan dari kepolisian, lalu datang ke kantor Dukcapil/Kecamatan setempat.\n" +
                    "• **BPJS Kesehatan**: Pendaftaran mandiri kini bisa dilakukan online lewat aplikasi Mobile JKN tanpa perlu antre panjang di kantor cabang.";
        }

        // 5. INTERAKSI RAMAH TAMAH & GREETINGS (Bahasa Gaul/Bebas)
        if (cekKecocokan(q, "halo", "hai", "pagi", "siang", "malam", "assalamualaikum", "permisi", "bro", "sist")) {
            return "Halo warga! Senang bisa menyapamu. Ada yang bisa saya bantu terkait info kesehatan, resep masakan, tips usaha, atau administrasi hari ini? 😊";
        }

        if (cekKecocokan(q, "kabar", "sehat", "gimana", "piye", "kumaha")) {
            return "Kabar baik dan sistem saya sangat optimal! Semoga Anda dan keluarga di rumah selalu diberikan kesehatan dan kelancaran rezeki ya.";
        }

        if (cekKecocokan(q, "terima kasih", "makasih", "suwun", "nuhun", "oke", "siap")) {
            return "Sama-sama! Semoga informasinya bermanfaat bagi kehidupan sehari-hari. Sukses selalu, cuy! 👍";
        }

        // 6. PENANGANAN JIKA PERTANYAAN TERLALU ACAK
        return "Saya memahami kata kunci Anda, tetapi pengetahuan lokal saya masih terbatas untuk detail topik tersebut. 🧠\n\n" +
                "Coba tanyakan hal praktis seputar kehidupan sehari-hari seperti:\n" +
                "• *'Cara mengatasi obat batuk alami'*\n" +
                "• *'Tips jualan modal kecil'*\n" +
                "• *'Cara mengurus KTP yang hilang'*\n" +
                "• *'Resep masakan bumbu dasar'*";
    }

    private boolean cekKecocokan(String teksInput, String... kataKunci) {
        for (String kunci : kataKunci) {
            if (teksInput.contains(kunci)) {
                return true;
            }
        }
        return false;
    }

    private void tampilkanBubble(boolean isBot, String teks) {
        TextView tvBubble = new TextView(ChatBotActivity.this);
        tvBubble.setText(teks);
        tvBubble.setTextSize(15);
        tvBubble.setPadding(34, 22, 34, 22);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 24);

        if (isBot) {
            tvBubble.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tvBubble.setTextColor(Color.parseColor("#2C3E50"));
            params.gravity = Gravity.START;
        } else {
            tvBubble.setBackgroundColor(Color.parseColor("#0088CC"));
            tvBubble.setTextColor(Color.WHITE);
            params.gravity = Gravity.END;
        }

        tvBubble.setLayoutParams(params);
        layoutAreaChatBot.addView(tvBubble);
    }

    private void gulirKeBawah() {
        botScrollView.post(() -> botScrollView.fullScroll(View.FOCUS_DOWN));
    }
}