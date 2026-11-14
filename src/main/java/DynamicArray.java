public class DynamicArray<T> implements StrukturData<T> {
private Object[] elements;
private int size;
private static final int DEFAULT_CAPACITY = 10;

public DynamicArray() {
    this.elements = new Object[DEFAULT_CAPACITY];
    this.size = 0;
}
@Override
    public void add(T value) {
    if (size == elements.length) {
        grow();
    }
    elements[size] = value;
    size++;
}
@SuppressWarnings("unchecked")
    @Override
    public T get(int position) {
    checkIndex(position);
    return (T) elements[position];
}
@SuppressWarnings("unchecked")
    @Override
    public T remove(int position) {
    checkIndex(position);
    T removedElement = (T) elements[position];

    for (int i = position; i < size - 1; i++) {
        elements[i] = elements[i + 1];
    }

    elements[size - 1] = null;
    size--;
    return removedElement;
}

@Override
    public int size() {
    return size;
}

@Override
    public boolean isEmpty() {
    return size == 0;
}

@Override
    public boolean contains(T value) {
    for (int i = 0; i < size; i++) {
        if (value == null){
            if (elements[i] == null) return true;
    } else{
        if (value.equals(elements[i])) return true;
         }
    }
    return false;
}
@Override
public void clear() {
    for (int i = 0; i < size; i++) {
        elements[i] = null;
    }
    size = 0;
    }
private void grow() {
    int newCapacity = elements.length * 2;
    Object[] newElements = new Object[newCapacity];
    System.arraycopy(elements, 0, newElements, 0, size);
    elements = newElements;
}

private void checkIndex(int position) {
    if (position < 0 || position >= size) {
        throw new IndexOutOfBoundsException("Position: " + position +", Size: " + size);
    }
}
    public void set(int position, T value) {
        checkIndex(position);
        elements[position] = value;
    }

    public void insert(int position, T value) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position: " + position + ", Size: " + size);
        }
        if (size == elements.length) {
            grow();
        }
        for (int i = size; i > position; i--) {
            elements[i] = elements[i - 1];
        }
        elements[position] = value;
        size++;
    }


    public boolean validateInvariant() {
        if (elements == null || size < 0 || size > elements.length) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (elements[i] == null) {
                return false;
            }
        }
        for (int i = size; i < elements.length; i++) {
            if (elements[i] != null) {
                return false;
            }
        }
        return true;
    }
}
