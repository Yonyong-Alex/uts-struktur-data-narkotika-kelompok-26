# Penelusuran Manual (Manual Trace) Struktur Data & Algoritma

Dokumen ini menyajikan penelusuran manual langkah demi langkah menggunakan data aktual dari `data/putusan-50.csv` untuk memverifikasi ketepatan indeks, jumlah langkah, pergeseran elemen, serta sifat stabil pengurutan.

---

## 1. Penelusuran Linear Search (`PutusanService`)

Dataset `daftarPutusan` berisi 50 elemen pada indeks `0` s.d. `49` (`P001` s.d. `P050`).

| Skenario Uji | Method | Kata Kunci | Indeks Ditemukan | Jumlah Langkah | Hasil `SearchResult` |
| :--- | :--- | :--- | :---: | :---: | :--- |
| **Best Case (Batas Awal)** | `cariByCaseId` | `"P001"` | `0` | **1** | Ditemukan (`1113/Pid.Sus/2025/PN Plg`) |
| **Middle Case (Tengah)** | `cariByNomorPerkara` | `"1412/Pid.Sus/2025/PN Plg"` | `22` (`P023`) | **23** | Ditemukan (`vonisBulan = 240`) |
| **Worst Case (Batas Akhir)** | `cariByCaseId` | `"P050"` | `49` | **50** | Ditemukan (`958/Pid.Sus/2026/PN Plg`) |
| **Not Found (Tidak Ada)** | `cariByCaseId` | `"P999"` | `-` | **50** | `putusan = null`, `isFound() = false` |
| **Input Kosong / Null** | `cariByCaseId` | `null` / `"   "` | `-` | **0** | Langsung kembali tanpa iterasi |

---

## 2. Penelusuran Pergeseran Indeks (`remove` & `insert` pada `DynamicArray`)

Kondisi awal: `size = 50`, kapasitas array = `80` (hasil pertumbuhan `10 -> 20 -> 40 -> 80`), isi `[P001, P002, ..., P026, ..., P049, P050]`.

1. **`remove(0)` (Hapus Elemen Pertama):**
    - Elemen yang diambil: `elements[0]` = `P001`.
    - Loop pergeseran `i = 0` s.d. `48`: `elements[i] = elements[i + 1]` (49 kali penyalinan ke kiri).
    - Slot `elements[49]` diisi `null`, `size` berkurang menjadi `49`.
    - Keadaan baru: `elements[0] = P002`, `elements[48] = P050`, `validateInvariant() == true`.

2. **`remove(24)` (Hapus Elemen Tengah):**
    - Karena `P001` sudah dihapus, indeks `24` kini ditempati oleh `P026`, dan indeks `25` ditempati `P027`.
    - Elemen yang diambil: `elements[24]` = `P026`.
    - Loop pergeseran `i = 24` s.d. `47`: `elements[i] = elements[i + 1]` (24 kali penyalinan ke kiri).
    - Slot `elements[48]` diisi `null`, `size` berkurang menjadi `48`.
    - Keadaan baru: `elements[24] = P027`, `elements[47] = P050`, `validateInvariant() == true`.

3. **`remove(47)` (Hapus Elemen Terakhir):**
    - Elemen yang diambil: `elements[47]` = `P050`.
    - Loop pergeseran tidak berjalan (`47 < 47` bernilai `false`, kompleksitas $O(1)$).
    - Slot `elements[47]` diisi `null`, `size` berkurang menjadi `47`.
    - Keadaan baru: `elements[46] = P049`, `validateInvariant() == true`.

4. **`insert(0, P-NEW-1)` (Sisipkan di Awal):**
    - Loop pergeseran `i = 47` turun ke `1`: `elements[i] = elements[i - 1]` (47 kali penyalinan ke kanan).
    - `elements[0]` diisi `P-NEW-1`, `size` bertambah menjadi `48`.
    - Keadaan baru: `elements[0] = P-NEW-1`, `elements[1] = P002`, `validateInvariant() == true`.

---

## 3. Penelusuran Out-of-Place Merge Sort (`urutkanBerdasarkanVonis` Ascending)

Contoh penelusuran pada 8 record pertama dataset (`[P001..P008]`) yang disalin ke `tempArray`:
- Data awal: `[P001(78), P002(6), P003(96), P004(84), P005(72), P006(66), P007(72), P008(84)]`

### Tahap Pembagian (*Divide*)
- Split `[0..7]` (`mid = 3`) menjadi Kiri `[0..3]` dan Kanan `[4..7]`.
- Kiri `[0..3]` dibagi menjadi `[0..1]` (`[P001(78), P002(6)]`) dan `[2..3]` (`[P003(96), P004(84)]`).
- Kanan `[4..7]` dibagi menjadi `[4..5]` (`[P005(72), P006(66)]`) dan `[6..7]` (`[P007(72), P008(84)]`).

### Tahap Penggabungan (*Merge*)
1. **Merge `[0..1]`:** Bandingkan `78 <= 6` (`false`) $\rightarrow$ `[P002(6), P001(78)]`.
2. **Merge `[2..3]`:** Bandingkan `96 <= 84` (`false`) $\rightarrow$ `[P004(84), P003(96)]`.
3. **Merge `[0..3]`:** Gabungkan `[P002(6), P001(78)]` dan `[P004(84), P003(96)]`:
    - `6 <= 84` (`true`) $\rightarrow$ `P002(6)`
    - `78 <= 84` (`true`) $\rightarrow$ `P001(78)`
    - Sisa kanan disalin $\rightarrow$ `[P002(6), P001(78), P004(84), P003(96)]`.
4. **Merge `[4..5]`:** Bandingkan `72 <= 66` (`false`) $\rightarrow$ `[P006(66), P005(72)]`.
5. **Merge `[6..7]`:** Bandingkan `72 <= 84` (`true`) $\rightarrow$ `[P007(72), P008(84)]`.
6. **Merge `[4..7]` (Pembuktian Stabilitas):** Gabungkan `L = [P006(66), P005(72)]` dan `R = [P007(72), P008(84)]`:
    - `66 <= 72` (`true`) $\rightarrow$ `P006(66)`
    - `72 <= 72` (`true`, operator `<=` memilih subarray kiri lebih dulu) $\rightarrow$ `P005(72)` tetap mendahului `P007(72)`.
    - Hasil: `[P006(66), P005(72), P007(72), P008(84)]`.
7. **Merge Akhir `[0..7]`:**
    - Hasil akhir: `[P002(6), P006(66), P005(72), P007(72), P001(78), P004(84), P008(84), P003(96)]`.
    - Seluruh urutan kembar terjaga (`P005` sebelum `P007` untuk 72 bulan; `P004` sebelum `P008` untuk 84 bulan), dan `daftarPutusan` asli tetap dimulai dari `P001` pada indeks `0`.