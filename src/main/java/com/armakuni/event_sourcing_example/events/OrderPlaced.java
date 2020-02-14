package com.armakuni.event_sourcing_example.events;

import com.armakuni.event_sourcing_example.OrderEvent;

import java.util.Objects;

public class OrderPlaced extends OrderEvent {
    @Override
    public boolean equals(Object o) {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}
