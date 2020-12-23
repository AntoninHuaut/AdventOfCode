package fr.maner.adventofcode.day20;

public enum Transform {
    R0() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[row][column];
        }
    }, R90() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[column][img.length - 1 - row];
        }
    }, R180() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[img.length - 1 - row][img.length - 1 - column];
        }
    }, R270() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[img.length - 1 - column][row];
        }
    },
    FR0() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[img.length - 1 - row][column];
        }
    }, FR90() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[img.length - 1 - column][img.length - 1 - row];
        }
    }, FR180() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[row][img.length - 1 - column];
        }
    }, FR270() {
        @Override
        boolean nextChar(int row, int column, boolean[][] img) {
            return img[column][row];
        }
    };

    abstract boolean nextChar(int row, int column, boolean[][] img);
}
