package com.armakuni.event_sourcing_example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemCodeTest {
    @Test
    void test_value_cannot_be_null() {
        assertThrows(IllegalArgumentException.class, () -> {
           new ItemCode(null);
        });
    }

    @Test
    void test_ItemCodes_are_equal_when_values_are_equal() {
        assertEquals(new ItemCode("CODE"), new ItemCode("CODE"));
    }

    @Test
    void test_ItemCodes_are_not_equal_when_values_are_not_equal() {
        assertNotEquals(new ItemCode("CODE1"), new ItemCode("CODE2"));
    }
}