# UaspemogramanMobile4
# Muhamad wafa mufida zulfi
# 312410334
# I241D
# Tugas akhir uas
# Donny Maulana, S. Kom., M. M. S. I

# Link Clickup: https://sharing.clickup.com/90181768528/g/h/2kzm15ag-238/48df0a6063fafe0


# 📱 Pitchat - Android Chat Application

Pitchat adalah aplikasi pesan instan berbasis Android yang dirancang dengan antarmuka yang bersih, interaktif, dan responsif. Aplikasi ini dibangun secara dinamis tanpa ketergantungan penuh pada layout XML statis, melainkan memanfaatkan konstruksi komponen langsung via kode Java untuk fleksibilitas performa yang maksimal.

## 🛠️ Teknologi & Bahasa yang Digunakan

Aplikasi ini dibangun menggunakan ekosistem native Android Studio dengan rincian teknologi sebagai berikut:

* **Java**: Bahasa pemrograman utama untuk menangani seluruh logika bisnis, render UI dinamis, kontrol siklus hidup komponen (`Activity` & `Fragment`), serta manajemen data.
* **XML (Extensible Markup Language)**: Digunakan untuk mendefinisikan layout dasar, konfigurasi manifestasi aplikasi (`AndroidManifest.xml`), serta pengelolaan sumber daya gaya/warna (*resources*).
* **SharedPreferences**: Mekanisme penyimpanan data lokal (*local storage*) untuk mengelola sesi login pengguna, data profil, riwayat panggilan, serta konten status.
* **Google Gson**: Library untuk proses serialisasi dan deserialisasi objek Java ke format JSON, digunakan untuk mengelola struktur data kompleks seperti daftar kontak global dan riwayat panggilan.

## A. 🚀 Fitur Utama Aplikasi

Pitchat dilengkapi dengan berbagai fitur modern layaknya aplikasi pesan instan populer:

## 1. 🔐 Sistem Otentikasi Lokal
   * Manajemen sesi login dan registrasi pengguna yang tersinkronisasi secara global menggunakan penyimpanan lokal.

## 2. 💬 Ruang Obrolan Dinamis (Chat & Chatbot AI)
   * Komunikasi antarmuka obrolan yang adaptif dengan kontak terdaftar.
   * Integrasi tombol **AI Asisten Materi (Chatbot)** bawaan untuk bantuan pintar di atas daftar kontak.

## 3. 🎬 Status / Story Media Interaktif
   * Mendukung pembuatan status berbasis **Teks Ketikan**, **Foto**, maupun **Video** langsung dari galeri ponsel.
   * Tampilan visual lingkaran status (*story ring*) dinamis yang akan berubah warna ketika status telah dibaca.
   * Fitur **Hapus Status** instan dari menu input beranda.
   * Pemutar media otomatis (*fullscreen*) pada `ViewStatusActivity` dengan pemutar video looping otomatis.

## 4. 📝 Manajemen Profil & Edit Mandiri
   * Tampilan informasi akun berupa Nama Pengguna dan Bio Status dalam kontainer kartu (*card*) yang elegan.
   * Mode **Edit Profil langsung di tempat** yang mengubah teks menjadi kolom input secara *real-time* dan memperbarui data kontak global.

## 5. 📞 Riwayat Panggilan Telepon (Call History)
   * Pencatatan otomatis setiap aktivitas panggilan keluar dilengkapi dengan nama kontak dan penanda waktu (*timestamp*) ril.
   * Opsi pembersihan daftar riwayat panggilan lewat satu tombol.

## 📂 Struktur Berkas Utama
```
└── app/
    ├── manifests/
    │   └── AndroidManifest.xml
    │
    ├── kotlin+java/
    │   └── com.example.pitchat/
    │       ├── CallActivity.java
    │       ├── CallHistoryActivity.java
    │       ├── ChatBotActivity.java
    │       ├── ChatFragment.java
    │       ├── LoginActivity.java
    │       ├── MainActivity.java
    │       ├── ProfileFragment.java
    │       ├── RegisterActivity.java
    │       ├── RoomChatActivity.java
    │       ├── StartActivity.java
    │       └── ViewStatusActivity.java
    │
    └── res/
        ├── drawable/
        │   ├── ic_launcher_background.xml
        │   ├── ic_launcher_foreground.xml
        │   └── ic_pitchat_logo.png
        │
        ├── layout/
        │   ├── activity_call.xml
        │   ├── activity_call_history.xml
        │   ├── activity_chatbot.xml
        │   ├── activity_login.xml
        │   ├── activity_main.xml
        │   ├── activity_register.xml
        │   ├── activity_room_chat.xml
        │   ├── activity_start.xml
        │   ├── activity_view_status.xml
        │   ├── fragment_chat.xml
        │   ├── fragment_profile.xml
        │   └── item_chat_bubble.xml
        │
        ├── mipmap/
        │
        ├── values/
        │   ├── themes/
        │   │   ├── themes.xml
        │   │   └── themes.xml (night)
        │   ├── colors.xml
        │   └── strings.xml
        │
        └── xml/
            ├── backup_rules.xml
            └── data_extraction_rules.xml
```
# B. Berikut ini Adalah tampilan dari aplikasi tersebut
## 1. Start
<img width="720" height="1600" alt="Start" src="https://github.com/user-attachments/assets/acd124de-26bb-4704-9803-8a8cbb184098" />

