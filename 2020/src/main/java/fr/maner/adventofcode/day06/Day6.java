package fr.maner.adventofcode.day06;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day6 extends Day {

    private final List<String> lines = new ArrayList<>();

    public Day6() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day06_scanner.txt");

        boolean previousEmpty = true;

        while (scan.hasNextLine()) {
            String get = scan.nextLine();
            if (get.isEmpty()) {
                previousEmpty = true;
            }

            if (previousEmpty) {
                this.lines.add(get.trim());
                previousEmpty = false;
            } else {
                int index = this.lines.size() - 1;
                this.lines.set(index, (this.lines.get(index) + " " + get).trim());
            }
        }

        scan.close();
    }

    @Override
    public void partOne() {
        long total = this.lines.stream().map(line -> line.chars().filter(c -> c != ' ').distinct().count()).reduce(Long::sum).orElseThrow();
        System.out.println(total);
    }

    @Override
    public void partTwo() {
        long total = 0;

        for (String line : this.lines) {
            Set<Character> yes = new HashSet<>();
            String[] parts = line.split(" ");
            boolean init = true;

            for (String part : parts) {
                List<Character> cPart = part.chars().distinct().mapToObj(e -> (char) e).collect(Collectors.toList());
                if (init) {
                    init = false;
                    yes.addAll(cPart);
                }
                yes = yes.stream().filter(cPart::contains).collect(Collectors.toSet());
            }

            total += yes.size();
        }

        System.out.println(total);
    }

    public static void main(String[] args) {
        try {
            new Day6().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}