package fr.maner.adventofcode.day3;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

public class Day3 extends Day {

    private final String[][] map;
    private final int maxHeight;
    private final int maxWidth;

    private int currentHeight;
    private int currentWidth;

    public Day3() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day03_scanner.txt");
        List<String> lines = new ArrayList<>();

        while (scan.hasNextLine()) {
            lines.add(scan.nextLine());
        }
        scan.close();

        this.maxHeight = lines.size();
        this.maxWidth = lines.get(0).length();

        this.map = initMap(lines, this.maxHeight, this.maxWidth);
    }

    @Override
    public void partOne() {
        int score = 0;
        this.currentHeight = 0;
        this.currentWidth = 0;

        while (this.currentHeight != this.maxHeight - 1) {
            for (int goRight = 0; goRight < 3; goRight++) {
                this.currentWidth++;

                if (this.currentWidth == this.maxWidth) this.currentWidth = 0;
            }

            this.currentHeight++;
            score += calcScore(this.map[this.currentHeight][this.currentWidth]);
        }

        System.out.println(score);
    }

    @Override
    public void partTwo() {
        List<Entry<Integer, Integer>> entryList = Arrays.asList(new SimpleEntry<>(1, 1), new SimpleEntry<>(3, 1),
                new SimpleEntry<>(5, 1), new SimpleEntry<>(7, 1), new SimpleEntry<>(1, 2));

        long score = 1;

        for (Entry<Integer, Integer> entry : entryList) {
            int amtRight = entry.getKey();
            int amtBot = entry.getValue();
            int subScore = 0;

            this.currentHeight = 0;
            this.currentWidth = 0;

            while (this.currentHeight != this.maxHeight - 1) {
                for (int goRight = 0; goRight < amtRight; goRight++) {
                    this.currentWidth++;

                    if (this.currentWidth == this.maxWidth) this.currentWidth = 0;
                }

                for (int goBot = 0; goBot < amtBot; goBot++) {
                    this.currentHeight++;
                }

                subScore += calcScore(this.map[this.currentHeight][this.currentWidth]);
            }

            score *= subScore;
        }

        System.out.println(score);
    }

    private int calcScore(String c) {
        return c.equals("#") ? 1 : 0;
    }

    private String[][] initMap(List<String> lines, int maxHeight, int maxWidth) {
        String[][] map = new String[maxHeight][maxWidth];

        for (int i = 0; i < maxHeight; i++) {
            for (int j = 0; j < maxWidth; j++) {
                map[i][j] = "" + lines.get(i).charAt(j);
            }
        }

        return map;
    }

    public static void main(String[] args) {
        try {
            new Day3().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
