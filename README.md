# TP6DPBO2425C2
TUGAS PRAKTIKUM 6 DPBO SWING GAME Renaldy Heryana (2509867) Ilmu Komputer C2 Universitas Pendidikan Indonesia

# janji
Saya Renaldy Heryana dengan NIM 2509867 mengerjakan TP6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# STRUKTUR REPO
<img src="struktur_repo.png" alt="gambar struktur file" width="400">

Isi folder `src`:

```
src
├── FlappyBirdAssets
│   ├── Audio
│   │   └── BGM.wav
│   ├── background.png
│   ├── bird.png
│   ├── lowerPipe.png
│   └── upperPipe.png
├── App.java
├── Logic.java
├── Pipe.java
├── Player.java
└── View.java
```


# ALUR PROGRAM

1. Program dimulai dari class `App`. Program menampilkan Main Menu berupa dialog dengan dua tombol, yaitu "Mulai" dan "Keluar".
2. Jika user memilih "Mulai", program membuat jendela `FlappyBird` berukuran 360x640 yang ukurannya tidak bisa diubah. Menutup jendela ini juga menghentikan program. Jika user memilih "Keluar" atau menutup dialog lewat tombol X, jendela tidak dibuat dan program langsung selesai.
3. Class `Logic` dibuat lebih dulu, lalu class `View` dibuat dengan menerima `Logic`, kemudian `Logic` diberi `View` lewat `setView`. Saat `Logic` dibuat, program memuat gambar burung dan pipa, membuat burung di tengah layar, menyalakan dua timer, lalu memutar musik latar `BGM.wav` secara berulang.
4. Timer `gameLoop` berjalan sekitar 60 kali per detik. Di setiap tick program memanggil `move`, `nabrakTembok`, dan `cekTiang` secara berurutan, lalu menggambar ulang layar dengan `repaint`.
5. Burung selalu jatuh karena gravitasi. User menekan tombol spasi agar burung melompat ke atas. Burung hanya bergerak naik dan turun, sedangkan pipa yang bergerak ke kiri sehingga terlihat seperti burung terbang maju.
6. Timer `pipesCooldown` memanggil `placePipes` tiap 1500 ms. Setiap pemanggilan membuat sepasang pipa (atas dan bawah) di tepi kanan layar dengan posisi celah yang acak. Tinggi celah antar pipa adalah seperempat tinggi layar.
7. Skor bertambah 1 setiap burung berhasil melewati sepasang pipa dan ditampilkan pada label di bagian atas layar.
8. Game over terjadi jika burung jatuh melewati batas bawah layar atau menabrak pipa. Saat itu `gameLoop` dihentikan, musik latar dihentikan, dan muncul dialog "GAME OVER, tekan R untuk mengulang permainan".
9. User menekan tombol R untuk mengulang permainan. Program menyalakan lagi `gameLoop`, menghapus semua pipa, mengembalikan burung ke posisi awal, mengembalikan skor menjadi 0, dan memutar musik latar dari awal.
10. Program terus berjalan sampai jendela ditutup.

# ERROR HANDLING
Error handling saya buat untuk menangani kegagalan memuat audio, pilihan menu, dan kondisi objek yang belum siap saat program berjalan.

1. Jika file audio tidak ditemukan (`getResourceAsStream` mengembalikan `null`), method `loadSound` mengembalikan `null` dan tidak ada exception yang dilempar. Di constructor `Logic`, musik hanya dinyalakan jika `backgroundClip` tidak `null`.
2. Jika file audio gagal dibuka atau formatnya tidak didukung (misalnya bukan WAV PCM), exception ditangkap oleh `catch` pada `loadSound`, detailnya dicetak dengan `printStackTrace`, lalu method mengembalikan `null`.
3. Jika user memilih "Keluar" atau menutup dialog Main Menu, nilai yang dikembalikan dialog bukan `START_OPTION`, sehingga jendela tidak dibuat dan program selesai.
4. Posisi burung dijepit dengan `Math.max` agar burung tidak keluar dari batas atas layar.
5. Pada `actionPerformed`, `view.repaint()` hanya dipanggil jika `view` tidak `null`, karena timer bisa berjalan sebelum `setView` dipanggil.
6. Pada `draw`, burung dan pipa hanya digambar jika objeknya tidak `null`.
7. Kondisi game over (jatuh ke bawah layar atau menabrak pipa) menghentikan `gameLoop` terlebih dahulu sebelum dialog ditampilkan agar dialog tidak muncul berulang di tiap frame.

# PENJELASAN ATRIBUT DAN METHOD

Disini adalah penjelasan berbagai atribut dan method dari code yang saya buat dari setiap class

## CLASS App

Penjelasan atribut dan method dari class App. Class ini adalah titik masuk program yang menampilkan Main Menu dan menyusun jendela game.

### ATRIBUT

- MENU_OPTIONS, adalah konstanta berisi label tombol pada Main Menu, yaitu "Mulai" dan "Keluar"
- START_OPTION, adalah konstanta untuk indeks tombol "Mulai" (bernilai 0), dipakai untuk membandingkan pilihan user

