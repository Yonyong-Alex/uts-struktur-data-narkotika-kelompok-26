public class AuditStack<T> {
    private Object[] elements;
    private int top;
    private static final int DEFAULT_CAPACITY = 10;

    public AuditStack() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.top = -1;
    }

    // Menambahkan elemen ke puncak Stack - O(1) amortized
    public void push(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Elemen tidak boleh null.");
        }
        if (top + 1 == elements.length) {
            grow();
        }
        top++;
        elements[top] = item;
    }

    // Mengambil dan menghapus elemen terbaru dari puncak Stack - O(1)
    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("AuditStack kosong, tidak ada riwayat untuk di-pop.");
        }
        T item = (T) elements[top];
        elements[top] = null;
        top--;
        return item;
    }

    // Melihat elemen terbaru tanpa menghapusnya - O(1)
    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return (T) elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    // Menyusun seluruh riwayat dari terbaru (top) ke terlama (bottom) sebagai String untuk memudahkan unit testing
    public String riwayatSebagaiTeks() {
        if (isEmpty()) {
            return "Belum ada aktivitas dalam riwayat audit.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== RIWAYAT AUDIT (TERBARU -> TERLAMA) ===");
        for (int i = top; i >= 0; i--) {
            sb.append("\n").append(top - i + 1).append(". ").append(elements[i]);
        }
        return sb.toString();
    }

    // Menampilkan seluruh riwayat audit ke konsol
    public void tampilkanRiwayat() {
        System.out.println(riwayatSebagaiTeks());
    }

    private void grow() {
        int newCapacity = elements.length * 2;
        Object[] newElements = new Object[newCapacity];
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