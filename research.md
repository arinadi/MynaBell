Strategi Arsitektur dan Perencanaan Proyek Alarm Radio Online: Solusi Teknis Terintegrasi untuk Mitigasi Habituasi Auditori melalui Verifikasi Kognitif TOTP
Fenomena kegagalan alarm konvensional berakar pada mekanisme adaptasi sensorik yang dikenal sebagai habituasi, di mana otak manusia secara bertahap belajar untuk mengabaikan stimulus yang berulang dan dapat diprediksi. Dalam konteks alarm pagi, penggunaan nada dering statis yang sama setiap hari menciptakan kondisi kelelahan alarm (alarm fatigue), di mana operator atau pengguna menjadi desensitisasi terhadap sinyal yang frekuensinya tinggi namun relevansinya dianggap rendah oleh sistem kognitif bawah sadar. Proyek alarm radio online ini dirancang untuk memecahkan kebuntuan tersebut dengan memanfaatkan variabilitas konten dari siaran radio global sebagai stimulus yang dinamis, dikombinasikan dengan mekanisme verifikasi kognitif berbasis Time-based One-Time Password (TOTP) untuk memastikan transisi dari kondisi tidur ke kondisi terjaga berlangsung secara efektif.   

Analisis Psikologis Habituasi dan Urgensi Stimulus Dinamis
Habituasi didefinisikan sebagai penurunan kekuatan respon setelah presentasi berulang dari stimulus yang sama, sebuah proses yang secara evolusioner membantu manusia menyaring kebisingan latar belakang untuk fokus pada ancaman baru. Ketika alarm ponsel menggunakan pola suara yang identik setiap hari, sistem aktivasi retikular di otak mulai mengkategorikan suara tersebut sebagai "aman" atau "tidak relevan," sehingga intensitas respon fisiologis menurun secara signifikan. Penelitian menunjukkan bahwa interval antar-stimulus yang pendek dan repetisi yang masif mempercepat proses habituasi ini, membuat telinga manusia seakan-akan menjadi kebal.   

Kelelahan alarm (alarm fatigue) terjadi ketika operator atau individu terpapar volume alarm yang tinggi dalam jangka waktu lama, menyebabkan otak menyaring sinyal yang dulunya bermakna. Dalam lingkungan dengan risiko tinggi, desensitisasi ini dapat menyebabkan keterlambatan respon atau kegagalan merespon peristiwa kritis. Overload kognitif terjadi ketika jumlah permintaan simultan melebihi kapasitas mental; dalam keadaan ini, perhatian menyempit pada petunjuk yang segera dan akrab, sementara peristiwa langka namun kritis mudah terlewatkan.   

Sebaliknya, stimulus yang tidak terduga atau bervariasi—seperti siaran radio yang menyajikan kombinasi musik, berita, dan percakapan manusia yang berbeda setiap pagi—mampu mencegah terbentuknya pola habituasi jangka panjang. Paparan terhadap kebisingan yang keras dan tiba-tiba (loud noise) memicu respon sistem saraf simpatis, meningkatkan detak jantung antara 20 hingga 66 detak per menit. Alarm darurat dapat terjadi kapan saja dan bertindak sebagai pendahulu untuk peristiwa traumatis potensial, mempersiapkan personel untuk merespon insiden kritis. Dengan mengintegrasikan radio online, sistem alarm ini memastikan bahwa setiap pagi otak menerima "kejutan kognitif" yang baru, yang secara efektif merestorasi kepercayaan sistem alarm di mata persepsi manusia.   

Parameter Psikologis	Alarm Nada Statis	Alarm Radio Dinamis	Dampak Kognitif
Tingkat Habituasi	Tinggi (Cepat)	Rendah (Lambat)	
Radio mencegah otak melakukan filter otomatis terhadap stimulus.

Beban Kognitif	Sangat Rendah	Menengah	
Konten baru memaksa otak untuk memproses informasi segera setelah terjaga.