### METHOD

- main, adalah method titik masuk program. Method ini menampilkan Main Menu dengan `showOptionDialog`, lalu jika user memilih "Mulai" method ini membuat jendela, `Logic`, dan `View`, menghubungkan keduanya, dan menampilkan jendela

## CLASS Pipe

Penjelasan atribut dan method dari class Pipe. Class ini adalah model data yang mewakili satu pipa. Pipa atas dan pipa bawah adalah dua objek Pipe yang terpisah.

### ATRIBUT

- posX dan posY, adalah atribut untuk posisi kiri dan posisi atas pipa di layar
- width dan height, adalah atribut untuk lebar dan tinggi pipa
- image, adalah atribut untuk gambar pipa
- velocityX, adalah atribut kecepatan horizontal pipa. Atribut ini belum dipakai, karena pergerakan pipa diatur oleh `pipeVelocityX` di class Logic
- passed, adalah atribut yang rencananya menandai pipa sudah dilewati atau belum, tetapi belum dipakai

### METHOD

- Pipe (tanpa parameter), adalah constructor kosong yang membuat objek Pipe tanpa mengisi atributnya
- Pipe (dengan parameter), adalah constructor untuk membuat objek Pipe sekaligus mengisi posX, posY, width, height, dan image. velocityX diisi 0 dan passed diisi false
- getPosX dan setPosX, adalah method untuk mendapatkan (get) dan mengubah (set) nilai posX dari kelas ini
- getPosY dan setPosY, adalah method untuk mendapatkan (get) dan mengubah (set) nilai posY dari kelas ini
- getWidth dan setWidth, adalah method untuk mendapatkan (get) dan mengubah (set) nilai width dari kelas ini
- getHeight dan setHeight, adalah method untuk mendapatkan (get) dan mengubah (set) nilai height dari kelas ini
- getImage dan setImage, adalah method untuk mendapatkan (get) dan mengubah (set) nilai image dari kelas ini
- getVelocityX dan setVelocityX, adalah method untuk mendapatkan (get) dan mengubah (set) nilai velocityX dari kelas ini
- getPassed dan setPassed, adalah method untuk mendapatkan (get) dan mengubah (set) nilai passed dari kelas ini

## CLASS Player

Penjelasan atribut dan method dari class Player. Class ini adalah model data yang mewakili burung. Logika geraknya tidak ada di sini, tetapi di class Logic.

### ATRIBUT

- posX dan posY, adalah atribut untuk posisi kiri dan posisi atas burung di layar
- width dan height, adalah atribut untuk lebar dan tinggi burung
- image, adalah atribut untuk gambar burung
- velocityY, adalah atribut kecepatan vertikal burung. Nilai negatif berarti naik dan nilai positif berarti jatuh
- velocityX, adalah atribut kecepatan horizontal burung. Atribut ini belum dipakai untuk menggerakkan burung, hanya diatur ke 0 saat permainan diulang

### METHOD

- Player (tanpa parameter), adalah constructor kosong yang membuat objek Player tanpa mengisi atributnya
- Player (dengan parameter), adalah constructor untuk membuat objek Player sekaligus mengisi posX, posY, width, height, dan image
- getPosX dan setPosX, adalah method untuk mendapatkan (get) dan mengubah (set) nilai posX dari kelas ini
- getPosY dan setPosY, adalah method untuk mendapatkan (get) dan mengubah (set) nilai posY dari kelas ini
- getWidth dan setWidth, adalah method untuk mendapatkan (get) dan mengubah (set) nilai width dari kelas ini
- getHeight dan setHeight, adalah method untuk mendapatkan (get) dan mengubah (set) nilai height dari kelas ini
- getImage dan setImage, adalah method untuk mendapatkan (get) dan mengubah (set) nilai image dari kelas ini
- getVelocityY dan setVelocityY, adalah method untuk mendapatkan (get) dan mengubah (set) nilai velocityY dari kelas ini
- getVelocityX dan setVelocityX, adalah method untuk mendapatkan (get) dan mengubah (set) nilai velocityX dari kelas ini

## CLASS View

Penjelasan atribut dan method dari class View. Class ini turunan dari JPanel dan bertugas menggambar latar, burung, pipa, serta menampilkan skor. View tidak menyimpan state game, hanya membaca dari Logic.

### ATRIBUT

- width dan height, adalah atribut untuk ukuran area gambar (360 x 640)
- logic, adalah atribut untuk menyimpan Logic yang menjadi sumber data burung dan pipa
- backgroundImage, adalah atribut untuk gambar latar (`background.png`). Gambar dimuat sekali di constructor agar tidak dibaca ulang dari disk di setiap frame
- TITLE_ROW, TITLE_COLUMN, dan TITLE_COLUMN_SPAN, adalah konstanta untuk pengaturan posisi label skor
- labelTitle, adalah atribut berupa label yang menampilkan skor, nilai awalnya "0"

### METHOD

