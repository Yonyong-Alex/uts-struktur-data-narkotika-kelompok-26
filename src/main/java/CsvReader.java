import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {

    // Sesuaikan parameter tipenya dengan struktur data buatanmu (misal DynamicArray atau StrukturData)
    public static void loadPutusanData(DynamicArray<Putusan> targetList) {
        String filePath = "data/putusan-50.csv";
        String line = "";
        // Regex untuk menghindari pemisahan koma yang berada di dalam tanda kutip string CSV
        String csvSplitBy = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Lewati baris pertama jika itu adalah header kolom
            String header = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);

                if (data.length >= 16) {
                    // Membersihkan tanda kutip ganda dan spasi
                    String caseId = data[0].replace("\"", "").trim();
                    String nomorPerkara = data[1].replace("\"", "").trim();
                    String pengadilan = data[2].replace("\"", "").trim();
                    String tanggalPutusan = data[3].replace("\"", "").trim();
                    String terdakwaKode = data[4].replace("\"", "").trim();
                    String jenisNarkotika = data[5].replace("\"", "").trim();
                    double beratBarangBuktiGram = Double.parseDouble(data[6].replace("\"", "").trim());
                    String pasalDakwaan = data[7].replace("\"", "").trim();
                    String pasalTerbukti = data[8].replace("\"", "").trim();
                    int tuntutanBulan = Integer.parseInt(data[9].replace("\"", "").trim());
                    int vonisBulan = Integer.parseInt(data[10].replace("\"", "").trim());
                    long dendaRupiah = Long.parseLong(data[11].replace("\"", "").trim());
                    String barangBukti = data[12].replace("\"", "").trim();
                    String keadaanMemberatkan = data[13].replace("\"", "").trim();
                    String keadaanMeringankan = data[14].replace("\"", "").trim();
                    String sourcePages = data[15].replace("\"", "").trim();

                    // Buat objek Putusan
                    Putusan putusan = new Putusan(caseId, nomorPerkara, pengadilan, tanggalPutusan,
                            terdakwaKode, jenisNarkotika, beratBarangBuktiGram, pasalDakwaan,
                            pasalTerbukti, tuntutanBulan, vonisBulan, dendaRupiah, barangBukti,
                            keadaanMemberatkan, keadaanMeringankan, sourcePages);

                    // Masukkan ke struktur data utama buatanmu
                    targetList.add(putusan);
                }
            }
            System.out.println("Berhasil memuat 50 data putusan ke dalam struktur data!");

        } catch (IOException e) {
            System.err.println("Gagal membaca file CSV: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Kesalahan format angka/parse: " + e.getMessage());
        }
    }
}