package fr.maner.adventofcode.day23;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day23 extends Day {

    private final Map<Integer, Node> valueToNode = new HashMap<>();
    private Node firstNode;

    public static void main(String[] args) {
        try {
            new Day23().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day23_scanner.txt");
        Node prev = null;

        for (char character : scan.nextLine().toCharArray()) {
            int value = Integer.parseInt("" + character);

            Node currentNode = new Node(value);
            if (prev == null) {
                this.firstNode = currentNode;
            } else {
                prev.setNext(currentNode);
            }

            this.valueToNode.put(value, currentNode);
            prev = currentNode;
        }

        Node node = this.firstNode;
        while (node != null) {
            Node next = node.getNext();

            if (next != null) {
                next.setPrev(node);
            } else {
                this.firstNode.setPrev(node);
                node.setNext(this.firstNode);
            }
            node = next;
        }

        scan.close();
    }

    @Override
    public void partOne() throws Exception {
        new Day23().separateOne();
    }

    @Override
    public void partTwo() throws Exception {
        new Day23().separateTwo();
    }

    private void separateOne() throws Exception {
        parse();

        run(100, 9);

        StringBuilder builder = new StringBuilder();
        Node node = this.valueToNode.get(1).getNext();

        while (node.getValue() != 1) {
            builder.append(node.getValue());
            node = node.getNext();
        }

        System.out.println(builder);
    }

    private void separateTwo() throws Exception {
        parse();

        Node lastNode = this.firstNode.getPrev();
        int maxValue = 9;

        for (int value = maxValue + 1; value <= 1000000; value++) {
            Node node = new Node(value);
            lastNode.insert(node);
            lastNode = node;

            maxValue = Math.max(maxValue, node.getValue());
            this.valueToNode.put(value, node);
        }

        run(10000000, maxValue);

        Node node = this.valueToNode.get(1);
        long firstValue = node.getNext().getValue();
        long secondValue = node.getNext().getNext().getValue();

        System.out.println(firstValue * secondValue);
    }

    private void run(int maxIndex, int maxValue) {
        Node current = this.firstNode;

        for (int i = 0; i < maxIndex; i++) {
            current = move(current, maxValue);
        }
    }

    private Node move(Node current, int maxValue) {
        Node first = current.getNext();
        Node second = first.getNext();
        Node third = second.getNext();
        List<Node> three = Arrays.asList(third, second, first);

        three.forEach(Node::remove);

        int destination = current.getValue();
        Node destinationNode;
        do {
            destination = destination == 1 ? maxValue : destination - 1;
            destinationNode = this.valueToNode.get(destination);
        } while (three.contains(destinationNode));

        for (Node node : three)
            destinationNode.insert(node);

        return current.getNext();
    }
}