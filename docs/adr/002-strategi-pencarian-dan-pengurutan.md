# ADR 002: Strategi Pencarian Linear Search dan Pengurutan Merge Sort Non-Destruktif

- Status: Diterima (Accepted)
- Tanggal: 2026-11-15
- Penulis: Muhammad Excel (Kelompok 26)

## Konteks
Aplikasi wajib menyediakan fitur pencarian putusan berdasarkan `caseId` atau `nomorPerkara` beserta jumlah langkah pemeriksaannya, serta fitur pengurutan putusan berdasarkan lama pidana penjara (`vonisBulan`) tanpa mengubah urutan asli pada struktur data utama. Seluruh algoritma wajib ditulis dari nol tanpa koleksi `java.util`.

## Keputusan
1. Pencarian: Menggunakan Linear Search (pencarian beruntun) melalui antarmuka `StrukturData<T>` yang mengembalikan objek `SearchResult` berisi data `Putusan` dan jumlah langkah pencarian.
2. Pengurutan: Menggunakan Out-of-Place Merge Sort pada array sementara `Putusan[]`, lalu menyalin hasilnya ke objek `DynamicArray<Putusan>` baru.

## Alternatif yang Dipertimbangkan
1. Binary Search: Memiliki kompleksitas O(log n), tetapi mewajibkan data dalam keadaan terurut berdasarkan kata kunci pencarian. Karena dataset `putusan-50.csv` tidak terurut berdasarkan `nomorPerkara`, penggunaan Binary Search akan memaksa pengurutan ulang struktur utama setiap kali kriteria pencarian berubah.
2. In-Place Bubble Sort atau Selection Sort: Memiliki kompleksitas O(n^2) yang lebih lambat dan mengubah langsung urutan data pada `DynamicArray` utama, sehingga urutan asli dataset menjadi hilang.

## Konsekuensi dan Analisis Kompleksitas
Pencarian (Linear Search):
- Jika `caseId` atau `nomorPerkara` bernilai null atau hanya spasi, pencarian langsung mengembalikan `SearchResult` dengan `putusan = null` dan `jumlahLangkah = 0`.
- Jika data tidak ditemukan setelah seluruh array diperiksa, `jumlahLangkah = size()`.
- Best Case: O(1), yaitu 1 langkah apabila data berada pada indeks 0 (contoh: P001).
- Worst Case: O(n), yaitu 50 langkah apabila data berada pada indeks terakhir (contoh: P050) atau tidak ditemukan di dalam daftar.
- Tidak mengubah susunan indeks maupun merusak konsistensi struktur data utama.

Pengurutan (Out-of-Place Merge Sort):
- Kompleksitas waktu tetap O(n log n) pada kondisi terbaik, rata-rata, maupun terburuk, serta bersifat stabil sehingga perkara dengan lama vonis yang sama tetap mempertahankan urutan aslinya.
- Membutuhkan memori tambahan sebesar O(n) untuk array sementara, tetapi menjaga `DynamicArray` utama tetap utuh 100% dan menghasilkan daftar terurut baru yang lolos pemeriksaan `validateInvariant()`.