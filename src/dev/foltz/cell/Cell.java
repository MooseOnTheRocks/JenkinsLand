package dev.foltz.cell;

import dev.foltz.World;

public abstract class Cell {
    public final TypeCell type;

    public Cell(TypeCell type) {
        this.type = type;
    }

    public void tick(World world, int x, int y) { }
    abstract public int color();

    public float friction() {
        return 1f;
    }

    public int flatness() {
        return 1;
    }

    public float density() {
        return 1f;
    }

    public boolean ofType(TypeCell type) { return this.type == type; }
    public boolean isEmpty() { return false; }
}

