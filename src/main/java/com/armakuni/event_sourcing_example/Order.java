package com.armakuni.event_sourcing_example;

import com.armakuni.event_sourcing_example.errors.*;
import com.armakuni.event_sourcing_example.events.ItemAdded;
import com.armakuni.event_sourcing_example.events.OrderCreated;
import com.armakuni.event_sourcing_example.events.OrderPlaced;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Order {
    private ArrayList<OrderEvent> newEvents = new ArrayList<>();

    private OrderStateProjection state = new OrderStateProjection();

    public static Order create(OrderID orderID) {
        OrderCreated orderCreated = new OrderCreated(orderID);
        ArrayList<OrderEvent> events = new ArrayList<>(Collections.singletonList(orderCreated));
        Order order = Order.fromEventStream(events);
        order.newEvents = events;
        return order;
    }

    public static Order fromEventStream(ArrayList<OrderEvent> events) {
        return new Order(events);
    }

    private Order(ArrayList<OrderEvent> events) {
        if (events.size() == 0) {
            throw new EmptyEventStream();
        }

        if (!(events.get(0) instanceof OrderCreated)) {
            throw new InvalidEvent("The first event must be OrderCreated");
        }

        for (OrderEvent event : events) {
            event.apply(state);
        }
    }

    public List<OrderEvent> retrieveNewEvents() {
        return newEvents;
    }

    public void addItem(ItemCode item) {
        OrderEvent event = new ItemAdded(item);
        event.apply(state);
        newEvents.add(event);
    }

    public void place() {
        OrderEvent event = new OrderPlaced();
        event.apply(state);
        newEvents.add(event);
    }

    public void print(OrderPrinter printer) {
        if (!state.isPlaced()) {
            throw new OrderHasNotBeenPlaced(state.getId());
        }

        var orderLines = state.getLines().entrySet().stream()
                .map((entry) -> new OrderLine(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        printer.print(orderLines);
    }

}
