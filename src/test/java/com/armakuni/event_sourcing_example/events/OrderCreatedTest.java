package com.armakuni.event_sourcing_example.events;

import com.armakuni.event_sourcing_example.OrderID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderCreatedTest {
    @Test
    void test_id_cannot_be_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderCreated(null);
        });
    }

    @Test
    void test_is_equal_if_ID_is_the_same() {
        var orderID = OrderID.generate();
        assertEquals(new OrderCreated(orderID), new OrderCreated(orderID));
    }

    @Test
    void test_is_equal_if_IDs_are_different() {
        assertNotEquals(new OrderCreated(OrderID.generate()), new OrderCreated(OrderID.generate()));
    }
}