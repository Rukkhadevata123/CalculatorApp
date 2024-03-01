import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.StringJoiner;
import java.util.Objects;
import java.util.Set;
import java.io.Serializable;

public class ManualArray<T> implements Iterable<T>, Serializable {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int INCREMENT = 10;

    private T[] elements;
    private int size = 0;

    public ManualArray() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ManualArray(int initialCapacity) {
        elements = (T[]) new Object[initialCapacity];
    }

    public T get(int index) {
        checkIndex(index);
        return elements[index];
    }

    public void set(int index, T value) {
        checkIndex(index);
        elements[index] = value;
    }

    public void add(T value) {
        if (size == elements.length) {
            resize(elements.length + INCREMENT);
        }
        elements[size++] = value;
    }

    public void addAll(Collection<? extends T> collection) {
        for (T value : collection) {
            add(value);
        }
    }

    public void remove(int index) {
        checkIndex(index);
        int numElements = size - index - 1;
        if (numElements > 0) {
            System.arraycopy(elements, index + 1, elements, index, numElements);
        }
        elements[--size] = null;
        if (size > 0 && size == elements.length / 2) {
            resize(elements.length / 2);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    public int indexOf(T value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    public T[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public Iterator<T> iterator() {
        return new ManualArrayIterator();
    }

    private void resize(int newSize) {
        elements = Arrays.copyOf(elements, newSize);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private class ManualArrayIterator implements Iterator<T> {
        private int cursor = 0;

        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new ArrayIndexOutOfBoundsException();
            }
            return elements[cursor++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove during iteration");
        }
    }

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            sj.add(String.valueOf(elements[i]));
        }
        return sj.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ManualArray<?> that = (ManualArray<?>) o;
        if (size != that.size)
            return false;
        for (int i = 0; i < size; i++) { // 考虑元素的顺序
            if (!Objects.equals(elements[i], that.elements[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(size);
        result = 31 * result + Arrays.hashCode(elements);
        return result;
    }

    @Override
    public ManualArray<T> clone() { // 添加 clone 方法
        try {
            @SuppressWarnings("unchecked")
            ManualArray<T> cloned = (ManualArray<T>) super.clone();
            cloned.elements = Arrays.copyOf(elements, size);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // 不应该发生
        }
    }

    public List<T> toList() { // 添加转换为 List 的方法
        return new ArrayList<>(Arrays.asList(elements));
    }

    public Set<T> toSet() { // 添加转换为 Set 的方法
        return new HashSet<>(Arrays.asList(elements));
    }

}