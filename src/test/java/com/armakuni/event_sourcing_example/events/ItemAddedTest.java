package com.armakuni.event_sourcing_example.events;

import com.armakuni.event_sourcing_example.ItemCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemAddedTest {
    @Test
    void test_id_cannot_be_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            new ItemAdded(null);
        });
    }

    @Test
    void test_is_equal_if_itemCode_is_the_same() {
        assertEquals(new ItemAdded(new ItemCode("CODE")), new ItemAdded(new ItemCode("CODE")));
    }

    @Test
    void test_is_not_equal_if_itemCode_is_not_the_same() {
        assertNotEquals(new ItemAdded(new ItemCode("CODE1")), new ItemAdded(new ItemCode("CODE2")));
    }
}