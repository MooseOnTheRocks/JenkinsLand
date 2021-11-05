package dev.foltz.cell.behavior;

import dev.foltz.Util;
import dev.foltz.World;
import dev.foltz.cell.Cell;

import static dev.foltz.Util.*;

public class Gravity {
    public static void tick(Cell self, World world, int x, int y) {
        float density = self.density();
        if (world.getCell(x, y + 1).isEmpty()) {
            world.swap(x, y + 1);
        }
        else if (world.getCell(x, y + 1).density() < density && chance(0.95f)) {
            Cell cell = world.getCell(x, y + 1);
            if (cell == OUT_OF_BOUNDS_CELL) return;
            float p = min(cell.density(), density) / max(cell.density(), density);
            if (!chance(p)) {
                return;
            }
            world.swap(x, y + 1);
        }
        else {
            int s = random(2) == 0 ? -1 : 1;
            boolean shouldFall = false;
            boolean adjacent = false;
            for (int i = 1; i <= self.flatness(); i++) {
                if (!(world.getCell(x + i * s, y).density() < density)) {
                    break;
                }
                else if (world.getCell(x + i*s, y + 1).isEmpty()
                        || (world.getCell(x + i*s, y + 1).density() < density && chance(0.97f))) {
                    shouldFall = true;
                    adjacent = i == 1;
                    break;
                }
            }

            if (shouldFall) {
                if (adjacent) {
                    world.swap(x + s, y + 1);
                }
                else {
                    world.swap(x + s, y);
                }
            }
        }
    }
}
