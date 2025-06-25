# Redesign Aplikasi Starbucks - Prototipe Autentikasi

Ini adalah proyek aplikasi Android yang merupakan prototipe untuk proses autentikasi pengguna dengan desain antarmuka yang terinspirasi dari merek Starbucks. Aplikasi ini dibuat menggunakan Kotlin dan Jetpack Compose, yang menunjukkan pendekatan modern dalam pengembangan UI Android.

## ✨ Fitur

* **Layar Perkenalan:** Sebuah layar selamat datang yang menarik dengan branding "Starbucks" akan menyambut pengguna saat pertama kali membuka aplikasi.
* **Pendaftaran Pengguna:**
    * Formulir untuk membuat akun baru dengan **Nama Tampilan**, **Alamat Email**, dan **Kata Sandi**.
    * Verifikasi menggunakan **OTP (One-Time Password)** yang dihasilkan secara lokal untuk keamanan tambahan.
* **Login Pengguna:** Pengguna yang sudah terdaftar dapat masuk ke dalam aplikasi menggunakan kredensial mereka.
* **Layar Selamat Datang:** Setelah berhasil login, pengguna akan disambut di layar utama dengan nama tampilan mereka.
* **Manajemen Sesi Sederhana:** Aplikasi dapat membedakan antara status sudah login dan belum.
* **Desain Antarmuka yang Menarik:** UI yang bersih dan modern dengan skema warna yang terinspirasi dari Starbucks.

## 🛠️ Teknologi yang Digunakan

* **Bahasa Pemrograman:** [Kotlin](https://kotlinlang.org/)
* **Toolkit UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Arsitektur:** UI Layer (State-driven)
* **Dependensi Utama:**
    * Jetpack Compose (UI, Material 3, Tooling)
    * Kotlin Coroutines untuk operasi asinkron (timer OTP).
    * Lifecycle KTX

## ⚙️ Konfigurasi Proyek

* **Minimum SDK:** API 24 (Android 7.0)
* **Target SDK:** API 35
* **Versi Java:** Java 11
* **Build System:** Gradle dengan Kotlin DSL

## 📄 Struktur Kode

Struktur kode utama berada di dalam file `app/src/main/java/com/example/appauthentication/MainActivity.kt`. File ini berisi:

* `MainActivity`: Aktivitas utama yang menjadi titik masuk aplikasi.
* `AppWithIntro`: Composable yang mengatur tampilan antara layar intro dan layar utama aplikasi.
* `IntroScreen`: Composable untuk layar perkenalan.
* `SimpleAuthApp`: Composable utama yang mengelola state untuk login, registrasi, dan sesi pengguna.
* `AuthScreen`: Composable yang menampilkan formulir login dan registrasi.
* `WelcomeScreen`: Composable yang ditampilkan setelah pengguna berhasil login.
* **Database Dummy:** Sebuah `mutableStateMapOf` digunakan untuk mensimulasikan database pengguna.

## 📱 Tampilan Aplikasi
<p align="center">
  <img src="https://github.com/user-attachments/assets/72fdb99a-20af-4495-b21d-c0e604d3925b" width="30%" />
  <img src="https://github.com/user-attachments/assets/34b27a3d-5826-4d29-8f32-1f012ae00d6d" width="30%" />
  <img src="https://github.com/user-attachments/assets/b30f2842-fb5d-4265-aaf8-957e69c26987" width="30%" />
</p>

