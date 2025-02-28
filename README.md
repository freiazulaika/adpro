# Advanced Programming
Nama: Freia Arianti Zulaika

NPM: 2306152254

Kelas: A

Deplyoment link: https://eshop-advprog-freiazulaika-8ebdba4e.koyeb.app/

## Modul 1
### Reflection 1

Penerapan clean code dan secure coding:

- Penamaan yang bermakna dan efektif: saya menggunakan nama-nama yang jelas dan efektif dalam membuat class, method, dan variabel dalam modul ini. Penamaan ini dapat mempermudah dalam memahami kode tanpa harus menambahkan komentar penjelas. Contohnya terdapat pada createProductPage dan findProductById yang langsung menjelaskan fungsinya.
- Penggunaan UUID: UUID meningkatkan keamanan dengan menghasilkan ID unik yang sulit ditebak sehingga mencegah prediksi ID (ID enumeration attacks).
- Penggunaan fungsi yang efisien: membuat fungsi seefisien mungkin dengan satu tujuan utama, seperti create, edit, atau delete.

Aspek yang dapat ditingkatkan antara lain:
- Validasi Input: peningkatan validasi input yang lebih menyeluruh sehingga seluruh input dipastikan aman dan sesuai dengan yang diharapkan.
- Penanganan Exception yang Lebih Baik: program seharusnya menunjukkan exception yang lebih baik dengan tidak menunjukkan data informasi yang sensitif.

### Reflection 2
1. Setelah menulis unit test, saya merasa lebih yakin dengan kode saya dan hasil yang diharapkan. Unit test dapat membantu saya dalam menemukan kesalahan serta dapat memastikan fungsionalitas program dapat bekerja sesuai yang diinginkan. Jumlah unit test dalam sebuah kelas harus dapat mencakup semua method penting dan kemungkinan kasus-kasus khusus. Untuk menentukan apakah unit test yang kita miliki sudah memadai, kita bisa menggunakan code coverage untuk mengukur seberapa banyak kode kita yang telah dieksekusi selama pengujian. 100% code coverage menunjukkan bahwa tiap baris kode yang kita miliki telah tereksekusi semua. Namun, 100% code coverage tidak menentukan bahwa kode kita sudah pasti terbebas dari bug. Kesalahan-kesalahan seperti logika maupun kasus-kasus yang tidak tertangani bisa saja terjadi.

