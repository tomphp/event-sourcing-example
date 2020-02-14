package com.armakuni.event_sourcing_example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderIDTest {
    @Test
    void test_value_cannot_be_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            new OrderID(null);
        });
    }

    @Test
    void test_generate_creates_an_OrderID_with_a_UUID_value() {
        assertEquals(36, OrderID.generate().value.length());
    }

    @Test
    void test_IDs_are_equal_when_the_values_are_equal() {
        assertEquals(new OrderID("value"), new OrderID("value"));
    }

    @Test
    void test_generate_generates_different_ids_each_time() {
        assertNotEquals(OrderID.generate(), OrderID.generate());
    }
}