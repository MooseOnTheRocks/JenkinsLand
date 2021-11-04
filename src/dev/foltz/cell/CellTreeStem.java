package dev.foltz.cell;

import dev.foltz.Util;
import dev.foltz.World;

import java.util.Map;

import static dev.foltz.Quanta.JUICE;
import static dev.foltz.Quanta.WATER;
import static dev.foltz.Util.*;

public class CellTreeStem extends Cell {
    public static final int[] ROLL_TREE_GROW = new int[] {0, 0, 0, 0, 1, 3};
    boolean hasGrown = false;
    public CellTreeStem() {
        super(TypeCell.CELL_TREE_STEM, Map.of(WATER, 100, JUICE, 5));
    }

    public Cell tick(World world, int x, int y) {
        if (!hasGrown && amountOf(JUICE) == capacityOf(JUICE)) {
            int choice = ROLL_TREE_GROW[random(ROLL_TREE_GROW.length)];
            Cell cell = switch (choice) {
                case 1 -> world.getCell(x - 1, y);
                case 3 -> world.getCell(x + 1, y);
                default -> world.getCell(x, y - 1);
            };
            if (cell.isEmpty()) {
                CellTreeStem child = new CellTreeStem();
                switch (choice) {
                    case 1 -> world.setCell(x - 1, y, child);
                    case 3 -> world.setCell(x + 1, y, child);
                    default -> world.setCell(x, y - 1, child);
                }
                if (random(1f) > 0.2) {
                    hasGrown = true;
                }
                removeQuanta(JUICE, capacityOf(JUICE));
            }
        }

        Cell other = world.randomNeighbor(x, y);
        if (other.ofType(TypeCell.CELL_TREE_STEM)) {
            int otherJuice = other.amountOf(JUICE);
            if (otherJuice < amountOf(JUICE)) {
                giveQuanta(other, JUICE, 1);
            }
        }

        return this;
    }

    public int color() {
        int colorFrom = Util.color(150, 100, 50);
        int colorJuiced = Util.color(170, 200, 90);
        float factorJuice = map(amountOf(JUICE), 0, capacityOf(JUICE), 0, 1);
        return lerpColor(colorFrom, colorJuiced, factorJuice);
    }
}
