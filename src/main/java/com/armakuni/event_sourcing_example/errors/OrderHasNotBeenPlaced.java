package com.armakuni.event_sourcing_example.errors;

import com.armakuni.event_sourcing_example.OrderID;

public class OrderHasNotBeenPlaced extends RuntimeException {
    public OrderHasNotBeenPlaced(OrderID id) {
        super("Order " + id.value + " has not been placed");
    }
}
