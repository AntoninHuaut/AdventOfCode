package fr.maner.adventofcode.day20;

import fr.maner.adventofcode.utils.Day;
import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.*;

public class Day20 extends Day {

    private final List<Tile> tileList = new ArrayList<>();
    private final List<Map.Entry<Integer, Integer>> monsterPlace = new ArrayList<>();
    private Tile[][] puzzle;

    public Day20(String str) throws Exception {
        ScannerFromFile scan = ScannerFromFile.buildScan(getClass(), str);
        parse(scan);
        scan.close();
    }

    public static void main(String[] args) {
        try {
            new Day20("day20_scanner.txt").launch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parse(ScannerFromFile scan) {
        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.contains("Tile")) {
                String idPart = line.split(" ")[1];
                int id = Integer.parseInt(idPart.substring(0, idPart.length() - 1));

                Tile tile = new Tile(id, scan);
                this.tileList.add(tile);
            }
        }

        int rowColumn = (int) Math.sqrt(this.tileList.size());
        this.puzzle = new Tile[rowColumn][rowColumn];
    }

    @Override
    public void partOne() {
        place(0, 0, new boolean[this.tileList.size()]);

        int length = this.puzzle.length - 1;
        long mul = this.puzzle[0][0].getId() * this.puzzle[0][length].getId() * this.puzzle[length][0].getId() * this.puzzle[length][length].getId();

        System.out.println(mul);
    }

    @Override
    public void partTwo() {
        stripBorder();
        List<List<List<String>>> images = new ArrayList<>();

        for (int row = 0; row < this.puzzle.length; row++) {
            images.add(new ArrayList<>());
            for (int column = 0; column < this.puzzle[row].length; column++) {
                Tile tile = this.puzzle[column][row];

                images.get(images.size() - 1).add(tile.getImageString());
            }
        }

        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(0, 18));

        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 0));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 5));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 6));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 11));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 12));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 17));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 18));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(1, 19));

        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(2, 1));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(2, 4));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(2, 7));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(2, 10));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(2, 13));
        this.monsterPlace.add(new AbstractMap.SimpleEntry<>(2, 16));

        String strImage = getFullImage(images);

        Tile tile = new Tile(-1, strImage.split("\\n"));
        detectMonster(tile);
    }

    private void detectMonster(Tile tile) {
        int countMonster = 0;
        int loop = 0;

        boolean[][] image = new boolean[0][];

        while (countMonster == 0) {
            image = tile.getTransform(Transform.values()[loop]);

            for (int row = 0; row < image.length; row++) {
                for (int column = 0; column < image[row].length; column++) {
                    boolean isMonster = isMonster(row, column, image);

                    if (isMonster)
                        countMonster++;
                }
            }

            loop++;
        }

        int total = 0;

        for (boolean[] booleans : image) {
            for (boolean bool : booleans) {
                if (bool)
                    total++;
            }
        }

        System.out.println(total - countMonster * this.monsterPlace.size());
    }

    private boolean isMonster(int startRow, int startColumn, boolean[][] image) {
        int monsterHeight = 3;
        if (startRow + monsterHeight > image.length) return false;
        int monsterWidth = 20;
        if (startColumn + monsterWidth > image[startRow].length) return false;

        int validCoords = 0;

        for (int row = startRow; row < startRow + monsterHeight; row++) {
            for (int column = startColumn; column < startColumn + monsterWidth; column++) {
                if (isValidCoords(row, column, startRow, startColumn, image))
                    validCoords++;
            }
        }

        return validCoords == this.monsterPlace.size();
    }

    private boolean isValidCoords(int row, int column, int startRow, int startColumn, boolean[][] image) {
        int rowRelative = row - startRow;
        int columnRelative = column - startColumn;

        for (Map.Entry<Integer, Integer> entry : this.monsterPlace) {
            if (entry.getKey() == rowRelative && entry.getValue() == columnRelative) {
                if (image[row][column])
                    return true;
            }
        }

        return false;
    }

    private boolean place(int row, int col, boolean[] memory) {
        for (int index = 0; index < this.tileList.size(); index++) {
            Tile tile = this.tileList.get(index);
            if (memory[index])
                continue;

            for (Transform transform : Transform.values()) {
                boolean[] borderTop = tile.getBorder(TileDirection.TOP, transform);
                boolean[] borderBot = getTileBorder(row - 1, col, TileDirection.BOT);
                boolean[] borderLeft = tile.getBorder(TileDirection.LEFT, transform);
                boolean[] borderRight = getTileBorder(row, col - 1, TileDirection.RIGHT);

                if (borderNotEqual(row, borderTop, borderBot) || borderNotEqual(col, borderLeft, borderRight))
                    continue;

                this.puzzle[row][col] = new Tile(tile.getId(), tile.getTransform(transform));

                int newCol = col + 1;
                int newRow = row;
                if (newCol >= this.puzzle[row].length) {
                    newCol = 0;
                    newRow++;

                    if (newRow >= this.puzzle.length) return true;
                }

                memory[index] = true;

                if (place(newRow, newCol, memory))
                    return true;

                memory[index] = false;
            }
        }

        return false;
    }

    private String getFullImage(List<List<List<String>>> imageList) {
        StringBuilder builder = new StringBuilder();

        for (List<List<String>> oneImageRow : imageList) {
            int lineLength = oneImageRow.get(0).size();

            for (int i = 0; i < lineLength; i++) {
                for (List<String> image : oneImageRow)
                    builder.append(image.get(i));
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private void stripBorder() {
        for (int row = 0; row < this.puzzle.length; row++) {
            for (int column = 0; column < this.puzzle[row].length; column++) {
                Tile tile = this.puzzle[row][column];
                this.tileList.remove(tile);

                Tile newTile = new Tile(tile.getId(), stripTileBorder(tile));
                this.puzzle[row][column] = newTile;
                this.tileList.add(newTile);
            }
        }
    }

    private boolean[][] stripTileBorder(Tile tile) {
        boolean[][] image = tile.getImage();
        int length = image.length - 2;
        boolean[][] newImage = new boolean[length][length];

        for (int row = 1; row <= length; row++) {
            System.arraycopy(image[row], 1, newImage[row - 1], 0, length);
        }

        return newImage;
    }

    private boolean borderNotEqual(int index, boolean[] first, boolean[] second) {
        return index > 0 && !Arrays.equals(first, second);
    }

    private boolean[] getTileBorder(int row, int col, TileDirection direction) {
        if (row < 0 || col < 0) return null;

        return this.puzzle[row][col].getBorder(direction, Transform.R0);
    }
}