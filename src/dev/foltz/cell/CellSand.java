package dev.foltz.cell;

import dev.foltz.Util;
import dev.foltz.World;

import java.util.Map;

import static dev.foltz.Quanta.WATER;

public class CellSand extends Cell {
    private static final int[] WATER_ROLL = new int[] {0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3};

    public CellSand() {
        super(TypeCell.CELL_SAND, Map.of(WATER, 5));
        flatness = 5;
//        this.density = 0.5f;
    }

    @Override
    public Cell tick(World world, int x, int y) {
        Cell neighbor = world.randomNeighbor(x, y, WATER_ROLL);
        giveQuanta(neighbor, WATER, 1);
        density = Util.map(amountOf(WATER), 0, capacityOf(WATER), 0.5f, 3f);
        flatness = (int) Util.map(amountOf(WATER), 0, capacityOf(WATER), 5, 10);
        return super.updateGravity(world, x, y);
    }

    public int color() {
        int colorDry = Util.color(230, 220, 160);
        int colorWet = Util.color(140, 140, 180);
        float factorWet = Util.map(amountOf(WATER), 0, capacityOf(WATER), 0, 1);
        return Util.lerpColor(colorDry, colorWet, factorWet);
    }
}
