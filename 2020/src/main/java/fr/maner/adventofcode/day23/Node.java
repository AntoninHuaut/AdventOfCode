package fr.maner.adventofcode.day23;

public class Node implements Cloneable {

    private Node prev;
    private Node next;

    private final int value;

    public Node(int value) {
        this.value = value;
    }

    public Node getPrev() {
        return this.prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return this.next;
    }

    public void insert(Node b) {
        Node a = this;

        if (a.getNext() == null) {
            a.setNext(b);
            b.setPrev(this);
        } else {
            Node c = this.getNext();
            a.setNext(b);
            b.setNext(c);

            c.setPrev(b);
            b.setPrev(a);
        }
    }

    public void remove() {
        Node prev = getPrev();
        Node next = getNext();

        prev.setNext(next);
        next.setPrev(prev);
    }

    public void setNext(Node node) {
        this.next = node;
    }

    public int getValue() {
        return this.value;
    }
}
