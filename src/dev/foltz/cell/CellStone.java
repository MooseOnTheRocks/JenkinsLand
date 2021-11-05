package dev.foltz.cell;

import dev.foltz.Util;
import dev.foltz.World;

import java.util.Map;

public class CellStone extends Cell {
    public CellStone() {
        super(TypeCell.CELL_STONE);
    }

    public int color() {
        return Util.color(120, 120, 120);
    }
}