Respon Fisiologis	Menurun seiring waktu	Stabil	
Mempertahankan lonjakan kortisol yang diperlukan untuk mobilisasi.

Risiko Kelelahan	Signifikan	Minimal	
Variasi konten menjaga ambang batas kewaspadaan tetap tinggi.

  
Penelitian pada subjek hewan menunjukkan bahwa presentasi stimulus yang berjarak (spaced interval) menyebabkan habituasi yang lebih lengkap dan tahan lama dibandingkan dengan presentasi yang padat (massed interval). Namun, dalam konteks alarm bangun tidur, tujuannya bukan habituasi, melainkan justru menghindari habituasi agar alarm tetap efektif. Interval antar-stresor yang relatif singkat menghasilkan habituasi respon endokrin dan otonom yang lebih lemah, yang berarti respon terhadap stimulus tetap kuat jika stimulus tersebut bervariasi secara kognitif.   

Arsitektur Perangkat Lunak: Implementasi Kotlin untuk Pengembang Pemula
Dalam membangun aplikasi mobile yang ringan namun elegan, pemilihan bahasa pemrograman Kotlin merupakan keputusan strategis. Kotlin telah menjadi bahasa resmi untuk pengembangan Android, menawarkan fitur-fitur modern yang secara signifikan mengurangi boilerplate code dibandingkan Java. Fitur seperti null safety sangat krusial dalam mencegah NullPointerException, yang merupakan penyebab umum aplikasi crash. Bagi pengembang dengan pengalaman minimal di aplikasi mobile, Kotlin menyediakan sintaksis yang ekspresif dan ringkas, sehingga memudahkan proses belajar melalui struktur kode yang lebih mudah dipahami.   

Langkah pertama dalam implementasi adalah mengatur lingkungan pengembangan menggunakan Android Studio. IDE ini menyediakan perangkat yang diperlukan untuk menulis, menguji, dan mendebug aplikasi Kotlin. Struktur proyek Android terdiri dari beberapa komponen utama: folder src untuk file sumber Kotlin (.kt), folder res untuk sumber daya seperti layout XML dan gambar, serta file build.gradle untuk manajemen dependensi. Penggunaan Gradle dengan Kotlin DSL sangat direkomendasikan karena menawarkan integrasi yang ketat dengan sintaksis Kotlin.   

Untuk mencapai tampilan yang elegan, arsitektur Model-View-ViewModel (MVVM) disarankan. MVVM memisahkan logika bisnis dari antarmuka pengguna, memungkinkan aplikasi tetap responsif dan mudah dipelihara. Komponen seperti LiveData atau StateFlow digunakan untuk mengamati perubahan data dan memperbarui UI secara otomatis. Dalam konteks alarm radio, ViewModel akan mengelola status pemutar audio dan jadwal alarm, sementara View (Activity atau Fragment) hanya bertanggung jawab untuk menampilkan informasi kepada pengguna.   

Tahapan Belajar Kotlin	Topik Utama	Relevansi dengan Proyek Alarm
Dasar Pemrograman	Variabel (val/var), Tipe Data, Kontrol Alur	
Logika dasar pengaturan waktu alarm dan pemilihan stasiun.

Null Safety	Safe Calls (?.), Elvis Operator (?:)	
Mencegah crash saat koneksi radio terputus atau API gagal.

Coroutines	Suspend Functions, Dispatchers, Scopes	
Menangani streaming audio di latar belakang tanpa membekukan UI.

Jetpack Compose	Composable Functions, State Management	
Membangun UI elegan dengan tema gelap permanen secara deklaratif.

Data Persistence	Room Database, SharedPreferences	
Menyimpan jadwal alarm dan kunci rahasia TOTP secara permanen.

  
Implementasi Kotlin Multiplatform (KMP) juga dapat dipertimbangkan jika di masa depan aplikasi akan diperluas ke platform iOS, karena KMP memungkinkan berbagi logika bisnis (seperti algoritma TOTP dan parsing API) sambil tetap menjaga UI asli di setiap platform.   

