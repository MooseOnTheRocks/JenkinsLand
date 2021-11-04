package dev.foltz.cell;

import dev.foltz.Quanta;
import dev.foltz.Util;
import dev.foltz.World;

import java.util.Map;

import static dev.foltz.Quanta.WATER;
import static dev.foltz.Util.*;
import static dev.foltz.cell.TypeCell.CELL_WATER;

public class CellWater extends Cell {
    public static final int[] ROLL_WATER_GROUND = new int[] {0, 1, 1, 2, 2, 2, 3, 3};
    public CellWater(int water) {
        super(CELL_WATER, Map.of(WATER, 10));
        receiveQuanta(WATER, water);
        density = 0.25f;
        flatness = 15;
    }

    public Cell tick(World world, int x, int y) {
        Cell neighbor = world.randomNeighbor(x, y, ROLL_WATER_GROUND);
        Cell a2 = world.getCell(x, y - 2);
        Cell a1 = world.getCell(x, y - 1);
        Cell b1 = world.getCell(x, y + 1);
        Cell b2 = world.getCell(x, y + 2);
        float pa2 = a2.capacityOf(WATER) == 0 ? 0 : a2.amountOf(WATER) / (float) a2.capacityOf(WATER);
        float pa1 = a1.capacityOf(WATER) == 0 ? 0 : a1.amountOf(WATER) / (float) a1.capacityOf(WATER);
        float pb1 = b1.capacityOf(WATER) == 0 ? 0 : b1.amountOf(WATER) / (float) b1.capacityOf(WATER);
        float pb2 = b2.capacityOf(WATER) == 0 ? 0 : b2.amountOf(WATER) / (float) b2.capacityOf(WATER);
        float pressureDiff = (pb2 + pb1) - (pa2 + pa1);
        if (pressureDiff < 0.1) {
            giveQuanta(b1, WATER, 1);
        }
        else if (neighbor.amountOf(WATER) < amountOf(WATER) || random(1f) > 0.9f) {
            giveQuanta(neighbor, WATER, 1);
        }
        if (amountOf(WATER) == 0) {
            return EMPTY_CELL;
        }
        density = Util.map(amountOf(WATER), 0, capacityOf(WATER), 0.2f, 1f);
        return super.updateGravity(world, x, y);
    }

    public int color() {
        int colorFew = Util.color(80, 80, 255);
        int colorMany = Util.color(70, 80, 100);
        float factorWater = Util.map(amountOf(WATER), 0, capacityOf(WATER), 0, 1);
        return Util.lerpColor(colorFew, colorMany, factorWater);
    }
}
