public class AuditStack {
    private AuditLog[] elements;
    private int top;
    private static final int DEFAULT_CAPACITY = 10;

    public AuditStack() {
        this.elements = new AuditLog[DEFAULT_CAPACITY];
        this.top = -1;
    }

    // Menambahkan catatan audit ke puncak Stack - O(1) amortized
    public void push(AuditLog log) {
        if (top + 1 == elements.length) {
            grow();
        }
        top++;
        elements[top] = log;
    }

    // Mengambil dan menghapus catatan audit terbaru dari puncak Stack - O(1)
    public AuditLog pop() {
        if (isEmpty()) {
            throw new IllegalStateException("AuditStack kosong, tidak ada riwayat untuk di-pop.");
        }
        AuditLog log = elements[top];
        elements[top] = null;
        top--;
        return log;
    }

    // Melihat catatan audit terbaru tanpa menghapusnya - O(1)
    public AuditLog peek() {
        if (isEmpty()) {
            return null;
        }
        return elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    // Menampilkan seluruh riwayat audit dari yang terbaru (top) ke terlama (bottom)
    public void tampilkanRiwayat() {
        if (isEmpty()) {
            System.out.println("Belum ada aktivitas dalam riwayat audit.");
            return;
        }
        System.out.println("=== RIWAYAT AUDIT (TERBARU -> TERLAMA) ===");
        for (int i = top; i >= 0; i--) {
            System.out.println((top - i + 1) + ". " + elements[i]);
        }
    }

    private void grow() {
        int newCapacity = elements.length * 2;
        AuditLog[] newElements = new AuditLog[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size());
        elements = newElements;
    }

    // Validasi invariant Stack
    public boolean validateInvariant() {
        if (elements == null || top < -1 || top >= elements.length) {
            return false;
        }
        for (int i = 0; i <= top; i++) {
            if (elements[i] == null) return false;
        }
        for (int i = top + 1; i < elements.length; i++) {
            if (elements[i] != null) return false;
        }
        return true;
    }
}
