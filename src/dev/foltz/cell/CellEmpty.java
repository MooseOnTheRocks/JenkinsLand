package dev.foltz.cell;

import java.util.Map;

import static dev.foltz.cell.TypeCell.CELL_EMPTY;

public final class CellEmpty extends Cell {
    public CellEmpty() {
        super(CELL_EMPTY);
    }

    @Override
    public float density() {
        return 0f;
    }

    public int color() {
        return 0;
    }

    public boolean isEmpty() {
        return true;
    }
}