Sistem Penjadwalan: AlarmManager dan Ketahanan Alarm di Android Modern
Penjadwalan alarm yang tepat waktu adalah inti dari aplikasi ini. Di Android, terdapat dua pilihan utama untuk menjalankan tugas di latar belakang: AlarmManager dan WorkManager. AlarmManager dirancang khusus untuk tugas-tugas yang memerlukan eksekusi pada waktu yang tepat (precise timing), seperti alarm bangun tidur. Sebaliknya, WorkManager lebih cocok untuk tugas-tugas yang dapat ditunda (deferrable) dan memerlukan jaminan eksekusi, seperti sinkronisasi data atau backup, karena WorkManager mempertimbangkan optimasi baterai dan kondisi perangkat.   

Untuk aplikasi alarm, AlarmManager dengan metode setExactAndAllowWhileIdle() atau setAlarmClock() adalah keharusan. Metode setAlarmClock() memberikan tingkat presisi tertinggi karena sistem Android akan memperlakukan alarm tersebut sebagai prioritas utama, bahkan saat perangkat dalam kondisi Doze Mode (mode hemat daya agresif). Selain itu, sistem akan menampilkan ikon alarm di status bar, memberikan konfirmasi visual kepada pengguna bahwa alarm telah diatur.   

Ketahanan alarm terhadap reboot perangkat dikelola melalui izin RECEIVE_BOOT_COMPLETED. Saat ponsel dinyalakan ulang, semua alarm yang dijadwalkan akan hilang dari memori sistem. Oleh karena itu, aplikasi harus mengimplementasikan sebuah BroadcastReceiver yang mendengarkan aksi BOOT_COMPLETED untuk menjadwalkan ulang semua alarm yang tersimpan di database lokal.   

Perbandingan Metode	Presisi	Dampak Baterai	Perilaku Doze Mode
set()	Inexact (Dapat ditunda sistem)	Rendah	Ditangguhkan hingga perangkat bangun.
setExact()	Tinggi	Menengah	
Ditangguhkan jika dalam Doze Mode.

setExactAndAllowWhileIdle()	Tinggi	Tinggi	
Tetap berjalan meskipun dalam Doze Mode.

setAlarmClock()	Maksimal	Tinggi	
Membangunkan perangkat; prioritas sistem tertinggi.

  
Penting untuk memperhatikan izin pada versi Android terbaru. Dimulai dari Android 12 (API level 31), aplikasi harus meminta izin SCHEDULE_EXACT_ALARM agar dapat menggunakan alarm yang tepat. Untuk aplikasi alarm fungsional, pengembang dapat menggunakan izin USE_EXACT_ALARM yang tidak memerlukan persetujuan manual pengguna namun tunduk pada kebijakan tinjauan Google Play. Selain itu, optimasi baterai pihak ketiga pada perangkat seperti Samsung atau Xiaomi sering kali mematikan aplikasi latar belakang secara agresif, sehingga pengguna harus diarahkan untuk memasukkan aplikasi ke dalam daftar putih (whitelist) "Jangan Optimalkan Baterai" agar alarm tetap andal.   

Pemutaran Audio Streaming: Integrasi Jetpack Media3 dan ExoPlayer
Untuk menangani pemutaran radio online, Jetpack Media3 adalah framework standar yang menggantikan library ExoPlayer mandiri. Media3 menyediakan antarmuka Player yang mendefinisikan fungsionalitas dasar seperti play, pause, dan seek, dengan ExoPlayer sebagai implementasi default yang sangat kuat. Dibandingkan dengan MediaPlayer bawaan Android, ExoPlayer menawarkan dukungan superior untuk berbagai protokol streaming modern seperti HLS (HTTP Live Streaming) dan DASH (Dynamic Adaptive Streaming over HTTP), yang umum digunakan oleh penyedia siaran radio digital.   

