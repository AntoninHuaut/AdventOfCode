package fr.maner.adventofcode.day4;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.*;

public class Day4 extends Day {

    private final List<String> mandatory = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
    private final List<String> lines = new ArrayList<>();

    public Day4() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day04_scanner.txt");
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
        int nbValid = 0;

        for (String line : this.lines) {
            String[] parts = line.split(" ");
            Set<String> keyList = new HashSet<>();

            for (String part : parts) {
                keyList.add(part.split(":")[0]);
            }

            boolean allFieldPresent = this.mandatory.stream().filter(el -> !keyList.contains(el)).findFirst().isEmpty();
            if (allFieldPresent) nbValid++;
        }

        System.out.println(nbValid);
    }

    @Override
    public void partTwo() {
        int nbValid = 0;

        for (String line : this.lines) {
            String[] parts = line.split(" ");
            Set<String> keyList = new HashSet<>();

            boolean fieldValueValid = true;

            for (String part : parts) {
                String[] subParts = part.split(":");
                String key = subParts[0];
                String value = subParts[1].trim();
                keyList.add(key);

                if (!KeyEnum.valueOf(key).isValid(value)) fieldValueValid = false;
            }

            boolean allFieldPresent = this.mandatory.stream().filter(el -> !keyList.contains(el)).findFirst().isEmpty();
            if (allFieldPresent && fieldValueValid) nbValid++;
        }

        System.out.println(nbValid);
    }

    public static void main(String[] args) {
        try {
            new Day4().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static class KeyHandle implements KeyVisitor {
        @Override
        public boolean isValidBYR(String el) {
            try {
                int nb = Integer.parseInt(el);
                return nb >= 1920 && nb <= 2002;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        @Override
        public boolean isValidIYR(String el) {
            try {
                int nb = Integer.parseInt(el);
                return nb >= 2010 && nb <= 2020;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        @Override
        public boolean isValidEYR(String el) {
            try {
                int nb = Integer.parseInt(el);
                return nb >= 2020 && nb <= 2030;
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        @Override
        public boolean isValidHGT(String el) {
            String cut = el.substring(0, el.length() - 2);
            int nb;
            try {
                nb = Integer.parseInt(cut);
            } catch (NumberFormatException ex) {
                return false;
            }

            if (el.endsWith("in")) {
                return nb >= 59 && nb <= 76;
            } else if (el.endsWith("cm")) {
                return nb >= 150 && nb <= 193;
            } else {
                return false;
            }
        }

        @Override
        public boolean isValidHCL(String el) {
            return el.startsWith("#") && el.length() == 7;
        }

        private final List<String> valid = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

        @Override
        public boolean isValidECL(String el) {
            return this.valid.contains(el);
        }

        @Override
        public boolean isValidPID(String el) {
            if (el.length() != 9) return false;

            try {
                Integer.parseInt(el);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }

    @SuppressWarnings("unused")
    public enum KeyEnum {
        byr {
            @Override
            public boolean isValid(String el) {
                return new KeyHandle().isValidBYR(el);
            }
        },
        iyr {
            @Override
            public boolean isValid(String el) {
                return new KeyHandle().isValidIYR(el);
            }
        },
        eyr {
            @Override
            public boolean isValid(String el) {
                return new KeyHandle().isValidEYR(el);
            }
        },
        hgt {
            @Override
            public boolean isValid(String el) {
                return new KeyHandle().isValidHGT(el);
            }
        },
        hcl {
            @Override
            public boolean isValid(String el) {
                return new KeyHandle().isValidHCL(el);
            }
        },
        ecl {
            @Override
            public boolean isValid(String el) {
                return new KeyHandle().isValidECL(el);
            }
        },
        pid {
            @Override
            public boolean isValid(String el) {
                return new KeyHandle().isValidPID(el);
            }
        }, cid {
            @Override
            public boolean isValid(String el) {
                return true;
            }
        };

        public abstract boolean isValid(String el);
    }

    @SuppressWarnings("unused")
    public interface KeyVisitor {
        boolean isValidBYR(String el);

        boolean isValidIYR(String el);

        boolean isValidEYR(String el);

        boolean isValidHGT(String el);

        boolean isValidHCL(String el);

        boolean isValidECL(String el);

        boolean isValidPID(String el);
    }
}
