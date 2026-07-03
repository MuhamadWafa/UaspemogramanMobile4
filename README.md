# UaspemogramanMobile4
# Muhamad wafa mufida zulfi
# 312410334
# I241D
# Tugas akhir uas
# Donny Maulana, S. Kom., M. M. S. I

# 📱 Pitchat - Android Chat Application

Pitchat adalah aplikasi pesan instan berbasis Android yang dirancang dengan antarmuka yang bersih, interaktif, dan responsif. Aplikasi ini dibangun secara dinamis tanpa ketergantungan penuh pada layout XML statis, melainkan memanfaatkan konstruksi komponen langsung via kode Java untuk fleksibilitas performa yang maksimal.

---

## 🛠️ Teknologi & Bahasa yang Digunakan

Aplikasi ini dibangun menggunakan ekosistem native Android Studio dengan rincian teknologi sebagai berikut:

* **Java**: Bahasa pemrograman utama untuk menangani seluruh logika bisnis, render UI dinamis, kontrol siklus hidup komponen (`Activity` & `Fragment`), serta manajemen data.
* **XML (Extensible Markup Language)**: Digunakan untuk mendefinisikan layout dasar, konfigurasi manifestasi aplikasi (`AndroidManifest.xml`), serta pengelolaan sumber daya gaya/warna (*resources*).
* **SharedPreferences**: Mekanisme penyimpanan data lokal (*local storage*) untuk mengelola sesi login pengguna, data profil, riwayat panggilan, serta konten status.
* **Google Gson**: Library untuk proses serialisasi dan deserialisasi objek Java ke format JSON, digunakan untuk mengelola struktur data kompleks seperti daftar kontak global dan riwayat panggilan.

## 🚀 Fitur Utama Aplikasi

Pitchat dilengkapi dengan berbagai fitur modern layaknya aplikasi pesan instan populer:

## 1. 🔐 Sistem Otentikasi Lokal**
   * Manajemen sesi login dan registrasi pengguna yang tersinkronisasi secara global menggunakan penyimpanan lokal.

## 2. 💬 Ruang Obrolan Dinamis (Chat & Chatbot AI)**
   * Komunikasi antarmuka obrolan yang adaptif dengan kontak terdaftar.
   * Integrasi tombol **AI Asisten Materi (Chatbot)** bawaan untuk bantuan pintar di atas daftar kontak.

## 3. 🎬 Status / Story Media Interaktif**
   * Mendukung pembuatan status berbasis **Teks Ketikan**, **Foto**, maupun **Video** langsung dari galeri ponsel.
   * Tampilan visual lingkaran status (*story ring*) dinamis yang akan berubah warna ketika status telah dibaca.
   * Fitur **Hapus Status** instan dari menu input beranda.
   * Pemutar media otomatis (*fullscreen*) pada `ViewStatusActivity` dengan pemutar video looping otomatis.

## 4. 📝 Manajemen Profil & Edit Mandiri**
   * Tampilan informasi akun berupa Nama Pengguna dan Bio Status dalam kontainer kartu (*card*) yang elegan.
   * Mode **Edit Profil langsung di tempat** yang mengubah teks menjadi kolom input secara *real-time* dan memperbarui data kontak global.

## 5. 📞 Riwayat Panggilan Telepon (Call History)**
   * Pencatatan otomatis setiap aktivitas panggilan keluar dilengkapi dengan nama kontak dan penanda waktu (*timestamp*) ril.
   * Opsi pembersihan daftar riwayat panggilan lewat satu tombol.

## 📂 Struktur Berkas Utama
