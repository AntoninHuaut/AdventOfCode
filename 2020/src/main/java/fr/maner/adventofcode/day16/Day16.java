package fr.maner.adventofcode.day16;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 extends Day {

    private final List<Rule> rules = new ArrayList<>();
    private final List<Ticket> nearbyTicket = new ArrayList<>();
    private Ticket myTicket;

    public Day16() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day16_scanner.txt");
        parse(scan);
        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day16().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        int countEmptyLine = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.isEmpty()) {
                countEmptyLine++;
                scan.nextLine();
                continue;
            }
            switch (countEmptyLine) {
                case 0 -> this.rules.add(Rule.parse(line));
                case 1 -> this.myTicket = Ticket.parse(line);
                case 2 -> this.nearbyTicket.add(Ticket.parse(line));
            }
        }
    }

    @Override
    public void partOne() {
        long sum = 0;

        for (Ticket nearby : this.nearbyTicket) {
            long value = nearby.getInvalidValue(this.rules);
            if (value != -1)
                sum += value;
        }

        System.out.println(sum);
    }

    @Override
    public void partTwo() {
        this.nearbyTicket.removeAll(this.nearbyTicket.stream().filter(el -> el.getInvalidValue(this.rules) != -1).collect(Collectors.toList()));
        initPossiblePosition();
        resolvePosition();

        long mul = this.rules.stream()
                .filter(rule -> rule.getName().startsWith("departure"))
                .map(rule -> this.myTicket.getNumberByPosition(rule.getPositionList().get(0)))
                .reduce(1L, (i, j) -> i * j);

        System.out.println(mul);
    }

    private void initPossiblePosition() {
        this.rules.forEach(rule -> IntStream.range(0, this.myTicket.getTicketNumber().size())
                .filter(pos -> this.nearbyTicket.stream().allMatch(tk -> rule.contains(tk.getNumberByPosition(pos))))
                .forEach(rule::addPosition)
        );
    }

    private void resolvePosition() {
        List<Rule> ruleClone = new ArrayList<>(this.rules);

        while (!ruleClone.isEmpty()) {
            Rule rulePos = ruleClone.stream()
                    .filter(rule -> rule.getPositionList().size() == 1)
                    .findFirst().orElseThrow();

            ruleClone.remove(rulePos);
            ruleClone.forEach(rule -> rule.removePosition(rulePos.getPositionList().get(0)));
        }
    }
}