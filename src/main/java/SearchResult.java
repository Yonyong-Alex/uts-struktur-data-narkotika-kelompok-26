public class SearchResult {
    private Putusan putusan;
    private int jumlahLangkah;

    public SearchResult(Putusan putusan, int jumlahLangkah) {
        this.putusan = putusan;
        this.jumlahLangkah = jumlahLangkah;
    }

    public Putusan getPutusan() {
        return putusan;
    }

    public int getJumlahLangkah() {
        return jumlahLangkah;
    }

    public boolean isFound() {
        return putusan != null;
    }
}