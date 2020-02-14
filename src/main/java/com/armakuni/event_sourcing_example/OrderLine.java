package com.armakuni.event_sourcing_example;

import java.util.Objects;

public class OrderLine {
    private final ItemCode code;
    private final Quantity quantity;

    public OrderLine(ItemCode code, Quantity quantity) {
        if (code == null) {
            throw new IllegalArgumentException("code cannot be null");
        }

        if (quantity == null) {
            throw new IllegalArgumentException("quantity cannot be null");
        }
        this.code = code;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return code.equals(orderLine.code) &&
                quantity.equals(orderLine.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, quantity);
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "code=" + code +
                ", quantity=" + quantity +
                '}';
    }
}
