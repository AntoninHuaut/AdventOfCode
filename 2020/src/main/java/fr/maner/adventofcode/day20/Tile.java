package fr.maner.adventofcode.day20;

import fr.maner.adventofcode.utils.ScannerFromFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Tile {

    private final long id;
    private int size = -1;

    private final boolean[][] image;

    public Tile(int id, ScannerFromFile scan) {
        this.id = id;

        List<String> lines = new ArrayList<>();
        String line;
        do {
            line = scan.nextLine();
            if (this.size == -1) this.size = line.length();

            lines.add(line);
        } while (!line.isEmpty() && scan.hasNextLine());

        this.image = new boolean[this.size][this.size];
        IntStream.range(0, lines.size()).forEach(i -> appendData(lines.get(i), i));
    }

    public Tile(long id, boolean[][] image) {
        this.id = id;
        this.image = image;
    }

    public Tile(long id, String[] imgArray) {
        this.id = id;
        this.image = new boolean[imgArray.length][imgArray[0].length()];

        for (int row = 0; row < imgArray.length; row++)
            for (int column = 0; column < imgArray[row].length(); column++)
                this.image[row][column] = imgArray[row].charAt(column) == '#';
    }

    private void appendData(String img, int row) {
        for (int i = 0; i < img.toCharArray().length; i++)
            this.image[row][i] = img.toCharArray()[i] == '#';
    }

    public boolean[][] getImage() {
        return this.image;
    }

    public boolean[][] getTransform(Transform transform) {
        boolean[][] nextImage = Arrays.stream(this.image).map(boolean[]::clone).toArray(boolean[][]::new);

        for (int row = 0; row < this.image.length; row++)
            for (int column = 0; column < this.image[row].length; column++)
                nextImage[row][column] = transform.nextChar(row, column, this.image);

        return nextImage;
    }

    public boolean[] getBorder(TileDirection direction, Transform transform) {
        boolean[] copy = new boolean[this.image.length];

        for (int i = 0; i < this.image.length; ++i)
            copy[i] = switch (direction) {
                case TOP -> transform.nextChar(0, i, this.image);
                case BOT -> transform.nextChar(this.image.length - 1, i, this.image);
                case RIGHT -> transform.nextChar(i, this.image.length - 1, this.image);
                case LEFT -> transform.nextChar(i, 0, this.image);
            };

        return copy;
    }

    public long getId() {
        return this.id;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public String toString() {
        return "Tile{" + this.id + "}";
    }

    public List<String> getImageString() {
        List<String> list = new ArrayList<>();

        for (int row = 0; row < this.image.length; row++) {
            list.add("");
            int index = list.size() - 1;

            for (int column = 0; column < this.image[row].length; column++)
                list.set(index, list.get(index) + (this.image[column][row] ? "#" : "."));
        }

        return list;
    }
}
