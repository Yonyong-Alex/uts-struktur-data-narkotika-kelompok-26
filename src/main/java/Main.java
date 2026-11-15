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

        // 3. Uji Filter Tambahan 1: Rentang Berat Barang Bukti (misal: >= 5.0 gram s.d. 100.0 gram)
        System.out.println("\n=== UJI FILTER RENTANG BERAT BARANG BUKTI (5.0 - 100.0 GRAM) ===");
        DynamicArray<Putusan> filterBeratBesar = PutusanService.filterByRentangBerat(daftarPutusan, 5.0, 100.0);
        System.out.println("Jumlah perkara BB 5-100 gr    : " + filterBeratBesar.size());
        System.out.println("Invariant hasil filter berat  : " + filterBeratBesar.validateInvariant());
        for (int i = 0; i < Math.min(3, filterBeratBesar.size()); i++) {
            System.out.println("  - " + filterBeratBesar.get(i));
        }

        // 4. Uji Filter Tambahan 2: Pasal Terbukti (misal: Pasal 114)
        System.out.println("\n=== UJI FILTER PASAL TERBUKTI (PASAL 114) ===");
        DynamicArray<Putusan> filterPasal114 = PutusanService.filterByPasalTerbukti(daftarPutusan, "114");
        System.out.println("Jumlah perkara Pasal 114      : " + filterPasal114.size());
        System.out.println("Invariant hasil filter pasal  : " + filterPasal114.validateInvariant());
        for (int i = 0; i < Math.min(3, filterPasal114.size()); i++) {
            System.out.println("  - " + filterPasal114.get(i));
        }

        // 5. Uji Agregasi Statistik (termasuk Total Denda & Distribusi Jenis Narkotika)
        System.out.println("\n=== UJI AGREGASI STATISTIK LENGKAP ===");
        StatistikPutusan statSemua = PutusanService.hitungStatistik(daftarPutusan, "Semua Perkara (50)");
        StatistikPutusan statSabu = PutusanService.hitungStatistik(filterSabu, "Narkotika Sabu");
        StatistikPutusan statEkstasi = PutusanService.hitungStatistik(
                PutusanService.filterByJenisNarkotika(daftarPutusan, "Ekstasi"), "Narkotika Ekstasi");
        StatistikPutusan statGanja = PutusanService.hitungStatistik(
                PutusanService.filterByJenisNarkotika(daftarPutusan, "Ganja"), "Narkotika Ganja");
        StatistikPutusan statPasal114 = PutusanService.hitungStatistik(filterPasal114, "Terbukti Pasal 114");

        System.out.println(statSemua);
        System.out.println(statSabu);
        System.out.println(statEkstasi);
        System.out.println(statGanja);
        System.out.println(statPasal114);

        // 6. Uji Ekspor Hasil ke CSV & TXT
        System.out.println("\n=== UJI EKSPOR HASIL FILTER & AGREGASI ===");
        ReportExporter.exportToCsv(filterSabu, "output/hasil_filter_sabu.csv");
        ReportExporter.exportLaporanToTxt(filterVonisRingan,
                PutusanService.hitungStatistik(filterVonisRingan, "Vonis 1-24 Bulan"),
                "output/laporan_vonis_ringan.txt");
        ReportExporter.exportLaporanToTxt(daftarPutusan, statSemua, "output/laporan_agregasi_keseluruhan.txt");
        ReportExporter.exportLaporanToTxt(filterPasal114, statPasal114, "output/laporan_pasal_114.txt");

        // =====================================================================
        // PENGUJIAN TAHAP 4: REKAM JEJAK AUDIT (STACK) & PENCARIAN NON-DESTRUKTIF
        // =====================================================================
        System.out.println("\n=== UJI AUDIT STACK & PENCARIAN NON-DESTRUKTIF ===");
        AuditStack<AuditLog> auditStack = new AuditStack<>();

        // Simulasi operasi TAMBAH menggunakan Putusan baru
        Putusan putusanSimulasi = new Putusan(
                "P051",
                "999/Pid.Sus/2026/PN Mlg",
                "PN Malang",
                "2026-10-15",
                "TDW-051",
                "Sabu",
                2.50,
                "Pasal 114 ayat (1) UU Narkotika",
                "Pasal 112 ayat (1) UU Narkotika",
                60,
                48,
                800000000L,
                "1 klip plastik narkotika jenis sabu berat netto 2,50 gram",
                "Perbuatan terdakwa bertentangan dengan program pemerintah",
                "Terdakwa belum pernah dihukum dan bersikap sopan",
                "hal. 1-18"
        );

        PutusanService.tambahPutusanDenganAudit(daftarPutusan, auditStack, putusanSimulasi);
        System.out.println("Setelah TAMBAH P051 -> Ukuran data: " + daftarPutusan.size()
                + ", Ukuran stack: " + auditStack.size());

        // Simulasi operasi HAPUS pada record terakhir (indeks ke-50 / P051) agar dataset kembali 50
        Putusan terhapus = PutusanService.hapusPutusanDenganAudit(daftarPutusan, auditStack, daftarPutusan.size() - 1);
        System.out.println("Setelah HAPUS " + (terhapus != null ? terhapus.getCaseId() : "null")
                + " -> Ukuran data: " + daftarPutusan.size() + ", Ukuran stack: " + auditStack.size());

        // Uji Pencarian Non-Destruktif di dalam AuditStack
        AuditLog topSebelumCari = auditStack.peek();
        int ukuranSebelumCari = auditStack.size();
        DynamicArray<AuditLog> hasilCariAudit = auditStack.cariRiwayatByKeyword("P051");

        System.out.println("Hasil cari non-destruktif 'P051' : " + hasilCariAudit.size() + " log ditemukan");
        for (int i = 0; i < hasilCariAudit.size(); i++) {
            System.out.println("  -> " + hasilCariAudit.get(i));
        }
        System.out.println("Verifikasi Stack setelah cari    : Ukuran tetap " + auditStack.size()
                + " (sebelum: " + ukuranSebelumCari + "), Top tetap: "
                + (auditStack.peek() != null && auditStack.peek().equals(topSebelumCari)));
        System.out.println("Invariant AuditStack             : " + auditStack.validateInvariant());
        auditStack.tampilkanRiwayat();

        // =====================================================================
        // SKENARIO #5: ANALISIS KOMPARATIF REHABILITASI (PASAL 127) VS PENGEDAR (PASAL 114)
        // =====================================================================
        System.out.println("\n=== SKENARIO #5: KOMPARASI PASAL 127 (PENYALAH GUNA) VS PASAL 114 (PENGEDAR) ===");
        DynamicArray<Putusan> perkaraPasal127 = PutusanService.filterByPasalTerbukti(daftarPutusan, "127");
        StatistikPutusan statPasal127 = PutusanService.hitungStatistik(perkaraPasal127, "Terbukti Pasal 127");

        System.out.println(statPasal127);
        System.out.println(statPasal114);
        ReportExporter.exportLaporanToTxt(perkaraPasal127, statPasal127, "output/skenario5_pasal_127.txt");

        // Pastikan struktur data utama tetap utuh dan invariant valid
        System.out.println("\n=== VERIFIKASI AKHIR STRUKTUR UTAMA ===");
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