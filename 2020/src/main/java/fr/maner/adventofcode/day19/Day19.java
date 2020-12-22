package fr.maner.adventofcode.day19;

import fr.maner.adventofcode.day19.rules.Rule;
import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.List;

public class Day19 extends Day {

    private RuleEngine ruleEngine;
    private final List<String> input = new ArrayList<>();

    public Day19(String str) throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), str);
        parse(scan);
        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day19("day19_scanner.txt").launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        List<String> ruleInput = new ArrayList<>();
        boolean isRule = true;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty()) {
                isRule = false;
                continue;
            }

            if (isRule) {
                ruleInput.add(line);
            } else {
                this.input.add(line);
            }
        }

        this.ruleEngine = new RuleEngine(ruleInput);
    }

    @Override
    public void partOne() {
        System.out.println(getValid());
    }

    @Override
    public void partTwo() throws Exception {
        System.out.println(new Day19("day19_scanner_2.txt").getValid());
    }

    public long getValid() {
        Rule mainRule = this.ruleEngine.getRules().get(0);
        return this.input.stream().filter(line -> this.ruleEngine.matches(mainRule, line)).count();
    }
}