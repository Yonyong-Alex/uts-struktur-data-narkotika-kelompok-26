public class StatistikPutusan {
    private String kategori;
    private int totalPerkara;
    private double rataRataVonisBulan;
    private double rataRataTuntutanBulan;
    private double totalBeratBarangBuktiGram;
    private int vonisMinimumBulan;
    private int vonisMaksimumBulan;

    public StatistikPutusan(String kategori, int totalPerkara, double rataRataVonisBulan,
                            double rataRataTuntutanBulan, double totalBeratBarangBuktiGram,
                            int vonisMinimumBulan, int vonisMaksimumBulan) {
        this.kategori = kategori;
        this.totalPerkara = totalPerkara;
        this.rataRataVonisBulan = rataRataVonisBulan;
        this.rataRataTuntutanBulan = rataRataTuntutanBulan;
        this.totalBeratBarangBuktiGram = totalBeratBarangBuktiGram;
        this.vonisMinimumBulan = vonisMinimumBulan;
        this.vonisMaksimumBulan = vonisMaksimumBulan;
    }

    public String getKategori() { return kategori; }
    public int getTotalPerkara() { return totalPerkara; }
    public double getRataRataVonisBulan() { return rataRataVonisBulan; }
    public double getRataRataTuntutanBulan() { return rataRataTuntutanBulan; }
    public double getTotalBeratBarangBuktiGram() { return totalBeratBarangBuktiGram; }
    public int getVonisMinimumBulan() { return vonisMinimumBulan; }
    public int getVonisMaksimumBulan() { return vonisMaksimumBulan; }

    @Override
    public String toString() {
        return String.format(
                "Kategori: %-18s | Total Perkara: %2d | Rata-rata Vonis: %6.2f bln | " +
                        "Rata-rata Tuntutan: %6.2f bln | Min-Max Vonis: %d-%d bln | Total BB: %.3f gr",
                kategori, totalPerkara, rataRataVonisBulan, rataRataTuntutanBulan,
                vonisMinimumBulan, vonisMaksimumBulan, totalBeratBarangBuktiGram
        );
    }
}