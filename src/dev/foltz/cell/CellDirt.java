package dev.foltz.cell;

import dev.foltz.Util;
import dev.foltz.World;

import java.util.Map;

import static dev.foltz.cell.TypeCell.CELL_DIRT;
import static dev.foltz.Quanta.JUICE;
import static dev.foltz.Quanta.WATER;
import static dev.foltz.Util.*;

public class CellDirt extends Cell {
    private static final int[] WATER_ROLL = new int[] {0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3};
    private static final int[] JUICE_ROLL = new int[] {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3};

    public CellDirt() {
        super(CELL_DIRT, Map.of(WATER, 50, JUICE, 100));
//        this.flatness = 3;
//        this.density = 1;
    }

    public Cell tick(World world, int x, int y) {
        // Transform water into juice
        if (random(1f) > 0.97 && amountOf(WATER) >= 1 && amountOf(JUICE) < capacityOf(JUICE)) {
            removeQuanta(WATER, 1);
            receiveQuanta(JUICE, 2);
        }
        // Transfer water
        Cell neighbor = world.randomNeighbor(x, y, WATER_ROLL);
        giveQuanta(neighbor, WATER, 1);
        // Transfer juice
        neighbor = world.randomNeighbor(x, y, JUICE_ROLL);
        giveQuanta(neighbor, JUICE, 1);

        density = Util.map(amountOf(WATER), 0, capacityOf(WATER), 2f, 4f);
        flatness = (int) Util.map(amountOf(WATER), 0, capacityOf(WATER), 3, 5);
        return super.updateGravity(world, x, y);
    }

    public int color() {
        float factorWater = map(amountOf(WATER), 0, capacityOf(WATER), 40, 200);
        float factorJuice = map(amountOf(JUICE), 0, capacityOf(JUICE), 60, 230);
        return Util.color(100, (int) factorJuice, (int) factorWater);
    }
}
