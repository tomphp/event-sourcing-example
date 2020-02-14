package com.armakuni.event_sourcing_example.events;

import com.armakuni.event_sourcing_example.OrderEvent;
import com.armakuni.event_sourcing_example.OrderStateProjection;

import java.util.Objects;

public class OrderPlaced extends OrderEvent {
    @Override
    public void apply(OrderStateProjection projection) {
        projection.applyEvent(this);
    }

    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}
