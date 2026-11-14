public class PutusanService {


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
}