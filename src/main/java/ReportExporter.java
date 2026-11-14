import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportExporter {

    /**
     * Mengekspor daftar Putusan (misalnya hasil filter atau sorting) ke file CSV baru.
     */
    public static boolean exportToCsv(StrukturData<Putusan> data, String outputPath) {
        if (data == null) return false;

        try {
            siapkanDirektori(outputPath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                // Tulis Header sesuai struktur putusan-50.csv
                writer.write("case_id,nomor_perkara,pengadilan,tanggal_putusan,terdakwa_kode," +
                        "jenis_narkotika,berat_barang_bukti_gram,pasal_dakwaan,pasal_terbukti," +
                        "tuntutan_bulan,vonis_bulan,denda_rupiah,barang_bukti," +
                        "keadaan_memberatkan,keadaan_meringankan,source_pages");
                writer.newLine();

                for (int i = 0; i < data.size(); i++) {
                    Putusan p = data.get(i);
                    String row = String.join(",",
                            escapeCsv(p.getCaseId()),
                            escapeCsv(p.getNomorPerkara()),
                            escapeCsv(p.getPengadilan()),
                            escapeCsv(p.getTanggalPutusan()),
                            escapeCsv(p.getTerdakwaKode()),
                            escapeCsv(p.getJenisNarkotika()),
                            String.valueOf(p.getBeratBarangBuktiGram()),
                            escapeCsv(p.getPasalDakwaan()),
                            escapeCsv(p.getPasalTerbukti()),
                            String.valueOf(p.getTuntutanBulan()),
                            String.valueOf(p.getVonisBulan()),
                            String.valueOf(p.getDendaRupiah()),
                            escapeCsv(p.getBarangBukti()),
                            escapeCsv(p.getKeadaanMemberatkan()),
                            escapeCsv(p.getKeadaanMeringankan()),
                            escapeCsv(p.getSourcePages())
                    );
                    writer.write(row);
                    writer.newLine();
                }
            }
            System.out.println("[SUKSES] Data berhasil diekspor ke CSV: " + outputPath + " (" + data.size() + " baris)");
            return true;
        } catch (IOException e) {
            System.err.println("[GAGAL] Gagal mengekspor ke CSV: " + e.getMessage());
            return false;
        }
    }

    /**
     * Mengekspor ringkasan laporan statistik (agregasi) dan rincian perkara ke file TXT.
     */
    public static boolean exportLaporanToTxt(StrukturData<Putusan> data, StatistikPutusan stat, String outputPath) {
        if (data == null || stat == null) return false;

        try {
            siapkanDirektori(outputPath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
                writer.write("==========================================================================");
                writer.newLine();
                writer.write("LAPORAN ANALISIS & AGREGASI PUTUSAN NARKOTIKA");
                writer.newLine();
                writer.write("==========================================================================");
                writer.newLine();
                writer.write("Kategori Analisis      : " + stat.getKategori());
                writer.newLine();
                writer.write("Total Perkara          : " + stat.getTotalPerkara() + " perkara");
                writer.newLine();
                writer.write(String.format("Rata-rata Vonis        : %.2f bulan", stat.getRataRataVonisBulan()));
                writer.newLine();
                writer.write(String.format("Rata-rata Tuntutan     : %.2f bulan", stat.getRataRataTuntutanBulan()));
                writer.newLine();
                writer.write("Rentang Vonis (Min-Max): " + stat.getVonisMinimumBulan() + " - " + stat.getVonisMaksimumBulan() + " bulan");
                writer.newLine();
                writer.write(String.format("Total Berat Barang Bukti: %.3f gram", stat.getTotalBeratBarangBuktiGram()));
                writer.newLine();
                writer.write("--------------------------------------------------------------------------");
                writer.newLine();
                writer.write("DAFTAR RINCIAN PERKARA:");
                writer.newLine();
                writer.write("--------------------------------------------------------------------------");
                writer.newLine();

                if (data.isEmpty()) {
                    writer.write("Tidak ada data perkara yang memenuhi kriteria filter.");
                    writer.newLine();
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        writer.write((i + 1) + ". " + data.get(i).toString());
                        writer.newLine();
                    }
                }
                writer.write("==========================================================================");
                writer.newLine();
            }
            System.out.println("[SUKSES] Laporan berhasil diekspor ke TXT: " + outputPath);
            return true;
        } catch (IOException e) {
            System.err.println("[GAGAL] Gagal mengekspor ke TXT: " + e.getMessage());
            return false;
        }
    }

    private static void siapkanDirektori(String outputPath) {
        File file = new File(outputPath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
    }

    private static String escapeCsv(String value) {
        if (value == null) return "\"\"";
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}