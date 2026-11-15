# Kontribusi Anggota Kelompok 26

Dokumen ini mencatat pembagian tugas keseluruhan (ekstraksi data, struktur data, algoritma, pengujian, dan dokumentasi) serta tautan bukti kolaborasi Git (Issue, Pull Request, Peer Review, dan Commit Utama) untuk proyek UTS Struktur Data dan Algoritma.

- Repositori Utama: https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26
- Tag Rilis Final (uts-v1.0): https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/releases/tag/uts-v1.0

---

## Pembagian Tugas Keseluruhan (Data dan Kode)

### Tahap 1: Ekstraksi dan Verifikasi Dataset (50/50)
- **Muhammad Excel (@Yonyong-Alex):** Mengunduh dan mengekstrak 25 putusan PN Palembang urutan 1 sampai 25 (P001-P025) ke data/putusan-50.csv, mencatat metadata sumber halaman di docs/data-provenance.csv, serta melakukan verifikasi silang untuk data urutan 26 sampai 50.
- **Fahir (@alimfakhir):** Mengunduh dan mengekstrak 25 putusan PN Palembang urutan 26 sampai 50 (P026-P050) ke data/putusan-50.csv, mencatat metadata sumber halaman di docs/data-provenance.csv, serta melakukan verifikasi silang untuk data urutan 1 sampai 25.

### Tahap 2: Struktur Data Inti dan File I/O (Target PR 1)
- **Muhammad Excel (@Yonyong-Alex):** Merancang kontrak interface generik StrukturData dan mengimplementasikan struktur data utama DynamicArray dari nol (add, get, remove, set, insert, grow, validateInvariant), serta memperkuat validasi error per baris pada CsvReader.java.
- **Fahir (@alimfakhir):** Membuat kelas model immutable Putusan.java (16 atribut dataset) dan merancang fungsi pembacaan berkas CSV awal pada CsvReader.java.

### Tahap 3: Fitur Layanan dan Algoritma (Target PR 2)
- **Muhammad Excel (@Yonyong-Alex):** Mengembangkan algoritma pencarian sekuensial (Linear Search) cariByCaseId dan cariByNomorPerkara beserta SearchResult.java, serta algoritma pengurutan stabil non-destruktif (Out-of-Place Merge Sort) urutkanBerdasarkanVonis pada PutusanService.java.
- **Fahir (@alimfakhir):** Mengembangkan fitur penyaringan multi-kriteria (filterByJenisNarkotika, filterByRentangVonis, filterByRentangBeratBB, filterByPasalTerbukti, dan filterKombinasi), perhitungan agregasi statistik pada StatistikPutusan.java, serta ekspor laporan ke berkas TXT melalui ReportExporter.java.

### Tahap 4: Struktur Sekunder, Pengujian, dan Dokumentasi (Target PR 3)
- **Muhammad Excel (@Yonyong-Alex):** Menyusun pengujian batas DynamicArrayBoundaryTest.java (10 skenario uji termasuk validasi CSV tidak valid) dan AuditStackTest.java, serta menulis dokumen keputusan arsitektur (docs/adr/001 sampai 003) dan penelusuran manual algoritma (docs/trace/penelusuran-manual-algoritma.md).
- **Fahir (@alimfakhir):** Membangun struktur data sekunder AuditStack (prinsip LIFO) dan AuditLog.java, menghubungkan fitur audit pada PutusanService.java, menyusun alur demo lengkap di Main.java, serta melengkapi README.md, AI_USAGE.md, dan bukti eksekusi di folder docs/demo/.

### Tahap 5: Integrasi dan Rilis Final
- **Muhammad Excel (@Yonyong-Alex):** Melakukan peer review dan merge PR milik rekan tim, menyusun CONTRIBUTIONS.md, dan membuat tag rilis uts-v1.0.
- **Fahir (@alimfakhir):** Melakukan peer review dan merge PR milik rekan tim (PR #16), menyelaraskan cabang main, dan memverifikasi rilis uts-v1.0.

---

## Detail Kontribusi dan Tautan Bukti GitHub

### 1. Muhammad Excel (@Yonyong-Alex)
- **Daftar Semua Issue:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/issues
- **Issue Tugas Excel:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/issues/3 (sesuaikan nomor issue jika berbeda)
- **Pull Request Utama (Hardening CSV, Unit Test, dan Docs):** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/pull/16
- **Daftar Semua PR Excel:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/pulls?q=is%3Apr+author%3AYonyong-Alex
- **Bukti Peer Review (Me-review PR Fahir):** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/pulls?q=is%3Apr+reviewed-by%3AYonyong-Alex
- **Daftar Commit Utama Excel:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/commits/main?author=Yonyong-Alex

### 2. Fahir (@alimfakhir)
- **Daftar Semua Issue:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/issues
- **Issue Tugas Fahir:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/issues/4 (sesuaikan nomor issue jika berbeda)
- **Daftar Semua PR Fahir:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/pulls?q=is%3Apr+author%3Aalimfakhir
- **Bukti Peer Review (Me-review PR #16 milik Excel):** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/pull/16
- **Daftar Semua Review Fahir:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/pulls?q=is%3Apr+reviewed-by%3Aalimfakhir
- **Daftar Commit Utama Fahir:** https://github.com/Yonyong-Alex/uts-struktur-data-narkotika-kelompok-26/commits/main?author=alimfakhir
