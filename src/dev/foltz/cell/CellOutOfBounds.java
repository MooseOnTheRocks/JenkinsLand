package dev.foltz.cell;

import java.util.Map;

import static dev.foltz.cell.TypeCell.CELL_OUT_OF_BOUNDS;

public final class CellOutOfBounds extends Cell {
    public CellOutOfBounds() {
        super(CELL_OUT_OF_BOUNDS, Map.of());
    }

    public int color() {
        return 0;
    }

    public boolean ofType(TypeCell type) {
        return type == CELL_OUT_OF_BOUNDS;
    }
}
