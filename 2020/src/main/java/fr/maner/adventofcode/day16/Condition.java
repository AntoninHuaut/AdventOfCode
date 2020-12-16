package fr.maner.adventofcode.day16;

public class Condition {

    private final int min;
    private final int max;

    public Condition(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static Condition parse(String expression) {
        String[] parts = expression.split("-");
        return new Condition(Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim()));
    }

    public boolean contains(long value) {
        return this.min <= value && value <= this.max;
    }
}
