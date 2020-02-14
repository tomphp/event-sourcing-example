package com.armakuni.event_sourcing_example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {
    @Test
    void test_value_cannot_be_less_than_1() {
        assertThrows(IllegalArgumentException.class, () -> Quantity.of(0));
        assertThrows(IllegalArgumentException.class, () -> Quantity.of(-1));
    }
    @Test
    void test_are_equal_when_the_values_are_equal() {
        assertEquals(Quantity.of(1), Quantity.of(1));
    }

    @Test
    void test_are_not_equal_when_the_values_are_not_equal() {
        assertNotEquals(Quantity.of(1), Quantity.of(2));
    }

    @Test
    void test_increment_returns_a_new_quantity_with_the_amount_incremented() {
        assertEquals(Quantity.of(2), Quantity.of(1).increment());
        assertEquals(Quantity.of(3), Quantity.of(2).increment());
    }
}