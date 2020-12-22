package fr.maner.adventofcode.day02;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

@SuppressWarnings("DuplicatedCode")
public class Day2 extends Day {

    @Override
    public void partOne() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day02_scanner.txt");
        int nbValid = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split(" ");
            String[] numbers = parts[0].split("-");

            int min = Integer.parseInt(numbers[0]);
            int max = Integer.parseInt(numbers[1]);
            char letter = parts[1].charAt(0);
            String password = parts[2];

            long occ = password.chars().filter(l -> l == letter).count();
            boolean validPassword = occ >= min && occ <= max;

            if (validPassword)
                nbValid++;
        }

        scan.close();
        System.out.println(nbValid);
    }

    @Override
    public void partTwo() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "scanner.txt");
        int nbValid = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] parts = line.split(" ");
            String[] numbers = parts[0].split("-");

            int posOne = Integer.parseInt(numbers[0]);
            int posTwo = Integer.parseInt(numbers[1]);
            char letter = parts[1].charAt(0);
            String password = parts[2];

            boolean posOneCheck = password.charAt(posOne - 1) == letter;
            boolean posTwoCheck = password.charAt(posTwo - 1) == letter;

            if (posOneCheck ^ posTwoCheck)
                nbValid++;
        }

        scan.close();
        System.out.println(nbValid);
    }

    public static void main(String[] args) {
        new Day2().launch();
    }

}
