# Advanced Programming
Nama: Freia Arianti Zulaika

NPM: 2306152254

Kelas: A

## Reflection 1

Penerapan clean code dan secure coding:

- Penamaan yang bermakna dan efektif: saya menggunakan nama-nama yang jelas dan efektif dalam membuat class, method, dan variabel dalam modul ini. Penamaan ini dapat mempermudah dalam memahami kode tanpa harus menambahkan komentar penjelas. Contohnya terdapat pada createProductPage dan findProductById yang langsung menjelaskan fungsinya.
- Penggunaan UUID: UUID meningkatkan keamanan dengan menghasilkan ID unik yang sulit ditebak sehingga mencegah prediksi ID (ID enumeration attacks).
- Penggunaan fungsi yang efisien: membuat fungsi seefisien mungkin dengan satu tujuan utama, seperti create, edit, atau delete.

Aspek yang dapat ditingkatkan antara lain:
- Validasi Input: peningkatan validasi input yang lebih menyeluruh sehingga seluruh input dipastikan aman dan sesuai dengan yang diharapkan.
- Penanganan Exception yang Lebih Baik: program seharusnya menunjukkan exception yang lebih baik dengan tidak menunjukkan data informasi yang sensitif.

## Reflection 2
1. Setelah menulis unit test, saya merasa lebih yakin dengan kode saya dan hasil yang diharapkan. Unit test dapat membantu saya dalam menemukan kesalahan serta dapat memastikan fungsionalitas program dapat bekerja sesuai yang diinginkan. Jumlah unit test dalam sebuah kelas harus dapat mencakup semua method penting dan kemungkinan kasus-kasus khusus. Untuk menentukan apakah unit test yang kita miliki sudah memadai, kita bisa menggunakan code coverage untuk mengukur seberapa banyak kode kita yang telah dieksekusi selama pengujian. 100% code coverage menunjukkan bahwa tiap baris kode yang kita miliki telah tereksekusi semua. Namun, 100% code coverage tidak menentukan bahwa kode kita sudah pasti terbebas dari bug. Kesalahan-kesalahan seperti logika maupun kasus-kasus yang tidak tertangani bisa saja terjadi.

2. Jika membuat functional test suite baru untuk verifikasi jumlah item dalam daftar produk setelah CreateProductFunctionalTest.java, kita akan kembali menangani prosedur setup dan variabel instance yang sama. Hal ini dapat melanggar prinsip DRY (Don't Repeat Yourself) karena terdapat duplikasi kode yang memiliki fungsi yang sama. Cara mengatasi masalah tersebut yaitu dengan menggunakan sifat inheritance dengan membuat kelas untuk membuat setup serta fungsi-fungsi umum.
   Untuk mengatasinya, kita bisa menggunakan inheritance dengan membuat kelas dasar abstrak untuk setup dan fungsi umum. Untuk test suite kecil, lebih baik menyimpan test terkait dalam kelas yang sama dengan metode terpisah. Sedangkan untuk test suite besar, penggunaan inheritance menjadi pilihan yang lebih tepat. Yang terpenting adalah menjaga keseimbangan antara pengurangan duplikasi kode dan kemudahan dalam membaca serta memelihara test.