Implementasi pemutaran audio harus dilakukan di dalam sebuah MediaSessionService atau Foreground Service. Hal ini memastikan bahwa pemutaran tetap berlanjut meskipun pengguna meninggalkan aplikasi atau layar dimatikan. Media3 secara otomatis menangani integrasi dengan kontrol media sistem dan notifikasi, sehingga pengguna dapat melihat informasi stasiun yang sedang diputar langsung dari lock screen.   

Kotlin
// Contoh inisialisasi ExoPlayer sederhana di Kotlin
val player = ExoPlayer.Builder(context).build()
val mediaItem = MediaItem.fromUri("https://streaming.radio.co/station/listen")
player.setMediaItem(mediaItem)
player.prepare()
player.play()
Penanganan error merupakan aspek krusial dalam alarm radio online. Karena koneksi internet bisa tidak stabil, aplikasi harus memiliki strategi fallback. Melalui Player.Listener, aplikasi dapat memantau status pemutaran. Jika terjadi PlaybackException karena kegagalan jaringan, aplikasi harus segera beralih memutar file audio lokal (ringtone cadangan) yang disimpan di folder raw aplikasi. ExoPlayer juga memungkinkan kustomisasi kebijakan penanganan kesalahan beban melalui LoadErrorHandlingPolicy, di mana pengembang dapat menentukan jumlah percobaan ulang sebelum menyerah dan mengaktifkan alarm cadangan.   

Selain itu, aplikasi harus menangani "Audio Focus". Jika ada panggilan telepon masuk saat alarm berbunyi, aplikasi harus merespon dengan menurunkan volume atau menjeda pemutaran sesuai dengan instruksi sistem melalui setAudioAttributes(). Menggunakan ContentType.MOVIE atau Usage.ALARM memastikan bahwa volume alarm dikendalikan secara independen dari volume media reguler.   

Akses Ekosistem Radio Global: Integrasi API Radio Browser
Untuk mendapatkan daftar stasiun radio secara dinamis, proyek ini akan memanfaatkan Radio Browser API. Radio Browser adalah basis data berbasis komunitas yang gratis digunakan dan memiliki ribuan stasiun dari seluruh dunia. API ini mengikuti prinsip arsitektur REST, mengembalikan respon berformat JSON yang bersih dan mudah diproses oleh aplikasi Kotlin.   

Teknis penggunaan API ini dimulai dengan mencari daftar server yang tersedia melalui DNS lookup pada all.api.radio-browser.info. Strategi ini menjamin ketersediaan tinggi (high availability); jika satu server mati, aplikasi dapat mencoba server lain dari daftar tersebut. Data stasiun yang disediakan sangat komprehensif, mencakup nama stasiun, URL aliran data yang sudah diresolusi (url_resolved), tag genre, negara, bitrate, hingga logo stasiun (favicon).   

Endpoint API	Kegunaan	Parameter Penting
/json/stations/search	Mencari stasiun berdasarkan kriteria tertentu.	
name, countrycode, tag, limit.

/json/stations/topclick	Mendapatkan stasiun paling populer.	
limit.

/json/countries	Mendapatkan daftar negara yang tersedia.	
N/A.

/json/url/{stationuuid}	Mendapatkan URL terbaru untuk stasiun spesifik.	
stationuuid.

  
Untuk melakukan permintaan jaringan secara efisien di Kotlin, library Retrofit dikombinasikan dengan Gson atau Moshi untuk parsing JSON sangat direkomendasikan. Retrofit mendefinisikan endpoint API sebagai antarmuka Kotlin, membuat kode lebih bersih dan tipe-data aman (type-safe). Menggunakan Kotlin Coroutines bersama Retrofit memungkinkan permintaan jaringan dilakukan secara asinkron tanpa mengganggu responsivitas antarmuka pengguna.   

