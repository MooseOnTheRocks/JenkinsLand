package dev.foltz.cell;

import dev.foltz.Util;
import dev.foltz.World;

import java.util.Map;

public class CellStone extends Cell {
    public CellStone() {
        super(TypeCell.CELL_STONE, Map.of());
        density = 5f;
        flatness = -1;
    }

    public Cell tick(World world, int x, int y) {
        return this;
    }

    public int color() {
        return Util.color(120, 120, 120);
    }
}
