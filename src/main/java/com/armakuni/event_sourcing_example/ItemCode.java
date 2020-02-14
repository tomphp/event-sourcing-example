package com.armakuni.event_sourcing_example;

import java.util.Objects;

public class ItemCode {
    private final String value;

    public ItemCode(String value) {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCode itemCode = (ItemCode) o;
        return value.equals(itemCode.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ItemCode{" +
                "value='" + value + '\'' +
                '}';
    }
}
