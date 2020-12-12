package fr.maner.adventofcode.day12;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Day12 extends Day {

    private final List<Move> list = new ArrayList<>();

    public Day12() throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), "day12_scanner.txt");

        parse(scan);

        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day12().launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            DirectionEnum direction = DirectionEnum.valueOf("" + line.charAt(0));
            int number = Integer.parseInt(line.substring(1));
            this.list.add(new Move(direction, number));
        }
        scan.close();
    }

    @Override
    public void partOne() {
        Ship ship = new Ship(false);
        ship.move(this.list);

        System.out.println(ship.getPoint());
        System.out.println(ship.getManhattanDistance());
    }

    @Override
    public void partTwo() {
        Ship ship = new Ship(true);
        ship.move(this.list);

        System.out.println(ship.getPoint());
        System.out.println(ship.getManhattanDistance());
    }

    public static class Move {

        private final DirectionEnum direction;
        private final int value;

        public Move(DirectionEnum direction, int value) {
            this.direction = direction;
            this.value = value;
        }

        public DirectionEnum getDirection() {
            return this.direction;
        }

        public int getValue() {
            return this.value;
        }
    }

    public static class Waypoint {

        private final Point point = new Point();

        public Waypoint() {
            this.point.setLocation(10, 1);
        }


        public Point getPoint() {
            return this.point;
        }

        public void move(Move move) {
            switch (move.getDirection()) {
                case N, S -> {
                    int valueY = move.getValue();
                    if (move.getDirection() == DirectionEnum.S) valueY *= -1;

                    this.point.setLocation(this.point.getX(), this.point.getY() + valueY);
                }
                case E, W -> {
                    int valueX = move.getValue();
                    if (move.getDirection() == DirectionEnum.W) valueX *= -1;

                    this.point.setLocation(this.point.getX() + valueX, this.point.getY());
                }
            }
        }

        public void rotate(Move move) {
            int valueD = move.getValue() / 90;

            for (int i = 0; i < valueD; i++) {
                double x = this.point.getX();
                double y = this.point.getY();

                double newX;
                double newY;
                if (move.getDirection() == DirectionEnum.L) {
                    newX = -y;
                    newY = x;
                } else {
                    newX = y;
                    newY = -x;
                }
                this.point.setLocation(newX, newY);
            }
        }
    }

    public static class Ship {

        private final boolean partTwo;

        private Day12.DirectionEnum facingPartOne = Day12.DirectionEnum.E;

        private final Point point = new Point();
        private final Waypoint waypoint;

        public Ship(boolean partTwo) {
            this.partTwo = partTwo;
            this.point.setLocation(0, 0);
            this.waypoint = new Waypoint();
        }

        public void move(List<Move> list) {
            list.forEach(el -> {
                if (this.partTwo) movePartTwo(el);
                else movePartOne(el);
            });
        }

        public void movePartOne(Move move) {
            switch (move.getDirection()) {
                case N, S -> {
                    int valueY = move.getValue();
                    if (move.getDirection() == Day12.DirectionEnum.S) valueY *= -1;

                    this.point.setLocation(this.point.getX(), this.point.getY() + valueY);
                }
                case E, W -> {
                    int valueX = move.getValue();
                    if (move.getDirection() == Day12.DirectionEnum.W) valueX *= -1;

                    this.point.setLocation(this.point.getX() + valueX, this.point.getY());
                }
                case L, R -> {
                    int valueD = move.getValue() / 90;

                    for (int i = 0; i < valueD; i++) {
                        if (move.getDirection() == Day12.DirectionEnum.L)
                            this.facingPartOne = this.facingPartOne.previous();
                        else
                            this.facingPartOne = this.facingPartOne.next();
                    }
                }
                case F -> {
                    switch (this.facingPartOne) {
                        case N -> this.point.setLocation(this.point.getX(), this.point.getY() + move.getValue());
                        case E -> this.point.setLocation(this.point.getX() + move.getValue(), this.point.getY());
                        case S -> this.point.setLocation(this.point.getX(), this.point.getY() - move.getValue());
                        case W -> this.point.setLocation(this.point.getX() - move.getValue(), this.point.getY());
                    }
                }
            }
        }

        public void movePartTwo(Move move) {
            switch (move.getDirection()) {
                case N, S, E, W -> this.waypoint.move(move);
                case L, R -> this.waypoint.rotate(move);
                case F -> {
                    double x = this.waypoint.getPoint().getX();
                    double y = this.waypoint.getPoint().getY();
                    this.point.setLocation(this.point.getX() + move.getValue() * x, this.point.getY() + move.getValue() * y);
                }
            }
        }

        public Point getPoint() {
            return this.point;
        }

        public int getManhattanDistance() {
            return Math.abs((int) this.point.getX()) + Math.abs((int) this.point.getY());
        }
    }

    public enum DirectionEnum {
        N,
        E,
        S,
        W,

        L,
        R,
        F;

        public DirectionEnum previous() {
            if (this == DirectionEnum.N) return DirectionEnum.W;

            DirectionEnum[] values = DirectionEnum.values();
            int index = this.ordinal() - 1;
            return values[index < 0 ? values.length - 1 : index];
        }

        public DirectionEnum next() {
            if (this == DirectionEnum.W) return DirectionEnum.N;

            DirectionEnum[] values = DirectionEnum.values();
            return values[(this.ordinal() + 1) % values.length];
        }
    }
}