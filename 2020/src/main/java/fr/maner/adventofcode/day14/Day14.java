package fr.maner.adventofcode.day14;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.math.BigInteger;
import java.util.*;

public class Day14 extends Day {

    private final List<Mask> list = new ArrayList<>();

    public Day14() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day14_scanner.txt");

        parse(scan);

        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day14().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.startsWith("mask")) {
                this.list.add(new Mask(line));
            } else {
                this.list.get(this.list.size() - 1).addMem(new Mem(line));
            }
        }

        scan.close();
    }

    @Override
    public void partOne() {
        HashMap<Long, Long> memory = new HashMap<>();

        for (Mask mask : this.list) {
            for (Mem mem : mask.getMemList()) {
                memory.put(mem.getAddress(), mem.getValueP1(mask));
            }
        }

        long sum = memory.values().stream().mapToLong(e -> e).sum();
        System.out.println(sum);
    }

    @Override
    public void partTwo() {
        HashMap<Long, Long> memory = new HashMap<>();

        for (Mask mask : this.list) {
            for (Mem mem : mask.getMemList()) {
                for (Long add : mem.getAllAddressP2(mask)) {
                    memory.put(add, mem.getValue());
                }
            }
        }

        long sum = memory.values().stream().mapToLong(e -> e).sum();
        System.out.println(sum);
    }

    public static class Mask {

        private final String mask;
        private final List<Mem> memList = new ArrayList<>();

        public Mask(String line) {
            this.mask = extractMask(line);
        }

        public String getMask() {
            return this.mask;
        }

        public void addMem(Mem mem) {
            this.memList.add(mem);
        }

        public List<Mem> getMemList() {
            return this.memList;
        }

        public static String extractMask(String line) {
            return line.split("=")[1].trim();
        }
    }

    public static class Mem {

        private final long address;
        private final long value;

        public Mem(String line) {
            this.address = extractAddress(line);
            this.value = extractValue(line);
        }

        public long getValueP1(Mask mask) {
            String valueStr = String.format("%036d", new BigInteger(Long.toBinaryString(this.value)));
            StringBuilder valueBuild = new StringBuilder(valueStr);
            String maskStr = mask.getMask();

            for (int i = 0; i < maskStr.length(); i++)
                if (maskStr.charAt(i) != 'X')
                    valueBuild.setCharAt(i, maskStr.charAt(i));

            return Long.parseLong(valueBuild.toString(), 2);
        }

        public long getValue() {
            return this.value;
        }

        public Set<Long> getAllAddressP2(Mask mask) {
            String addressStr = String.format("%036d", new BigInteger(Long.toBinaryString(this.address)));
            StringBuilder addressBuild = new StringBuilder(addressStr);
            String maskStr = mask.getMask();

            for (int i = 0; i < maskStr.length(); i++)
                if (maskStr.charAt(i) != '0')
                    addressBuild.setCharAt(i, maskStr.charAt(i));

            return generateAllAddress(new HashSet<Long>(), addressBuild.toString());
        }

        private Set<Long> generateAllAddress(Set<Long> addList, String add) {
            if (!add.contains("X")) {
                addList.add(Long.parseLong(add, 2));
            } else {
                generateAllAddress(addList, add.replaceFirst("X", "1"));
                generateAllAddress(addList, add.replaceFirst("X", "0"));
            }

            return addList;
        }

        public long getAddress() {
            return this.address;
        }

        public static long extractAddress(String line) {
            return Long.parseLong(line.split("=")[0].split("\\[")[1].split("]")[0].trim());
        }

        public static long extractValue(String line) {
            return Long.parseLong(line.split("=")[1].trim());
        }
    }
}