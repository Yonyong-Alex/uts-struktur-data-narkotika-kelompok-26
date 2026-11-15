public class PutusanService {

    // =========================================================================
    // TAHAP 2: FITUR PENCARIAN & PENGURUTAN (KODE LAMA TETAP DIPERTAHANKAN)
    // =========================================================================

    public static SearchResult cariByCaseId(StrukturData<Putusan> daftarPutusan, String caseId) {
        int langkah = 0;
        if (daftarPutusan == null || caseId == null || caseId.trim().isEmpty()) {
            return new SearchResult(null, langkah);
        }

        String target = caseId.trim();
        for (int i = 0; i < daftarPutusan.size(); i++) {
            langkah++;
            Putusan current = daftarPutusan.get(i);
            if (current != null && current.getCaseId().equalsIgnoreCase(target)) {
                return new SearchResult(current, langkah);
            }
        }
        return new SearchResult(null, langkah);
    }

    public static SearchResult cariByNomorPerkara(StrukturData<Putusan> daftarPutusan, String nomorPerkara) {
        int langkah = 0;
        if (daftarPutusan == null || nomorPerkara == null || nomorPerkara.trim().isEmpty()) {
            return new SearchResult(null, langkah);
        }

        String target = nomorPerkara.trim();
        for (int i = 0; i < daftarPutusan.size(); i++) {
            langkah++;
            Putusan current = daftarPutusan.get(i);
            if (current != null && current.getNomorPerkara().equalsIgnoreCase(target)) {
                return new SearchResult(current, langkah);
            }
        }
        return new SearchResult(null, langkah);
    }

    public static DynamicArray<Putusan> urutkanBerdasarkanVonis(StrukturData<Putusan> daftarPutusan, boolean ascending) {
        DynamicArray<Putusan> hasilUrut = new DynamicArray<>();
        if (daftarPutusan == null || daftarPutusan.isEmpty()) {
            return hasilUrut;
        }

        int n = daftarPutusan.size();
        Putusan[] tempArray = new Putusan[n];
        for (int i = 0; i < n; i++) {
            tempArray[i] = daftarPutusan.get(i);
        }

        mergeSort(tempArray, 0, n - 1, ascending);

        for (int i = 0; i < n; i++) {
            hasilUrut.add(tempArray[i]);
        }
        return hasilUrut;
    }

    private static void mergeSort(Putusan[] arr, int left, int right, boolean ascending) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid, ascending);
            mergeSort(arr, mid + 1, right, ascending);
            merge(arr, left, mid, right, ascending);
        }
    }

    private static void merge(Putusan[] arr, int left, int mid, int right, boolean ascending) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Putusan[] leftArr = new Putusan[n1];
        Putusan[] rightArr = new Putusan[n2];

        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            boolean kondisi = ascending
                    ? leftArr[i].getVonisBulan() <= rightArr[j].getVonisBulan()
                    : leftArr[i].getVonisBulan() >= rightArr[j].getVonisBulan();

            if (kondisi) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    // =========================================================================
    // TAHAP 3A: FITUR FILTER
    // =========================================================================

    public static DynamicArray<Putusan> filterByJenisNarkotika(StrukturData<Putusan> daftarPutusan, String jenisNarkotika) {
        DynamicArray<Putusan> hasilFilter = new DynamicArray<>();
        if (daftarPutusan == null || jenisNarkotika == null || jenisNarkotika.trim().isEmpty()) {
            return hasilFilter;
        }

        String keyword = jenisNarkotika.trim().toLowerCase();
        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan current = daftarPutusan.get(i);
            if (current != null && current.getJenisNarkotika() != null) {
                if (current.getJenisNarkotika().toLowerCase().contains(keyword)) {
                    hasilFilter.add(current);
                }
            }
        }
        return hasilFilter;
    }

    public static DynamicArray<Putusan> filterByRentangVonis(StrukturData<Putusan> daftarPutusan, int minBulan, int maxBulan) {
        DynamicArray<Putusan> hasilFilter = new DynamicArray<>();
        if (daftarPutusan == null || minBulan > maxBulan) {
            return hasilFilter;
        }

        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan current = daftarPutusan.get(i);
            if (current != null) {
                int vonis = current.getVonisBulan();
                if (vonis >= minBulan && vonis <= maxBulan) {
                    hasilFilter.add(current);
                }
            }
        }
        return hasilFilter;
    }

    public static DynamicArray<Putusan> filterByRentangBerat(StrukturData<Putusan> daftarPutusan, double minGram, double maxGram) {
        DynamicArray<Putusan> hasilFilter = new DynamicArray<>();
        if (daftarPutusan == null || minGram > maxGram) {
            return hasilFilter;
        }

        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan current = daftarPutusan.get(i);
            if (current != null) {
                double berat = current.getBeratBarangBuktiGram();
                if (berat >= minGram && berat <= maxGram) {
                    hasilFilter.add(current);
                }
            }
        }
        return hasilFilter;
    }

    public static DynamicArray<Putusan> filterByPasalTerbukti(StrukturData<Putusan> daftarPutusan, String pasal) {
        DynamicArray<Putusan> hasilFilter = new DynamicArray<>();
        if (daftarPutusan == null || pasal == null || pasal.trim().isEmpty()) {
            return hasilFilter;
        }

        String keyword = pasal.trim().toLowerCase();
        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan current = daftarPutusan.get(i);
            if (current != null && current.getPasalTerbukti() != null) {
                if (current.getPasalTerbukti().toLowerCase().contains(keyword)) {
                    hasilFilter.add(current);
                }
            }
        }
        return hasilFilter;
    }

    public static DynamicArray<Putusan> filterKombinasi(StrukturData<Putusan> daftarPutusan,
                                                        String jenisNarkotika, int minBulan, int maxBulan) {
        DynamicArray<Putusan> hasilFilter = new DynamicArray<>();
        if (daftarPutusan == null || minBulan > maxBulan) {
            return hasilFilter;
        }

        boolean cekJenis = (jenisNarkotika != null && !jenisNarkotika.trim().isEmpty());
        String keyword = cekJenis ? jenisNarkotika.trim().toLowerCase() : "";

        for (int i = 0; i < daftarPutusan.size(); i++) {
            Putusan current = daftarPutusan.get(i);
            if (current != null) {
                boolean cocokJenis = !cekJenis ||
                        (current.getJenisNarkotika() != null && current.getJenisNarkotika().toLowerCase().contains(keyword));
                int vonis = current.getVonisBulan();
                boolean cocokVonis = (vonis >= minBulan && vonis <= maxBulan);

                if (cocokJenis && cocokVonis) {
                    hasilFilter.add(current);
                }
            }
        }
        return hasilFilter;
    }

    // =========================================================================
    // TAHAP 3B: FITUR AGREGASI
    // =========================================================================

    public static StatistikPutusan hitungStatistik(StrukturData<Putusan> daftarPutusan, String namaKategori) {
        if (daftarPutusan == null || daftarPutusan.isEmpty()) {
            return new StatistikPutusan(namaKategori, 0, 0.0, 0.0, 0.0, 0, 0, 0L, "-");
        }

        int totalPerkara = daftarPutusan.size();
        long totalVonis = 0;
        long totalTuntutan = 0;
        double totalBeratBB = 0.0;
        long totalDenda = 0L;
        int minVonis = Integer.MAX_VALUE;
        int maxVonis = Integer.MIN_VALUE;

        DynamicArray<String> daftarJenis = new DynamicArray<>();
        DynamicArray<Integer> jumlahPerJenis = new DynamicArray<>();

        for (int i = 0; i < totalPerkara; i++) {
            Putusan p = daftarPutusan.get(i);
            int vonis = p.getVonisBulan();

            totalVonis += vonis;
            totalTuntutan += p.getTuntutanBulan();
            totalBeratBB += p.getBeratBarangBuktiGram();
            totalDenda += p.getDendaRupiah();

            if (vonis < minVonis) minVonis = vonis;
            if (vonis > maxVonis) maxVonis = vonis;

            String jenis = (p.getJenisNarkotika() != null && !p.getJenisNarkotika().trim().isEmpty())
                    ? p.getJenisNarkotika().trim()
                    : "Tidak Diketahui";

            int idxDitemukan = -1;
            for (int j = 0; j < daftarJenis.size(); j++) {
                if (daftarJenis.get(j).equalsIgnoreCase(jenis)) {
                    idxDitemukan = j;
                    break;
                }
            }

            if (idxDitemukan == -1) {
                daftarJenis.add(jenis);
                jumlahPerJenis.add(1);
            } else {
                // Catatan: menyesuaikan jika StrukturData tidak memiliki set(), gunakan array/rebuild atau ganti jika set() tersedia
                int jumlahLama = jumlahPerJenis.get(idxDitemukan);
                jumlahPerJenis.remove(idxDitemukan);
                // Menjaga konsistensi indeks antara daftarJenis dan jumlahPerJenis
                daftarJenis.remove(idxDitemukan);
                daftarJenis.add(jenis);
                jumlahPerJenis.add(jumlahLama + 1);
            }
        }

        StringBuilder sbDistribusi = new StringBuilder();
        for (int j = 0; j < daftarJenis.size(); j++) {
            if (j > 0) sbDistribusi.append(", ");
            sbDistribusi.append(daftarJenis.get(j)).append("=").append(jumlahPerJenis.get(j));
        }

        double rataVonis = (double) totalVonis / totalPerkara;
        double rataTuntutan = (double) totalTuntutan / totalPerkara;

        return new StatistikPutusan(
                namaKategori,
                totalPerkara,
                rataVonis,
                rataTuntutan,
                totalBeratBB,
                minVonis,
                maxVonis,
                totalDenda,
                sbDistribusi.toString()
        );
    }

    // =========================================================================
    // TAHAP 4: OPERASI DATA DENGAN REKAM JEJAK AUDIT (STACK)
    // =========================================================================

    public static void tambahPutusanDenganAudit(StrukturData<Putusan> daftarPutusan,
                                                AuditStack<AuditLog> auditStack, Putusan putusanBaru) {
        if (daftarPutusan == null || putusanBaru == null) return;

        daftarPutusan.add(putusanBaru);
        if (auditStack != null) {
            auditStack.push(new AuditLog("TAMBAH", putusanBaru.getCaseId(), putusanBaru.getNomorPerkara()));
        }
    }

    public static Putusan hapusPutusanDenganAudit(StrukturData<Putusan> daftarPutusan,
                                                  AuditStack<AuditLog> auditStack, int position) {
        if (daftarPutusan == null) return null;

        Putusan terhapus = daftarPutusan.remove(position);
        if (terhapus != null && auditStack != null) {
            auditStack.push(new AuditLog("HAPUS", terhapus.getCaseId(), terhapus.getNomorPerkara()));
        }
        return terhapus;
    }
}