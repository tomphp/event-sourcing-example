package com.armakuni.event_sourcing_example.errors;

import com.armakuni.event_sourcing_example.OrderID;

public class OrderHasAlreadyBeenPlaced extends RuntimeException {
    public OrderHasAlreadyBeenPlaced(OrderID id) {
        super("Order " + id.value + " has already been placed");
    }
}
