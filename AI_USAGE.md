# Log Penggunaan Kecerdasan Buatan (AI Usage Log)

Dokumen ini mencatat pemanfaatan alat bantu Kecerdasan Buatan (*Artificial Intelligence*) secara transparan, etis, dan bertanggung jawab selama proses penyusunan dataset, pengembangan kode, pengujian, serta dokumentasi proyek UTS Struktur Data **Kelompok 26**.

## Informasi Umum
* **Alat AI yang Digunakan:** Google Gemini
* **Peran AI:** Asisten ekstraksi data teks hukum, perancangan algoritma struktur data kustom (*from scratch*), bantuan *debugging*, serta penyusunan kerangka dokumentasi teknis.
* **Prinsip Penggunaan:** Seluruh kode dan data yang dihasilkan AI telah ditinjau ulang, diuji secara lokal, dan disesuaikan agar mematuhi batasan tugas (tanpa menggunakan *Java Collection Framework* bawaan).

---

## Ringkasan Pemanfaatan per Tahap Pengembangan

| Tahap | Komponen / File Terkait | Aktivitas Bantuan AI | Verifikasi & Validasi Manual oleh Tim |
| :--- | :--- | :--- | :--- |
| **Tahap 1: Dataset & Provenance** | `data/putusan-50.csv`, `docs/data-provenance.csv` | Membantu mengekstrak informasi kunci dari dokumen PDF putusan pengadilan (nomor perkara, terdakwa, jenis & berat barang bukti, tuntutan, serta amar vonis dalam satuan bulan) ke format CSV. | Tim memeriksa ulang setiap baris data terhadap dokumen PDF asli (termasuk memverifikasi konversi satuan volume *liquid vape* ke gram berdasarkan keterangan ahli) sebelum memberi status `verified`. |
| **Tahap 2: Pencarian & Pengurutan** | `src/main/java/PutusanService.java`, `src/main/java/SearchResult.java` | Membantu merancang logika *Linear Search* dengan penghitung langkah iterasi serta algoritma *Merge Sort* (*ascending* & *descending*) di atas struktur `DynamicArray<T>`. | Tim menguji kasus batas awal (`P001`), data tengah, batas akhir (`P050`), data tidak ditemukan (`P999`), serta memastikan `validateInvariant()` tetap bernilai `true`. |
| **Tahap 3: Filter, Agregasi & Ekspor** | `src/main/java/StatistikPutusan.java`, `src/main/java/ReportExporter.java` | Membantu menyusun logika filter kombinasi (*case-insensitive*), perhitungan statistik agregasi (rata-rata, nilai minimum/maksimum, total berat), dan *escaping* karakter pada ekspor `.csv`/`.txt`. | Tim menjalankan program secara lokal, memverifikasi keakuratan perhitungan rata-rata vonis/tuntutan secara manual, dan mengecek kerapian format file hasil ekspor di folder `output/`. |
| **Tahap 4: Audit Stack & Dokumentasi** | `src/main/java/AuditLog.java`, `src/main/java/AuditStack.java`, `README.md`, `AI_USAGE.md` | Membantu merancang struktur data sekunder *Stack* (prinsip LIFO) menggunakan array kustom untuk pencatatan riwayat mutasi data (`TAMBAH` & `HAPUS`) serta merapikan dokumentasi repositori. | Tim melakukan simulasi penambahan dan penghapusan data dummy (`P051`) pada `Main.java`, memastikan urutan riwayat tampil dari terbaru ke terlama, dan melakukan *peer review* melalui Pull Request di GitHub. |

---

## Pernyataan Integritas Akademik
Seluruh anggota Kelompok 26 memahami sepenuhnya alur kerja kode, kompleksitas waktu (*Big-O*), serta struktur data (`DynamicArray` dan `AuditStack`) yang diimplementasikan di dalam repositori ini dan siap mempertanggungjawabkannya pada sesi evaluasi/presentasi.