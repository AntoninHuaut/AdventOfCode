package fr.maner.adventofcode.day22;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Day22 extends Day {

    private final LinkedList<Long> p1Save = new LinkedList<>();
    private final LinkedList<Long> p2Save = new LinkedList<>();
    
    private final Set<LinkedList<Long>> history = new HashSet<>();

    public Day22() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day22_scanner.txt");
        parse(scan);
        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day22().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        boolean first = true;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty()) {
                first = false;
                continue;
            }

            try {
                long value = Long.parseLong(line);

                if (first) this.p1Save.add(value);
                else this.p2Save.add(value);
            } catch (NumberFormatException ignored) {
            }
        }
    }

    @Override
    public void partOne() {
        LinkedList<Long> p1 = new LinkedList<>(this.p1Save);
        LinkedList<Long> p2 = new LinkedList<>(this.p2Save);
        runOne(p1, p2);

        System.out.println(getScore(p1, p2));
    }

    @Override
    public void partTwo() {
        LinkedList<Long> p1 = new LinkedList<>(this.p1Save);
        LinkedList<Long> p2 = new LinkedList<>(this.p2Save);
        runTwo(p1, p2);

        System.out.println(getScore(p1, p2));
    }

    private void runOne(LinkedList<Long> p1, LinkedList<Long> p2) {
        while (isNotOver(p1, p2)) {
            addValues(p1.pop(), p2.pop(), p1, p2);
        }
    }

    public int runTwo(LinkedList<Long> p1, LinkedList<Long> p2) {
        while (isNotOver(p1, p2)) {
            if (this.history.contains(p1)) return 1;
            this.history.add(p1);

            long p1Value = p1.pop();
            long p2Value = p2.pop();

            if (p1Value <= p1.size() && p2Value <= p2.size()) {
                if (runTwo(getSubList(p1, p1Value), getSubList(p2, p2Value)) == 1) {
                    p1.add(p1Value);
                    p1.add(p2Value);
                } else {
                    p2.add(p2Value);
                    p2.add(p1Value);
                }
            } else
                addValues(p1Value, p2Value, p1, p2);
        }

        return p1.isEmpty() ? 2 : 1;
    }

    private LinkedList<Long> getSubList(LinkedList<Long> list, long index) {
        return new LinkedList<>(list.subList(0, (int) index));
    }

    private void addValues(long p1Value, long p2Value, LinkedList<Long> p1, LinkedList<Long> p2) {
        if (p1Value > p2Value) {
            p1.add(p1Value);
            p1.add(p2Value);
        } else {
            p2.add(p2Value);
            p2.add(p1Value);
        }
    }

    private long getScore(LinkedList<Long> p1, LinkedList<Long> p2) {
        LinkedList<Long> list = p1.isEmpty() ? p2 : p1;
        long sum = 0;

        for (int i = list.size(); i > 0; i--) {
            sum += list.pop() * i;
        }

        return sum;
    }

    private boolean isNotOver(LinkedList<Long> p1, LinkedList<Long> p2) {
        return !p1.isEmpty() && !p2.isEmpty();
    }
}