import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DynamicArrayBoundaryTest {

    private static int totalTest = 0;
    private static int testLulus = 0;
    private static int testGagal = 0;

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("MENJALANKAN UNIT TEST & BOUNDARY TEST DYNAMIC ARRAY");
        System.out.println("=================================================================");

        testMuat50DataDanInvariantAwal();
        testBoundaryAksesGetDanSet();
        testBoundaryHapusDataKe51DanIndeksInvalid();
        testHapusAwalTengahAkhirDanPergeseran();
        testBoundaryInsertDanTambahKembali3Record();
        testBoundaryPencarianDanPengurutanNonDestruktif();
        testTolakNull();
        testCariInputKosong();
        testBoundaryKapasitasGrow();
        testTolakRecordCsvTidakValid();

        System.out.println("\n=================================================================");
        System.out.printf("RINGKASAN HASIL TEST: Total = %d | LULUS = %d | GAGAL = %d%n",
                totalTest, testLulus, testGagal);
        System.out.println("=================================================================");

        if (testGagal > 0) {
            throw new AssertionError("Terdapat " + testGagal + " pengujian yang gagal!");
        }
    }

    // Skenario 4.1 #1: Muat 50 record dan buktikan size bernilai 50
    private static void testMuat50DataDanInvariantAwal() {
        System.out.println("\n[TEST 1] Muat 50 Record CSV & Validasi Invariant Awal");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        assertEquals(50, daftar.size(), "Size harus tepat 50 setelah memuat putusan-50.csv");
        assertTrue(!daftar.isEmpty(), "DynamicArray tidak boleh kosong");
        assertEquals("Pengadilan Negeri Palembang", daftar.get(0).getPengadilan(),
                "Pengadilan data pertama harus Pengadilan Negeri Palembang");
        assertTrue(daftar.validateInvariant(), "Invariant awal harus bernilai true");
    }

    // Uji Boundary get() dan set() pada indeks batas bawah, atas, negatif, dan ke-51
    private static void testBoundaryAksesGetDanSet() {
        System.out.println("\n[TEST 2] Boundary Akses get() & set() (Indeks Negatif & Data ke-51)");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        // Batas valid: indeks 0 (data ke-1) dan indeks 49 (data ke-50)
        assertEquals("P001", daftar.get(0).getCaseId(), "Indeks 0 harus P001");
        assertEquals("P050", daftar.get(49).getCaseId(), "Indeks 49 harus P050");

        // Boundary invalid get(): indeks negatif (-1)
        boolean lemparGetNegatif = false;
        try {
            daftar.get(-1);
        } catch (IndexOutOfBoundsException e) {
            lemparGetNegatif = true;
        }
        assertTrue(lemparGetNegatif, "get(-1) harus melempar IndexOutOfBoundsException");

        // Boundary invalid get(): data ke-51 (indeks 50 saat size = 50)
        boolean lemparGetKe51 = false;
        try {
            daftar.get(50);
        } catch (IndexOutOfBoundsException e) {
            lemparGetKe51 = true;
        }
        assertTrue(lemparGetKe51, "get(50) [data ke-51] harus melempar IndexOutOfBoundsException");

        // Boundary invalid set(): indeks negatif (-1)
        boolean lemparSetNegatif = false;
        try {
            daftar.set(-1, daftar.get(0));
        } catch (IndexOutOfBoundsException e) {
            lemparSetNegatif = true;
        }
        assertTrue(lemparSetNegatif, "set(-1, val) harus melempar IndexOutOfBoundsException");

        // Boundary invalid set(): data ke-51 (indeks 50)
        boolean lemparSetKe51 = false;
        try {
            daftar.set(50, daftar.get(0));
        } catch (IndexOutOfBoundsException e) {
            lemparSetKe51 = true;
        }
        assertTrue(lemparSetKe51, "set(50, val) [data ke-51] harus melempar IndexOutOfBoundsException");
        assertTrue(daftar.validateInvariant(), "Invariant harus tetap true setelah percobaan akses invalid");
    }

    // Uji Boundary remove(): menghapus data ke-51, indeks negatif, dan saat kosong
    private static void testBoundaryHapusDataKe51DanIndeksInvalid() {
        System.out.println("\n[TEST 3] Boundary Penghapusan Data ke-51, Indeks Negatif, & Array Kosong");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        // Kasus utama: mencoba menghapus data ke-51 (indeks 50) ketika total data hanya 50
        boolean gagalHapusKe51 = false;
        try {
            daftar.remove(50);
        } catch (IndexOutOfBoundsException e) {
            gagalHapusKe51 = true;
            System.out.println("  -> Exception tertangkap sesuai harapan: " + e.getMessage());
        }
        assertTrue(gagalHapusKe51, "remove(50) [data ke-51] harus ditolak dengan IndexOutOfBoundsException");
        assertEquals(50, daftar.size(), "Size harus tetap 50 setelah gagal menghapus data ke-51");
        assertTrue(daftar.validateInvariant(), "Invariant harus tetap true");

        // Kasus indeks negatif
        boolean gagalHapusNegatif = false;
        try {
            daftar.remove(-1);
        } catch (IndexOutOfBoundsException e) {
            gagalHapusNegatif = true;
        }
        assertTrue(gagalHapusNegatif, "remove(-1) harus melempar IndexOutOfBoundsException");

        // Kasus menghapus pada DynamicArray kosong
        DynamicArray<Putusan> arrayKosong = new DynamicArray<>();
        boolean gagalHapusKosong = false;
        try {
            arrayKosong.remove(0);
        } catch (IndexOutOfBoundsException e) {
            gagalHapusKosong = true;
        }
        assertTrue(gagalHapusKosong, "remove(0) pada array kosong harus melempar IndexOutOfBoundsException");
        assertTrue(arrayKosong.validateInvariant(), "Invariant array kosong harus true");
    }

    // Skenario 4.1 #3: Hapus elemen pertama, tengah, dan terakhir + verifikasi pergeseran
    private static void testHapusAwalTengahAkhirDanPergeseran() {
        System.out.println("\n[TEST 4] Hapus Elemen Pertama, Tengah, dan Terakhir (Verifikasi Shifting)");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        // 1. Hapus elemen pertama (indeks 0 -> P001)
        Putusan hapusAwal = daftar.remove(0);
        assertEquals("P001", hapusAwal.getCaseId(), "Elemen yang dihapus di indeks 0 harus P001");
        assertEquals("P002", daftar.get(0).getCaseId(), "Setelah remove(0), indeks 0 harus bergeser menjadi P002");
        assertEquals(49, daftar.size(), "Size setelah hapus pertama harus 49");
        assertTrue(daftar.validateInvariant(), "Invariant setelah hapus pertama harus true");

        // 2. Hapus elemen tengah (indeks 24 -> P026 karena sebelumnya P001 sudah terhapus)
        String idSetelahTengah = daftar.get(25).getCaseId();
        Putusan hapusTengah = daftar.remove(24);
        assertEquals("P026", hapusTengah.getCaseId(), "Elemen tengah indeks 24 harus P026");
        assertEquals(idSetelahTengah, daftar.get(24).getCaseId(), "Elemen indeks 25 harus bergeser ke indeks 24");
        assertEquals(48, daftar.size(), "Size setelah hapus tengah harus 48");
        assertTrue(daftar.validateInvariant(), "Invariant setelah hapus tengah harus true");

        // 3. Hapus elemen terakhir (indeks 47 -> P050)
        Putusan hapusAkhir = daftar.remove(daftar.size() - 1);
        assertEquals("P050", hapusAkhir.getCaseId(), "Elemen terakhir yang dihapus harus P050");
        assertEquals("P049", daftar.get(daftar.size() - 1).getCaseId(), "Elemen terakhir baru harus P049");
        assertEquals(47, daftar.size(), "Size setelah hapus terakhir harus 47");
        assertTrue(daftar.validateInvariant(), "Invariant setelah hapus terakhir harus true");
    }

    // Skenario 4.1 #4: Tambahkan kembali 3 record dan buktikan invariant serta urutan data
    private static void testBoundaryInsertDanTambahKembali3Record() {
        System.out.println("\n[TEST 5] Boundary insert() & Penambahan Kembali 3 Record");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        // Hapus 3 elemen sehingga size = 47
        daftar.remove(0);
        daftar.remove(24);
        daftar.remove(daftar.size() - 1);

        Putusan dummy1 = buatDummyPutusan("P-NEW-1", "01/Pid.Sus/2026/PN Plg", 36);
        Putusan dummy2 = buatDummyPutusan("P-NEW-2", "02/Pid.Sus/2026/PN Plg", 48);
        Putusan dummy3 = buatDummyPutusan("P-NEW-3", "03/Pid.Sus/2026/PN Plg", 60);

        // Boundary invalid pada insert: indeks negatif (-1)
        boolean gagalInsertNegatif = false;
        try {
            daftar.insert(-1, dummy1);
        } catch (IndexOutOfBoundsException e) {
            gagalInsertNegatif = true;
        }
        assertTrue(gagalInsertNegatif, "insert(-1, val) harus melempar IndexOutOfBoundsException");

        // Boundary invalid pada insert: indeks > size
        boolean gagalInsertLewatBatas = false;
        try {
            daftar.insert(daftar.size() + 1, dummy1);
        } catch (IndexOutOfBoundsException e) {
            gagalInsertLewatBatas = true;
        }
        assertTrue(gagalInsertLewatBatas, "insert(size + 1, val) harus melempar IndexOutOfBoundsException");

        // Sisipkan kembali 3 record: di awal (0), di tengah (24), dan di akhir (add)
        daftar.insert(0, dummy1);
        daftar.insert(24, dummy2);
        daftar.add(dummy3);

        assertEquals(50, daftar.size(), "Size harus kembali menjadi 50 setelah menambah 3 record");
        assertEquals("P-NEW-1", daftar.get(0).getCaseId(), "Record baru pertama harus berada di indeks 0");
        assertEquals("P-NEW-2", daftar.get(24).getCaseId(), "Record baru kedua harus berada di indeks 24");
        assertEquals("P-NEW-3", daftar.get(49).getCaseId(), "Record baru ketiga harus berada di indeks 49");
        assertTrue(daftar.validateInvariant(), "Invariant harus tetap true setelah insert dan add");
    }

    // Skenario 4.1 #2 & Pengurutan Non-Destruktif
    private static void testBoundaryPencarianDanPengurutanNonDestruktif() {
        System.out.println("\n[TEST 6] Boundary Pencarian (CaseId & Nomor Perkara) & Pengurutan Non-Destruktif");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        // 1. Cari berdasarkan caseId (batas awal, batas akhir, tidak ada)
        SearchResult awalId = PutusanService.cariByCaseId(daftar, "P001");
        assertTrue(awalId.isFound(), "P001 harus ditemukan");
        assertEquals(1, awalId.getJumlahLangkah(), "P001 di posisi batas awal harus ditemukan dalam 1 langkah");

        SearchResult akhirId = PutusanService.cariByCaseId(daftar, "P050");
        assertTrue(akhirId.isFound(), "P050 harus ditemukan");
        assertEquals(50, akhirId.getJumlahLangkah(), "P050 di posisi batas akhir harus ditemukan dalam 50 langkah");

        SearchResult tidakAdaId = PutusanService.cariByCaseId(daftar, "P999");
        assertTrue(!tidakAdaId.isFound(), "P999 tidak boleh ditemukan");
        assertEquals(50, tidakAdaId.getJumlahLangkah(), "Pencarian P999 harus memeriksa seluruh 50 elemen");

        // 2. Cari berdasarkan nomorPerkara PN Plg (batas awal, batas akhir, tidak ada)
        SearchResult awalNomor = PutusanService.cariByNomorPerkara(daftar, "1113/Pid.Sus/2025/PN Plg");
        assertTrue(awalNomor.isFound(), "Nomor perkara 1113/Pid.Sus/2025/PN Plg harus ditemukan");
        assertEquals(1, awalNomor.getJumlahLangkah(), "Nomor perkara batas awal harus ditemukan dalam 1 langkah");

        SearchResult akhirNomor = PutusanService.cariByNomorPerkara(daftar, "958/Pid.Sus/2026/PN Plg");
        assertTrue(akhirNomor.isFound(), "Nomor perkara 958/Pid.Sus/2026/PN Plg harus ditemukan");
        assertEquals(50, akhirNomor.getJumlahLangkah(), "Nomor perkara batas akhir harus ditemukan dalam 50 langkah");

        SearchResult tidakAdaNomor = PutusanService.cariByNomorPerkara(daftar, "9999/Pid.Sus/2026/PN Plg");
        assertTrue(!tidakAdaNomor.isFound(), "Nomor perkara fiktif tidak boleh ditemukan");
        assertEquals(50, tidakAdaNomor.getJumlahLangkah(), "Pencarian nomor perkara tidak ada harus memeriksa 50 elemen");

        // 3. Uji Pengurutan Ascending & Descending tanpa merusak struktur asli
        DynamicArray<Putusan> urutAsc = PutusanService.urutkanBerdasarkanVonis(daftar, true);
        assertEquals(50, urutAsc.size(), "Hasil pengurutan ascending harus tetap berisi 50 elemen");
        assertTrue(urutAsc.validateInvariant(), "Invariant hasil pengurutan ascending harus true");

        boolean terurutNaik = true;
        for (int i = 0; i < urutAsc.size() - 1; i++) {
            if (urutAsc.get(i).getVonisBulan() > urutAsc.get(i + 1).getVonisBulan()) {
                terurutNaik = false;
                break;
            }
        }
        assertTrue(terurutNaik, "Seluruh elemen urutAsc harus terurut vonisBulan dari kecil ke besar");

        DynamicArray<Putusan> urutDesc = PutusanService.urutkanBerdasarkanVonis(daftar, false);
        boolean terurutTurun = true;
        for (int i = 0; i < urutDesc.size() - 1; i++) {
            if (urutDesc.get(i).getVonisBulan() < urutDesc.get(i + 1).getVonisBulan()) {
                terurutTurun = false;
                break;
            }
        }
        assertTrue(terurutTurun, "Seluruh elemen urutDesc harus terurut vonisBulan dari besar ke kecil");

        assertEquals("P001", daftar.get(0).getCaseId(), "Struktur utama tidak boleh berubah urutannya");
        assertTrue(daftar.validateInvariant(), "Invariant struktur utama harus tetap true");
    }

    private static Putusan buatDummyPutusan(String caseId, String nomorPerkara, int vonisBulan) {
        return new Putusan(caseId, nomorPerkara, "Pengadilan Negeri Palembang", "2026-11-15",
                "TDK-TEST", "Sabu", 1.5, "Pasal 112 ayat (1)", "Pasal 112 ayat (1)",
                vonisBulan + 12, vonisBulan, 800000000L, "1 klip sabu",
                "Meresahkan masyarakat", "Mengakui perbuatan", "1,2,3");
    }

    private static void assertEquals(Object expected, Object actual, String pesan) {
        totalTest++;
        boolean sama = (expected == null) ? (actual == null) : expected.equals(actual);
        if (sama) {
            testLulus++;
            System.out.println("  [PASS] " + pesan);
        } else {
            testGagal++;
            System.err.println("  [FAIL] " + pesan + " | Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(boolean kondisi, String pesan) {
        totalTest++;
        if (kondisi) {
            testLulus++;
            System.out.println("  [PASS] " + pesan);
        } else {
            testGagal++;
            System.err.println("  [FAIL] " + pesan);
        }
    }

    // Skenario: penolakan input null (R28, R43)
    private static void testTolakNull() {
        System.out.println("\n[TEST 7] Penolakan Input Null pada add, set, insert");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        boolean addNull = false;
        try { daftar.add(null); } catch (IllegalArgumentException e) { addNull = true; }
        assertTrue(addNull, "add(null) harus melempar IllegalArgumentException");

        boolean setNull = false;
        try { daftar.set(0, null); } catch (IllegalArgumentException e) { setNull = true; }
        assertTrue(setNull, "set(0, null) harus melempar IllegalArgumentException");

        boolean insertNull = false;
        try { daftar.insert(0, null); } catch (IllegalArgumentException e) { insertNull = true; }
        assertTrue(insertNull, "insert(0, null) harus melempar IllegalArgumentException");

        assertEquals(50, daftar.size(), "Size tidak boleh berubah setelah input null ditolak");
        assertTrue(daftar.validateInvariant(), "Invariant tetap true");
    }

    // Skenario: pencarian dengan input null atau kosong (R17)
    private static void testCariInputKosong() {
        System.out.println("\n[TEST 8] Pencarian dengan Input Null atau Kosong");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        CsvReader.loadPutusanData(daftar);

        SearchResult r1 = PutusanService.cariByCaseId(daftar, null);
        assertTrue(!r1.isFound(), "caseId null tidak boleh ditemukan");
        assertEquals(0, r1.getJumlahLangkah(), "caseId null harus 0 langkah");

        SearchResult r2 = PutusanService.cariByNomorPerkara(daftar, "   ");
        assertTrue(!r2.isFound(), "nomorPerkara kosong tidak boleh ditemukan");
        assertEquals(0, r2.getJumlahLangkah(), "nomorPerkara kosong harus 0 langkah");
    }

    // Skenario: boundary grow() pada size 10, 20, dan 40 (R16)
    private static void testBoundaryKapasitasGrow() {
        System.out.println("\n[TEST 9] Boundary grow() pada size 10, 20, 40");
        DynamicArray<Putusan> daftar = new DynamicArray<>();
        int n = 0;
        int[] titikPenuh = {10, 20, 40};
        for (int target : titikPenuh) {
            while (n < target) {
                n++;
                daftar.add(buatDummyPutusan("K-" + n, "K/" + n + "/Pid.Sus/2026/PN Plg", 12));
            }
            assertEquals(target, daftar.size(), "Size harus " + target + " (array tepat penuh)");
            assertTrue(daftar.validateInvariant(), "Invariant true saat tepat penuh di " + target);

            n++;
            daftar.add(buatDummyPutusan("K-" + n, "K/" + n + "/Pid.Sus/2026/PN Plg", 12));
            assertEquals(target + 1, daftar.size(), "Size harus " + (target + 1) + " setelah grow()");
            assertTrue(daftar.validateInvariant(), "Invariant true setelah grow() dari " + target);
            assertEquals("K-" + target, daftar.get(target - 1).getCaseId(),
                    "Elemen sebelum grow() harus tetap utuh");
        }
    }

    // Skenario 4.1 #7: Memasukkan record CSV tidak valid, menampilkan error, dan menjaga konsistensi invariant
    private static void testTolakRecordCsvTidakValid() {
        System.out.println("\n[TEST 10] Penolakan Record CSV Tidak Valid & Konsistensi Invariant");
        File tempCsv = new File("temp_invalid_putusan_test.csv");
        try (FileWriter writer = new FileWriter(tempCsv)) {
            writer.write("caseId,nomorPerkara,pengadilan,tanggalPutusan,terdakwaKode,jenisNarkotika,"
                    + "beratBarangBuktiGram,pasalDakwaan,pasalTerbukti,tuntutanBulan,vonisBulan,"
                    + "dendaRupiah,barangBukti,keadaanMemberatkan,keadaanMeringankan,sourcePages\n");
            // Baris valid 1
            writer.write("P-VAL-1,10/Pid.Sus/2026/PN Plg,PN Palembang,2026-10-01,TDK-1,Sabu,1.250,"
                    + "Pasal 112 (1),Pasal 112 (1),60,48,800000000,1 klip sabu,Memberatkan,Meringankan,1-3\n");
            // Baris invalid 1: vonisBulan bukan angka ("BUKAN_ANGKA")
            writer.write("P-ERR-1,11/Pid.Sus/2026/PN Plg,PN Palembang,2026-10-02,TDK-2,Sabu,2.000,"
                    + "Pasal 112 (1),Pasal 112 (1),60,BUKAN_ANGKA,800000000,1 klip sabu,Memberatkan,Meringankan,1-3\n");
            // Baris invalid 2: kolom kurang dari 16
            writer.write("P-ERR-2,12/Pid.Sus/2026/PN Plg,PN Palembang,kolom_kurang\n");
            // Baris invalid 3: berat barang bukti negatif (-3.5)
            writer.write("P-ERR-3,13/Pid.Sus/2026/PN Plg,PN Palembang,2026-10-03,TDK-3,Ganja,-3.500,"
                    + "Pasal 111 (1),Pasal 111 (1),60,48,800000000,1 paket ganja,Memberatkan,Meringankan,1-3\n");
            // Baris valid 2 (program harus tetap lanjut membaca baris ini setelah menolak 3 baris rusak)
            writer.write("P-VAL-2,14/Pid.Sus/2026/PN Plg,PN Palembang,2026-10-04,TDK-4,Ekstasi,3.100,"
                    + "Pasal 114 (1),Pasal 114 (1),84,72,1000000000,5 butir ekstasi,Memberatkan,Meringankan,1-4\n");
        } catch (IOException e) {
            throw new RuntimeException("Gagal membuat file CSV sementara untuk pengujian: " + e.getMessage());
        }

        try {
            DynamicArray<Putusan> daftar = new DynamicArray<>();
            CsvReader.loadPutusanData(daftar, tempCsv.getPath());

            assertEquals(2, daftar.size(), "Hanya 2 record valid yang boleh masuk (3 record tidak valid ditolak)");
            assertEquals("P-VAL-1", daftar.get(0).getCaseId(), "Record pertama harus P-VAL-1");
            assertEquals("P-VAL-2", daftar.get(1).getCaseId(), "Record kedua harus P-VAL-2 setelah melewati record rusak");
            assertTrue(daftar.validateInvariant(), "Invariant harus tetap true setelah menolak record tidak valid");
        } finally {
            if (tempCsv.exists()) {
                tempCsv.delete();
            }
        }
    }
}