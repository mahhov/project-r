package util;

import java.util.Iterator;

public class Queue<T> implements Iterable<T> { // todo implement using array instead of nodes, for constant time random access, to support log scrollign
    private int maxSize;
    private Node head, tail;
    private int size;

    public Queue(int maxSize) {
        this.maxSize = maxSize;
    }

    public Node add(T value) {
        if (head == null)
            head = tail = new Node(value);
        else
            tail = tail.next = new Node(value, null);

        if (size < maxSize)
            size++;
        else
            head = head.next;

        return tail;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T r = current.value;
                current = current.next;
                return r;
            }
        };
    }

    public class Node {
        private T value;
        private Node next;

        private Node(T value) {
            this.value = value;
        }

        private Node(T value, Node next) {
            this.value = value;
            this.next = next;
        }

        public T getValue() {
            return value;
        }
    }
}