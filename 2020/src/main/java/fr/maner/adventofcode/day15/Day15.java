package fr.maner.adventofcode.day15;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

public class Day15 extends Day {

    private final List<Long> defaultValue = new ArrayList<>();

    public Day15() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day15_scanner.txt");

        parse(scan);

        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day15().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        for (String s : scan.nextLine().split(","))
            this.defaultValue.add(Long.parseLong(s));
    }

    @Override
    public void partOne() {
        System.out.println(play(2020));
    }

    @Override
    public void partTwo() {
        System.out.println(play(30000000));
    }

    private long play(long limit) {
        HashMap<Long, Long> memory = new HashMap<>();
        List<Long> history = new ArrayList<>(this.defaultValue);

        IntStream.range(0, history.size()).forEach(index -> memory.put(history.get(index), index + 1L));

        while (history.size() < limit) {
            long nbValues = history.size();
            long last = history.get(history.size() - 1);

            if (!memory.containsKey(last))
                history.add(0L);
            else
                history.add(nbValues - memory.get(last));

            memory.put(last, nbValues);
        }

        return history.get(history.size() - 1);
    }
}