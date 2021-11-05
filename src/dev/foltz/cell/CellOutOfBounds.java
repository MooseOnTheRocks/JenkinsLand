package dev.foltz.cell;

import java.util.Map;

import static dev.foltz.cell.TypeCell.CELL_OUT_OF_BOUNDS;

public final class CellOutOfBounds extends Cell {
    public CellOutOfBounds() {
        super(CELL_OUT_OF_BOUNDS);
    }

    public int color() {
        return 0;
    }
}
