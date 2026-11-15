import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {

    public static void loadPutusanData(StrukturData<Putusan> targetList) {
        loadPutusanData(targetList, "data/putusan-50.csv");
    }

    public static void loadPutusanData(StrukturData<Putusan> targetList, String filePath) {
        if (targetList == null) {
            throw new IllegalArgumentException("Target struktur data tidak boleh null.");
        }

        String line;
        String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
        int lineNumber = 1;
        int validCount = 0;
        int invalidCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Lewati baris header
            String header = br.readLine();
            if (header == null) {
                System.err.println("[PERINGATAN] File CSV kosong: " + filePath);
                return;
            }

            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    invalidCount++;
                    System.err.println("[TOLAK CSV Baris " + lineNumber + "] Baris kosong dilewati.");
                    continue;
                }

                try {
                    String[] data = line.split(csvSplitBy, -1);
                    if (data.length < 16) {
                        throw new IllegalArgumentException("Jumlah kolom kurang dari 16 (ditemukan " + data.length + ").");
                    }

                    String caseId = cleanField(data[0]);
                    String nomorPerkara = cleanField(data[1]);
                    String pengadilan = cleanField(data[2]);
                    String tanggalPutusan = cleanField(data[3]);
                    String terdakwaKode = cleanField(data[4]);
                    String jenisNarkotika = cleanField(data[5]);
                    double beratBarangBuktiGram = Double.parseDouble(cleanField(data[6]));
                    String pasalDakwaan = cleanField(data[7]);
                    String pasalTerbukti = cleanField(data[8]);
                    int tuntutanBulan = Integer.parseInt(cleanField(data[9]));
                    int vonisBulan = Integer.parseInt(cleanField(data[10]));
                    long dendaRupiah = Long.parseLong(cleanField(data[11]));
                    String barangBukti = cleanField(data[12]);
                    String keadaanMemberatkan = cleanField(data[13]);
                    String keadaanMeringankan = cleanField(data[14]);
                    String sourcePages = cleanField(data[15]);

                    if (caseId.isEmpty() || nomorPerkara.isEmpty() || jenisNarkotika.isEmpty()) {
                        throw new IllegalArgumentException("Field wajib (caseId/nomorPerkara/jenisNarkotika) kosong.");
                    }
                    if (beratBarangBuktiGram < 0 || tuntutanBulan < 0 || vonisBulan < 0 || dendaRupiah < 0) {
                        throw new IllegalArgumentException("Nilai numerik (berat/tuntutan/vonis/denda) tidak boleh negatif.");
                    }

                    Putusan putusan = new Putusan(caseId, nomorPerkara, pengadilan, tanggalPutusan,
                            terdakwaKode, jenisNarkotika, beratBarangBuktiGram, pasalDakwaan,
                            pasalTerbukti, tuntutanBulan, vonisBulan, dendaRupiah, barangBukti,
                            keadaanMemberatkan, keadaanMeringankan, sourcePages);

                    targetList.add(putusan);
                    validCount++;
                } catch (NumberFormatException e) {
                    invalidCount++;
                    System.err.println("[TOLAK CSV Baris " + lineNumber + "] Format angka tidak valid: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    invalidCount++;
                    System.err.println("[TOLAK CSV Baris " + lineNumber + "] Data tidak valid: " + e.getMessage());
                }
            }

            System.out.println("Berhasil memuat " + validCount + " data putusan ke dalam struktur data"
                    + (invalidCount > 0 ? " (" + invalidCount + " record tidak valid ditolak)." : "!"));

        } catch (IOException e) {
            System.err.println("Gagal membaca file CSV: " + e.getMessage());
        }
    }

    private static String cleanField(String raw) {
        return raw == null ? "" : raw.replace("\"", "").trim();
    }
}