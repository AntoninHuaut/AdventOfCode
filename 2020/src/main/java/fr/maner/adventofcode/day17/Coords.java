package fr.maner.adventofcode.day17;

import java.util.Objects;
import java.util.StringJoiner;

public class Coords implements Cloneable {

    private int x;
    private int y;
    private int z;
    private int w;

    public Coords(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public int getW() {
        return this.w;
    }

    @Override
    public Coords clone() {
        try {
            return (Coords) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return this.x == coords.x && this.y == coords.y && this.z == coords.z && this.w == coords.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z, this.w);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Coords.class.getSimpleName() + "[", "]")
                .add("x=" + this.x)
                .add("y=" + this.y)
                .add("z=" + this.z)
                .add("w=" + this.w)
                .toString();
    }
}
