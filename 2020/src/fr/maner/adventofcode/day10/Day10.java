package fr.maner.adventofcode.day10;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Day10 extends Day {

    private final HashMap<Integer, Long> cache = new HashMap<>();
    private final List<Integer> list = new ArrayList<>();

    public Day10() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "scanner.txt");

        parse(scan);

        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day10().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        this.list.add(0);
        while (scan.hasNextInt()) {
            this.list.add(scan.nextInt());
        }
        int highAdapter = this.list.stream().mapToInt(e -> e).max().orElseThrow() + 3;
        this.list.add(highAdapter);
        Collections.sort(this.list);
    }

    @Override
    public void partOne() {
        HashMap<Integer, Integer> count = solveOne();
        System.out.println(count.get(1) * count.get(3));
    }

    @Override
    public void partTwo() {
        this.cache.put(this.list.size() - 1, 1L);
        System.out.println(solveTwo(0));
    }

    private HashMap<Integer, Integer> solveOne() {
        HashMap<Integer, Integer> count = new HashMap<>();

        for (int i = 0; i < this.list.size(); i++)
            for (int j = i + 1; j < this.list.size() && j < i + 3; j++) {
                int diff = this.list.get(j) - this.list.get(i);

                if (diff <= 3) count.put(diff, count.computeIfAbsent(diff, d -> 0) + 1);
            }

        return count;
    }

    private long solveTwo(int iStart) {
        long value = 0;
        if (this.cache.containsKey(iStart)) {
            value = this.cache.get(iStart);
        } else {
            for (int i = iStart + 1; i < this.list.size() && i < iStart + 4; i++)
                if (this.list.get(iStart) + 3 >= this.list.get(i))
                    value += solveTwo(i);

            this.cache.put(iStart, value);
        }
        return value;
    }
}