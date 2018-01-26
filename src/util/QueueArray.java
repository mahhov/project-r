package util;

import java.util.Iterator;

public class QueueArray<T> implements Iterable<T> {
    private Object[] elements;
    private int tail;

    public QueueArray(int maxSize) {
        elements = new Object[maxSize];
    }

    public void add(T value) {
        elements[tail] = value;
        tail = next(tail);
    }

    private int next(int i) {
        return i == elements.length - 1 ? 0 : i + 1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int current = QueueArray.this.next(tail);

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public T next() {
                T r = (T) elements[current];
                current = QueueArray.this.next(current);
                return r;
            }
        };
    }
}