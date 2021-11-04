package dev.foltz.cell;

import dev.foltz.Container;
import dev.foltz.Quanta;
import dev.foltz.Util;
import dev.foltz.World;

import java.util.HashMap;
import java.util.Map;

import static dev.foltz.Util.*;
import static dev.foltz.Util.EMPTY_CELL;

public abstract class Cell {
    public final TypeCell type;
    private final Map<Quanta, Container> containers;
    protected int flatness;
    protected float density = 1f;

    public Cell(TypeCell type, Map<Quanta, Integer> containerLimits) {
        this.type = type;
        containers = new HashMap<>();
        containerLimits.forEach((quanta, capacity) -> containers.put(quanta, new Container(capacity)));
    }

    public Cell tick(World world, int x, int y) { return this; }
    abstract public int color();

    protected Cell updateGravity(World world, int x, int y) {
        if (world.getCell(x, y + 1).isEmpty()) {
            world.setCell(x, y + 1, this);
            return EMPTY_CELL;
        }
        else if (world.getCell(x, y + 1).density < this.density && Util.random(1f) >= 0.95) {
            Cell cell = world.getCell(x, y + 1);
            if (cell == OUT_OF_BOUNDS_CELL) return this;
            if (Util.random(1f) < min(cell.density, density) / max(cell.density, density)) {
                return this;
            }
            world.setCell(x, y + 1, this);
            return cell;
        }
        else {
            int s = random(2) == 0 ? -1 : 1;
            boolean shouldFall = false;
            for (int i = 1; i <= flatness; i++) {
                if (!(world.getCell(x + i * s, y).density < density)) {
                    break;
                }
                else if (world.getCell(x + i*s, y + 1).isEmpty()
                    || (world.getCell(x + i*s, y + 1).density < density && Util.random(1f) > 0.97)) {
                    shouldFall = true;
                    break;
                }
            }


            if (shouldFall) {
                Cell other = world.getCell(x + s, y);
                if (other == OUT_OF_BOUNDS_CELL) return this;
                world.setCell(x + 1 * s, y, this);
                if (other.isEmpty()) {
                    return EMPTY_CELL;
                }
                else {
                    return other;
                }
            }
        }
        return this;
    }

    public int receiveQuanta(Quanta quanta, int amount) {
        if (!containers.containsKey(quanta)) return 0;
        return containers.get(quanta).fill(amount);
    }

    // Gives amount of quanta to other.
    // Returns the amount of quanta accepted by other.
    protected int giveQuanta(Cell other, Quanta quanta, int amount) {
        if (capacityOf(quanta) == 0) return 0;
        if (other.capacityOf(quanta) == 0) return 0;
        Container container = containers.get(quanta);
        int amountToGive = min(amount, container.amount());
        int accepted = other.receiveQuanta(quanta, amountToGive);
        return container.drain(accepted);
    }

    protected int removeQuanta(Quanta quanta, int amount) {
        if (!containers.containsKey(quanta)) return 0;
        return containers.get(quanta).drain(amount);
    }

    public int amountOf(Quanta quanta) {
        if (!containers.containsKey(quanta)) return 0;
        return containers.get(quanta).amount();
    }

    public int capacityOf(Quanta quanta) {
        if (!containers.containsKey(quanta)) return 0;
        return containers.get(quanta).capacity;
    }

    public boolean ofType(TypeCell type) { return this.type == type; }
    public boolean isEmpty() { return false; }
}