Sebagai catatan, meskipun Radio Garden menawarkan pengalaman visual yang menarik dengan navigasi globe, layanan tersebut merupakan platform proprietary. Pengembang disarankan untuk tetap menggunakan Radio Browser API sebagai sumber data primer karena sifatnya yang open-source dan didukung oleh banyak server mirror. Implementasi fitur pencarian stasiun favorit dan riwayat pendengaran dapat disimpan secara lokal menggunakan Room Database untuk memberikan pengalaman pengguna yang lebih personal.   

Estetika Desain: Implementasi Material 3 dengan Tema Gelap Permanen
Permintaan untuk tema gelap (dark theme) permanen sejalan dengan tren desain modern yang mengutamakan kenyamanan visual, terutama untuk penggunaan di malam hari atau kondisi cahaya rendah. Material Design 3 (M3) memperkenalkan konsep "Material You" yang sangat adaptif dan berfokus pada aksesibilitas. Dalam mode gelap, emisi cahaya dari layar dikurangi secara signifikan, yang tidak hanya menghemat baterai pada layar OLED tetapi juga meningkatkan visibilitas bagi pengguna dengan sensitivitas cahaya tinggi.   

Secara teknis, tema gelap di Android tidak berarti menggunakan warna hitam murni (#000000). Material Design merekomendasikan penggunaan abu-abu tua (#121212) sebagai warna latar belakang utama. Warna abu-abu tua memungkinkan penggunaan bayangan (elevation) yang lebih efektif untuk menunjukkan kedalaman; semakin tinggi sebuah komponen (seperti tombol atau kartu), semakin terang overlay warna yang diaplikasikannya.   

Komponen Warna	Nilai Hex (Contoh)	Deskripsi Peran
surface	#121212	
Warna latar belakang utama aplikasi.

primary	Desaturated Tone	
Warna utama untuk elemen interaktif (seperti tombol set alarm).

onSurface	High Opacity White (87%)	
Warna teks utama untuk kontras maksimal.

secondary	Muted Acent	
Warna untuk elemen pendukung seperti ikon kecil atau tag.

error	Desaturated Red	
Warna untuk indikasi kesalahan, misalnya koneksi gagal.

  
Untuk memaksa aplikasi tetap dalam mode gelap, pengembang dapat mengatur AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) di kelas Application atau Activity. Selain itu, atribut android:forceDarkAllowed="false" harus dipastikan pada tema aplikasi untuk mencegah sistem melakukan konversi warna otomatis yang mungkin merusak desain yang sudah dikurasi.   

Desain numpad untuk mematikan alarm harus mengikuti prinsip minimalisme. Menggunakan Jetpack Compose, pengembang dapat membuat tombol bulat dengan angka yang besar dan jelas. Setiap tombol harus memiliki target sentuh minimal 48dp untuk memastikan kemudahan penggunaan saat pengguna baru saja bangun tidur. Tipografi harus menggunakan font yang bersih dan berukuran besar, memanfaatkan skala tipografi Material 3 seperti Display Large untuk angka kode verifikasi.   

Logika Verifikasi Kognitif: Implementasi Algoritma TOTP
Inti dari pencegahan mematikan alarm secara tidak sadar adalah tantangan kognitif. Penggunaan kode enam digit berbasis Time-based One-Time Password (TOTP) merupakan solusi yang elegan karena kodenya berubah setiap 30 detik, mencegah pembentukan memori otot. Algoritma TOTP didefinisikan dalam RFC 6238 dan merupakan standar industri yang digunakan oleh aplikasi seperti Google Authenticator.   

Algoritma ini bekerja dengan menggabungkan kunci rahasia (shared secret) dengan waktu saat ini. Langkah-langkah teknisnya adalah sebagai berikut:

Ambil Waktu Unix: Dapatkan waktu saat ini dalam detik.

Hitung Counter: Bagi waktu Unix dengan interval langkah (biasanya 30 detik).

HMAC-SHA1: Hitung hash HMAC menggunakan kunci rahasia dan nilai counter sebagai pesan.   

Dynamic Truncation: Ambil sebagian dari hash tersebut dan konversikan menjadi angka enam digit.   

