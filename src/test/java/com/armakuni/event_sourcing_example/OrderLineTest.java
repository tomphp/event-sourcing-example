package com.armakuni.event_sourcing_example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest {
    @Test
    void test_item_code_cannot_be_null() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine(null, Quantity.of(1));
        });
        assertEquals("code cannot be null", error.getMessage());
    }

    @Test
    void test_quantity_cannot_be_null() {
        IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () -> {
            new OrderLine(new ItemCode("CODE"), null);
        });
        assertEquals("quantity cannot be null", error.getMessage());
    }

    @Test
    void test_are_equal_if_item_code_and_quantity_are_equal() {
        assertEquals(
                new OrderLine(new ItemCode("CODE"), Quantity.of(1)),
                new OrderLine(new ItemCode("CODE"), Quantity.of(1))
        );
    }

    @Test
    void test_are_not_equal_if_item_codes_are_not_equal() {
        assertNotEquals(
                new OrderLine(new ItemCode("CODE_1"), Quantity.of(1)),
                new OrderLine(new ItemCode("CODE_2"), Quantity.of(1))
        );
    }
}