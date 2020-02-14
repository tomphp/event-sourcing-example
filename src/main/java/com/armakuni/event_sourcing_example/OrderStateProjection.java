package com.armakuni.event_sourcing_example;

import com.armakuni.event_sourcing_example.errors.OrderHasAlreadyBeenPlaced;
import com.armakuni.event_sourcing_example.errors.OrderHasNoItems;
import com.armakuni.event_sourcing_example.events.ItemAdded;
import com.armakuni.event_sourcing_example.events.OrderCreated;
import com.armakuni.event_sourcing_example.events.OrderPlaced;

import java.util.HashMap;

public class OrderStateProjection {
    private OrderID id;
    private HashMap<ItemCode, Quantity> lines = new HashMap<>();
    private boolean placed = false;

    OrderID getId() {
        return id;
    }

    HashMap<ItemCode, Quantity> getLines() {
        return lines;
    }

    boolean isPlaced() {
        return placed;
    }

    public void applyEvent(OrderCreated event) {
        id = event.id;
    }

    public void applyEvent(ItemAdded event) {
        if (placed) {
            throw new OrderHasAlreadyBeenPlaced(id);
        }

        var quantity = lines.containsKey(event.itemCode)
                ? lines.get(event.itemCode).increment()
                : Quantity.of(1);

        lines.put(event.itemCode, quantity);
    }

    public void applyEvent(OrderPlaced event) {
        if (placed) {
            throw new OrderHasAlreadyBeenPlaced(id);
        }

        if (lines.isEmpty()) {
            throw new OrderHasNoItems(id);
        }

        placed = true;
    }
}
