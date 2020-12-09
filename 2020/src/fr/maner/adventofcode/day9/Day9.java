package fr.maner.adventofcode.day9;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

public class Day9 extends Day {

    private final int STEP = 25;
    private final List<Integer> list = new ArrayList<>();

    public Day9() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "scanner.txt");

        parse(scan);

        scan.close();
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextInt()) {
            this.list.add(scan.nextInt());
        }
    }

    @Override
    public void partOne() {
        System.out.println(getInvalidNumber());
    }

    @Override
    public void partTwo() {
        int invalidNumber = getInvalidNumber();

        for (int i = 0; i < this.list.size(); i++) {
            int sum = 0;
            int subIndex = i;
            Set<Integer> set = new HashSet<>();

            while (sum < invalidNumber && subIndex < this.list.size()) {
                set.add(this.list.get(subIndex));
                sum += this.list.get(subIndex);
                subIndex++;
            }

            if (sum == invalidNumber) {
                int minValue = toInt(set).min().getAsInt();
                int maxValue = toInt(set).max().getAsInt();
                System.out.println(minValue + maxValue);
                return;
            }
        }
    }

    private IntStream toInt(Set<Integer> set) {
        return set.parallelStream().mapToInt(Integer::intValue);
    }

    private int getInvalidNumber() {
        for (int i = this.STEP; i < this.list.size(); i++) {
            if (!isSumPreviousNumber(i)) {
                return this.list.get(i);
            }
        }

        return -1;
    }

    private boolean isSumPreviousNumber(int currentIndex) {
        int start = Math.max(currentIndex - this.STEP, 0);
        int search = this.list.get(currentIndex);

        for (int i = start; i < currentIndex; i++) {
            int vi = this.list.get(i);
            for (int j = start + 1; j < currentIndex; j++) {
                int vj = this.list.get(j);

                if (vi + vj == search) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        try {
            new Day9().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}