Formula matematis untuk TOTP adalah sebagai berikut:

TOTP(K,T)=HOTP(K,⌊ 
X
T−T 
0
​
 
​
 ⌋)

Di mana K adalah kunci rahasia, T adalah waktu saat ini, T 
0
​
  adalah epoch awal (0), dan X adalah interval waktu (30 detik).   

Implementasi di Kotlin dapat memanfaatkan library yang sudah ada seperti totp-kt atau menulis implementasi sendiri menggunakan javax.crypto.Mac. Rahasia (secret key) untuk TOTP harus disimpan dengan aman. Karena aplikasi ini ditujukan untuk penggunaan pribadi dan bukan keamanan perbankan, rahasia tersebut dapat dibuat secara acak saat instalasi aplikasi dan disimpan dalam EncryptedSharedPreferences agar tidak mudah diakses oleh aplikasi lain.   

Kotlin
// Logika dasar pembuatan HMAC-SHA1 di Kotlin
fun generateHmacSha1(key: ByteArray, message: ByteArray): ByteArray {
    val mac = Mac.getInstance("HmacSHA1")
    val secretKey = SecretKeySpec(key, "HmacSHA1")
    mac.init(secretKey)
    return mac.doFinal(message)
}
Penting untuk mencatat bahwa validasi angka harus dilakukan secara real-time. Karena kode TOTP berubah setiap 30 detik, antarmuka pengguna harus menampilkan penghitung waktu mundur atau secara otomatis memperbarui kode yang harus dimasukkan pengguna untuk menghindari frustrasi akibat kegagalan verifikasi karena jeda waktu. Penggunaan angka random sederhana sebagai alternatif juga tetap memerlukan aktivitas kognitif yang serupa, namun daya tarik "cantik" dari logika TOTP memberikan nilai tambah estetika dan teknis pada aplikasi.   

Keandalan dan Manajemen Daya: Menghadapi Optimasi Baterai Android
Salah satu tantangan terbesar bagi pengembang aplikasi alarm adalah memastikan sistem operasi tidak mematikan alarm untuk menghemat baterai. Android memiliki fitur seperti Doze Mode dan Standby Mode yang membatasi aktivitas latar belakang. Jika aplikasi tidak dikonfigurasi dengan benar, alarm mungkin akan tertunda beberapa menit atau bahkan tidak berbunyi sama sekali.   

Aplikasi alarm radio memiliki konsumsi daya yang lebih tinggi dibandingkan alarm nada dering karena penggunaan koneksi data terus-menerus dan pemrosesan audio streaming. Oleh karena itu, optimasi sumber daya sangat penting. Strategi utama meliputi:

Gunakan Wake Locks Seperlunya: Pastikan perangkat tetap terjaga saat alarm berbunyi, namun segera lepaskan wake lock setelah alarm dimatikan untuk mencegah pengurasan baterai yang tidak perlu.   

Batching Request: Untuk pembaruan daftar stasiun, lakukan secara berkala atau hanya saat aplikasi di latar depan (foreground) daripada terus-menerus menarik data di latar belakang.   

Monitoring Baterai: Gunakan alat seperti Battery Historian untuk melacak penggunaan daya aplikasi selama fase pengujian.   

Pengalaman pengguna pada perangkat dengan modifikasi Android yang agresif (seperti ColorOS, MIUI, atau One UI) sering kali terganggu karena sistem mematikan aplikasi yang dianggap "haus daya". Aplikasi harus menyertakan panduan bagi pengguna untuk menonaktifkan optimasi baterai secara manual melalui pengaturan sistem. Tanpa langkah ini, keandalan aplikasi sebagai alarm utama tidak dapat dijamin sepenuhnya.   

Rencana Eksekusi Proyek (Milestones)
Proyek ini dirancang untuk diselesaikan dalam siklus pengembangan bertahap, memungkinkan pengembang pemula untuk belajar sambil membangun komponen fungsional.

