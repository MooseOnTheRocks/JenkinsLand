package dev.foltz.cell;

import dev.foltz.World;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static dev.foltz.Util.*;
import static java.lang.Math.round;

public class Ray {
    // Prints each tile coordinate in a
    // straight line path between two coordinates.
    public static void stepTiles(int x1, int y1, int x2, int y2, BiConsumer<Integer, Integer> func) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int adx = abs(dx);
        int ady = abs(dy);
        if (adx >= ady) {
//            System.out.println("Stepping along X");
            stepTilesX(x1, y1, x2, y2, func);
        }
        else {
//            System.out.println("Stepping along Y");
//            stepTilesY(x1, y1, x2, y2);
        }
    }

    // adx >= ady
    private static void stepTilesX(int x1, int y1, int x2, int y2, BiConsumer<Integer, Integer> func) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int adx = abs(dx);
        int ady = abs(dy);

        int upper = adx;
        int lower = ady;
        float slope = (lower == 0 || upper == 0) ? 0 : ((float) (lower + 1) / (float) (upper + 1));

        int sx = sign(dx);
        int sy = sign(dy);
        // Since adx > ady, we will step along x axis
        for (int i = 0; i < adx; i++) {
            int x = x1 + sx * i;
            int y = y1 + sy * floor(slope * i);
            func.accept(x, y);
        }
    }
}
