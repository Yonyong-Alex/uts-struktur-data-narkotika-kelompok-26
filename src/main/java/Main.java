public class Main {
    public static void main(String[] args) {
        DynamicArray<Putusan> daftarPutusan = new DynamicArray<>();
        CsvReader.loadPutusanData(daftarPutusan);

        System.out.println("Total data dimuat : " + daftarPutusan.size());
        System.out.println("Invariant awal    : " + daftarPutusan.validateInvariant());

        // =====================================================================
        // PENGUJIAN TAHAP 2: PENCARIAN & PENGURUTAN
        // =====================================================================

        // a) Record pada posisi batas awal (P001 - indeks 0)
        System.out.println("\n=== UJI CARI POSISI BATAS AWAL (P001) ===");
        SearchResult resAwal = PutusanService.cariByCaseId(daftarPutusan, "P001");
        cetakHasilCari(resAwal);

        // b) Record yang ada di tengah (Nomor Perkara)
        System.out.println("\n=== UJI CARI PERKARA ADA (1412/Pid.Sus/2025/PN Plg) ===");
        SearchResult resTengah = PutusanService.cariByNomorPerkara(daftarPutusan, "1412/Pid.Sus/2025/PN Plg");
        cetakHasilCari(resTengah);

        // c) Record pada posisi batas akhir (P050 - indeks 49)
        System.out.println("\n=== UJI CARI POSISI BATAS AKHIR (P050) ===");
        SearchResult resAkhir = PutusanService.cariByCaseId(daftarPutusan, "P050");
        cetakHasilCari(resAkhir);

        // d) Record yang tidak ada (P999)
        System.out.println("\n=== UJI CARI PERKARA TIDAK ADA (P999) ===");
        SearchResult resTidakAda = PutusanService.cariByCaseId(daftarPutusan, "P999");
        cetakHasilCari(resTidakAda);

        // Uji Urutan Vonis (Ascending & Descending)
        System.out.println("\n=== UJI URUTAN VONIS TERPENDEK -> TERLAMA (TOP 5) ===");
        DynamicArray<Putusan> urutAsc = PutusanService.urutkanBerdasarkanVonis(daftarPutusan, true);
        for (int i = 0; i < Math.min(5, urutAsc.size()); i++) {
            System.out.println((i + 1) + ". " + urutAsc.get(i));
        }
        System.out.println("Invariant hasil urut : " + urutAsc.validateInvariant());

        System.out.println("\n=== UJI URUTAN VONIS TERLAMA -> TERPENDEK (TOP 5) ===");
        DynamicArray<Putusan> urutDesc = PutusanService.urutkanBerdasarkanVonis(daftarPutusan, false);
        for (int i = 0; i < Math.min(5, urutDesc.size()); i++) {
            System.out.println((i + 1) + ". " + urutDesc.get(i));
        }

        // Pastikan struktur utama tidak rusak setelah pengurutan
        System.out.println("\n=== VERIFIKASI INVARIANT STRUKTUR UTAMA (TAHAP 2) ===");
        System.out.println("Index 0 tetap P001 : " + daftarPutusan.get(0).getCaseId());
        System.out.println("Invariant tahap 2  : " + daftarPutusan.validateInvariant());

        // =====================================================================
        // PENGUJIAN TAHAP 3: FILTER, AGREGASI & EKSPOR
        // =====================================================================

        // 1. Uji Filter berdasarkan Jenis Narkotika
        System.out.println("\n=== UJI FILTER JENIS NARKOTIKA: SABU ===");
        DynamicArray<Putusan> filterSabu = PutusanService.filterByJenisNarkotika(daftarPutusan, "Sabu");
        System.out.println("Jumlah perkara Sabu ditemukan : " + filterSabu.size());
        System.out.println("Invariant hasil filter Sabu   : " + filterSabu.validateInvariant());
        for (int i = 0; i < Math.min(3, filterSabu.size()); i++) {
            System.out.println("  - " + filterSabu.get(i));
        }

        // 2. Uji Filter berdasarkan Rentang Vonis (misal: vonis ringan 1 s.d. 24 bulan)
        System.out.println("\n=== UJI FILTER RENTANG VONIS (1 - 24 BULAN) ===");
        DynamicArray<Putusan> filterVonisRingan = PutusanService.filterByRentangVonis(daftarPutusan, 1, 24);
        System.out.println("Jumlah perkara vonis 1-24 bln : " + filterVonisRingan.size());
        for (int i = 0; i < filterVonisRingan.size(); i++) {
            System.out.println("  - " + filterVonisRingan.get(i));
        }

        // 3. Uji Agregasi Statistik
        System.out.println("\n=== UJI AGREGASI STATISTIK ===");
        StatistikPutusan statSemua = PutusanService.hitungStatistik(daftarPutusan, "Semua Perkara (50)");
        StatistikPutusan statSabu = PutusanService.hitungStatistik(filterSabu, "Narkotika Sabu");
        StatistikPutusan statEkstasi = PutusanService.hitungStatistik(
                PutusanService.filterByJenisNarkotika(daftarPutusan, "Ekstasi"), "Narkotika Ekstasi");
        StatistikPutusan statGanja = PutusanService.hitungStatistik(
                PutusanService.filterByJenisNarkotika(daftarPutusan, "Ganja"), "Narkotika Ganja");

        System.out.println(statSemua);
        System.out.println(statSabu);
        System.out.println(statEkstasi);
        System.out.println(statGanja);

        // 4. Uji Ekspor Hasil ke CSV & TXT
        System.out.println("\n=== UJI EKSPOR HASIL FILTER & AGREGASI ===");
        ReportExporter.exportToCsv(filterSabu, "output/hasil_filter_sabu.csv");
        ReportExporter.exportLaporanToTxt(filterVonisRingan,
                PutusanService.hitungStatistik(filterVonisRingan, "Vonis 1-24 Bulan"),
                "output/laporan_vonis_ringan.txt");
        ReportExporter.exportLaporanToTxt(daftarPutusan, statSemua, "output/laporan_agregasi_keseluruhan.txt");

        // Pastikan struktur data utama tetap utuh dan invariant valid
        System.out.println("\n=== VERIFIKASI AKHIR STRUKTUR UTAMA (TAHAP 3) ===");
        System.out.println("Ukuran akhir daftarPutusan : " + daftarPutusan.size());
        System.out.println("Invariant akhir            : " + daftarPutusan.validateInvariant());
    }

    private static void cetakHasilCari(SearchResult result) {
        if (result.isFound()) {
            System.out.println("Status         : DITEMUKAN dalam " + result.getJumlahLangkah() + " langkah");
            System.out.println("Detail Putusan : " + result.getPutusan());
        } else {
            System.out.println("Status         : TIDAK DITEMUKAN (diperiksa " + result.getJumlahLangkah() + " langkah)");
        }
    }
}