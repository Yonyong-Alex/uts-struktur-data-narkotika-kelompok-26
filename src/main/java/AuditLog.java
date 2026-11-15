import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {
    private String aksi; // "TAMBAH" atau "HAPUS"
    private String caseId;
    private String nomorPerkara;
    private String timestamp;

    public AuditLog(String aksi, String caseId, String nomorPerkara) {
        this.aksi = aksi;
        this.caseId = caseId;
        this.nomorPerkara = nomorPerkara;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = LocalDateTime.now().format(formatter);
    }

    public String getAksi() { return aksi; }
    public String getCaseId() { return caseId; }
    public String getNomorPerkara() { return nomorPerkara; }
    public String getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %-6s | ID: %-5s | Nomor Perkara: %s",
                timestamp, aksi, caseId, nomorPerkara);
    }
}