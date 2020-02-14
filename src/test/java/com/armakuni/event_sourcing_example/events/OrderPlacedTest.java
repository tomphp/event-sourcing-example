package com.armakuni.event_sourcing_example.events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderPlacedTest {
    @Test
    void test_all_are_equal() {
        assertEquals(new OrderPlaced(), new OrderPlaced());
    }
}