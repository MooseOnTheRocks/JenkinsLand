package dev.foltz.cell;

import dev.foltz.Util;
import dev.foltz.World;
import dev.foltz.cell.behavior.Gravity;

import static dev.foltz.cell.TypeCell.CELL_DIRT;

public class CellDirt extends Cell {
    private static final int[] WATER_ROLL = new int[] {0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3};
    private static final int[] JUICE_ROLL = new int[] {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 3};

    public CellDirt() {
        super(CELL_DIRT);
    }

    @Override
    public float friction() {
        return 1f;
    }

    @Override
    public int flatness() {
        return 3;
    }

    @Override
    public float density() {
        return 1f;
    }

    public void tick(World world, int x, int y) {
        Gravity.tick(this, world, x, y);
    }

    public int color() {
//        float factorWater = map(amountOf(WATER), 0, capacityOf(WATER), 40, 200);
//        float factorJuice = map(amountOf(JUICE), 0, capacityOf(JUICE), 60, 230);
//        return Util.color(100, (int) factorJuice, (int) factorWater);
        return Util.color(100, 60, 40);
    }
}
