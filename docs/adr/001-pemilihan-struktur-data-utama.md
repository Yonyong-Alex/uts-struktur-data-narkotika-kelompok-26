# ADR 001: Pemilihan DynamicArray sebagai Struktur Data Utama

- Status: Diterima (Accepted)
- Tanggal: 2026-11-15
- Penulis: Muhammad Excel (Kelompok 26)

## Konteks
Aplikasi pengelolaan 50 putusan pengadilan narkotika memerlukan struktur data linier generik (`StrukturData<T>`) yang dibangun dari nol tanpa memakai koleksi bawaan `java.util`. Struktur data ini harus mampu menampung seluruh data hasil pembacaan CSV, mendukung akses cepat berdasarkan indeks untuk fitur tampilan, filter, agregasi, dan pengurutan, serta menjaga konsistensi kapasitas dan susunan elemen di memori.

## Keputusan
Memilih Array Dinamis (`DynamicArray<T>`) berbasis array mentah `Object[]` dengan kapasitas awal 10 dan strategi penggandaan kapasitas (`newCapacity = elements.length * 2`) pada method `grow()`.

## Alternatif yang Dipertimbangkan
1. Singly Linked List: Unggul saat menyisipkan data di awal dengan kompleksitas O(1), tetapi pencarian berdasarkan indeks (`get(i)`) membutuhkan penelusuran berantai O(n) dan memerlukan memori tambahan untuk menyimpan referensi node berikutnya.
2. Array Statis Ukuran Tetap (`Putusan[50]`): Tidak fleksibel jika ada penambahan data baru melebihi 50 putusan saat proses pengujian.

## Konsekuensi dan Analisis Kompleksitas
Kelebihan:
- Akses elemen berdasarkan indeks `get(position)` dan pembaruan `set(position, value)` berjalan dalam waktu konstan O(1).
- Penambahan data di akhir `add(value)` memiliki kompleksitas waktu teramortisasi O(1).
- Data tersimpan berurutan di dalam `Object[]`, sehingga efisien saat dibaca berulang kali untuk filter dan agregasi.

Kekurangan:
- Operasi penyisipan `insert(position, value)` dan penghapusan `remove(position)` pada indeks awal atau tengah memerlukan pergeseran elemen (shifting) dengan kompleksitas O(n).

Jaminan Konsistensi Struktur (`validateInvariant()`):
- Kapasitas array selalu lebih besar atau sama dengan jumlah elemen aktif (`elements.length >= size` dan `size >= 0`).
- Seluruh indeks aktif 0 sampai `size - 1` terisi objek putusan (tidak null), sedangkan sisa slot kosong dari `size` sampai `elements.length - 1` selalu bernilai null.
- Akses indeks di luar batas (`position < 0` atau `position >= size`) selalu ditolak dengan `IndexOutOfBoundsException`.
- Kapasitas awal tetap 10 (konstanta `DEFAULT_CAPACITY`) dan tidak dapat diatur dari luar, sehingga kapasitas 0 atau negatif tidak mungkin terjadi.
- `add(null)`, `set(position, null)`, dan `insert(position, null)` melempar `IllegalArgumentException`.