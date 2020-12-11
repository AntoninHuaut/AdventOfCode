package fr.maner.adventofcode.day11;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Day11 extends Day {

    private List<Line> list = new ArrayList<>();

    public Day11() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "scanner.txt");

        parse(scan);

        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day11().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextLine()) {
            this.list.add(new Line(scan.nextLine()));
        }
        scan.close();
    }

    @Override
    public void partOne() {
        System.out.println(countSeat(false));
    }

    @Override
    public void partTwo() {
        this.list.forEach(line -> line.getList().stream().filter(c -> c.getCaseType().equals(CaseType.OCCUPIED)).forEach(c -> c.setType(CaseType.EMPTY)));

        System.out.println(countSeat(true));
    }

    private long countSeat(boolean partTwo) {
        int lastHash, currentHash;

        do {
            lastHash = this.list.hashCode();
            simulateRound(partTwo);
            currentHash = this.list.hashCode();
        } while (lastHash != currentHash);

        long seatOccupied = 0;

        for (Line line : this.list) {
            seatOccupied += line.getList().stream().filter(el -> el.getCaseType().equals(CaseType.OCCUPIED)).count();
        }

        return seatOccupied;
    }

    private void simulateRound(boolean partTwo) {
        List<Line> next = new ArrayList<>();
        this.list.forEach(line -> next.add(line.clone()));

        for (int line = 0; line < this.list.size(); line++) {
            List<Case> caseList = this.list.get(line).getList();
            for (int row = 0; row < caseList.size(); row++) {
                Case currentCase = caseList.get(row);

                List<Case> near;
                if (partTwo) {
                    near = getNearSight(line, row);
                } else {
                    near = getNear(line, row);
                }

                switch (currentCase.getCaseType()) {
                    case FLOOR -> {
                    }
                    case EMPTY -> {
                        boolean allNearEmpty = near.stream().noneMatch(pred -> pred.getCaseType().equals(CaseType.OCCUPIED));
                        if (allNearEmpty) {
                            next.get(line).getList().get(row).setType(CaseType.OCCUPIED);
                        }
                    }
                    case OCCUPIED -> {
                        long nbOccupied = near.stream().filter(pred -> pred.getCaseType().equals(CaseType.OCCUPIED)).count();
                        long limit = partTwo ? 5 : 4;

                        if (nbOccupied >= limit) {
                            next.get(line).getList().get(row).setType(CaseType.EMPTY);
                        }
                    }
                }
            }
        }

        this.list = next;
    }

    private List<Case> getNearSight(int line, int row) {
        List<Case> nearList = new ArrayList<>();

        for (int iLine = line - 1; iLine <= line + 1; iLine++) {
            if (iLine < 0 || iLine >= this.list.size()) continue;

            for (int iRow = row - 1; iRow <= row + 1; iRow++) {
                if ((line == iLine && row == iRow) || iRow < 0) continue;

                int linePadding = iLine - line;
                int rowPadding = iRow - row;

                if (linePadding == 0 && rowPadding == 0) continue;

                int loopLine = iLine;
                int loopRow = iRow;

                Case notEmptyCase;
                do {
                    if (validCord(loopLine, loopRow)) {
                        notEmptyCase = this.list.get(loopLine).getList().get(loopRow);
                    } else {
                        notEmptyCase = null;
                    }

                    loopLine += linePadding;
                    loopRow += rowPadding;
                } while (notEmptyCase != null && notEmptyCase.getCaseType().equals(CaseType.FLOOR));

                loopLine -= linePadding;
                loopRow -= rowPadding;

                if (notEmptyCase != null && (line != loopLine || row != loopRow)) {
                    nearList.add(notEmptyCase);
                }
            }
        }

        return nearList;
    }

    private boolean validCord(int line, int row) {
        return line >= 0 && line < this.list.size() && row >= 0 && row < this.list.get(line).getList().size();
    }

    private List<Case> getNear(int line, int row) {
        List<Case> nearList = new ArrayList<>();

        for (int iLine = line - 1; iLine <= line + 1; iLine++) {
            if (iLine < 0 || iLine >= this.list.size()) continue;

            for (int iRow = row - 1; iRow <= row + 1; iRow++) {
                if ((line == iLine && row == iRow) || iRow < 0) continue;

                List<Case> caseList = this.list.get(iLine).getList();

                if (iRow < caseList.size()) {
                    nearList.add(caseList.get(iRow));
                }
            }
        }

        return nearList;
    }

    private enum CaseType {
        FLOOR("."),
        EMPTY("L"),
        OCCUPIED("#");

        private final String str;

        CaseType(String str) {
            this.str = str;
        }

        public static CaseType getEnumByValue(String str) {
            for (CaseType type : CaseType.values())
                if (type.toString().equals(str))
                    return type;
            return null;
        }

        @Override
        public String toString() {
            return this.str;
        }
    }

    private static class Line implements Cloneable {

        private List<Case> list = new ArrayList<>();

        public Line(String line) {
            for (int i = 0; i < line.length(); i++) {
                this.list.add(new Case(line.charAt(i)));
            }
        }

        public List<Case> getList() {
            return this.list;
        }

        @Override
        public Line clone() {
            try {
                Line line = (Line) super.clone();
                this.list = this.list.stream().map(Case::clone).collect(Collectors.toList());
                return line;
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Line.class.getSimpleName() + "[", "]")
                    .add("list=" + this.list)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return Objects.equals(this.list, line.list);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.list);
        }
    }

    private static class Case implements Cloneable {

        private CaseType type;

        public Case(char str) {
            this.type = CaseType.getEnumByValue("" + str);
        }

        public void setType(CaseType type) {
            this.type = type;
        }

        public CaseType getCaseType() {
            return this.type;
        }

        @Override
        public Case clone() {
            try {
                return (Case) super.clone();
            } catch (CloneNotSupportedException e) {
                return null;
            }
        }

        @Override
        public String toString() {
            return this.type.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Case aCase = (Case) o;
            return this.type == aCase.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.type);
        }
    }
}