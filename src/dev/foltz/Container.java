package dev.foltz;

import static dev.foltz.Util.constrain;
import static dev.foltz.Util.max;

public class Container {
    public final int capacity;
    private int quantity;

    public Container(int capacity) {
        this(capacity, 0);
    }

    public Container(int capacity, int amount) {
        this.capacity = capacity;
        this.quantity = amount;
    }

    public int quantity() {
        return quantity;
    }

    // Attempt to fill the Container with an amount.
    // Returns the amount accepted.
    public int fill(int amount) {
        int limit = capacity - quantity;
        int accepted = constrain(amount, 0, limit);
        quantity += accepted;
        return accepted;
    }

    // Attempt to drain this Container of an amount.
    // Returns the amount drained.
    public int drain(int amount) {
        amount = constrain(amount, 0, quantity);
        quantity -= amount;
        return amount;
    }

    // Attempt to fill another Container with an amount
    // from this Container.
    // Returns the amount accepted (and hence the amount removed).
    public int push(Container other, int amount) {
        int pushed = other.fill(drain(amount));
        quantity -= pushed;
        return pushed;
    }
}
