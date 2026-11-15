# Analisis Data Putusan Perkara Narkotika Menggunakan Struktur Data Kustom (Java)

Repositori ini berisi proyek Ujian Tengah Semester (UTS) Struktur Data **Kelompok 26** yang mengimplementasikan struktur data kustom dari nol (*from scratch*) tanpa menggunakan Java Collection Framework (`ArrayList`, `LinkedList`, `Stack`, `HashMap`, dsb.) untuk mengolah, mencari, mengurutkan, menyaring, menghitung agregasi, dan mencatat riwayat audit 50 data putusan pengadilan perkara narkotika[cite: 1].

## Anggota Kelompok & Pembagian Tugas
* **Excel (Yonyong-Alex):** Perancangan interface `StrukturData<T>`, implementasi struktur data utama `DynamicArray<T>`, parser `CsvReader`, pengujian *boundary*, dan penyusunan Architecture Decision Record (`docs/adr/`)[cite: 1].
* **Fahir (alimfakhir):** Verifikasi *data provenance* (`docs/data-provenance.csv`), pengembangan fitur Pencarian (*Linear Search*), Pengurutan (*Merge Sort*), Filter, Agregasi Statistik, Ekspor laporan (`.csv` & `.txt`), struktur data sekunder generik `AuditStack<T>` (termasuk pencarian non-destruktif), serta dokumentasi proyek (`README.md` & `AI_USAGE.md`)[cite: 1].

---

## Arsitektur & Struktur Folder

```text
uts-struktur-data-narkotika-kelompok-26/
├── data/
│   └── putusan-50.csv          # Dataset utama 50 putusan perkara narkotika
├── docs/
│   ├── adr/                    # Rekam jejak keputusan arsitektur (ADR)
│   └── data-provenance.csv     # Log verifikasi sumber data dari dokumen PDF putusan
├── output/                     # Direktori hasil ekspor laporan (.csv dan .txt)
├── src/
│   ├── StrukturData.java       # Interface ADT struktur data utama
│   ├── DynamicArray.java       # Implementasi struktur data utama (Array Dinamis)
│   ├── AuditLog.java           # Model catatan riwayat perubahan data
│   ├── AuditStack.java         # Implementasi struktur data sekunder generik (Stack LIFO)
│   ├── Putusan.java            # Model entitas putusan perkara narkotika
│   ├── SearchResult.java       # Model hasil pencarian beserta jumlah langkah
│   ├── StatistikPutusan.java   # Model hasil perhitungan agregasi statistik
│   ├── CsvReader.java          # Pembaca dan parser dataset putusan-50.csv
│   ├── PutusanService.java     # Layanan algoritma (Search, Sort, Filter, Agregasi, Audit)
│   ├── ReportExporter.java     # Modul ekspor hasil ke format .csv dan .txt
│   └── Main.java               # Titik masuk program & simulasi pengujian seluruh fitur
├── AI_USAGE.md                 # Log penggunaan alat bantu AI selama pengembangan
└── README.md                   # Dokumentasi utama proyek