package util;

import java.util.Iterator;

public class LList<T> implements Iterable<T> {
    private Node head, tail;
    private int size;

    public Node addHead(T value) {
        if (head == null)
            head = tail = new Node(value);
        else {
            head = new Node(value, null, head);
            head.next.prev = head;
        }

        size++;

        return head;
    }

    public Node addTail(T value) {
        if (head == null)
            head = tail = new Node(value);
        else {
            tail = new Node(value, tail, null);
            tail.prev.next = tail;
        }

        size++;

        return tail;
    }

    public void remove(Node node) {
        if (node.prev != null)
            node.prev.next = node.next;
        else
            head = node.next;

        if (node.next != null)
            node.next.prev = node.prev;
        else
            tail = node.prev;

        size--;
    }

    public void removeAll() {
        head = tail = null;
        size = 0;
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

    public Iterable<Node> nodeIterator() {
        return () -> new Iterator<Node>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Node next() {
                Node r = current;
                current = current.next;
                return r;
            }
        };
    }

    public class Node {
        private T value;
        private Node prev, next;

        private Node(T value) {
            this.value = value;
        }

        private Node(T value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public T getValue() {
            return value;
        }

        public Node getPrev() {
            return prev;
        }

        public Node getNext() {
            return next;
        }
    }
}