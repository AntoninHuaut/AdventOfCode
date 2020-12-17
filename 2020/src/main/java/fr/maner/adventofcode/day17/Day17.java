package fr.maner.adventofcode.day17;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day17 extends Day {

    private final HashMap<Coords, Boolean> map = new HashMap<>();

    private int maxX = 0;
    private int minX = 0;

    private int maxY = 0;
    private int minY = 0;

    private int minZ = 0;
    private int maxZ = 0;

    private int minW = 0;
    private int maxW = 0;

    public Day17() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day17_scanner.txt");
        parse(scan);
        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day17().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        int initX = 0;

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            int initY = 0;
            for (char c : line.toCharArray()) {
                this.minX = Math.min(this.minX, initX);
                this.maxX = Math.max(this.maxX, initX);
                this.minY = Math.min(this.minY, initY);
                this.maxY = Math.max(this.maxY, initY);

                Coords coords = new Coords(initX, initY++, 0, 0);
                this.map.put(coords, c == '#');
            }
            initX++;
        }
    }

    @Override
    public void partOne() {
        System.out.println(run(false));
    }

    @Override
    public void partTwo() {
        System.out.println(run(true));
    }

    private long run(boolean partTwo) {
        HashMap<Coords, Boolean> workingMap = new HashMap<>();
        this.map.forEach(workingMap::put);

        for (int cycle = 0; cycle < 6; cycle++) {
            this.minX--;
            this.minY--;
            this.minZ--;
            this.maxX++;
            this.maxY++;
            this.maxZ++;

            if (partTwo) {
                this.minW--;
                this.maxW++;
            }

            HashMap<Coords, Boolean> tmp = new HashMap<>();

            for (int iw = this.minW; iw <= this.maxW; iw++) {
                for (int iz = this.minZ; iz <= this.maxZ; iz++) {
                    for (int ix = this.minX; ix <= this.maxX; ix++) {
                        for (int iy = this.minY; iy <= this.maxY; iy++) {
                            Coords loopCoords = new Coords(ix, iy, iz, iw);
                            long neighborsActive = getNeighbors(partTwo, workingMap, loopCoords).stream().filter(e -> e).count();
                            boolean isActive = workingMap.getOrDefault(loopCoords, false);

                            tmp.put(loopCoords, neighborsActive == 3 || (neighborsActive == 2 && isActive));
                        }
                    }
                }
            }

            tmp.forEach(workingMap::put);
        }

        return workingMap.values().stream().filter(e -> e).count();
    }

    private List<Boolean> getNeighbors(boolean partTwo, HashMap<Coords, Boolean> workingMap, Coords coords) {
        List<Boolean> set = new ArrayList<>();

        int subMinW = partTwo ? coords.getW() - 1 : 0;
        int subMaxW = partTwo ? coords.getW() + 1 : 0;

        for (int iw = subMinW; iw <= subMaxW; iw++) {
            for (int iz = coords.getZ() - 1; iz <= coords.getZ() + 1; iz++) {
                for (int ix = coords.getX() - 1; ix <= coords.getX() + 1; ix++) {
                    for (int iy = coords.getY() - 1; iy <= coords.getY() + 1; iy++) {
                        Coords loopCoords = new Coords(ix, iy, iz, iw);
                        Boolean neighbor = workingMap.getOrDefault(loopCoords, false);

                        if (!loopCoords.equals(coords))
                            set.add(neighbor);
                    }
                }
            }
        }

        return set;
    }
}