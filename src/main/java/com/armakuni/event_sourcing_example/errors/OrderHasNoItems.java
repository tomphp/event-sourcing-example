package com.armakuni.event_sourcing_example.errors;

import com.armakuni.event_sourcing_example.OrderID;

public class OrderHasNoItems extends RuntimeException {
    public OrderHasNoItems(OrderID id) {
        super("Order " + id.value + " has no items");
    }
}