Milestone 1: Struktur Dasar dan Penjadwalan
Fase ini berfokus pada pengaturan proyek di Android Studio menggunakan Kotlin dan Gradle. Pengembang akan mengimplementasikan AlarmManager dasar untuk memicu notifikasi pada waktu tertentu. Fokus utamanya adalah memahami bagaimana PendingIntent bekerja dan memastikan alarm tetap aktif setelah perangkat dimulai ulang (reboot).   

Milestone 2: Streaming Audio dan Networking
Implementasi library Media3 ExoPlayer dimulai pada tahap ini. Pengembang akan membuat modul jaringan sederhana untuk mengambil data JSON dari Radio Browser API menggunakan Retrofit. Tujuan akhir fase ini adalah aplikasi mampu memutar URL aliran radio secara manual dari antarmuka pengguna sebelum diintegrasikan dengan pemicu alarm.   

Milestone 3: Verifikasi TOTP dan Logika Dismissal
Implementasi algoritma HMAC-SHA1 untuk menghasilkan kode enam digit. Pengembang akan membangun antarmuka numpad minimalis menggunakan Jetpack Compose. Logika dismissal harus diuji untuk memastikan alarm hanya berhenti jika enam digit yang dimasukkan sesuai dengan kode TOTP yang berlaku saat itu.   

Milestone 4: Desain UI Elegance dan Tema Gelap
Penyempurnaan visual aplikasi dengan menerapkan prinsip Material 3 secara menyeluruh. Fokus pada implementasi tema gelap permanen, tipografi yang elegan, dan transisi antar-layar yang mulus. Pengembang juga harus memastikan aplikasi memenuhi standar aksesibilitas dasar seperti target sentuh yang cukup besar.   

Milestone 5: Stabilisasi dan Strategi Fallback
Fase terakhir adalah pengujian ketahanan. Pengembang akan mensimulasikan kegagalan jaringan untuk memastikan alarm beralih ke nada dering lokal secara otomatis. Selain itu, dilakukan audit penggunaan baterai dan penambahan instruksi "whitelist" bagi pengguna untuk memastikan alarm tetap andal di berbagai model perangkat Android.   

Milestone	Durasi Estimasi	Deliverable Utama
M1: Penjadwalan	2 Minggu	
Alarm dasar fungsional dengan persistensi reboot.

M2: Streaming	3 Minggu	
Integrasi Radio Browser API dan ExoPlayer.

M3: Verifikasi	2 Minggu	
Algoritma TOTP dan UI Numpad fungsional.

M4: Desain	2 Minggu	
UI Material 3 dengan tema gelap permanen.

M5: Pengujian	2 Minggu	
Penanganan error dan optimasi daya.

  
Kesimpulan dan Implikasi Strategis
Implementasi alarm radio online dengan verifikasi TOTP menawarkan solusi teknis yang komprehensif terhadap masalah biologis habituasi suara. Dengan memanfaatkan ekosistem radio global melalui API terbuka, aplikasi ini menyediakan variabilitas stimulus yang diperlukan untuk menjaga tingkat kewaspadaan kognitif tanpa menimbulkan stres yang tidak perlu akibat nada alarm yang monoton. Penggunaan Kotlin sebagai bahasa pengembangan memberikan keseimbangan antara efisiensi performa dan kemudahan pemeliharaan bagi pengembang baru.   

Secara keseluruhan, proyek ini tidak hanya menuntut ketajaman teknis dalam mengelola tugas latar belakang dan streaming audio, tetapi juga pemahaman mendalam tentang interaksi manusia dan komputer melalui desain Material 3 yang elegan. Dengan mengikuti rencana yang terstruktur, pengembang dapat mengatasi keterbatasan teknis mereka sambil menghasilkan produk yang memiliki keandalan tinggi dan nilai estetika yang kuat, memenuhi kebutuhan fungsional sekaligus kepuasan visual dalam penggunaan sehari-hari.
