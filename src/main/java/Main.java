public class Main {
    public static void main(String[] args) {
        DynamicArray<Putusan> daftarPutusan = new DynamicArray<>();
        CsvReader.loadPutusanData(daftarPutusan);

        System.out.println("Total data dimuat : " + daftarPutusan.size());
        System.out.println("Invariant awal    : " + daftarPutusan.validateInvariant());

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
        System.out.println("\n=== VERIFIKASI INVARIANT STRUKTUR UTAMA ===");
        System.out.println("Index 0 tetap P001 : " + daftarPutusan.get(0).getCaseId());
        System.out.println("Invariant akhir    : " + daftarPutusan.validateInvariant());
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