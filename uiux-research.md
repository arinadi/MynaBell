1. Filosofi Visual
Warna Latar: Deep Charcoal (#121212) untuk mengurangi ketegangan mata saat bangun tidur.

Warna Aksen: Muted Gold atau Teal (Warna desaturasi agar tetap kontras namun tidak menyilaukan).

Tipografi: Menggunakan font Sans-serif modern dengan ukuran Display Large (57sp) untuk jam agar terbaca jelas meski pandangan masih kabur.

2. Mockup Layar Utama (Alarm List)
Layar ini minimalis, hanya menampilkan daftar alarm yang aktif dengan navigasi yang bersih.

+---------------------------------------+

| MYNABELL |
+---------------------------------------+

| |
| Good Morning! |
| |
| +---------------------------------+ |
| | 07:00 AM | |
| | Mon, Tue, Wed, Thu, Fri      [O] | |
| | Radio: Jazz FM - London | |
| +---------------------------------+ |
| |
| +---------------------------------+ |
| | 09:30 AM | |
| | Sat, Sun                     [ ] | |
| | Radio: BBC World Service | |
| +---------------------------------+ |
| |
| [ + ] (FAB) |
+---------------------------------------+

Fitur: Tombol tambah (FAB) di pojok kanan bawah. Kartu alarm menggunakan SurfaceContainer M3 yang sedikit lebih terang dari latar belakang untuk memberi kedalaman.

3. Mockup Layar Aktif (The Wake-Up Screen)
Ini adalah layar yang muncul saat alarm berbunyi. Radio akan memutar suara secara otomatis (Streaming via Media3/ExoPlayer).

+---------------------------------------+

| [ 07:00 AM ] |
| |
| Now Playing: |
| "Prabors FM - Jakarta" |
| |
| |
| Enter 6-Digit Verification Code |
| [  8  ][  2  ][  5  ][ _ ][ _ ][ _ ] |
| |
| Target Code: 825109 |
| |
| ( 1 )      ( 2 )      ( 3 ) |
| ( 4 )      ( 5 )      ( 6 ) |
| ( 7 )      ( 8 )      ( 9 ) |
| ( 0 )      ( X ) |
| |
| |
+---------------------------------------+

Logic Challenge: Menampilkan 6 angka TOTP yang berubah setiap 30 detik.

Numpad: Tombol bulat dengan diameter minimal 48dp untuk meminimalkan kesalahan input.

Visualizer: Animasi gelombang sederhana yang bergerak sesuai ritme radio online yang sedang diputar.

4. Mockup Pemilih Stasiun (Radio Browser Integration)
Menggunakan data dari Radio Browser API untuk memberikan variasi suara tak terbatas.

+---------------------------------------+

| <  Select Radio Station |
+---------------------------------------+

| |
+---------------------------------------+

| Trending Now: |
| - KEXP 90.3 FM (Seattle, US) |
| - Prambors FM (Jakarta, ID) |
| - Lo-fi Girl Radio (Global) |
| |
| Categories: |
| [ Jazz ][ News ][ Chill ] |
+---------------------------------------+

| Preview: |
| [ > ] Playing: KEXP 90.3 FM |
| |
+---------------------------------------+

Integrasi API: Menampilkan favicon stasiun, lokasi, dan kualitas bitrate.

Preview: Pengguna bisa mendengarkan aliran (stream) sebelum menyimpannya sebagai nada alarm.

5. Detail UX & Interaksi
Fade-In Audio: Volume radio tidak langsung meledak, melainkan meningkat perlahan dari 0% ke 80% dalam durasi 10 detik untuk bangun yang lebih natural.

TOTP Sync: Jika pengguna terlambat memasukkan kode dan waktu 30 detik habis, kotak input akan bergetar merah dan angka target akan diperbarui secara otomatis (Real-time syncing).

Haptic Feedback: Setiap tekanan pada numpad memberikan getaran halus (haptic) untuk memastikan input terasa nyata.

Offline Switch: Jika Media3 mendeteksi PlaybackException (internet mati), layar akan berubah warna menjadi kuning lembut dan memutar nada lokal cadangan yang tersimpan di memori internal.