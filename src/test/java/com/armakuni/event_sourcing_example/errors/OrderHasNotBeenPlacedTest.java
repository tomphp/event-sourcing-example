package com.armakuni.event_sourcing_example.errors;

import com.armakuni.event_sourcing_example.OrderID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderHasNotBeenPlacedTest {
    @Test
    void test_the_message_is_from_the_order_id() {
        var id = OrderID.generate();
        assertEquals(
                "Order " + id.value + " has already been placed",
                new OrderHasAlreadyBeenPlaced(id).getMessage()
        );
    }
}