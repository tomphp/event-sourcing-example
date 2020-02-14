package com.armakuni.event_sourcing_example;

import java.util.Objects;

public class Quantity {
    private final int value;

    public static Quantity of(int value) {
        return new Quantity(value);
    }

    private Quantity(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("value must be greater than 0");
        }

        this.value = value;
    }

    public Quantity increment() {
        return Quantity.of(value + 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "value=" + value +
                '}';
    }
}
