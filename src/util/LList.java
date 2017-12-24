package util;

public class LList<T> {
    private Node head, tail;

    public void addHead(T value) {
        if (head == null)
            head = tail = new Node(value);
        else {
            head = new Node(value, null, head);
            head.next.prev = head;
        }
    }

    public void addTail(T value) {
        if (head == null)
            head = tail = new Node(value);
        else {
            tail = new Node(value, tail, null);
            tail.prev.next = tail;
        }
    }

    public void remove(Node node) {
        if (node.prev != null)
            node.prev.next = node.next;
        else
            head = node.next;
        
        if (node.next != null)
            node.next.prev = node;
        else
            tail = node.prev;
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
    }
}
