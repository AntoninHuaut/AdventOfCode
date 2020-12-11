package fr.maner.adventofcode.utils;

public abstract class Day {

    public void launch() {
        try {
            System.out.println("Part 1 : ");
            partOne();

            System.out.println("\nPart 2 : ");
            partTwo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void partOne() throws Exception;

    public abstract void partTwo() throws Exception;

}