2. Jika membuat functional test suite baru untuk verifikasi jumlah item dalam daftar produk setelah CreateProductFunctionalTest.java, kita akan kembali menangani prosedur setup dan variabel instance yang sama. Hal ini dapat melanggar prinsip DRY (Don't Repeat Yourself) karena terdapat duplikasi kode yang memiliki fungsi yang sama. Cara mengatasi masalah tersebut yaitu dengan menggunakan sifat inheritance dengan membuat kelas untuk membuat setup serta fungsi-fungsi umum.
   Untuk mengatasinya, kita bisa menggunakan inheritance dengan membuat kelas dasar abstrak untuk setup dan fungsi umum. Untuk test suite kecil, lebih baik menyimpan test terkait dalam kelas yang sama dengan metode terpisah. Sedangkan untuk test suite besar, penggunaan inheritance menjadi pilihan yang lebih tepat. Yang terpenting adalah menjaga keseimbangan antara pengurangan duplikasi kode dan kemudahan dalam membaca serta memelihara test.

## Modul 2
> List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
 
- Import yang tidak digunakan pada berkas ProductController

   Isu: Penggunaan import org.springframework.web.bind.annotation.* yang tidak dibutuhkan.

   Solusi: Hanya menggunakan import-import yang diperlukan, sehingga tidak menyebabkan redundansi import.

   Code terbaru:
   
   ```
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PostMapping;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.ModelAttribute;
  ```

- For loop seharusnya menggunakan Foreach di berkas ProductRepository

   Isu: Saya menggunakan for loop biasa (menggunakan indeks) yang membuat kode menjadi kurang optimal.
  
  Solusi: Saya menggunakan foreach loop untuk mendapatkan kode yang memiliki _readability_ yang lebih baik.

  Code terbaru:
  
  ```
  public Product edit(Product newData) {
    for (Product prevData : productData) {
        if (prevData.getProductId().equals(newData.getProductId())) {
            prevData.setProductName(newData.getProductName());
            prevData.setProductQuantity(newData.getProductQuantity());
            return prevData;
        }
    }
    return null;
  }
  ```

- Penggunaan modifier Public pada tiap atribut di interface ProductService
  
  Isu: Saya menggunakan modifier public pada semua atribut, sementara interface sendiri memiliki sifat `public abstract`, sehingga penggunaan modifier `public` menjadi tidak bermakna.

  Solusi: Saya menghapus semua modifier `public` di semua atribut.

  Code terbaru:
  
  ```
  public interface ProductService {
    Product create(Product product);
    List<Product> findAll();
    Product findProductById(String productId);
    Product edit(Product product);
    void delete(String productId);
  }
  ```
  
> Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!

Pada proyek ini, saya menerapkan _Continuous Integration_ (CI) dengan membuat workflow `ci.yml`. Langkah ini akan secara otomatis menjalankan semua unit test saat push atau pull request. Kemudian, program akan dilanjutkan ke PMD untuk dilakukan _scanning code_ dan _analysis_. Langkah ini diperlukan untuk mengetahui kualitas kode serta _best practices_ yang dapat dilakukan. Setelah melakukan langkah-langkah tersebut, akan dilanjutkan ke langkah _Continuous Deployment/Delivery_ dengan mengintegrasikan dengan Koyeb sebagai platform _deployment_ otomatis.

## Modul 3

> Explain what principles you apply to your project!
1. Single Responsibility Principle (SRP)

Dalam proyek yang saya kerjakan, saya memisahkan class `CarController` dari class `ProductController`. Sebelum pengaplikasian SOLID, class `CarController` meng-_extend_ class `ProductController` serta berada di berkas yang sama dengan class `ProductController`. Setelah pengaplikasian SRP, class `CarController` berada di berkas yang berbeda serta tidak meng-_extend_ class `ProductController`, sehingga `CarController` tidak lagi terkait dengan `ProductController`.

2. Liskov Substitution Principle (LSP)

`CarServiceImpl` mengimplementasikan interface `CarService` dan dapat disubstitusi dimana pun `CarService` digunakan tanpa mengubah perilaku program.

3. Interface Segregation Principle (ISP)

Interface `CarService` memiliki method yang terfokus hanya pada operasi yang berhubungan dengan model `Car`.

4. Dependency Inversion Principle (DIP)

Class `CarController` bergantung pada interface `CarService` dan bukan berupa implementasi konkretnya.

> Explain the advantages of applying SOLID principles to your project with examples.

Penerapan prinsip SOLID pada proyek ini dapat meningkatkan kualitas kode, meningkatkan _readability_, serta memudahkan dalam mengembangkan kode. Dengan mengikuti prinsip-prinsip ini, kita dapat membuat sistem yang lebih mudah untuk di-_maintain_ serta lebih fleksibel terhadap perubahan kebutuhan di masa depan. Sebagai contoh, pemisahan `CarService` dari `CarController` memungkinkan kita untuk mengubah logika bisnis mobil (seperti menambahkan validasi) tanpa harus memodifikasi kode _controller_. Contoh lainnya adalah penggunaan interface `CarService` yang memungkinkan kita untuk mengganti implementasi penyimpanan data dari _in-memory_ list ke _database_ tanpa perlu mengubah kode di controller.

> Explain the disadvantages of not applying SOLID principles to your project with examples.

Jika tidak menerapkan SOLID, pengembangan dan pemeliharaan kode akan menjadi lebih susah dan terhambat, selain itu _readability_ kode juga akan berkurang dan akan membuat pembaca menjadi bingung. Sebagai contoh, jika kita ingin menguji `CarController`, maka kita harus melakukan _setup_ di seluruh sistem termasuk _repository_. Duplikasi kode sangat mungkin terjadi di berbagai tempat, seperti logika pengambilan data `Car` yang sama harus ditulis ulang di setiap bagian yang membutuhkannya.