- View (constructor), adalah method untuk menyimpan Logic, mengatur ukuran dan warna latar panel, memuat gambar latar, mengaktifkan fokus keyboard, mendaftarkan Logic sebagai KeyListener, lalu memasang label skor
- paintComponent, adalah method yang dipanggil Swing setiap `repaint`. Method ini membersihkan layar lebih dulu lalu memanggil `draw`
- addTitle, adalah method untuk memasang label skor ke panel
- updateTitle, adalah method untuk mengganti angka skor pada label
- draw, adalah method untuk menggambar latar lebih dulu agar burung dan pipa tampil di atasnya, lalu menggambar burung dan semua pipa dari data yang diambil dari Logic

## CLASS Logic

Penjelasan atribut dan method dari class Logic. Class ini adalah otak dari game. Class ini mengimplementasikan ActionListener (untuk timer) dan KeyListener (untuk keyboard), menyimpan state game, menggerakkan objek, mengecek tabrakan dan skor, serta mengatur musik latar.

### ATRIBUT

- frameWidth dan frameHeight, adalah atribut untuk ukuran layar yang menjadi acuan posisi awal burung dan pipa
- playerStartPosX dan playerStartPosY, adalah atribut untuk posisi awal burung, yaitu di tengah layar
- playerWidth dan playerHeight, adalah atribut untuk ukuran burung
- pipeStartPosX dan pipeStartPosY, adalah atribut untuk posisi awal pipa. pipeStartPosX berada di tepi kanan layar agar pipa masuk dari luar layar
- pipeWidth dan pipeHeight, adalah atribut untuk ukuran pipa
- gameLoop, adalah atribut berupa timer utama yang menjalankan satu frame tiap tick (sekitar 60 kali per detik)
- pipesCooldown, adalah atribut berupa timer yang membuat sepasang pipa baru tiap 1500 ms
- gravity, adalah atribut untuk percepatan jatuh burung per frame
- pipeVelocityX, adalah atribut untuk kecepatan pipa. Nilainya negatif karena pipa bergerak ke kiri
- view, adalah atribut untuk menyimpan View agar Logic bisa menggambar ulang layar dan memperbarui skor
- birdImage, lowerPipeImage, dan upperPipeImage, adalah atribut untuk gambar burung, pipa bawah, dan pipa atas
- player, adalah atribut untuk objek burung
- pipes, adalah atribut berupa ArrayList yang menyimpan semua pipa. Pipa disimpan berpasangan, indeks genap untuk pipa atas dan indeks ganjil untuk pipa bawah
- backgroundClip, adalah atribut untuk menyimpan klip musik latar

### METHOD

- Logic (constructor), adalah method untuk memuat gambar, membuat burung, membuat list pipa, menyalakan timer pipesCooldown dan gameLoop, lalu memuat dan memutar musik latar
- setView, adalah method untuk menyimpan View. View tidak diberikan lewat constructor karena View sendiri membutuhkan Logic saat dibuat
- getPlayer dan getPipes, adalah method agar View bisa mengambil burung dan daftar pipa untuk digambar
- placePipes, adalah method untuk membuat sepasang pipa (atas lalu bawah) di tepi kanan layar dengan posisi celah acak. Celah antar pipa adalah seperempat tinggi layar
- nabrakTembok, adalah method untuk mengecek apakah burung jatuh melewati batas bawah layar. Jika iya, musik dan gameLoop dihentikan lalu dialog game over ditampilkan. Batas atas tidak dicek di sini karena posisi burung sudah dijepit di move
- cekTiang, adalah method untuk mengecek tabrakan burung dengan setiap pipa sekaligus menghitung skor. Hitbox burung diperkecil (12 piksel horizontal dan 8 piksel vertikal) dari ukuran gambarnya, dan pengecekan vertikal memakai sisi bawah burung. Skor dihitung ulang tiap frame dari jumlah pasangan pipa yang sudah dilewati, dan tiap pasangan hanya dihitung sekali. Jika terjadi tabrakan, perulangan berhenti, musik dan gameLoop dihentikan, lalu dialog game over ditampilkan
- move, adalah method untuk menggerakkan burung dan pipa pada satu frame. Gravitasi menambah kecepatan vertikal burung lebih dulu, lalu posisi burung berubah. Semua pipa digeser ke kiri dengan pipeVelocityX
- loadSound, adalah method untuk memuat file audio dari folder `FlappyBirdAssets/Audio` menjadi Clip. Parameter `loop` menentukan apakah klip diputar berulang. Jika file tidak ditemukan atau gagal dibuka, method mengembalikan null
- actionPerformed, adalah method yang dipanggil gameLoop tiap tick. Urutannya gerak dulu, lalu cek batas bawah, lalu cek pipa, lalu menggambar ulang layar
- keyTyped dan keyReleased, adalah method dari KeyListener yang tidak dipakai sehingga dibiarkan kosong
- keyPressed, adalah method untuk menangani tombol keyboard. Tombol spasi membuat burung melompat dengan kecepatan vertikal -10. Tombol R mengulang permainan dengan menyalakan gameLoop, menghapus semua pipa, mengembalikan burung ke posisi awal, mengembalikan skor menjadi 0, dan memutar musik dari awal

# DOKUMENTASI

Dokumentasi berupa video dari setiap fitur program.

