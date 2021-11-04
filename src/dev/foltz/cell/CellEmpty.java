package dev.foltz.cell;

import java.util.Map;

import static dev.foltz.cell.TypeCell.CELL_EMPTY;

public final class CellEmpty extends Cell {
    public CellEmpty() {
        super(CELL_EMPTY, Map.of());
        this.density = 0;
    }

    public int color() {
        return 0;
    }

    public boolean ofType(TypeCell type) {
        return type == CELL_EMPTY;
    }

    public boolean isEmpty() {
        return true;
    }
}
