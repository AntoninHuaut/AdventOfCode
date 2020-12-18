package fr.maner.adventofcode.day18;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.List;

public class Day18 extends Day {

    private final List<String> expressions = new ArrayList<>();

    public Day18() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day18_scanner.txt");
        parse(scan);
        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day18().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextLine()) {
            this.expressions.add(scan.nextLine());
        }
    }

    @Override
    public void partOne() {
        System.out.println(getSum(false));
    }

    @Override
    public void partTwo() {
        System.out.println(getSum(true));
    }

    private long getSum(boolean partTwo) {
        return this.expressions.stream().mapToLong(el -> new ExpressionParser(partTwo, el).resolveRPN()).sum();
    }
}