### Berikut adalah penjelasan singkat mengenai elemen-elemen di dalamnya: 
### Nama & Slogan Aplikasi: Aplikasi ini bernama Pitchat dengan slogan "Simple. Secure. Reliable messaging" (Pesan yang simpel, aman, dan tepercaya). Ini menunjukkan bahwa Pitchat adalah sebuah aplikasi pengirim pesan (chatting).
### Logo: Terdapat logo berbentuk bintang berwarna kuning di bagian tengah. Tombol Navigasi: SIGN IN (Masuk): Tombol biru untuk pengguna yang sudah punya akun agar bisa langsung masuk. CREATE ACCOUNT (Buat Akun): Pilihan di bawahnya untuk pengguna baru yang ingin mendaftar.

## 2. Login
<img width="720" height="1600" alt="welcome" src="https://github.com/user-attachments/assets/ef21d583-7549-4afa-b3f2-1141a5b26ab5" />

### Berikut adalah penjelasan singkat mengenai elemen-elemen di dalamnya:
### Judul Halaman: Terdapat tulisan "Welcome Back" beserta sub-teks "Sign in to continue your chat", yang menyambut kembali pengguna untuk masuk dan melanjutkan obrolan mereka.

Kolom Input (Form):
### Username: Kolom tempat pengguna memasukkan nama pengguna mereka.
### Password: Kolom tempat pengguna memasukkan kata sandi akun.
### Tombol LOGIN: Tombol berwarna ungu terang yang digunakan untuk memproses masuk ke dalam aplikasi setelah mengisi username dan password.

## 3. Buat akun 
<img width="720" height="1600" alt="Buat akun" src="https://github.com/user-attachments/assets/a76534bb-bb8b-4695-8f75-92cfcc13a977" />

Judul Halaman: "Create Account" dengan keterangan "Daftar akun baru untuk mulai menggunakan Pitchat".

Kolom Input: Terdiri dari kolom Username dan Password yang harus diisi untuk membuat akun baru.

Tombol REGISTER: Tombol berwarna biru di bagian bawah untuk memproses pendaftaran setelah data diisi.

## 4. Beranda chat
<img width="720" height="1600" alt="WhatsApp Image 2026-07-03 at 22 53 24" src="https://github.com/user-attachments/assets/01d02cbf-b8fb-4621-8b49-c91a834323b6" />

Menampilkan daftar obrolan aktif (seperti Saiful Udin, Budi Setiawan), fitur pencarian di bagian atas, deretan status terbaru dari pengguna lain, serta tombol besar berwarna biru untuk mengakses fitur chatbot AI.

## 5. Profil Pengguna
<img width="720" height="1600" alt="WhatsApp Image 2026-07-03 at 22 53 23" src="https://github.com/user-attachments/assets/2ed2bd92-1265-45ae-9e68-fcb53d676fa1" />

Menampilkan halaman profil milik pengguna bernama danil. Di halaman ini terdapat info bio status, tombol hijau untuk mengedit profil, tombol jingga untuk melihat riwayat panggilan, dan tombol merah untuk keluar dari akun (logout).

## 6. Fitur AI Asisten Materi (Chatbot)
<img width="720" height="1600" alt="WhatsApp Image 2026-07-03 at 22 53 23 (1)" src="https://github.com/user-attachments/assets/12feccff-25de-43d6-9563-31b0b8194ed5" />

Menampilkan ruang obrolan dengan AI Materi ChatBot. Asisten ini siap membantu menjawab berbagai pertanyaan seputar kesehatan, resep memasak, keuangan, hingga edukasi umum melalui kolom input di bagian bawah.
