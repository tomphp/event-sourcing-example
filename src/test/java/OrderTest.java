import com.armakuni.event_sourcing_example.*;
import com.armakuni.event_sourcing_example.errors.*;
import com.armakuni.event_sourcing_example.events.ItemAdded;
import com.armakuni.event_sourcing_example.events.OrderCreated;
import com.armakuni.event_sourcing_example.events.OrderPlaced;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OrderTest {
    private OrderID orderID = OrderID.generate();

    @Test
    void test_create_order_emits_OrderCreated() {
        var order = Order.create(orderID);
        assertEquals(Collections.singletonList(new OrderCreated(orderID)), order.retrieveNewEvents());
    }

    @Test
    void test_addItem_emits_an_item_added_event() {
        var order = Order.create(orderID);
        order.addItem(new ItemCode("ITEM99"));
        assertEquals(Arrays.asList(
                new OrderCreated(orderID),
                new ItemAdded(new ItemCode("ITEM99"))), order.retrieveNewEvents()
        );
    }

    @Test
    void test_an_order_cannot_be_placed_without_any_items() {
        var order = Order.create(orderID);
        OrderHasNoItems error = assertThrows(OrderHasNoItems.class, order::place);
        assertEquals("Order " + orderID.value + " has no items", error.getMessage());
    }

    @Test
    void test_place_emits_an_order_placed_event() {
        var order = Order.create(orderID);
        order.addItem(new ItemCode("ITEM99"));
        order.place();
        assertEquals(Arrays.asList(
                new OrderCreated(orderID),
                new ItemAdded(new ItemCode("ITEM99")),
                new OrderPlaced()
        ), order.retrieveNewEvents());
    }

    @Test
    void test_an_order_cannot_be_placed_twice() {
        var order = Order.create(orderID);
        order.addItem(new ItemCode("ITEM99"));
        order.place();
        OrderHasAlreadyBeenPlaced error = assertThrows(OrderHasAlreadyBeenPlaced.class, order::place);
        assertEquals("Order " + orderID.value + " has already been placed", error.getMessage());
    }

    @Test
    void test_an_item_cannot_be_added_after_the_order_has_been_placed() {
        var order = Order.create(orderID);
        order.addItem(new ItemCode("ITEM99"));
        order.place();
        OrderHasAlreadyBeenPlaced error = assertThrows(
                OrderHasAlreadyBeenPlaced.class,
                () -> order.addItem(new ItemCode("ITEM99"))
        );
        assertEquals("Order " + orderID.value + " has already been placed", error.getMessage());
    }

    @Test
    void test_an_order_cannot_be_printed_before_it_has_been_placed() {
        var order = Order.create(orderID);
        order.addItem(new ItemCode("ITEM99"));
        OrderPrinter orderPrinter = mock(OrderPrinter.class);
        OrderHasNotBeenPlaced error = assertThrows(OrderHasNotBeenPlaced.class, () -> order.print(orderPrinter));
        assertEquals("Order " + orderID.value + " has not been placed", error.getMessage());
    }

    @Test
    void test_print_prints_all_order_lines() {
        var orderPrinter = mock(OrderPrinter.class);
        var order = Order.create(orderID);
        order.addItem(new ItemCode("ITEM_1"));
        order.addItem(new ItemCode("ITEM_2"));
        order.addItem(new ItemCode("ITEM_1"));
        order.place();
        order.print(orderPrinter);

        verify(orderPrinter).print(Arrays.asList(
                new OrderLine(new ItemCode("ITEM_1"), Quantity.of(2)),
                new OrderLine(new ItemCode("ITEM_2"), Quantity.of(1))
        ));
    }

    @Test
    void test_recreates_from_a_stream_of_events() {
        var orderPrinter = mock(OrderPrinter.class);
        var order = Order.fromEventStream(new ArrayList<>(Arrays.asList(
                new OrderCreated(OrderID.generate()),
                new ItemAdded(new ItemCode("ITEM_1")),
                new ItemAdded(new ItemCode("ITEM_2")),
                new ItemAdded(new ItemCode("ITEM_1")),
                new OrderPlaced()
        )));
        order.print(orderPrinter);
        verify(orderPrinter).print(Arrays.asList(
                new OrderLine(new ItemCode("ITEM_1"), Quantity.of(2)),
                new OrderLine(new ItemCode("ITEM_2"), Quantity.of(1))
        ));
    }

    @Test
    void test_retrieve_events_do_not_contain_events_from_the_stream() {
        var order = Order.fromEventStream(new ArrayList<>(Arrays.asList(
                new OrderCreated(OrderID.generate()),
                new ItemAdded(new ItemCode("ITEM_1"))
        )));
        assertEquals(0, order.retrieveNewEvents().size());
    }

    @Test
    void test_retrieve_events_returns_new_events_from_a_loaded_event_stream() {
        var order = Order.fromEventStream(new ArrayList<>(Arrays.asList(
                new OrderCreated(OrderID.generate()),
                new ItemAdded(new ItemCode("ITEM_1"))
        )));
        order.place();
        assertEquals(Arrays.asList(new OrderPlaced()), order.retrieveNewEvents());
    }

    @Test
    void test_retrieve_events_throws_if_event_stream_is_empty() {
        assertThrows(EmptyEventStream.class, () -> Order.fromEventStream(new ArrayList<>()));
    }

    @Test
    void test_retrieve_events_throws_if_the_first_event_is_not_OrderCreated() {
        assertThrows(InvalidEvent.class, () -> Order.fromEventStream(new ArrayList<>(Collections.singletonList(
                new ItemAdded(new ItemCode("ITEM_1"))
        ))));
    }
}