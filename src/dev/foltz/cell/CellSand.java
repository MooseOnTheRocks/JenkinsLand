package dev.foltz.cell;

import dev.foltz.Util;
import dev.foltz.World;
import dev.foltz.cell.behavior.Gravity;

import java.util.Map;

import static dev.foltz.Quanta.WATER;

public class CellSand extends Cell {
    public CellSand() {
        super(TypeCell.CELL_SAND);
    }

    @Override
    public float friction() {
        return 0.67f;
    }

    @Override
    public int flatness() {
        return 5;
    }

    @Override
    public float density() {
        return 0.5f;
    }

    @Override
    public void tick(World world, int x, int y) {
        Gravity.tick(this, world, x, y);
    }

    public int color() {
        int colorDry = Util.color(230, 220, 160);
        int colorWet = Util.color(140, 140, 180);
//        float factorWet = Util.map(amountOf(WATER), 0, capacityOf(WATER), 0, 1);
//        return Util.lerpColor(colorDry, colorWet, factorWet);
        return colorDry;
    }
}
