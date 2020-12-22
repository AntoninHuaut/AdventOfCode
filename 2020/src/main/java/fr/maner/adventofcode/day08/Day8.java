package fr.maner.adventofcode.day08;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Day8 extends Day {

    private final List<Instruct> list = new ArrayList<>();

    public Day8() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day08_scanner.txt");

        parse(scan);

        scan.close();
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextLine()) {
            String[] line = scan.nextLine().split(" ");
            this.list.add(new Instruct(InstructEnum.valueOf(line[0].toUpperCase()), Integer.parseInt(line[1])));
        }
    }

    @Override
    public void partOne() {
        System.out.println(runProgram().getValue());
    }

    @Override
    public void partTwo() {
        Map.Entry<Boolean, Integer> acc = null;

        for (Instruct instruct : this.list) {
            InstructEnum original = instruct.getInstruction();

            switch (original) {
                case NOP -> {
                    if (instruct.getValue() != 0) {
                        instruct.setInstruction(InstructEnum.JMP);
                    }
                }
                case JMP -> instruct.setInstruction(InstructEnum.NOP);
            }

            acc = runProgram();
            if (acc.getKey()) break;

            instruct.setInstruction(original);
        }

        System.out.println(acc == null ? "Not found" : acc.getValue());
    }

    private Map.Entry<Boolean, Integer> runProgram() {
        int index = 0;
        int accumulator = 0;

        while (index < this.list.size() && !this.list.get(index).isVisited()) {
            Instruct instruct = this.list.get(index);

            switch (instruct.getInstruction()) {
                case NOP -> index++;
                case ACC -> {
                    accumulator += instruct.getValue();
                    index++;
                }
                case JMP -> index += instruct.getValue();
            }

            instruct.setVisited(true);
        }

        reset();
        return new AbstractMap.SimpleEntry<>(index >= this.list.size(), accumulator);
    }

    private void reset() {
        this.list.forEach(el -> el.setVisited(false));
    }

    private static class Instruct {

        private InstructEnum instruction;

        private final int value;

        private boolean visited;

        private Instruct(InstructEnum instruction, int value) {
            this.instruction = instruction;
            this.value = value;
            this.visited = false;
        }

        public InstructEnum getInstruction() {
            return this.instruction;
        }

        public void setInstruction(InstructEnum instruction) {
            this.instruction = instruction;
        }

        public int getValue() {
            return this.value;
        }

        public boolean isVisited() {
            return this.visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }
    }

    public enum InstructEnum {
        NOP,
        ACC,
        JMP
    }

    public static void main(String[] args) {
        try {
            new Day8().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}