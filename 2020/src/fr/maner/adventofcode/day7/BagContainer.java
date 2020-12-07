package fr.maner.adventofcode.day7;

public class BagContainer {

    private final String bagName;
    private final int amount;

    public BagContainer(String bagName, int amount) {
        this.bagName = bagName;
        this.amount = amount;
    }

    public String getBagName() {
        return this.bagName;
    }

    public int getAmount() {
        return this.amount;
    }

    public boolean isBag(String bagCompare) {
        return this.bagName.equals(bagCompare);
    }

    public static String parseName(String name) {
        return name.replace("bags", "").replace("bag", "").replace(".", "").trim();
    }
}
