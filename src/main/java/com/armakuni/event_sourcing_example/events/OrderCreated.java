package com.armakuni.event_sourcing_example.events;

import com.armakuni.event_sourcing_example.OrderEvent;
import com.armakuni.event_sourcing_example.OrderID;

import java.util.Objects;

public class OrderCreated extends OrderEvent {
    public final OrderID id;

    public OrderCreated(OrderID id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }

        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderCreated that = (OrderCreated) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
