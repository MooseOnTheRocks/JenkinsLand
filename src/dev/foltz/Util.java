package dev.foltz;

import dev.foltz.cell.CellEmpty;
import dev.foltz.cell.CellOutOfBounds;

import java.util.Random;

public class Util {
    public static final int[] FAIR_ROLL = new int[] {0, 1, 2, 3};
    public static final int CELL_SIZE = 4;
    public static final CellEmpty EMPTY_CELL = new CellEmpty();
    public static final CellOutOfBounds OUT_OF_BOUNDS_CELL = new CellOutOfBounds();

    public static float lerp(float from, float to, float amount) {
        return BonsaiSketch.INSTANCE.lerp(from, to, amount);
    }

    public static int lerpColor(int colorFrom, int colorTo, float amount) {
        return BonsaiSketch.INSTANCE.lerpColor(colorFrom, colorTo, amount);
    }

    public static final Random RNG = new Random();
    public static float random(float n) {
        return RNG.nextFloat(n);
    }

    public static float random(float min, float max) {
        return RNG.nextFloat(min, max);
    }

    public static int random(int n) {
        return RNG.nextInt(n);
    }

    public static int random(int min, int max) {
        return RNG.nextInt(min, max);
    }

    public static int min(int a, int b) {
        return a <= b ? a : b;
    }

    public static float min(float a, float b) {
        return a <= b ? a : b;
    }

    public static float max(float a, float b) {
        return a >= b ? a : b;
    }

    public static float map(float x, float smin, float smax, float dmin, float dmax) {
        return ((x - smin) / (smax - smin)) * (dmax - dmin) + dmin;
    }

    public static int color(int r, int g, int b) {
        // ARGB
        return (0xff << 24) + (r << 16) + (g << 8) + b;
    }
}
