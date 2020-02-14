package com.armakuni.event_sourcing_example;

import java.util.Objects;
import java.util.UUID;

public class OrderID {
    public final String value;

    public static OrderID generate() {
        return new OrderID(UUID.randomUUID().toString());
    }

    OrderID(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderID orderID = (OrderID) o;
        return value.equals(orderID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
