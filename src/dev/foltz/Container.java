package dev.foltz;

public class Container {
    public final int capacity;
    private int amount;

    public Container(int capacity) {
        this(capacity, 0);
    }

    public Container(int capacity, int amount) {
        this.capacity = capacity;
        this.amount = amount;
    }

    public int amount() {
        return amount;
    }

    // Returns the amount removed.
    public int drain(int removing) {
        if (removing > amount) {
            int amountToRemove = amount;
            amount = 0;
            return amountToRemove;
        }
        else {
            amount -= removing;
            return removing;
        }
    }

    // Returns the amount accepted.
    public int fill(int receiving) {
        int limit = capacity - amount;
        if (receiving >= limit) {
            amount += limit;
            return limit;
        }
        else {
            amount += receiving;
            return receiving;
        }
    }
}
