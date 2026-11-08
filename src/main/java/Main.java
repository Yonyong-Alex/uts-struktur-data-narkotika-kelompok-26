public class Main {
    public static void main(String[] args) {
        // 1. Inisialisasi struktur data buatan kamu (DynamicArray)
        DynamicArray<Putusan> daftarPutusan = new DynamicArray<>();

        // 2. Panggil CsvReader buatan temanmu untuk memasukkan data dari CSV
        CsvReader.loadPutusanData(daftarPutusan);

        // 3. Verifikasi apakah data berhasil masuk
        System.out.println("Total data di dalam DynamicArray: " + daftarPutusan.size());

        // 4. Coba ambil dan cetak data pertama untuk memastikan isinya valid
        if (daftarPutusan.size() > 0) {
            Putusan p1 = daftarPutusan.get(0); // Pastikan struktur datamu punya method get(index)
            System.out.println("--- Contoh Data Pertama ---");
            System.out.println("Case ID       : " + p1.getCaseId());
            System.out.println("Nomor Perkara : " + p1.getNomorPerkara());
            System.out.println("Jenis Narkotika: " + p1.getJenisNarkotika());
            System.out.println("Berat BB      : " + p1.getBeratBarangBuktiGram() + " gram");
        }
    }
}