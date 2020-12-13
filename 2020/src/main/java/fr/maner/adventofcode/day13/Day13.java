package fr.maner.adventofcode.day13;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Day13 extends Day {

    private int earliest;
    private final List<Bus> list = new ArrayList<>();

    public Day13() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day13_scanner.txt");

        parse(scan);

        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day13().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        this.earliest = Integer.parseInt(scan.nextLine());

        String[] values = scan.nextLine().split(",");
        int tAfter = 0;

        for (String s : values) {
            try {
                this.list.add(new Bus(Integer.parseInt(s), tAfter));
            } catch (NumberFormatException ex) {
                // Ignore
            }
            tAfter++;
        }

        scan.close();
    }

    @Override
    public void partOne() {
        int busId;
        int time = this.earliest;

        do {
            busId = getBusIDForTime(time);
            if (busId == -1) time++;
        } while (busId == -1);

        System.out.println(busId * (time - this.earliest));
    }

    /* https://fr.wikipedia.org/wiki/Th%C3%A9or%C3%A8me_des_restes_chinois */
    @Override
    public void partTwo() {
        BigInteger product = BigInteger.valueOf(this.list.stream().mapToLong(Bus::getBusId).reduce((i, j) -> i * j).orElseThrow());
        BigInteger sum = BigInteger.valueOf(0);

        for (Bus bus : this.list) {
            BigInteger busId = BigInteger.valueOf(bus.getBusId());
            BigInteger timeIndex = BigInteger.valueOf(bus.getTimeIndex());

            BigInteger partialProduct = product.divide(busId);
            BigInteger invMod = partialProduct.modInverse(busId);
            BigInteger mul = partialProduct.multiply(invMod.multiply(timeIndex));

            sum = sum.add(mul);
        }

        System.out.println(sum.remainder(product));
    }

    private int getBusIDForTime(int time) {
        for (Bus bus : this.list) {
            if (time % bus.getBusId() == 0) {
                return bus.getBusId();
            }
        }

        return -1;
    }

    public static class Bus {

        private final int busId;
        private final int timeIndex;

        public Bus(int busId, int tAfter) {
            this.busId = busId;
            this.timeIndex = busId - tAfter;
        }

        public int getBusId() {
            return this.busId;
        }

        public int getTimeIndex() {
            return this.timeIndex;
        }
    }
}