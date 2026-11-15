public class AuditStackTest {

    private static int totalTest = 0;
    private static int testLulus = 0;
    private static int testGagal = 0;

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("MENJALANKAN UNIT TEST & BOUNDARY TEST AUDIT STACK (LIFO)");
        System.out.println("=================================================================");

        testPushPeekPopDanUrutanLifo();
        testBoundaryKosongDanNull();
        testEkspansiKapasitasGrow();
        testPencarianNonDestruktifPreserveUrutan();

        System.out.println("\n=================================================================");
        System.out.printf("RINGKASAN HASIL TEST AUDIT STACK: Total = %d | LULUS = %d | GAGAL = %d%n",
                totalTest, testLulus, testGagal);
        System.out.println("=================================================================");

        if (testGagal > 0) {
            throw new AssertionError("Terdapat " + testGagal + " pengujian AuditStack yang gagal!");
        }
    }

    private static void testPushPeekPopDanUrutanLifo() {
        System.out.println("\n[TEST 1] Operasi Dasar push(), peek(), pop(), dan Disiplin LIFO");
        AuditStack<AuditLog> stack = new AuditStack<>();
        assertTrue(stack.isEmpty(), "Stack baru harus kosong");
        assertEquals(0, stack.size(), "Size awal harus 0");
        assertTrue(stack.validateInvariant(), "Invariant awal harus true");

        AuditLog log1 = new AuditLog("TAMBAH", "P051", "101/Pid.Sus/2026/PN Plg");
        AuditLog log2 = new AuditLog("HAPUS", "P010", "1404/Pid.Sus/2025/PN Plg");
        AuditLog log3 = new AuditLog("TAMBAH", "P052", "102/Pid.Sus/2026/PN Plg");

        stack.push(log1);
        stack.push(log2);
        stack.push(log3);

        assertEquals(3, stack.size(), "Size setelah 3 push harus 3");
        assertEquals("P052", stack.peek().getCaseId(), "peek() harus mengembalikan elemen terakhir (P052)");
        assertEquals(3, stack.size(), "peek() tidak boleh mengurangi size");

        assertEquals("P052", stack.pop().getCaseId(), "pop() pertama harus P052");
        assertEquals("P010", stack.pop().getCaseId(), "pop() kedua harus P010");
        assertEquals("P051", stack.pop().getCaseId(), "pop() ketiga harus P051");
        assertTrue(stack.isEmpty(), "Stack harus kembali kosong setelah semua elemen di-pop");
        assertTrue(stack.validateInvariant(), "Invariant setelah pengosongan stack harus true");
    }

    private static void testBoundaryKosongDanNull() {
        System.out.println("\n[TEST 2] Boundary Stack Kosong & Penolakan Null");
        AuditStack<AuditLog> stack = new AuditStack<>();

        assertEquals(null, stack.peek(), "peek() pada stack kosong harus mengembalikan null");

        boolean gagalPopKosong = false;
        try {
            stack.pop();
        } catch (IllegalStateException e) {
            gagalPopKosong = true;
        }
        assertTrue(gagalPopKosong, "pop() pada stack kosong harus melempar IllegalStateException");

        boolean gagalPushNull = false;
        try {
            stack.push(null);
        } catch (IllegalArgumentException e) {
            gagalPushNull = true;
        }
        assertTrue(gagalPushNull, "push(null) harus melempar IllegalArgumentException");
        assertTrue(stack.validateInvariant(), "Invariant harus tetap true");
    }

    private static void testEkspansiKapasitasGrow() {
        System.out.println("\n[TEST 3] Ekspansi Kapasitas Dinamis grow() Melebihi DEFAULT_CAPACITY (10)");
        AuditStack<AuditLog> stack = new AuditStack<>();
        for (int i = 1; i <= 25; i++) {
            stack.push(new AuditLog("TAMBAH", "P-" + i, i + "/Pid.Sus/2026/PN Plg"));
        }

        assertEquals(25, stack.size(), "Size harus 25 setelah 25 kali push");
        assertEquals("P-25", stack.peek().getCaseId(), "Elemen puncak harus P-25");
        assertTrue(stack.validateInvariant(), "Invariant harus tetap true setelah beberapa kali grow()");
    }

    // Skenario 4.1 #6: Membuktikan pencarian pada Stack tidak mengubah urutan akhir tumpukan
    private static void testPencarianNonDestruktifPreserveUrutan() {
        System.out.println("\n[TEST 4] Pencarian Non-Destruktif pada Stack (Urutan Akhir Tetap Utuh)");
        AuditStack<AuditLog> stack = new AuditStack<>();
        stack.push(new AuditLog("TAMBAH", "P001", "1113/Pid.Sus/2025/PN Plg"));
        stack.push(new AuditLog("HAPUS", "P015", "983/Pid.Sus/2026/PN Plg"));
        stack.push(new AuditLog("TAMBAH", "P030", "438/Pid.Sus/2026/PN Plg"));
        stack.push(new AuditLog("HAPUS", "P050", "958/Pid.Sus/2026/PN Plg"));

        String urutanSebelum = stack.riwayatSebagaiTeks();
        int sizeSebelum = stack.size();

        // Cari log dengan caseId "P015" menggunakan stack sementara lalu pulihkan (restore)
        AuditStack<AuditLog> tempStack = new AuditStack<>();
        AuditLog ditemukan = null;
        while (!stack.isEmpty()) {
            AuditLog topItem = stack.pop();
            tempStack.push(topItem);
            if ("P015".equals(topItem.getCaseId())) {
                ditemukan = topItem;
                break;
            }
        }
        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }

        String urutanSesudah = stack.riwayatSebagaiTeks();

        assertTrue(ditemukan != null, "Log audit P015 harus ditemukan di tengah tumpukan");
        assertEquals("HAPUS", ditemukan.getAksi(), "Aksi log P015 harus HAPUS");
        assertEquals(sizeSebelum, stack.size(), "Ukuran stack setelah pencarian harus tetap 4");
        assertEquals(urutanSebelum, urutanSesudah, "Urutan elemen stack sebelum dan sesudah pencarian harus identik");
        assertTrue(stack.validateInvariant(), "Invariant stack setelah restore harus true");
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
}