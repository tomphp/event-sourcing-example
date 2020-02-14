package com.armakuni.event_sourcing_example.events;

import com.armakuni.event_sourcing_example.OrderEvent;
import com.armakuni.event_sourcing_example.ItemCode;

import java.util.Objects;

public class ItemAdded extends OrderEvent {
    public final ItemCode itemCode;

    public ItemAdded(ItemCode itemCode) {
        if (itemCode == null) {
            throw new IllegalArgumentException("itemCode cannot be null");
        }

        this.itemCode = itemCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemAdded itemAdded = (ItemAdded) o;
        return itemCode.equals(itemAdded.itemCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemCode);
    }
}
