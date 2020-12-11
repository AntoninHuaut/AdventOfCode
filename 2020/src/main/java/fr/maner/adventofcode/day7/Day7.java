package fr.maner.adventofcode.day7;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day7 extends Day {

    private final HashMap<String, Set<BagContainer>> bagsSet;

    public Day7() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day07_scanner.txt");
        this.bagsSet = parse(scan);
        scan.close();
    }

    @Override
    public void partOne() {
        Set<String> res = new HashSet<>();
        recursePartOne("shiny gold", res);
        System.out.println(res.size());
    }

    @Override
    public void partTwo() {
        System.out.println(recursePartTwo("shiny gold"));
    }

    private void recursePartOne(String colorSearch, Set<String> result) {
        this.bagsSet.forEach((bagColor, subBags) -> {
            if (bagColor.equals(colorSearch) || result.contains(bagColor)) return;

            if (subBags.stream().anyMatch(el -> el.isBag(colorSearch))) {
                result.add(bagColor);
                recursePartOne(bagColor, result);
            }
        });
    }

    private int recursePartTwo(String colorSearch) {
        Set<BagContainer> bags = this.bagsSet.get(colorSearch);
        int amount = 0;

        for (BagContainer bag : bags) {
            int bagAmount = bag.getAmount();
            String bagName = bag.getBagName();

            amount += bagAmount + bagAmount * recursePartTwo(bagName);
        }

        return amount;
    }

    private HashMap<String, Set<BagContainer>> parse(ScannerFromFile scan) {
        HashMap<String, Set<BagContainer>> bags = new HashMap<>();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split("contain");
            String bagColor = BagContainer.parseName(parts[0]);

            Set<BagContainer> container = new HashSet<>();

            if (!parts[1].contains("no other")) {
                for (String fullBag : parts[1].split(",")) {
                    fullBag = BagContainer.parseName(fullBag);
                    String[] spaceSplit = fullBag.split(" ");

                    int amount = extractBagAmount(spaceSplit);
                    String bagName = extractSubBagName(spaceSplit);

                    container.add(new BagContainer(BagContainer.parseName(bagName), amount));
                }
            }

            bags.put(bagColor, container);
        }

        return bags;
    }

    private int extractBagAmount(String[] split) {
        return Integer.parseInt(split[0]);
    }

    private String extractSubBagName(String[] split) {
        StringBuilder bagName = new StringBuilder();

        for (int i = 1; i < split.length; i++) {
            bagName.append(split[i]);
            bagName.append(" ");
        }

        return bagName.toString();
    }

    public static void main(String[] args) {
        try {
            new Day7().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}