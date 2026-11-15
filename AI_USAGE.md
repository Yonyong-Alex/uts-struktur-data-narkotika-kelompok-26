# Log Penggunaan Kecerdasan Buatan (AI Usage Log)

Dokumen ini mencatat pemanfaatan alat bantu Kecerdasan Buatan (*Artificial Intelligence*) secara transparan, etis, dan bertanggung jawab selama proses penyusunan dataset, pengembangan kode, pengujian, serta dokumentasi proyek UTS Struktur Data **Kelompok 26**.

## Informasi Umum
- **Alat AI yang Digunakan:** Google Gemini
- **Peran AI:** Asisten ekstraksi data teks hukum, perancangan algoritma struktur data kustom (*from scratch*), bantuan *debugging*, serta penyusunan kerangka dokumentasi teknis.
- **Prinsip Penggunaan:** Seluruh kode dan data yang dihasilkan AI telah ditinjau ulang, diuji secara lokal, dan disesuaikan agar mematuhi batasan tugas (tanpa menggunakan *Java Collection Framework* bawaan).

---

## Ringkasan Pemanfaatan per Tahap Pengembangan

| Tahap | Komponen / File Terkait | Aktivitas Bantuan AI | Verifikasi & Validasi Manual oleh Tim |
| :--- | :--- | :--- | :--- |
| **Tahap 1: Dataset & Provenance** | `data/putusan-50.csv`, `docs/data-provenance.csv` | Membantu mengekstrak informasi kunci dari dokumen PDF putusan pengadilan (nomor perkara, terdakwa, jenis & berat barang bukti, tuntutan, serta amar vonis dalam satuan bulan) ke format CSV. | Tim memeriksa ulang setiap baris data terhadap dokumen PDF asli (termasuk memverifikasi status record `P005`, `P006`, `P007`, dan `P012` serta kelengkapan `sourcePages`) sebelum memberi status `verified`. |
| **Tahap 2: Pencarian & Pengurutan** | `src/main/java/PutusanService.java`, `src/main/java/SearchResult.java` | Membantu merancang logika *Linear Search* dengan penghitung langkah iterasi serta algoritma *Merge Sort* (*ascending* & *descending*) di atas struktur `DynamicArray<T>`. | Tim menguji kasus batas awal (`P001`), data tengah, batas akhir (`P050`), data tidak ditemukan (`P999`), serta memastikan `validateInvariant()` tetap bernilai `true`. |
| **Tahap 3: Filter, Agregasi & Ekspor** | `src/main/java/PutusanService.java`, `src/main/java/StatistikPutusan.java`, `src/main/java/ReportExporter.java` | Membantu menyusun logika filter (*jenis narkotika*, *rentang vonis*, *rentang berat barang bukti*, *pasal terbukti*, dan *kombinasi*), perhitungan statistik agregasi (rata-rata, nilai minimum/maksimum, total berat, total denda rupiah, distribusi jenis narkotika), serta *escaping* karakter pada ekspor `.csv`/`.txt`. | Tim menjalankan program secara lokal, memverifikasi keakuratan perhitungan rata-rata vonis/tuntutan dan total denda terhadap dataset 50 perkara, dan mengecek kerapian format file hasil ekspor di folder `output/`. |
| **Tahap 4: Audit Stack & Dokumentasi** | `src/main/java/AuditLog.java`, `src/main/java/AuditStack.java`, `src/main/java/PutusanService.java`, `src/main/java/Main.java`, `README.md`, `AI_USAGE.md` | Membantu merancang struktur data sekunder generik `AuditStack<T>` (prinsip LIFO) berbasis array `Object[]` kustom, metode pencarian non-destruktif (`cariNonDestruktif` dan `cariRiwayatByKeyword` dengan stack sementara + *restore*), integrasi audit mutasi (`TAMBAH` & `HAPUS`) pada `PutusanService`, demonstrasi Skenario #5, serta penyusunan dokumentasi repositori. | Tim memeriksa kesesuaian tipe generik `AuditStack<AuditLog>`, memverifikasi kompilasi seluruh kelas, memastikan `validateInvariant()` tetap valid setelah pencarian non-destruktif, dan melakukan *peer review* melalui Pull Request di GitHub. |

---

## Log Detail Pemanfaatan AI per Sesi

| Tanggal | Tahap / Fokus | Ringkasan Prompt / Kebutuhan | Output AI yang Digunakan | Validasi & Penyesuaian Manual |
| :--- | :--- | :--- | :--- | :--- |
| 2026-11-15 | Tahap 1 — Ekstraksi & Verifikasi Dataset | Ekstraksi atribut putusan narkotika dari PDF ke format tabel CSV terstruktur serta verifikasi record `P005`, `P006`, `P007`, dan `P012`. | Draf baris CSV untuk 50 putusan pengadilan dan matriks verifikasi atribut record. | Pemeriksaan silang seluruh angka vonis, tuntutan, berat barang bukti, denda, pasal terbukti, dan nomor perkara terhadap dokumen sumber. |
| 2026-11-15 | Tahap 2 — Pencarian & Pengurutan | Implementasi *Linear Search* penghitung langkah dan *Merge Sort* pada struktur data kustom. | Kode metode `cariByCaseId`, `cariByNomorPerkara`, dan `urutkanBerdasarkanVonis`. | Pengujian kasus batas pada `Main.java` dan pengecekan invariant struktur data utama. |
| 2026-11-15 | Tahap 3 — Perluasan Filter, Agregasi & Ekspor | Penambahan `filterByRentangBerat`, `filterByPasalTerbukti`, metrik `totalDendaRupiah`, `distribusiJenisNarkotika`, serta pembaruan format laporan `.txt`. | Pembaruan kelas `PutusanService`, `StatistikPutusan`, dan `ReportExporter` tanpa memakai `java.util.*`. | Uji eksekusi filter berat barang bukti dan pasal terbukti, serta pengecekan file output di direktori `output/`. |
| 2026-11-15 | Tahap 4 — Audit Stack Non-Destruktif & Finalisasi | Penambahan pencarian riwayat non-destruktif pada `AuditStack<T>`, demo Skenario #5 di `Main.java`, dan pembersihan konflik dokumentasi. | Metode `cariNonDestruktif` & `cariRiwayatByKeyword` dengan pemulihan stack sementara, serta dokumen `README.md` dan `AI_USAGE.md`. | Verifikasi ukuran stack, elemen puncak (`peek()`), dan `validateInvariant()` sebelum serta sesudah pencarian, dilanjutkan alur PR & *peer review* dengan rekan tim. |