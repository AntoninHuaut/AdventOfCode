package fr.maner.adventofcode.day05;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 extends Day {

    private final List<String> list = new ArrayList<>();

    public Day5() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day05_scanner.txt");

        while (scan.hasNextLine()) {
            this.list.add(scan.nextLine());
        }

        scan.close();
    }

    @Override
    public void partOne() {
        int best = -1;

        for (String line : this.list) {
            int seatId = getSeatId(line);
            best = Math.max(best, seatId);
        }

        System.out.println(best);
    }

    @Override
    public void partTwo() {
        List<Integer> intList = this.list.stream().map(this::getSeatId).collect(Collectors.toList());
        int mySeat = -1;

        for (int i = 0; i < intList.size() && mySeat == -1; i++) {
            if (intList.contains(i)) continue;

            if (intList.contains(i - 1) && intList.contains(i + 1)) {
                mySeat = i;
            }
        }

        System.out.println(mySeat);
    }

    private int getSeatId(String key) {
        return Integer.parseInt(getBinStr(key), 2);
    }

    private String getBinStr(String key) {
        return key.replaceAll("[FL]", "" + 0).replaceAll("[BR]", "" + 1);
    }

    public static void main(String[] args) {
        try {
            new Day5().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}