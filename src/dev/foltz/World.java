package dev.foltz;

import dev.foltz.cell.Cell;
import processing.core.PApplet;

import java.util.Arrays;

import static dev.foltz.Util.*;
import static dev.foltz.cell.TypeCell.CELL_OUT_OF_BOUNDS;

public class World {
    public final int width, height;
    private Cell[] cells;
    private int[] tickOrder;
    int worldRenderOffsetX, worldRenderOffsetY;
    int currentTileIndex;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new Cell[width * height];
        Arrays.fill(cells, EMPTY_CELL);
        this.tickOrder = new int[cells.length];
        for (int i = 0; i < tickOrder.length; i++) {
            tickOrder[i] = i;
        }
    }

    private void shuffleTickOrder() {
        for (int i = tickOrder.length - 1; i > 0; i--) {
            int index = random(i);
            int tmp = tickOrder[i];
            tickOrder[i] = tickOrder[index];
            tickOrder[index] = tmp;
        }
    }

    public boolean swap(int x2, int y2) {
        int x1 = currentTileIndex % width;
        int y1 = currentTileIndex / width;
        return swap(x1, y1, x2, y2);
    }

    public boolean swap(int x1, int y1, int x2, int y2) {
        Cell c1 = getCell(x1, y1);
        Cell c2 = getCell(x2, y2);
        if (!(setCell(x1, y1, c2) && setCell(x2, y2, c1))) {
            setCell(x1, y1, c1);
            setCell(x2, y2, c2);
            return false;
        }
        return true;
    }

    public Cell randomNeighbor(int x, int y) {
        return randomNeighbor(x, y, FAIR_ROLL);
    }

    public Cell randomNeighbor(int x, int y, int[] roll) {
        int index = roll[random(roll.length)];
        return switch (index) {
            case 0 -> getCell(x, y - 1);
            case 1 -> getCell(x + 1, y);
            case 2 -> getCell(x, y + 1);
            case 3 -> getCell(x - 1, y);
            default -> throw new IllegalStateException("Unexpected value: " + index);
        };
    }

    public void update() {
        shuffleTickOrder();
//        int rainDrops = random(30);
//        for (int i = 0; i < rainDrops; i++) {
//            int x = random(width);
//            if (getCell(x, 0).isEmpty()) {
//                setCell(x, 0, new CellWater(10));
//            }
//        }
        for (int index : tickOrder) {
            currentTileIndex = index;
            Cell cell = cells[index];
            if (cell == EMPTY_CELL || cell == OUT_OF_BOUNDS_CELL) continue;
            int x = index % width;
            int y = index / width;
//            cells[index] = cell.tick(this, x, y);
            cell.tick(this, x, y);
        }
    }

    public void render(PApplet sketch) {
        sketch.push();
        sketch.noStroke();
        worldRenderOffsetX = (int) ((sketch.width - width * CELL_SIZE) / 2f);
        worldRenderOffsetY = (int) ((sketch.height - height * CELL_SIZE));
        sketch.translate(worldRenderOffsetX, worldRenderOffsetY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int index = i + j * width;
                Cell cell = cells[index];
                if (cell.isEmpty()) continue;
                int color = cell.color();
                int x = i * CELL_SIZE;
                int y = j * CELL_SIZE;
                sketch.push();
                sketch.fill(color);
                sketch.rect(x, y, CELL_SIZE, CELL_SIZE);
                sketch.pop();
            }
        }
        sketch.pop();
    }

    public boolean setCell(int x, int y, Cell cell) {
        if (cell.ofType(CELL_OUT_OF_BOUNDS)) return false;
        if (x >= width  || x < 0) return false;
        if (y >= height || y < 0) return false;
        int index = x + y * width;
        cells[index] = cell;
        return true;
    }

    public Cell getCell(int x, int y) {
        if (x >= width  || x < 0) return OUT_OF_BOUNDS_CELL;
        if (y >= height || y < 0) return OUT_OF_BOUNDS_CELL;
        int index = x + y * width;
        return cells[index];
    }
}
